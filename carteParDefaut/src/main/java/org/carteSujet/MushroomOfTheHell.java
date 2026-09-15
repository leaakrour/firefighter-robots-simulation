package org.carteSujet;

import org.app.data.DonneesSimulation;
import org.app.io.LecteurDonnees;
import org.app.io.ResourceConsumer;

public class MushroomOfTheHell extends DonneesSimulation {

    public MushroomOfTheHell() {
        super("MushroomOfTheHell",
                LecteurDonnees
                        .lire(new ResourceConsumer("/mushroomOfHell-20x20.map",
                                "app.carteParDefaut").getResources()));
    }
}
