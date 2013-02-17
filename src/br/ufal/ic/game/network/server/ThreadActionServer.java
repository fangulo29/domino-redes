package br.ufal.ic.game.network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

import br.ufal.ic.game.DominoPiece;
import br.ufal.ic.game.Game;
import br.ufal.ic.game.network.Message;
import br.ufal.ic.game.network.Message.Acao;
import br.ufal.ic.game.network.client.Player;

/* todos os requests dos clientes entrarão aqui! :) */
public class ThreadActionServer implements Runnable {

	private Scanner leitor;
	private PrintWriter escritor;
	private Socket socketCliente;
	private ObjectInputStream entradaStream;
	private ObjectOutputStream saidaStream;

	private final Gson gson;
	private final Server server;



	/**
	 * 
	 * @param server
	 * @param socketCliente
	 */
	public ThreadActionServer(Server server, Socket socketCliente) {

		this.server = server;
		this.socketCliente = socketCliente;
		this.gson = new Gson();


		try {

			/* operações nas entradas e saídas dos streams do cliente */
			leitor = new Scanner(socketCliente.getInputStream());
			escritor = new PrintWriter(socketCliente.getOutputStream());

			// saida do servidor com referência da saída do cliente
			saidaStream = new ObjectOutputStream(socketCliente.getOutputStream());
			
			entradaStream = new ObjectInputStream(socketCliente.getInputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendToAll(Message message) {

		System.err.println("SERVIDOR: encaminhando da mensagem para todos...");

		// será entregue a mensagem para cada jogador na lista de conectados no servidor
		for (Socket socketCliente : server.getListOfConnectedPlayers()) {

			try {
				// objeto de saída (escritor) com referência do socket do
				// cliente
				ObjectOutputStream objetoSaida = 
								new ObjectOutputStream(socketCliente.getOutputStream()) {

					/*
					 * qdo vc faz um .writeObject((Object)) ele salva um
					 * cabeçalho e dpois as informações.. então, se vc le apenas
					 * com o .readObject(), ele vai ler apenas a primeira linha
					 * e não vai reconhecer os próximos itens, sendo assim, para
					 * cada reader.readObject() é necessario que o reader receba
					 * um new new ObjectInputStream() OU resolvemos, alterando o
					 * método que edita o cabeçalho de leitura para que não faça
					 * nada
					 */

					@Override
					protected void writeStreamHeader() throws IOException {
						// nada de edição de cabeçalho
					}

				};

				objetoSaida.writeObject(message);
				// objetoSaida.flush();

				System.err.println("SERVIDOR: Escrevi o objeto mensagem: "
						+ message + " para o cliente " + socketCliente);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * RECEPÇÃO DAS MENSAGENS DOS CLIENTES!
	 */
	@Override
	public void run() {

		Message mensagemRecebida = null;

		while(true) {

			try {
				
				System.err.println("SERVIDOR: esperando ação de cliente...");
				mensagemRecebida = (Message)entradaStream.readObject();
				System.out.println("Servidor recebeu = " + mensagemRecebida);
			
			} catch (Exception e) {
				e.printStackTrace();
				mensagemRecebida = null;
			}

			
			
			
			
			if (mensagemRecebida.getAcao().equals(Acao.CHAT)) {
				sendToAll(mensagemRecebida);
			}

			
			
			
			
			
			/* adicionar/atualizar os nomes do jogadores no quadrinho do chat */
			if (mensagemRecebida.getAcao().equals(Acao.JOGADOR_ADICIONADO)) {

				String nomeJogadorAdicionado =
								mensagemRecebida.getNomeJogador();

				// adicionando na lista de nomes dos jogadores conectados no servidor
				server.addNameInListNameOfConnectedPlayers(nomeJogadorAdicionado);
				
				//lista de nomes dos players conectados no servidor 
				List<String> namesOfConnectedPlayersInServer = server.getNamesOfConnectedPlayers();

				//setando a lista dos nomes dos jogadores (serializada) em mensagem, que será entregue a todos
				mensagemRecebida.setNomesJogadoresConectadosGSON(gson.toJson(namesOfConnectedPlayersInServer));

				System.out.println("ENCAMINHANDO PARA TODOS : " + namesOfConnectedPlayersInServer);

				sendToAll(mensagemRecebida); //enviar para todos

			}

			
			
			
			
			
			/* mensagem de inicialização do jogo */
			if (mensagemRecebida.getAcao().equals(Acao.JOGO_INICIADO)) {

				List<Player> listaJogadoresDomino = new ArrayList<Player>();

				/*para cada jogador conectado no servidor (sockets), 
				 * adicionar um player para referência no game
				 */
				for (Socket socketJogador : server.getListOfConnectedPlayers()) {
					listaJogadoresDomino.add(new Player(socketJogador.getPort()));
				}

				// se tiver de dois até quatro players
				if (listaJogadoresDomino.size() >= 2 && listaJogadoresDomino.size() <= 4) {

					// iniciaremos o jogo (enviando as mensagens com as
					// peças sorteadas, para os jogadores num Map)
					Game newGame = new Game(listaJogadoresDomino);
					
					server.setGame(newGame);
					server.startGame();

					// populando com as peças SORTEADAS||| em cada jogador
					// num MAP com a porta de origem de cada jogador no servidor
	
					Map<Integer,List<DominoPiece>> mapPlayesCurrentPieces = 
												new HashMap<Integer, List<DominoPiece>>();

					for (Player jogador : listaJogadoresDomino) {
						// listaJogadoresDomino.add(new
						// Player(socketJogador.getPort())); //referência é
						// a porta de origem
						mapPlayesCurrentPieces.put(
								Integer.valueOf(jogador.getUserName()),
								jogador.getPieces()); // peças sorteadas

					}

					//atualizando o map de players e suas respectivas peças NO SERVIDOR
					server.setMapPlayesCurrentPieces(mapPlayesCurrentPieces);
					
					// mandando o Map serializado e retornando a mensagem
					// para todos

					mensagemRecebida.setMapJogadoresPecasCorrentesGSON(gson
							.toJson(mapPlayesCurrentPieces, HashMap.class));

					sendToAll(mensagemRecebida);

				}

				
				
				
				
				
				
			}

		}
	}

}
