package org.carteSujet;

import org.app.data.DonneesSimulation;
import org.app.io.LecteurDonnees;
import org.app.io.ResourceConsumer;

public class DesertOfTheDeath extends DonneesSimulation {

    public DesertOfTheDeath() {
        super("DesertOfTheDeath",
                LecteurDonnees
                        .lire(new ResourceConsumer("/desertOfDeath-20x20.map",
                                "app.carteParDefaut").getResources()));
    }
}
