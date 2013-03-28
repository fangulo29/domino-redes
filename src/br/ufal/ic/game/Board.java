package br.ufal.ic.game;

import java.util.ArrayList;
import java.util.List;

import br.ufal.ic.game.exception.InvalidPieceToPlayException;

/**
 * 
 * @author Anderson Santos
 * @author Luciano Melo
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

    // Primeira peça jogada
    private static DominoPiece rootPiece = null;

    private List<DominoPiece> dominoes = null;

    /**
	 * 
	 */
    public Board() {
	dominoes = new ArrayList<DominoPiece>();
    }

    /**
     * 
     * @param piece
     * @param position
     * @throws InvalidPieceToPlayException
     */
    public void addPiece(DominoPiece piece, PiecePosition position)
	    throws InvalidPieceToPlayException {
	if (rootPiece == null) {
	    rootPiece = piece;
	    dominoes.add(piece);
	    return;
	}

	if (!isAValidPieceToPlay(piece, position))
	    throw new InvalidPieceToPlayException();

	DominoPiece d = null;

	// Pega a peça do lugar onde será feita a jogada
	d = position.equals(PiecePosition.START_POSITION) ? this
		.getStartPiece() : this.getEndPiece();

	// Verifica em qual face ainda pode ser feita uma jogada e se a peça
	// jogada é válida
	if (d.getFaceLink1() == null
		&& (piece.getFace1() == d.getFace1() || piece.getFace2() == d
			.getFace1())) {

	    d.setFaceLink1(piece);

	    if (piece.getFace1() == d.getFace1()) {
		piece.setFaceLink1(d);
		piece.swapFaces(); // Arruma peça na mesa
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
		piece.swapFaces(); // Arruma peça na mesa
	    }
	}

	// Coloca a peça no seu respectivo local
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
    private boolean isAValidPieceToPlay(DominoPiece piece,
	    PiecePosition position) {
	// Mesa está vazia
	if (dominoes == null || dominoes.size() == 0)
	    return true;

	else {
	    DominoPiece d = null;

	    // Pega a peça do lugar onde será feita a jogada
	    d = position.equals(PiecePosition.START_POSITION) ? this
		    .getStartPiece() : this.getEndPiece();

	    // Valida se a peça jogada é válida
	    if (d.getFaceLink1() == null
		    && (piece.getFace1() == d.getFace1() || piece.getFace2() == d
			    .getFace2())
		    || d.getFaceLink2() == null
		    && (piece.getFace1() == d.getFace2() || piece.getFace2() == d
			    .getFace1()))
		return true;

	    else
		return false;
	}
    }

    /**
     * 
     * @return the <i>piece</i> from the <b>start</b>
     */
    private DominoPiece getStartPiece() {
	return dominoes.get(0);
    }

    /**
     * 
     * @return the <i>piece</i> from the <b>end</b>
     */
    private DominoPiece getEndPiece() {
	return dominoes.get(dominoes.size() - 1);
    }

    /**
     * @return the rootPiece
     */
    public DominoPiece getRootPiece() {
	return rootPiece;
    }

    /**
     * @return the dominoes
     */
    public List<DominoPiece> getDominoes() {
	return dominoes;
    }
}
