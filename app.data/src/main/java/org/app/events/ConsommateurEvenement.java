package org.app.events;

import java.util.Collection;
import java.util.PriorityQueue;

import gui.Simulable;

public abstract class ConsommateurEvenement implements Simulable {

    /**
     * Liste de priorité des évènements triés par ordre d'approche.
     */
    protected PriorityQueue<Evenement> evenements;

    /**
     * Date actuelle du simulateur.
     */
    protected long currentDate;

    /**
     * Initialisation des évènements.
     */
    protected ConsommateurEvenement() {
        this.currentDate = 0;
        this.evenements = new PriorityQueue<Evenement>();
    }

    /**
     * Test si il n'y a plus d'évènements.
     * @return true si il n'y a plus rien à simuler.
     */
    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }

    /**
     * Ajoute un Evenement dans la liste des evenements.
     * @param e : L'evenement à ajouté.
     */
    public void ajouteEvenement(final Evenement e) {
        this.evenements.add(e);
    }

    /**
     * Ajoute des evenements dans la liste des evenements.
     * @param e : Une collection d'evenements.
     */
    public void ajouteEvenements(final Collection<Evenement> e) {
        this.evenements.addAll(e);
    }

    /**
     * Augmentation de la date de simulation.
     */
    protected void incrementeDate() {
        this.currentDate++;
    }

    /**
     * Retourne la date actuelle du simulateur.
     * @return la date du simulateur.
     */
    public long getCurrentDate() {
        return currentDate;
    }
}
