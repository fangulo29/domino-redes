package br.ufal.ic.game.network.client;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

import br.ufal.ic.game.DominoPiece;
import br.ufal.ic.game.network.Message;
import br.ufal.ic.game.network.Message.Acao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Anderson Santos
 * @author Luciano Melo
 * 
 */
@SuppressWarnings("serial")
public class Player extends JFrame {

    /*
     * classe serializada, responsável por armazenar as informações/mensagens
     * que são trocadas entre cliente <-> servidor
     */
    private Message message;
    private String userName;
    private Socket socketCliente;
    private Gson gson;
    // private PrintWriter writer;
    // private Scanner reader;
    private ObjectOutputStream outToServerStream;
    private ObjectInputStream fromServerStream;

    /* DOMINO */
    private List<DominoPiece> myPieces;
    private List<DominoPiece> piecesOnTable;

    /* GUI elements */
    private JButton buttonConnect;
    private JButton buttonMakeTurn;
    private JButton buttonSendChat;
    private JButton buttonStartGame;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator;
    private JDesktopPane paneChat;
    private JLabel labelChat;
    private JLabel labelGameName;
    private JLabel labelMesa;
    private JLabel labelPieces;
    private JLabel labelPlayerName;
    private JLabel labelPort;
    private JLabel labelSide;
    private JLabel labelTurn;
    private JLabel labelUrl;
    private JRadioButton radioLeft;
    private JRadioButton radioRight;
    private JTextArea textAreaChat;
    private JTextArea textAreaPlayersNames;
    private JTextArea textAreaInformation;
    private JTextField textFieldMessage;
    private JTextField textFieldTable;
    private JTextField textFieldPieces;
    private JTextField textFieldPlayerName;
    private JTextField textFieldPort;
    private JTextField textFieldTurn;
    private JTextField textFieldUrl;

    /**/

    public Player() {
    }

    public Player(Integer codeName) {
	this.userName = String.valueOf(codeName);
    }

    public Player(String userName) {
	this.userName = userName;
    }

    public List<DominoPiece> getPieces() {
	return myPieces;
    }

    public int getPiecesPoints() {
	if (myPieces == null || myPieces.size() == 0)
	    return 0;
	else {
	    int points = 0;

	    for (DominoPiece d : myPieces)
		points += d.getFace1() + d.getFace2();
	    return points;
	}
    }

    public String getUserName() {
	return userName;
    }

    public void setPieces(List<DominoPiece> pieces) {
	this.myPieces = pieces;
    }

    // inicializador da interface
    public void startGUI() {
	iniciarComponentesGUI();
    }

    private void iniciarComponentesGUI() {

	setTitle("DomiNóis > Jogador: ");
	buttonConnect = new JButton();
	labelGameName = new JLabel();
	labelPlayerName = new JLabel();
	labelUrl = new JLabel();
	labelPort = new JLabel();
	textFieldPlayerName = new JTextField();
	textFieldUrl = new JTextField();
	textFieldPort = new JTextField();
	jSeparator = new JSeparator();
	jPanel1 = new JPanel();
	labelMesa = new JLabel();
	textFieldTable = new JTextField();
	labelPieces = new JLabel();
	textFieldPieces = new JTextField();
	labelTurn = new JLabel();
	textFieldTurn = new JTextField();
	labelSide = new JLabel();
	radioLeft = new JRadioButton();
	radioRight = new JRadioButton();
	buttonMakeTurn = new JButton();
	paneChat = new JDesktopPane();
	labelChat = new JLabel();
	jScrollPane1 = new JScrollPane();
	textAreaChat = new JTextArea();
	buttonSendChat = new JButton();
	textFieldMessage = new JTextField();
	jScrollPane2 = new JScrollPane();
	textAreaPlayersNames = new JTextArea();
	buttonStartGame = new JButton();
	jScrollPane3 = new JScrollPane();
	textAreaInformation = new JTextArea();

	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	/* caso a janela seja fechada */
	this.addWindowListener(new java.awt.event.WindowAdapter() {
	    @Override
	    public void windowClosing(java.awt.event.WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
		    int selectedOption = JOptionPane.showConfirmDialog(null,
			    "Deseja Sair Realmente?", "Sistema informa:",
			    JOptionPane.YES_NO_OPTION);
		    if (selectedOption == JOptionPane.YES_OPTION) {

			try {
			    if (socketCliente != null) {
				socketCliente.close();
			    }
			} catch (IOException ex) {
			}

			System.exit(0);

		    }
		}
	    }
	});

	// TODO falta desacoplar Comandos
	// TODO falta desacoplar Comandos
	// TODO falta desacoplar Comandos

	buttonConnect.setText("Conectar");
	buttonConnect.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		buttonConnectActionPerformed(evt);
	    }
	});

	labelGameName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
	labelGameName.setText("DomiNóis");

	labelPlayerName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelPlayerName.setText("Nome:");

	labelUrl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelUrl.setText("URL:");

	labelPort.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelPort.setText("Porta:");

	textFieldUrl.setText("127.0.0.1");

	textFieldPort.setText("5000");

	jPanel1.setBorder(BorderFactory
		.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

	labelMesa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelMesa.setText("Mesa:");

	textFieldTable.setEditable(false);
	textFieldTable.setBackground(new java.awt.Color(46, 142, 46));
	textFieldTable.setForeground(new java.awt.Color(255, 255, 255));

	labelPieces.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelPieces.setText("Peças disponíveis:");

	textFieldPieces.setEditable(false);

	labelTurn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelTurn.setText("Jogada:");

	textFieldTurn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

	labelSide.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
	labelSide.setText("Lado:");

	radioLeft.setText("Esquerdo");
	radioLeft.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		radioLeftActionPerformed(evt);
	    }
	});

	radioRight.setText("Direito");
	radioRight.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		radioRightActionPerformed(evt);
	    }
	});

	buttonMakeTurn.setText("Fazer Jogada!");
	buttonMakeTurn.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		buttonMakeTurnActionPerformed(evt);
	    }
	});

	GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										labelPieces)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										textFieldPieces))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										labelMesa)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										textFieldTable))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										labelTurn)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										textFieldTurn,
										GroupLayout.PREFERRED_SIZE,
										130,
										GroupLayout.PREFERRED_SIZE)
									.addGap(18,
										18,
										18)
									.addComponent(
										labelSide)
									.addGap(18,
										18,
										18)
									.addComponent(
										radioLeft)
									.addGap(18,
										18,
										18)
									.addComponent(
										radioRight)
									.addGap(18,
										18,
										18)
									.addComponent(
										buttonMakeTurn,
										GroupLayout.DEFAULT_SIZE,
										104,
										Short.MAX_VALUE)))
					.addContainerGap()));
	jPanel1Layout
		.setVerticalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(labelMesa)
							.addComponent(
								textFieldTable,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED,
						24, Short.MAX_VALUE)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								labelPieces)
							.addComponent(
								textFieldPieces,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addGap(25,
										25,
										25)
									.addGroup(
										jPanel1Layout
											.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
											.addComponent(
												labelTurn)
											.addComponent(
												textFieldTurn,
												GroupLayout.PREFERRED_SIZE,
												35,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												labelSide)
											.addComponent(
												radioLeft)
											.addComponent(
												radioRight)))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										buttonMakeTurn,
										GroupLayout.PREFERRED_SIZE,
										65,
										GroupLayout.PREFERRED_SIZE)))
					.addContainerGap()));

	labelChat.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
	labelChat.setForeground(new java.awt.Color(255, 255, 255));
	labelChat.setText("ChaT:");
	labelChat.setBounds(20, 10, 110, 29);
	paneChat.add(labelChat, JLayeredPane.DEFAULT_LAYER);

	jScrollPane1
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	textAreaChat.setEditable(false);
	textAreaChat.setColumns(20);
	textAreaChat.setRows(5);
	textAreaChat.setWrapStyleWord(true);

	// autoscroll
	DefaultCaret caret = (DefaultCaret) textAreaChat.getCaret();
	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

	jScrollPane1.setViewportView(textAreaChat);

	jScrollPane1.setBounds(20, 50, 320, 200);
	paneChat.add(jScrollPane1, JLayeredPane.DEFAULT_LAYER);

	buttonSendChat.setText("Enviar");
	buttonSendChat.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		buttonSendChatActionPerformed(evt);
	    }
	});
	buttonSendChat.setBounds(400, 260, 90, 40);
	paneChat.add(buttonSendChat, JLayeredPane.DEFAULT_LAYER);
	textFieldMessage.setBounds(20, 270, 370, 30);
	paneChat.add(textFieldMessage, JLayeredPane.DEFAULT_LAYER);

	jScrollPane2
		.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	jScrollPane2.setEnabled(false);

	textAreaPlayersNames.setEditable(false);
	textAreaPlayersNames.setColumns(20);
	textAreaPlayersNames.setRows(5);
	jScrollPane2.setViewportView(textAreaPlayersNames);

	jScrollPane2.setBounds(354, 50, 140, 200);
	paneChat.add(jScrollPane2, JLayeredPane.DEFAULT_LAYER);

	buttonStartGame.setText("Iniciar Jogo!");
	buttonStartGame.addActionListener(new java.awt.event.ActionListener() {
	    @Override
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		buttonStartGameActionPerformed(evt);
	    }
	});

	textAreaInformation.setEditable(false);
	textAreaInformation.setBackground(new java.awt.Color(58, 110, 165));
	textAreaInformation.setColumns(20);
	textAreaInformation.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
	textAreaInformation.setForeground(new java.awt.Color(255, 255, 255));
	textAreaInformation.setRows(5);
	textAreaInformation.setBorder(null);
	jScrollPane3.setViewportView(textAreaInformation);

	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(labelGameName)
						.addGroup(
							layout.createSequentialGroup()
								.addGroup(
									layout.createParallelGroup(
										GroupLayout.Alignment.LEADING,
										false)
										.addComponent(
											jPanel1,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)
										.addComponent(
											jSeparator)
										.addGroup(
											GroupLayout.Alignment.TRAILING,
											layout.createSequentialGroup()
												.addGroup(
													layout.createParallelGroup(
														GroupLayout.Alignment.LEADING)
														.addGroup(
															layout.createSequentialGroup()
																.addComponent(
																	labelPlayerName)
																.addPreferredGap(
																	LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																	textFieldPlayerName)
																.addGap(18,
																	18,
																	18)
																.addComponent(
																	labelUrl)
																.addPreferredGap(
																	LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																	textFieldUrl,
																	GroupLayout.PREFERRED_SIZE,
																	138,
																	GroupLayout.PREFERRED_SIZE))
														.addGroup(
															layout.createSequentialGroup()
																.addComponent(
																	buttonConnect)
																.addPreferredGap(
																	LayoutStyle.ComponentPlacement.RELATED,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE)))
												.addGroup(
													layout.createParallelGroup(
														GroupLayout.Alignment.LEADING,
														false)
														.addGroup(
															layout.createSequentialGroup()
																.addGap(10,
																	10,
																	10)
																.addComponent(
																	buttonStartGame,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	Short.MAX_VALUE))
														.addGroup(
															GroupLayout.Alignment.TRAILING,
															layout.createSequentialGroup()
																.addComponent(
																	labelPort)
																.addPreferredGap(
																	LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																	textFieldPort,
																	GroupLayout.PREFERRED_SIZE,
																	50,
																	GroupLayout.PREFERRED_SIZE))))
										.addComponent(
											jScrollPane3))
								.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									paneChat,
									GroupLayout.PREFERRED_SIZE,
									512,
									GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE)));
	layout.setVerticalGroup(layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(labelGameName)
				.addGap(18, 18, 18)
				.addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addGroup(
							layout.createSequentialGroup()
								.addGroup(
									layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(
											labelPlayerName)
										.addComponent(
											textFieldPlayerName,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(
											textFieldUrl,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
										.addComponent(
											labelUrl)
										.addComponent(
											labelPort)
										.addComponent(
											textFieldPort,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
									layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(
											buttonConnect)
										.addComponent(
											buttonStartGame))
								.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									jScrollPane3,
									GroupLayout.PREFERRED_SIZE,
									68,
									GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
									LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(
									jSeparator,
									GroupLayout.PREFERRED_SIZE,
									10,
									GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									jPanel1,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
								.addGap(0,
									0,
									Short.MAX_VALUE))
						.addComponent(paneChat))
				.addContainerGap()));
	pack();
	textFieldPlayerName.setText(userName);
	setVisible(true);

    }

    private void radioLeftActionPerformed(java.awt.event.ActionEvent evt) {
	radioRight.setSelected(false);
    }

    private void radioRightActionPerformed(java.awt.event.ActionEvent evt) {
	radioLeft.setSelected(false);
    }

    private void buttonConnectActionPerformed(java.awt.event.ActionEvent evt) {

	try {

	    /* tentativa de conexão com o servidor */
	    conectarNoServidor();

	    /* montando mensagem de adição de jogador para o servidor */
	    message.setAcao(Acao.JOGADOR_ADICIONADO);
	    message.setNomeJogador(userName);

	    /* enviando a mensagem serializada, para o servidor */
	    outToServerStream.writeObject(message);
	    outToServerStream.flush();

	} catch (Exception e) {
	    e.printStackTrace();

	} finally {
	    labelGameName.setText("DomiNóis (Connected)");
	    textFieldPlayerName.setEnabled(false);
	    textFieldPort.setEnabled(false);
	    textFieldUrl.setEnabled(false);
	}
    }

    // fazer jogada
    private void buttonMakeTurnActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void buttonStartGameActionPerformed(java.awt.event.ActionEvent evt) {

	try {

	    System.out.println("Jogo de domino Iniciado");

	    message.setAcao(Acao.JOGO_INICIADO);
	    message.setNomeJogador(userName);

	    /* enviando a mensagem para o servidor */
	    outToServerStream.writeObject(message);
	    outToServerStream.flush();

	    // System.out.println("JOGADOR " + nome +
	    // ": enviou a mensagem de chat para o servidor: " +
	    // mensagem.getMensagemChat());

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    /* troca de mensagens por chat de bate-papo */
    private void buttonSendChatActionPerformed(java.awt.event.ActionEvent evt) {

	if (!textFieldMessage.getText().equals("")) {

	    /*
	     * montando a mensagem a ser entregue [nome do jogador que envia e
	     * mensagem]
	     */
	    message.setAcao(Acao.CHAT); // acao que definirá o que está
					// acontecendo nessa troca de mensagem
	    message.setNomeJogador(userName);
	    message.setMensagemChat(textFieldMessage.getText());

	    textFieldMessage.setText("");
	    textFieldMessage.requestFocus();

	    try {

		/* enviando a mensagem serializada, para o servidor */
		outToServerStream.writeObject(message);
		outToServerStream.flush();

		System.out.println("JOGADOR " + userName
			+ ": enviou a mensagem de chat para o servidor: "
			+ message.getMensagemChat());

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    private void conectarNoServidor() {

	try {

	    userName = textFieldPlayerName.getText();

	    /* nome do jogador no título da janela */
	    setTitle("DomiNóis > Jogador: " + userName);

	    String url = textFieldUrl.getText();
	    Integer porta = Integer.parseInt(textFieldPort.getText());

	    /* ponto de conexão entre o servidor e cliente (socket) */
	    socketCliente = new Socket(url, porta);
	    JOptionPane.showMessageDialog(this, "Jogador " + userName
		    + " conectado no Servidor DomiNóis!");

	    /* streams de mensagens (cliente <-> servidor) */
	    inicializarFluxosMensagens();

	    new Thread(new ThreadActionPlayer()).start(); // TODO se conseguir
							  // desacoplar

	    buttonConnect.setEnabled(false);

	} catch (Exception e) {
	    JOptionPane.showMessageDialog(this,
		    "Erro ao conectar no servidor: " + e);
	}
    }

    /*
     * responsável por fazer a conexão do escritor e leitor do cliente, com o
     * comunicador e receptor do servidor
     */
    private void inicializarFluxosMensagens() {
	try {

	    /* inicializar mensagem serializada que será passada */
	    /* guardar informacoes do jogador na "carta" de mensagem */
	    message = new Message();
	    message.setNomeJogador(userName);
	    message.setPortaJogador(socketCliente.getPort());

	    gson = new Gson(); // serializador

	    // writer = new PrintWriter(socketCliente.getOutputStream());
	    // reader = new Scanner(socketCliente.getInputStream());

	    outToServerStream = new ObjectOutputStream(
		    socketCliente.getOutputStream());
	    fromServerStream = new ObjectInputStream(
		    socketCliente.getInputStream());

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /*
     * enquanto a tela de jogador estiver aberta, uma thread com um while fica
     * monitorando as alterações, em paralelo, na entrada do stream do cliente
     * (mensagens virão da saída do servidor)
     */
    private class ThreadActionPlayer implements Runnable {

	@SuppressWarnings("unchecked")
	@Override
	public void run() {

	    /*
	     * monitorando quando uma mensagem na porta de entrada for recebida
	     * do servidor (loop de análise)
	     */
	    while (true) {

		try {

		    /* recebendo a mensagem do servidor */
		    message = (Message) fromServerStream.readObject();

		    System.out.println("JOGADOR " + userName
			    + ": Recebi mensagem do servidor: " + message);

		} catch (Exception e) {
		    e.printStackTrace();
		}

		// LOGICAS
		/*
		 * análise de qual ação tomar de acordo com a "carta" vinda do
		 * servidor
		 */

		if (message.getAcao().equals(Acao.CHAT)) {
		    addMessageInChat(message);
		}

		/*
		 * atualizando a área dos nomes de jogadores jogadores (quando
		 * adicionado)
		 */
		if (message.getAcao().equals(Acao.JOGADOR_ADICIONADO)) {

		    clearTextAreaPlayersNames();

		    List<String> nomesJogadoresConectados = gson.fromJson(
			    message.getNomesJogadoresConectadosGSON(),
			    ArrayList.class);

		    // para cada nome vindo da mensagem
		    Collections.sort(nomesJogadoresConectados); // ordem
								// alfabética
		    for (String nomeJogador : nomesJogadoresConectados) {
			addPlayerNameInTextAreaPlayersNames(nomeJogador); // adicionar
									  // no
									  // elemento
									  // GUI
		    }
		}

		if (message.getAcao().equals(Acao.JOGO_INICIADO)) {

		    try {

			JOptionPane.showMessageDialog(
				null,
				"O jogo foi iniciado pelo jogador "
					+ message.getNomeJogador());

			// carregar peças sorteadas vindo da mensagem no GUI

			// desserializando o HashMap passado via Mensagem (do
			// servidor)
			Map<Integer, List<DominoPiece>> mapJogadoresPecas = deserializeMap(message
				.getMapJogadoresPecasCorrentesGSON());

			// setando as peças de acordo com a porta local do
			// cliente
			Integer portaLocal = socketCliente.getLocalPort();

			List<DominoPiece> pecasCorrentes = mapJogadoresPecas
				.get(portaLocal);

			setTextFieldPieces(pecasCorrentes.toString()); // elemento
								       // GUI

		    } catch (Exception e) {
			e.printStackTrace();
		    }

		}

	    }
	}

	/* ACTIONS úteis para a relação com a Thread de ações */

	// CHAT
	private void addMessageInChat(Message message) {
	    textAreaChat.append(message.getNomeJogador() + ": "
		    + message.getMensagemChat() + "\n");
	}

	// JOGADOR ADICIONADO
	private void clearTextAreaPlayersNames() {
	    textAreaPlayersNames.setText("");
	}

	private void addPlayerNameInTextAreaPlayersNames(String playerName) {
	    textAreaPlayersNames.append(playerName + "\n");
	}

	// JOGO INICIADO

	private void setTextFieldPieces(String currentPieces) {
	    textFieldPieces.setText(currentPieces);
	}

	private Map<Integer, List<DominoPiece>> deserializeMap(
		String serializableString) {
	    java.lang.reflect.Type mapType = new TypeToken<Map<Integer, List<DominoPiece>>>() {
	    }.getType();
	    return gson.fromJson(serializableString, mapType);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (!(obj instanceof Player))
		return false;
	    Player other = (Player) obj;
	    if (myPieces == null) {
		if (other.myPieces != null)
		    return false;
	    } else if (!myPieces.equals(other.myPieces))
		return false;
	    if (userName == null) {
		if (other.userName != null)
		    return false;
	    } else if (!userName.equals(other.userName))
		return false;
	    return true;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
		    + (myPieces == null ? 0 : myPieces.hashCode());
	    result = prime * result
		    + (userName == null ? 0 : userName.hashCode());
	    return result;
	}

	@Override
	public String toString() {
	    return "Jogador: " + userName + ", peças: " + myPieces.toString();
	}

    }
}
