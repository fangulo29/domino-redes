package br.ufal.ic.game;

import java.util.ArrayList;
import java.util.List;

import br.ufal.ic.game.exception.InvalidPieceToPlayException;

/**
 * 
 * @author Anderson Santos
 * 
 */
public class Board {

	/**
	 * Inner class
	 * 
	 * @author Anderson Santos
	 * 
	 */
	public enum PiecePosition {
		START_POSITION, END_POSITION
	}

	// Primeira pe�a jogada
	private static Domino rootPiece = null;

	private List<Domino> dominoes = null;

	/**
	 * 
	 */
	public Board() {
		dominoes = new ArrayList<Domino>();
	}

	/**
	 * 
	 * @param piece
	 * @param position
	 * @throws InvalidPieceToPlayException
	 */
	public void addPiece(Domino piece, PiecePosition position)
			throws InvalidPieceToPlayException {
		if (rootPiece == null) {
			rootPiece = piece;
			dominoes.add(piece);
			return;
		}

		if (!isAValidPieceToPlay(piece, position))
			throw new InvalidPieceToPlayException();

		Domino d = null;

		// Pega a pe�a do lugar onde ser� feita a jogada
		d = (position.equals(PiecePosition.START_POSITION)) ? this
				.getStartPiece() : this.getEndPiece();

		// Verifica em qual face ainda pode ser feita uma jogada e se a pe�a
		// jogada � v�lida
		if (d.getFaceLink1() == null
				&& (piece.getFace1() == d.getFace1() || piece.getFace2() == d
						.getFace1())) {

			d.setFaceLink1(piece);

			if (piece.getFace1() == d.getFace1()) {
				piece.setFaceLink1(d);
				piece.swapFaces(); // Arruma pe�a na mesa
			} else
				piece.setFaceLink2(d);
		}

		else if (d.getFaceLink2() == null
				&& (piece.getFace1() == d.getFace2() || piece.getFace2() == d
						.getFace2())) {

			d.setFaceLink2(piece);

			if (piece.getFace1() == d.getFace2())
				piece.setFaceLink1(d);
			else {
				piece.setFaceLink2(d);
				piece.swapFaces(); // Arruma pe�a na mesa
			}
		}

		// Coloca a pe�a no seu respectivo local
		if (position.equals(PiecePosition.START_POSITION))
			dominoes.add(0, piece);
		else
			dominoes.add(piece);
	}

	/**
	 * 
	 * @param piece
	 * @param position
	 * @return
	 */
	private boolean isAValidPieceToPlay(Domino piece, PiecePosition position) {
		// Mesa est� vazia
		if (dominoes == null || dominoes.size() == 0)
			return true;

		else {
			Domino d = null;

			// Pega a pe�a do lugar onde ser� feita a jogada
			d = (position.equals(PiecePosition.START_POSITION)) ? this
					.getStartPiece() : this.getEndPiece();

			// Valida se a pe�a jogada � v�lida
			if ((d.getFaceLink1() == null && (piece.getFace1() == d.getFace1() || piece
					.getFace2() == d.getFace2()))
					|| (d.getFaceLink2() == null && (piece.getFace1() == d
							.getFace2() || piece.getFace2() == d.getFace1())))
				return true;

			else
				return false;
		}
	}

	/**
	 * 
	 * @return the <i>piece</i> from the <b>start</b>
	 */
	private Domino getStartPiece() {
		return dominoes.get(0);
	}

	/**
	 * 
	 * @return the <i>piece</i> from the <b>end</b>
	 */
	private Domino getEndPiece() {
		return dominoes.get(dominoes.size() - 1);
	}

	/**
	 * @return the rootPiece
	 */
	public Domino getRootPiece() {
		return rootPiece;
	}

	/**
	 * @return the dominoes
	 */
	public List<Domino> getDominoes() {
		return dominoes;
	}
}
