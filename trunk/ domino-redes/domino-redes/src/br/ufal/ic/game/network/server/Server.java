package br.ufal.ic.game.network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

import br.ufal.ic.game.DominoPiece;
import br.ufal.ic.game.Game;
import br.ufal.ic.game.Player;
import br.ufal.ic.game.network.Message;
import br.ufal.ic.game.network.Message.Acao;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Server extends JFrame {

	/* informaçoes dos jogadores conectados e de status dos jogos */
	private ServerSocket server;
	private final int porta;

	private int numJogadoresConectados;
	private final Gson gson;
	private final List<String> nomesJogadoresConectados;
	private final List<Socket> listaJogadoresConectados;
	// private final boolean jogoIniciou = false;
	// private List<PrintWriter> escritores = new ArrayList<PrintWriter>();

	private Game jogo;
	private Map<Integer, List<DominoPiece>> mapJogadoresPecasCorrentes;

	private JButton jButtonIniciar;
	private JButton jButtonNovoJogador;
	private JLabel jLabelPorta;
	private JLabel jLabelServidor;
	private JTextField jTextFieldPorta;
	private JTextField jTextFieldStatus;

	public Server(int porta) {
		this.porta = porta;
		gson = new Gson();
		iniciarComponentesGUI();
		inicializarServidor(porta);

		numJogadoresConectados = 0;
		nomesJogadoresConectados = new ArrayList<String>();
		listaJogadoresConectados = new ArrayList<Socket>();
	}

	private void iniciarComponentesGUI() {
		// atualizarLookAndFeel();
		setTitle("Servidor DomiNóis!");
		jLabelServidor = new JLabel();
		jButtonIniciar = new JButton();
		jLabelPorta = new JLabel();
		jTextFieldPorta = new JTextField();
		jButtonNovoJogador = new JButton();
		jTextFieldStatus = new JTextField();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jLabelServidor.setFont(new java.awt.Font("Tahoma", 1, 18));
		jLabelServidor.setText("Servidor DomiNóis!");

		jButtonIniciar.setText("Iniciar");

		jButtonIniciar.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonIniciarActionPerformed(evt);
			}
		});

		jLabelPorta.setText("Porta:");
		jTextFieldPorta.setText("5000");
		jButtonNovoJogador.setText("+Jogador");
		jButtonNovoJogador
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jButtonNovoJogadorActionPerformed(evt);
					}
				});
		jTextFieldStatus.setBackground(new java.awt.Color(0, 0, 0));
		jTextFieldStatus.setFont(new java.awt.Font("Tahoma", 2, 15));
		jTextFieldStatus.setForeground(new java.awt.Color(0, 204, 204));
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(18, 18,
																		18)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabelPorta)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(
																										jTextFieldPorta,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										103,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addComponent(
																						jLabelServidor)))
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						jButtonIniciar,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						277,
																						Short.MAX_VALUE)
																				.addComponent(
																						jTextFieldStatus))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jButtonNovoJogador,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		98,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabelServidor)
								.addGap(42, 42, 42)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelPorta)
												.addComponent(
														jTextFieldPorta,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										40, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												false)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jTextFieldStatus)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jButtonIniciar,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		37,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(
														jButtonNovoJogador,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														111,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		pack();
	}

	private void jButtonIniciarActionPerformed(java.awt.event.ActionEvent evt) {
		JOptionPane.showMessageDialog(this, "Implementar iniciar");
	}

	private void jButtonNovoJogadorActionPerformed(
			java.awt.event.ActionEvent evt) {

		System.err.println("Temos " + numJogadoresConectados + " conectados!");

		if (numJogadoresConectados <= 4) {

			/* abertura de uma nova tela de jogador */
			// new Jogador();

			String nomeJogador = JOptionPane.showInputDialog("Nome do Jogador");

		} else {
			JOptionPane.showMessageDialog(this,
					"Já existem jogadores suficientes.");
		}
	}

	/* TELA */

	private void inicializarServidor(int porta) {
		try {
			server = new ServerSocket(porta);
			System.err.println("SERVIDOR: Iniciado na porta " + porta
					+ " . . .");

			/* enviando para o elemento do visual */
			jTextFieldStatus.setText("ON " + porta + "! ");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void buscarJogadores() {

		try {

			while (numJogadoresConectados < 4) {

				/* aguarda até que alguém se conecte */
				System.err
						.println("SERVIDOR: Aguardando conexão de jogador...");
				Socket jogadorSocket = server.accept();

				/* recebeu uma solicitação de cliente */
				System.err.println("SERVIDOR: Conexão Recebida de: "
						+ jogadorSocket.getInetAddress());

				/*
				 * thread aberta com um while para análise de requests dos
				 * clientes
				 */
				new Thread(new Listener(this, jogadorSocket)).start();

				/*
				 * adicionar o jogador na lista de jogadores do servidor
				 * (referência)
				 */
				listaJogadoresConectados.add(jogadorSocket);
				numJogadoresConectados++;

				JOptionPane
						.showMessageDialog(
								this,
								"Jogador "
										+ jogadorSocket.toString()
										+ " adicionado na lista de jogadores no servidor.");

				/* enviando para o elemento do visual */
				jTextFieldStatus.setText("ON " + porta + "! " + "\n"
						+ numJogadoresConectados + " jogadores conectados!");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void encaminharParaTodos(Message mensagem) {

		System.err.println("encaminharParaTodos(Mensagem mensagem)");
		System.err
				.println("SERVIDOR: Inicio do encaminhamento da mensagem para todos...");

		/* para cada jogador na lista de conectador será entregue a mensagem */
		for (Socket socketCliente : listaJogadoresConectados) {

			try {
				/*
				 * objeto de saída (escritor) com referência do socket de
				 * cliente
				 */
				ObjectOutputStream objetoSaida = new ObjectOutputStream(
						socketCliente.getOutputStream()) {

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

				objetoSaida.writeObject(mensagem);
				// objetoSaida.flush();

				System.err.println("SERVIDOR: Escrevi o objeto mensagem: "
						+ mensagem + " para o cliente " + socketCliente);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void escreverParaJogador(int index, String texto) {
		try {

			System.err
					.println("SERVIDOR: Enviando mensagem para jogador (jogadinha com a String)...");

			// é enviado para todos, só que vai ser tratado na tela do jogador

		} catch (Exception e) {
		}
	}

	// private void atualizarLookAndFeel() {
	// try {
	// for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
	// .getInstalledLookAndFeels()) {
	// if ("Nimbus".equals(info.getName())) {
	// javax.swing.UIManager.setLookAndFeel(info.getClassName());
	// break;
	// }
	// }
	// } catch (ClassNotFoundException ex) {
	// java.util.logging.Logger.getLogger(Jogador.class.getName()).log(
	// java.util.logging.Level.SEVERE, null, ex);
	// } catch (InstantiationException ex) {
	// java.util.logging.Logger.getLogger(Jogador.class.getName()).log(
	// java.util.logging.Level.SEVERE, null, ex);
	// } catch (IllegalAccessException ex) {
	// java.util.logging.Logger.getLogger(Jogador.class.getName()).log(
	// java.util.logging.Level.SEVERE, null, ex);
	// } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	// java.util.logging.Logger.getLogger(Jogador.class.getName()).log(
	// java.util.logging.Level.SEVERE, null, ex);
	// }
	// }

	/**
	 * @return the listaJogadoresConectados
	 */
	protected List<Socket> getListaJogadoresConectados() {
		return listaJogadoresConectados;
	}

	public static void main(String args[]) {
		Server s = new Server(5000);
		s.setVisible(true);
		s.buscarJogadores();

		// ServidorGamebuscarJogadores();
	}
}
