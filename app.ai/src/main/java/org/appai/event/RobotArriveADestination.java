package org.appai.event;

import java.util.LinkedList;

import org.app.ai.AlgoPlusCC;
import org.app.data.Case;
import org.app.data.EtatDuRobot;
import org.app.data.Incendie;
import org.app.data.Robot;
import org.app.events.ConsommateurEvenement;
import org.app.events.Evenement;
import org.app.events.builtin.DeverserEauEvenement;
import org.app.events.builtin.RemplirReservoirEvenement;

public final class RobotArriveADestination extends Evenement {

    /**
     * Le consommateur d'evenement utilisé pour envoyer de nouveaux evenements.
     */
    private ConsommateurEvenement cons;

    /**
     * Le robot concerné par l'évènement.
     */
    private Robot robot;

    /**
     * L'incendie à éteindre.
     */
    private Incendie incendie;

    /**
     * La case d'eau la plus proche.
     */
    private Case eauLaPlusProche;

    /**
     * La liste des incendies (peut être mise à jour si l'incendie n'est pas
     * éteint).
     */
    private LinkedList<Incendie> incendies;

    /**
     * Evenement déclanché quand le robot arrive à son incendie assigné dans la
     * stratégie 2.
     * @param date : date de déclanchement de l'évènement.
     * @param consommateurEvenement : gestionnaire d'évènements.
     * @param robotAssigne : le robot concerné.
     * @param incendieAssigneAuRobot : l'incendie assigné au robot.
     * @param eauPlusProche : l'eau la plus proche de l'incendie.
     * @param listeIncendies : la liste d'incendie (peut être mise à jour)
     */
    public RobotArriveADestination(final double date,
            final ConsommateurEvenement consommateurEvenement,
            final Robot robotAssigne, final Incendie incendieAssigneAuRobot,
            final Case eauPlusProche,
            final LinkedList<Incendie> listeIncendies) {
        super(date);
        this.cons = consommateurEvenement;
        this.incendie = incendieAssigneAuRobot;
        this.robot = robotAssigne;
        this.eauLaPlusProche = eauPlusProche;
        this.incendies = listeIncendies;
    }

    @Override
    public void execute() {
        if (incendie.getLitreEau() != 0) {
            Evenement viderEau = new DeverserEauEvenement(
                    this.date + 2, robot, incendie);
            this.cons.ajouteEvenement(viderEau);
            this.cons.ajouteEvenement(new MiseAjourListeIncendieEvenement(
                    viderEau.getDate() + 2, this.incendies, incendie));
            if (robot.getVolumeEau() == 0) {
                LinkedList<Evenement> versEau = 
                        robot.plusCourtChemin(eauLaPlusProche, (long)(viderEau.getDate() + 2));
                if (versEau.isEmpty()) {
                    return;
                }
                this.cons.ajouteEvenements(versEau);
                Evenement remplissage = new RemplirReservoirEvenement(
                        versEau.getLast().getDate() + 2, robot);
                this.cons.ajouteEvenement(remplissage);
                this.cons.ajouteEvenement(new LibreRobotEvenement(
                        remplissage.getDate() + 2, robot));
            } else {
                this.robot.setEtat(EtatDuRobot.LIBRE);
            }
        } else {
            this.robot.setEtat(EtatDuRobot.LIBRE);
        }
    }

}
