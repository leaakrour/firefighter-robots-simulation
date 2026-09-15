package org.appai.strategie;

import org.app.ai.ChefPompier;
import org.app.ai.ChefPompierFactory;
import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;

public final class ChefElementaireFactory extends ChefPompierFactory {

    /**
     * Identifiant de la factory (utilisée pour le chargement de la strétégie).
     */
    private static final String ID_FACTORY = "ChefElementaire";

    @Override
    public ChefPompier getChefPompier(final DonneesSimulation donneesSimulation,
            final ConsommateurEvenement cons) {
        return new ChefElementaire(donneesSimulation, cons);
    }

    @Override
    public String getNomChef() {
        return ID_FACTORY;
    }

}
