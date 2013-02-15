package br.ufal.ic.game.network.server.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import br.ufal.ic.game.network.server.Server;

/**
 * 
 * @author Anderson Santos
 * 
 */
@SuppressWarnings("serial")
public class NewPlayerCommand extends AbstractAction {

	private final int numberOfConnectedPlayers;

	/**
	 * 
	 * @param server
	 */
	public NewPlayerCommand(Server server) {
		this.numberOfConnectedPlayers = server.getNumberOfConnectedPlayers();
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.err
				.println("Temos " + numberOfConnectedPlayers + " conectados!");

		if (numberOfConnectedPlayers <= 4) {

			/* abertura de uma nova tela de jogador */
			// new Jogador();

			String nomeJogador = JOptionPane.showInputDialog("Nome do Jogador");

		} else {
			JOptionPane.showMessageDialog(null, this,
					"Já existem jogadores suficientes.",
					numberOfConnectedPlayers);
		}
	}
}
