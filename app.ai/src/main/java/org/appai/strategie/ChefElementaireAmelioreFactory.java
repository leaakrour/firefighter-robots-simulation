package org.appai.strategie;

import org.app.ai.ChefPompier;
import org.app.ai.ChefPompierFactory;
import org.app.data.DonneesSimulation;
import org.app.events.ConsommateurEvenement;

public final class ChefElementaireAmelioreFactory extends ChefPompierFactory {

    @Override
    public ChefPompier getChefPompier(final DonneesSimulation donneesSimulation,
            final ConsommateurEvenement cons) {
        return new ChefElementaireAmeliore(donneesSimulation, cons);
    }

    @Override
    public String getNomChef() {
        return "ChefElementaireAmeliore";
    }

}
