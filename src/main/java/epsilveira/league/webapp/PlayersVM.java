package epsilveira.league.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.Init;

import epsilveira.league.persistence.Player;
import epsilveira.league.persistence.Players;

public class PlayersVM {

	private EntityManager em;
	private Players players;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.players = new Players(em);
	}
	
	/*public List<Players> getDepartments() {
		return this.players.findAll();
	}*/
}
