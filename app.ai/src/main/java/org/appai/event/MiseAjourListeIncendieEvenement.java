package org.appai.event;

import java.util.LinkedList;

import org.app.data.Incendie;
import org.app.events.Evenement;

public final class MiseAjourListeIncendieEvenement extends Evenement {

    /**
     * Liste des incendies à mettre à jour.
     */
    private LinkedList<Incendie> incendies;

    /**
     * L'incendie considéré.
     */
    private Incendie incendie;

    /**
     * Met à jour la liste des incendies à gérer si le feu n'est pas éteint.
     * @param date : date de mise à jour de la liste.
     * @param listeDesIncendies : liste des incendies.
     * @param incendieMisAJour : l'incendie considéré.
     */
    public MiseAjourListeIncendieEvenement(final double date,
            final LinkedList<Incendie> listeDesIncendies,
            final Incendie incendieMisAJour) {
        super(date);
        this.incendies = listeDesIncendies;
        this.incendie = incendieMisAJour;
    }

    @Override
    public void execute() {
        if (incendie.getLitreEau() != 0) {
            incendies.addFirst(incendie);
        }
    }

}
