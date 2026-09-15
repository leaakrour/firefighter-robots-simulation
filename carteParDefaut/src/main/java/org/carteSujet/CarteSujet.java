package org.carteSujet;

import org.app.data.DonneesSimulation;
import org.app.io.ResourceConsumer;
import org.app.io.LecteurDonnees;

public class CarteSujet extends DonneesSimulation {

    public CarteSujet() {
        super("CarteSujet", LecteurDonnees.lire(
                new ResourceConsumer("/carteSujet.map", "app.carteParDefaut")
                        .getResources()));
    }
}
