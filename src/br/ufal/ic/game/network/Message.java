package br.ufal.ic.game.network;

import java.io.Serializable;

/**
 * 
 * @author Anderson Santos
 * @author Luciano Melo
 * 
 */
@SuppressWarnings("serial")
public class Message implements Serializable {

    public static enum Acao {
	NULL, CHAT, JOGADOR_ADICIONADO, JOGO_INICIADO
    };

    /*
     * define qual acao tomar de acordo com as trocas de mensagens
     */
    private Acao acao;
    private String nomeJogador;
    private Integer portaJogador;
    private String mensagemChat;
    private String nomesJogadoresConectadosGSON; // objeto GSON com os nomes dos
						 // jogadores

    private String mapJogadoresPecasCorrentesGSON; // objeto GSON com o HashMap
						   // dos jogadores e suas
						   // respectivas pecas
						   // correntes

    public Message() {
	acao = Acao.NULL;
    }

    public Acao getAcao() {
	return acao;
    }

    public void setAcao(Acao acao) {
	this.acao = acao;
    }

    public String getNomeJogador() {
	return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
	this.nomeJogador = nomeJogador;
    }

    public int getPortaJogador() {
	return portaJogador;
    }

    public void setPortaJogador(Integer portaJogador) {
	this.portaJogador = portaJogador;
    }

    public String getMensagemChat() {
	return mensagemChat;
    }

    public void setMensagemChat(String mensagemChat) {
	this.mensagemChat = mensagemChat;
    }

    // GSON
    public void setNomesJogadoresConectadosGSON(
	    String nomesJogadoresConectadosGSON) {
	this.nomesJogadoresConectadosGSON = nomesJogadoresConectadosGSON;
    }

    public String getNomesJogadoresConectadosGSON() {
	return this.nomesJogadoresConectadosGSON;
    }

    // GSON
    public String getMapJogadoresPecasCorrentesGSON() {
	return mapJogadoresPecasCorrentesGSON;
    }

    public void setMapJogadoresPecasCorrentesGSON(
	    String mapJogadoresPecasCorrentesGSON) {
	this.mapJogadoresPecasCorrentesGSON = mapJogadoresPecasCorrentesGSON;
    }

    @Override
    public String toString() {
	return "Mensagem: [nomeJogador = " + nomeJogador + "], [acao = "
		+ acao.name() + "], [mensagemChat = " + mensagemChat
		+ "], [nomesJogadoresConectados = "
		+ nomesJogadoresConectadosGSON + "], [mapJogadoresPecas = "
		+ mapJogadoresPecasCorrentesGSON + "]";
    }

}
