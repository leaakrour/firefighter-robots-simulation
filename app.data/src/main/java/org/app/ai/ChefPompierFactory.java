package org.app.ai;

import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;

public abstract class ChefPompierFactory {

    public ChefPompierFactory() {
    }

    /**
     * Factory d'un chef de pompier.
     * @param donneesSimulation les donnees de la simulation.
     * @param cons le consommateur d'evenements.
     * @return le chef de pompier construit avec les donnees de simulation et le
     * consommateur d'evenements.
     */
    public abstract ChefPompier getChefPompier(
            DonneesSimulation donneesSimulation, ConsommateurEvenement cons);

    /**
     * Retourne le nom du chef de pompier (utilisé pour l'identification lors du
     * chargment dynamique de la stratégie).
     * @return
     */
    public abstract String getNomChef();
}
