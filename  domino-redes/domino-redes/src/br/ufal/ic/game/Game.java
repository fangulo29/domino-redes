package br.ufal.ic.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufal.ic.game.Board.PiecePosition;
import br.ufal.ic.game.exception.InvalidFaceValueException;
import br.ufal.ic.game.exception.InvalidPieceToPlayException;

/**
 * 
 * @author Anderson Santos
 * 
 */
public class Game {

	// TODO Criar algo para gerenciar eventos/mensagens do jogo
	private List<DominoPiece> dominoes;
	private final List<Player> players;
	private final Board board;
	private DominoPiece firstPiece = null;

	/**
	 * 
	 * @param players
	 */
	public Game(List<Player> players) {
		// TODO Definir número de jogadores permitidos
		this.players = players;
		this.board = new Board();
	}

	/**
	 * @throws InvalidFaceValueException
	 * @throws InvalidPieceToPlayException
	 * 
	 */
	public void startGame() {
		createDominoPieces();

		System.out.println("Jogo iniciado com " + players.size()
				+ " jogadores.\n");

		// Separa 6 peças para cada jogador
		setRandomPiecesForPlayers();

		// Define qual jogador será o primeiro a jogar
		findPlayerToStart();

		try {
			// board.addPiece(firstPiece, PiecePosition.START_POSITION);
			board.addPiece(new DominoPiece(6, 6), PiecePosition.START_POSITION);
			board.addPiece(new DominoPiece(6, 2), PiecePosition.START_POSITION);
			board.addPiece(new DominoPiece(2, 3), PiecePosition.START_POSITION);
			board.addPiece(new DominoPiece(3, 5), PiecePosition.START_POSITION);
			board.addPiece(new DominoPiece(0, 6), PiecePosition.END_POSITION);
		} catch (InvalidPieceToPlayException | InvalidFaceValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Double-6 domino set
	 */
	private void createDominoPieces() {
		dominoes = new ArrayList<DominoPiece>();

		for (int i = 0; i <= 6; i++) {
			for (int j = i; j <= 6; j++) {
				try {
					dominoes.add(new DominoPiece(i, j));
				} catch (InvalidFaceValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Dominó double 6 inicializado.\n");
	}

	/**
	 * Set 6 pieces for each player randomly
	 */
	private void setRandomPiecesForPlayers() {
		for (int i = 0; i < players.size(); i++) {

			List<DominoPiece> pieces = new ArrayList<DominoPiece>();

			for (int j = 0; j < 6; j++) {
				int index = generateNumberBetween(0, 28 - (i * 6 + j) - 1);
				// int index = (int) (Math.random() * (28 - (i * 6 + j)));
				pieces.add(dominoes.remove(index));
			}
			players.get(i).setPieces(pieces);
			System.out.println("Peças distribuídas para o jogador "
					+ players.get(i).getUserName());
		}
		System.out.println("Peças distribuídas para os " + players.size()
				+ " jogadores.\n");
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private int generateNumberBetween(int start, int end) {
		Random random = new Random();

		if (start > end)
			throw new IllegalArgumentException(
					"start can't be bigger than end.");
		long range = (long) end - (long) start + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + start);

		return randomNumber;
	}

	/**
	 * 
	 * @return
	 */
	private int findPlayerToStart() {
		int currentPlayer = -1; // Jogar que irá iniciar o jogo
		int pieceSought = 6; // Bomba procurada, quem tiver irá iniciar o jogo
		firstPiece = null;

		do {
			for (Player p : players) {
				for (DominoPiece d : p.getPieces()) {
					if (d.getFace1() == pieceSought
							&& d.getFace2() == pieceSought) {
						currentPlayer = players.indexOf(p);
						firstPiece = d; // Jogador deverá jogar está peça
						break;
					}
				}
				if (currentPlayer != -1)
					break;
			}
			pieceSought--; // Procura bombas menores
		} while (currentPlayer == -1 && pieceSought >= 0);
		// Nesse caso não foram encontradas nenhuma bomba, jogador 0 poderá
		// iniciar com qualquer peça
		// TODO Verificar outros critérios
		if (currentPlayer == -1)
			currentPlayer = 0;

		System.out.println("Jogador "
				+ players.get(currentPlayer).getUserName()
				+ " inicia o jogo com a peça " + firstPiece.toString() + ".\n");
		return currentPlayer;
	}

	@Deprecated
	public static void main(String[] args) {

		List<Player> p = new ArrayList<Player>();

		p.add(new Player("P1"));
		p.add(new Player("P2"));
		p.add(new Player("P3"));
		p.add(new Player("P4"));

		Game g = new Game(p);

		g.startGame();

		for (int i = 0; i < p.size(); i++) {
			System.out.println(p.get(i).toString());
		}

		System.out.println();
		System.out.println(g.board.getRootPiece().toString());
		System.out.println();
		System.out.println(g.board.getDominoes().toString());

		// Game g = new Game(null);
		//
		// for (int i = 0; i < 4; i++) {
		//
		// for (int j = 0; j < 6; j++) {
		// int index = g.generateNumberBetween(0, 28 - (i * 6 + j));
		// System.out.println(index);
		// }
		// System.out.println();
		// }
		//
		// System.out.println("-------------------------------------------------");
		//
		// for (int i = 0; i < 4; i++) {
		//
		// for (int j = 0; j < 6; j++) {
		// int index = (int) (Math.random() * (28 - (i * 6 + j)));
		// System.out.println(index);
		// }
		// System.out.println();
		// }
	}
}
