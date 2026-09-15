package org.app.ai;

import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;

public abstract class ChefPompier {

    /**
     * Les données de simulation de la classe pompier.
     */
    protected DonneesSimulation donneesSimulation;

    /**
     * La classe qui gère les évènements de l'application.
     */
    protected ConsommateurEvenement cons;

    /**
     * Construit un chef des pompiers avec les données de simulation fournies.
     * @param donnees : les donnee simulées.
     * @param consommateurEvenements : le gestionnaire d'évènements.
     */
    public ChefPompier(final DonneesSimulation donnees,
            final ConsommateurEvenement consommateurEvenements) {
        this.donneesSimulation = donnees;
        this.cons = consommateurEvenements;
    }

    /**
     * Retourne le gestionnaire d'évènements utilisé par ce chef des pompiers.
     * @return
     */
    public ConsommateurEvenement getConsommateurEvenement() {
        return cons;
    }

    /**
     * Fonction implémentant la stratégie adoptée par les pompiers.
     */
    public abstract void strategie();
}
