package org.appai.event;

import java.util.LinkedList;

import org.app.data.EtatDuRobot;
import org.app.data.Incendie;
import org.app.data.Robot;
import org.app.events.Evenement;

public final class FinActionRobotEvenement extends Evenement {

    /**
     * Le Robot ayant fini sont action.
     */
    private Robot robot;

    /**
     * L'incendie considéré.
     */
    private Incendie incendie;

    /**
     * La liste des incendies à mettre à jour.
     */
    private LinkedList<Incendie> incendiesAGerer;

    /**
     * Met à jour le robot et la liste des incendies suite à l'action du robot.
     * @param date date de l'évènement.
     * @param robotMisAJour robot ayant fini sont action.
     * @param incendieConsidere l'incendie considéré.
     * @param listeDesIncendies la liste des incendies à mettre à jour.
     */
    public FinActionRobotEvenement(final double date, final Robot robotMisAJour,
            final Incendie incendieConsidere,
            final LinkedList<Incendie> listeDesIncendies) {
        super(date);
        this.robot = robotMisAJour;
        this.incendie = incendieConsidere;
        this.incendiesAGerer = listeDesIncendies;
    }

    @Override
    public void execute() {
        if (this.incendie.getLitreEau() == 0) {
            this.incendiesAGerer.remove(this.incendie);
        } else {
            this.incendiesAGerer.add(incendie);
        }
        if (this.robot.getVolumeEau() != 0) {
            this.robot.setEtat(EtatDuRobot.LIBRE);
        }
    }

}
