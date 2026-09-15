package org.appai.strategie;

import java.util.LinkedList;

import org.app.ai.AlgoPlusCC;
import org.app.ai.ChefPompier;
import org.app.data.DonneesSimulation;
import org.app.data.EtatDuRobot;
import org.app.data.Incendie;
import org.app.data.Robot;
import org.app.events.ConsommateurEvenement;
import org.app.events.Evenement;
import org.app.events.builtin.DeverserEauEvenement;
import org.appai.event.FinActionRobotEvenement;

public final class ChefElementaire extends ChefPompier {

    /**
     * Liste des incendies à gérer.
     */
    private LinkedList<Incendie> incendiesAGerer;

    /**
     * Implémentation de la première stratégie proposée dans le sujet.
     * @param donneesSimulation : Les donnée actuellement dans le simulateur.
     * @param cons : Le gestionnaire d'évènements utilisé par la gui.
     */
    public ChefElementaire(final DonneesSimulation donneesSimulation,
            final ConsommateurEvenement cons) {
        super(donneesSimulation, cons);
        this.incendiesAGerer = new LinkedList<Incendie>();
        for (Incendie incendie : donneesSimulation.getIncendiesSim()) {
            this.incendiesAGerer.add(incendie);
        }
    }

    @Override
    public void strategie() {
        for (Robot robot : this.donneesSimulation.getRobotsSim()) {
            if (robot.getEtat() == EtatDuRobot.LIBRE) {
                robot.setEtat(EtatDuRobot.OCCUPE);
                Incendie incendie = incendiesAGerer.poll();
                if (incendie == null) {
                    break;
                }
                LinkedList<Evenement> chemin = 
                        robot.plusCourtChemin(incendie.getCaseIncendie(), cons.getCurrentDate() + 1);
                if (chemin.isEmpty()) {
                    incendiesAGerer.add(incendie);
                    continue;
                }
                this.cons.ajouteEvenements(chemin);
                Evenement deverserEau = new DeverserEauEvenement(
                        chemin.getLast().getDate() + 1, robot, incendie);
                this.cons.ajouteEvenement(deverserEau);
                this.cons.ajouteEvenement(
                        new FinActionRobotEvenement(deverserEau.getDate() + 1,
                                robot, incendie, incendiesAGerer));
            }
        }
    }
}
