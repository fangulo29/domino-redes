package br.ufal.ic.game.network.client;

import java.util.List;

import javax.swing.JFrame;

import br.ufal.ic.game.DominoPiece;

/**
 * 
 * @author Anderson Santos
 * 
 */
public class Player extends JFrame {

	// TODO Ver a questão da senha do usuário
	private final String userName;
	private List<DominoPiece> pieces;

	/**
	 * 
	 * @param codeName
	 */
	public Player(Integer codeName) {
		this.userName = String.valueOf(codeName);
	}

	/**
	 * 
	 * @param userName
	 * @param pieces
	 */
	public Player(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (pieces == null) {
			if (other.pieces != null)
				return false;
		} else if (!pieces.equals(other.pieces))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	/**
	 * @return the pieces
	 */
	public List<DominoPiece> getPieces() {
		return pieces;
	}

	/**
	 * 
	 * @return
	 */
	public int getPiecesPoints() {
		if (pieces == null || pieces.size() == 0)
			return 0;
		else {
			int points = 0;

			for (DominoPiece d : pieces)
				points += d.getFace1() + d.getFace2();
			return points;
		}
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (pieces == null ? 0 : pieces.hashCode());
		result = prime * result
				+ (userName == null ? 0 : userName.hashCode());
		return result;
	}

	/**
	 * @param pieces
	 *            the pieces to set
	 */
	public void setPieces(List<DominoPiece> pieces) {
		this.pieces = pieces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Jogador: " + userName + ", peças: " + pieces.toString();
	}
}
