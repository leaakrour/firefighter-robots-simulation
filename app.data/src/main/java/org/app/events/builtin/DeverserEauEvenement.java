package org.app.events.builtin;

import org.app.data.Robot;
import org.app.data.Incendie;
import org.app.events.Evenement;

public class DeverserEauEvenement extends Evenement {

    /**
     * Le robot conserné.
     */
    private Robot robot;

    /**
     * L'incendie à éteindre.
     */
    private Incendie incendie;

    /**
     * Construit un évènement déversement d'eau.
     * @param date date de l'évènement.
     * @param robotUtilise le robot utilisé.
     * @param incendieAEteindre l'incendie à éteindre.
     */
    public DeverserEauEvenement(final double date, final Robot robotUtilise,
            final Incendie incendieAEteindre) {
        super(date + (Math.min(incendieAEteindre.getLitreEau(),
                robotUtilise.getVolumeEau()) / robotUtilise.getDebitMax()));
        this.robot = robotUtilise;
        this.incendie = incendieAEteindre;
    }

    @Override
    public void execute() {
        this.robot.deverserEau(this.incendie);
    }

}
