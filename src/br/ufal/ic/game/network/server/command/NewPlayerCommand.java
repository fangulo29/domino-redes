package br.ufal.ic.game.network.server.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import br.ufal.ic.game.network.client.Player;
import br.ufal.ic.game.network.server.Server;

/**
 * 
 * @author Anderson Santos
 * @author Luciano Melo
 * 
 */
@SuppressWarnings("serial")
public class NewPlayerCommand extends AbstractAction {

    private final Server server;

    /**
     * 
     * @param server
     */
    public NewPlayerCommand(Server server) {
	this.server = server;
    }

    /**
	 * 
	 */
    @Override
    public void actionPerformed(ActionEvent e) {

	Integer numberOfConnectedPlayers = server.getNumberOfConnectedPlayers();

	System.err.println("Ja temos " + numberOfConnectedPlayers
		+ " conectados!");

	if (numberOfConnectedPlayers <= 4) {

	    /* abertura de uma nova tela de jogador */
	    // String nomeJogador =
	    // JOptionPane.showInputDialog("Nome do Jogador");
	    new Player().startGUI();

	} else {
	    JOptionPane.showMessageDialog(null, this,
		    "Já existem jogadores suficientes.",
		    numberOfConnectedPlayers);
	}
    }
}
