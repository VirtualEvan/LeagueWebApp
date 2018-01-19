package epsilveira.league.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import epsilveira.league.persistence.*;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class TeamsVM {

    private EntityManager em;
    private Teams teams;
    private List<Team.Region> regions;
    private Coaches coaches;

    private boolean isEditing = false;

    // Team under edition...
    private Team currentTeam;
    private Set<Player> players;

    @Init
    public void init() {
        this.em = DesktopEntityManagerManager.getDesktopEntityManager();
        this.teams = new Teams(em);
        this.regions = new ArrayList<>(Arrays.asList(Team.Region.values()));
        this.coaches = new Coaches(em);
    }

    public List<Team> getTeams() {
        return this.teams.findAll();
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public List<Team.Region> getRegions() {
        return regions;
    }

    public List<Coach> getCoaches() {
        return this.coaches.findAll();
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    @Command
    @NotifyChange("currentTeam")
    public void newTeam() {
        this.isEditing = false;
        this.currentTeam = new Team();
    }

    @Command
    @NotifyChange("currentTeam")
    public void resetEditing() {
        this.currentTeam = null;
    }

    @Command
    @NotifyChange({"currentTeam", "teams"})
    public void saveTeam() {

        this.em.getTransaction().begin();
        if (!isEditing) {
            this.teams.addTeam(this.currentTeam);
        }
        this.em.getTransaction().commit();

        this.currentTeam = null;
    }

    @Command
    @NotifyChange("teams")
    public void delete(@BindingParam("t") Team team) {
        this.em.getTransaction().begin();
            this.teams.deleteTeam(team);
        this.em.getTransaction().commit();
    }

    @Command
    @NotifyChange("currentTeam")
    public void edit(@BindingParam("t") Team team) {
        this.isEditing = true;
        this.currentTeam = team;
    }

    @Command
    @NotifyChange("players")
    public void viewPlayers(@BindingParam("t") Team team) {
        this.players = team.getPlayers();
        System.out.println(this.players.size());
    }

    @Command
    @NotifyChange("players")
    public void cancelViewPlayers() {
        this.players = null;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
