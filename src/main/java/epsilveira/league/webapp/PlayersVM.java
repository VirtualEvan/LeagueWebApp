package epsilveira.league.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import epsilveira.league.persistence.Player;
import epsilveira.league.persistence.Players;
import epsilveira.league.persistence.Teams;
import epsilveira.league.persistence.Team;

public class PlayersVM {

	private EntityManager em;
	private Players players;
	private Teams teams;
	private List<Player.Position> positions;

	private boolean isEditing = false;

	// Player under edition...
	private Player currentPlayer;

	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.players = new Players(em);
		this.teams = new Teams(em);
		this.positions = new ArrayList<>(Arrays.asList(Player.Position.values()));
	}

	public List<Player> getPlayers() {
		return this.players.findAll();
	}

	public List<Team> getTeams() {
		return this.teams.findAll();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<Player.Position> getPositions() {
		return positions;
	}

	@Command
	@NotifyChange("currentPlayer")
	public void newPlayer() {
		this.isEditing = false;
		this.currentPlayer = new Player();
	}

	@Command
	@NotifyChange("currentPlayer")
	public void resetEditing() {
		this.currentPlayer = null;
	}

	@Command
	@NotifyChange({"currentPlayer", "players"})
	public void savePlayer() {

		this.em.getTransaction().begin();
		if (!isEditing) {
			this.players.addPlayer(this.currentPlayer);
		}
		this.em.getTransaction().commit();

		this.currentPlayer = null;
	}

	@Command
	@NotifyChange({"players"})
	public void delete(@BindingParam("p") Player Player) {
		this.em.getTransaction().begin();
			this.players.deletePlayer(Player);
		this.em.getTransaction().commit();
	}

	@Command
	@NotifyChange("currentPlayer")
	public void edit(@BindingParam("p") Player Player) {
		this.isEditing = true;
		this.currentPlayer = Player;
	}


}
