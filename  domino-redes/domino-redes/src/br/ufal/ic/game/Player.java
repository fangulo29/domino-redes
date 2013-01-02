package br.ufal.ic.game;

import java.util.List;

/**
 * 
 * @author Anderson Santos
 * 
 */
public class Player {

	// TODO Ver a questão da senha do usuário
	private final String userName;
	private List<Domino> pieces;

	/**
	 * 
	 * @param userName
	 * @param pieces
	 */
	public Player(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the pieces
	 */
	public List<Domino> getPieces() {
		return pieces;
	}

	/**
	 * @param pieces
	 *            the pieces to set
	 */
	public void setPieces(List<Domino> pieces) {
		this.pieces = pieces;
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

			for (Domino d : pieces) {
				points += d.getFace1() + d.getFace2();
			}
			return points;
		}
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
		result = prime * result + ((pieces == null) ? 0 : pieces.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player) obj;
		if (pieces == null) {
			if (other.pieces != null) {
				return false;
			}
		} else if (!pieces.equals(other.pieces)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
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
