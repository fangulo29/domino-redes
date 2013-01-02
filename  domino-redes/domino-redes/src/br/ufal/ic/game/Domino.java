package br.ufal.ic.game;

import br.ufal.ic.game.exception.InvalidFaceValueException;

/**
 * 
 * @author Anderson Santos
 * 
 */
public class Domino {

	private int face1;
	private int face2;

	private Domino faceLink1 = null;
	private Domino faceLink2 = null;

	/**
	 * 
	 * @param face1
	 * @param face2
	 * @throws InvalidFaceValueException
	 */
	public Domino(int face1, int face2) throws InvalidFaceValueException {
		if (face1 < 0 || face1 > 6 || face2 < 0 || face2 > 6)
			throw new InvalidFaceValueException();

		else {
			this.face1 = face1;
			this.face2 = face2;
		}
	}

	/**
	 * 
	 */
	public void swapFaces() {
		int temp1 = face1;
		face1 = face2;
		face2 = temp1;
		Domino temp2 = faceLink1;
		faceLink1 = faceLink2;
		faceLink2 = temp2;
	}

	/**
	 * 
	 * @return the face1
	 */
	public int getFace1() {
		return face1;
	}

	/**
	 * 
	 * @return the face2
	 */
	public int getFace2() {
		return face2;
	}

	/**
	 * @return the facelink1
	 */
	public Domino getFaceLink1() {
		return faceLink1;
	}

	/**
	 * @return the facelink2
	 */
	public Domino getFaceLink2() {
		return faceLink2;
	}

	/**
	 * @param facelink1
	 *            the facelink1 to set
	 */
	public void setFaceLink1(Domino facelink1) {
		this.faceLink1 = facelink1;
	}

	/**
	 * @param facelink2
	 *            the facelink2 to set
	 */
	public void setFaceLink2(Domino facelink2) {
		this.faceLink2 = facelink2;
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
		result = prime * result + face1;
		result = prime * result + face2;
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
		if (getClass() != obj.getClass()) {
			return false;
		}

		Domino other = (Domino) obj;

		// face order doesn't matter
		if ((face1 == other.face1 && face2 == other.face2)
				|| (face2 == other.face1 && face1 == other.face2)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return face1 + "|" + face2;
	}
}
