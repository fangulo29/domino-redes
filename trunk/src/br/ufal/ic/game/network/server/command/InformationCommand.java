package br.ufal.ic.game.network.server.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import br.ufal.ic.game.network.server.Server;


@SuppressWarnings("serial")
public class InformationCommand extends AbstractAction {

	private Server server;
	
	public InformationCommand(Server server) {
		this.server = server;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		server.showServerInformationsDialog();
	}

}
