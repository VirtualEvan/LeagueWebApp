package epsilveira.league.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import epsilveira.league.persistence.Contest;
import epsilveira.league.persistence.Contests;
import epsilveira.league.persistence.Team;
import epsilveira.league.persistence.Teams;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class ContestsVM {

    private EntityManager em;
    private Teams teams;
    private Contests contests;

    private boolean isEditing = false;

    // Contest under edition...
    private Contest currentContest;

    @Init
    public void init() {
        this.em = DesktopEntityManagerManager.getDesktopEntityManager();
        this.teams = new Teams(em);
        this.contests = new Contests(em);
    }

    public List<Contest> getContests() {
        return this.contests.findAll();
    }

    public List<Team> getTeams() {
        return this.teams.findAll();
    }

    public Contest getCurrentContest() {
        return currentContest;
    }

    public void setCurrentContest(Contest currentContest) {
        this.currentContest = currentContest;
    }

    @Command
    @NotifyChange("currentContest")
    public void newContest() {
        this.isEditing = false;
        this.currentContest = new Contest();
    }

    @Command
    @NotifyChange("currentContest")
    public void resetEditing() {
        this.currentContest = null;
    }

    @Command
    @NotifyChange({"currentContest", "contests"})
    public void saveContest() {

        this.em.getTransaction().begin();
        if (!isEditing) {
            this.contests.addContest(this.currentContest);
        }
        this.em.getTransaction().commit();

        this.currentContest = null;
    }

    @Command
    @NotifyChange("contests")
    public void delete(@BindingParam("contest") Contest Contest) {
        this.em.getTransaction().begin();
            this.contests.deleteContest(Contest);
        this.em.getTransaction().commit();
    }

    @Command
    @NotifyChange("currentContest")
    public void edit(@BindingParam("contest") Contest Contest) {
        this.isEditing = true;
        this.currentContest = Contest;
    }
}

