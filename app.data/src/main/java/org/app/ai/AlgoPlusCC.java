package org.app.ai;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.app.data.Case;
import org.app.data.Direction;
import org.app.data.Robot;
import org.app.events.Evenement;
import org.app.events.builtin.MettreRobotACaseEvenement;

/**
 * Contient l'algorithme du plus court chemin utilisé dans la simulation. Dans
 * notre cas : A*.
 */
public class AlgoPlusCC {

    /**
     * Retourne une liste des cases représentant le plus court chemin entre deux
     * cases.
     * @param s la case de départ.
     * @param t la case destination.
     * @param robot Robot.
     * @return le plus court chemin selon A*.
     */
    public static LinkedList<Noeud> findPlusCC(Case s, Case t, Robot robot) {

        // Map pour stocker les g-coûts : g est le coût exact depuis la case
        // départ :
        Map<Case, Double> gCosts = new HashMap<>();
        // Map pour stocker les f-coûts : f est le coût total :
        Map<Case, Double> fCosts = new HashMap<>();
        // Map pour stocker les données nécessaires à la reconstruction du
        // chemin à la fin :
        Map<Case, Case> prevsNodes = new HashMap<>();
        // Les noeuds (cases) déjà traités :
        Set<Case> closedNodes = new HashSet<>();
        // openNodes ordonnée par f :
        PriorityQueue<Case> openNodes = new PriorityQueue<>(
                Comparator.comparing(fCosts::get));

        // Initialisation :
        openNodes.add(s);
        gCosts.put(s, 0.0);
        fCosts.put(s, h(s, t, robot));
        Case current = openNodes.poll();

        while (!current.equals(t)) {

            // On ajoute current dans les noeuds déjà traités :
            closedNodes.add(current);

            // On examine chaque voisin du nœud courant :
            for (Direction dir : Direction.values()) {
                Case neighbor = robot.getCarte().getVoisin(current, dir);

                // On ignore les voisins déjà traités :
                if (neighbor != null
                        && (robot.getVitesse(neighbor.getNature()) >= 0)
                        && !(closedNodes.contains(neighbor))) {

                    // Calculer le coût : le temps en min.
                    double moveCost = 60
                            * (((double) robot.getCarte().getTailleCases())
                                    / ((double) 2000))
                            * (1 / robot.getVitesse(current.getNature()) + 1
                                    / robot.getVitesse(neighbor.getNature()));
                    // Calcul des nouveaux coûts g, h et f pour le voisin :
                    double neighborGCost = gCosts.get(current) + moveCost;
                    double hCost = h(neighbor, t, robot);
                    double neighborFCost = neighborGCost + hCost;

                    // Mise à jour des coûts si le nouveau chemin est meilleur :
                    if (neighborFCost < fCosts.getOrDefault(neighbor,
                            Double.MAX_VALUE)) {
                        prevsNodes.put(neighbor, current);
                        gCosts.put(neighbor, neighborGCost);
                        fCosts.put(neighbor, neighborFCost);
                    }

                    // Ajout du voisin à la openNodes :
                    if (!openNodes.contains(neighbor)) {
                        openNodes.add(neighbor);
                    }
                }
            }
            if (openNodes.isEmpty()) {
                return new LinkedList<Noeud>();
            }
            current = openNodes.poll();
        }
        return constructPath(prevsNodes, current, gCosts);
    }

    /**
     * Retourne l'estimation heuristique de la distance entre la case d et la
     * case f
     * @param d case de départ
     * @param f case d'arrivée
     * @param r robot concerné
     * @param taille taille des cases
     * @return la valeur de l'estimation en min.
     */
    private static double h(Case d, Case f, Robot robot) {
        // Estimation du temps correspondant à la distance de Manhattan
        return 60 * (((Math.abs(d.getLigne() - f.getLigne())
                + Math.abs(d.getColonne() - f.getColonne()))
                * ((double) robot.getCarte().getTailleCases()) / 1000d)
                / ((robot.getVitesse(d.getNature())
                        + robot.getVitesse(f.getNature())) / 2));
    }

    /**
     * Construit un chemin
     * @param prevsNodes les noeuds utilisés dans la construction du chemin
     * @param current l'élément duquel on va construire le chemin en arrière
     * @return un chemin
     */
    private static LinkedList<Noeud> constructPath(Map<Case, Case> prevsNodes,
            Case current, Map<Case, Double> gCosts) {
        LinkedList<Noeud> path = new LinkedList<>();
        while (current != null) {
            Noeud noeud = new Noeud(current, gCosts.get(current));
            path.addFirst(noeud);
            current = prevsNodes.get(current);
        }
        return path;
    }

    /**
     * Construit un chemin sous forme d'une suite d'évènements.
     * @param s Case de départ.
     * @param t Case de fin.
     * @param robot Le robot utilisé pour le déplacement.
     * @param startingDate La date du premier déplacement.
     * @return une liste d'évènements.
     */
    public static LinkedList<Evenement> plusCourtCheminEvenements(Case s,
            Case t, Robot robot, long startingDate) {
        LinkedList<Evenement> path = new LinkedList<Evenement>();
        LinkedList<Noeud> noeuds = findPlusCC(s, t, robot);
        for (Noeud noeud : noeuds) {
            path.add(new MettreRobotACaseEvenement(
                    noeud.getTempsSecondes() + (double) startingDate, noeud.getCaseN(),
                    robot));
        }
        return path;
    }
}
