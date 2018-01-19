package epsilveira.league.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import epsilveira.league.persistence.*;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class CoachesVM {

    private EntityManager em;
    private Coaches coaches;
    private Teams teams;

    private boolean isEditing = false;

    // Coach under edition...
    private Coach currentCoach;

    @Init
    public void init() {
        this.em = DesktopEntityManagerManager.getDesktopEntityManager();
        this.coaches = new Coaches(em);
        this.teams = new Teams(em);
    }

    public List<Coach> getCoaches() {
        return this.coaches.findAll();
    }

    public List<Team> getTeams() {
        return this.teams.findAll();
    }

    public Coach getCurrentCoach() {
        return currentCoach;
    }

    public void setCurrentCoach(Coach currentCoach) {
        this.currentCoach = currentCoach;
    }

    @Command
    @NotifyChange("currentCoach")
    public void newCoach() {
        this.isEditing = false;
        this.currentCoach = new Coach();
    }

    @Command
    @NotifyChange("currentCoach")
    public void resetEditing() {
        this.currentCoach = null;
    }

    @Command
    @NotifyChange({"currentCoach", "coaches"})
    public void saveCoach() {

        this.em.getTransaction().begin();
        if (!isEditing) {
            this.coaches.addCoach(this.currentCoach);
        }
        this.em.getTransaction().commit();

        this.currentCoach = null;
    }

    @Command
    @NotifyChange("coaches")
    public void delete(@BindingParam("c") Coach Coach) {
        this.em.getTransaction().begin();
            this.coaches.deleteCoach(Coach);
        this.em.getTransaction().commit();
    }

    @Command
    @NotifyChange("currentCoach")
    public void edit(@BindingParam("c") Coach Coach) {
        this.isEditing = true;
        this.currentCoach = Coach;
    }


}
