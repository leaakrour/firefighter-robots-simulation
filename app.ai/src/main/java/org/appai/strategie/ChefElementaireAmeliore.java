package org.appai.strategie;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.app.ai.AlgoPlusCC;
import org.app.ai.ChefPompier;
import org.app.ai.Noeud;
import org.app.data.Case;
import org.app.data.Direction;
import org.app.data.DonneesSimulation;
import org.app.data.EtatDuRobot;
import org.app.data.Incendie;
import org.app.data.NatureTerrain;
import org.app.data.Robot;
import org.app.events.ConsommateurEvenement;
import org.app.events.Evenement;
import org.appai.event.RobotArriveADestination;

public final class ChefElementaireAmeliore extends ChefPompier {

    /**
     * Liste des incendies à gérer.
     */
    private LinkedList<Incendie> incendiesAGerer;

    /**
     * Implémentation de la seconde stratégie du sujet.
     * @param donneesSimulation : les donnees du simulateur.
     * @param cons : le consommateur d'évènements.
     */
    public ChefElementaireAmeliore(final DonneesSimulation donneesSimulation,
            final ConsommateurEvenement cons) {
        super(donneesSimulation, cons);
        incendiesAGerer = new LinkedList<Incendie>();
        for (Incendie incendie : donneesSimulation.getIncendiesSim()) {
            this.incendiesAGerer.add(incendie);
        }
    }

    /**
     * Renvoie le robot le plus proche de la case donnée en argument.
     * @param dest La case de destination du robot le plus proche
     * @return un couple de valeurs Robot/Suite d'evenements pour se rendre sur
     * la case renvoie null si il n'y a pas de chemin.
     */
    private Entry<Robot, LinkedList<Evenement>> robotLePlusProcheDisponible(
            final Case dest) {
        List<Robot> robotLibres = Stream
                .of(this.donneesSimulation.getRobotsSim())
                .filter(robot -> robot.getEtat() == EtatDuRobot.LIBRE)
                .collect(Collectors.toList());
        if (robotLibres.isEmpty()) {
            return null;
        }
        HashMap<Robot, LinkedList<Evenement>> cheminsDispo =
                new HashMap<Robot, LinkedList<Evenement>>();
        for (Robot robot : robotLibres) {
            LinkedList<Evenement> ev = robot.plusCourtChemin(dest, cons.getCurrentDate());
            if (!ev.isEmpty()) {
                cheminsDispo.put(robot, ev);
            }
        }
        Optional<Entry<Robot, LinkedList<Evenement>>> min = cheminsDispo
                .entrySet().stream()
                .min((entry1,
                        entry2) -> Double.compare(entry1.getValue().getLast().getDate(),
                                entry2.getValue().getLast().getDate()));
        return !min.isEmpty() ? min.get() : null;
    }

    @Override
    public void strategie() {
        // Prend un incendie pas géré encore.
        Incendie incendie = incendiesAGerer.poll();
        if (incendie != null) {
            Entry<Robot, LinkedList<Evenement>> robotLePlusProche =
                    robotLePlusProcheDisponible(incendie.getCaseIncendie());
            if (robotLePlusProche != null) {
                robotLePlusProche.getKey().setEtat(EtatDuRobot.OCCUPE);
                this.cons.ajouteEvenements(robotLePlusProche.getValue());
                double date = robotLePlusProche.getValue().getLast().getDate();
                Case casePlusProche = robotLePlusProche.getKey().eauLaPlusProche();
                if (casePlusProche != null) {
                    this.cons.ajouteEvenement(new RobotArriveADestination(date + 2,
                            cons, robotLePlusProche.getKey(), incendie,
                            casePlusProche, this.incendiesAGerer));
                }
            } else {
                incendiesAGerer.add(incendie);
            }
        }
    }

}
