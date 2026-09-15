package org.carteSujet;

import org.app.data.DonneesSimulation;
import org.app.io.LecteurDonnees;
import org.app.io.ResourceConsumer;

public class SpiralOfMadness extends DonneesSimulation {

    public SpiralOfMadness() {
        super("SpiralOfMadness",
                LecteurDonnees
                        .lire(new ResourceConsumer("/spiralOfMadness-50x50.map",
                                "app.carteParDefaut").getResources()));
    }
}
