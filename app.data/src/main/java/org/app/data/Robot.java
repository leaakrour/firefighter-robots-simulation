package org.app.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.app.ai.AlgoPlusCC;
import org.app.ai.Noeud;
import org.app.events.Evenement;
import org.app.exceptions.AucuneIncendieException;
import org.app.exceptions.RemplissageImpossibleException;
import org.app.exceptions.RobotSortiCarteException;

import gui.GraphicalElement;

/**
 * Représente un Robot, la classe doit être implémentée.
 */
public abstract class Robot implements GraphicalElement {

    /**
     * Position du robot sur la carte.
     */
    protected Case position;
    /**
     * Volume d'eau dans les réserves du robot.
     */
    protected int volumeEau;

    /**
     * La carte du robot
     */
    protected final Carte carte;

    /**
     * Etat du robot.
     */
    protected EtatDuRobot etat;

    /**
     * Crée un robot sur la case donnée.
     * 
     * @param positionRobot Position du robot
     */
    public Robot(final Case positionRobot, final Carte carte) {
        this.carte = carte;
        this.position = positionRobot;
        this.volumeEau = this.getCapaciteMax();
        this.etat = EtatDuRobot.LIBRE;
    }

    /**
     * Retourne l'état actuel du robot.
     * @return l'état du robot
     */
    public EtatDuRobot getEtat() {
        return etat;
    }

    /**
     * Met à jour l'état du robot.
     * @param etat : l'état dans lequel le robot sera.
     */
    public void setEtat(final EtatDuRobot etat) {
        this.etat = etat;
    }

    /**
     * Retourne la case sur laquelle se trouve le robot.
     * @return une case
     * @see org.app.data.Case
     */
    public Case getPosition() {
        return position;
    }

    /**
     * Getter de l'attribut carte.
     * @return la carte du robot.
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Met le robot sur la case donnée.
     * @param positionRobot : La nouvelle case sur laquelle se trouve le robot
     */
    public void setPosition(final Case positionRobot) {
        this.position = positionRobot;
    }

    /**
     * Setter du volumeEau
     * @param volumeEau
     */
    public void setVolumeEau(int volumeEau) {
        this.volumeEau = volumeEau;
    }

    /**
     * Retourne la quantité d'eau dans le robot.
     * @return La quantitée d'eau en litre sous forme d'un int
     */
    public int getVolumeEau() {
        return volumeEau;
    }

    /**
     * Deverse la quantité vol d'eau sur la case actuelle du robot, cette
     * quantité sera retirée de la quantité d'eau du robot.
     * @param incendie l'incendie à éteindre
     */
    public void deverserEau(Incendie incendie) {
        if (incendie.getCaseIncendie() != this.getPosition()) {
            throw new AucuneIncendieException();
        }
        if (this.volumeEau >= incendie.getLitreEau()) {
            this.volumeEau -= incendie.getLitreEau();
            incendie.setLitreEau(0);
            return;
        }
        incendie.setLitreEau(incendie.getLitreEau() - this.volumeEau);
        this.setVolumeEau(0);
    }

    /**
     * Remplit le réservoir jusqu'à la quantité maximale.
     */
    public void remplirReservoir() {
        for (Direction dir : Direction.values()) {
            Case voisin = this.carte.getVoisin(this.position, dir);
            if (voisin == null) {
                continue;
            }
            if (this.carte.getVoisin(this.position, dir)
                    .getNature() == NatureTerrain.EAU) {
                this.volumeEau = this.getCapaciteMax();
                return;
            }
        }
        throw new RemplissageImpossibleException();
    }
    
    public LinkedList<Evenement> plusCourtChemin(Case dest, long startingDate){
        return AlgoPlusCC.plusCourtCheminEvenements(this.getPosition(),
                dest, this, startingDate);
    }
    
    public Case eauLaPlusProche(){
        List<Case> caseEau = Stream
                .of(this.carte.getCarte())
                .flatMap(Stream::of)
                .filter(currentCase -> currentCase
                        .getNature() == NatureTerrain.EAU)
                .collect(Collectors.toList());
        HashMap<Case, Double> costs = new HashMap<Case, Double>();
        for (Case c : caseEau) {
            for (Direction d : Direction.values()) {
                if (this.carte.voisinExiste(c, d)) {
                    Case voisin = this.carte.getVoisin(c,
                            d);
                    if (this.getVitesse(voisin.getNature()) > 0) {
                        LinkedList<Noeud> noeuds = AlgoPlusCC.findPlusCC(this.getPosition(),
                                voisin, this);
                        if (!noeuds.isEmpty()) {
                            costs.put(voisin, noeuds.getLast().getTemps());
                        }
                    }
                }
            }
        }
        Optional<Entry<Case, Double>> optional = costs.entrySet().stream().min((
                entry1,
                entry2) -> (int) (entry1.getValue() - entry2.getValue()));
        return !optional.isEmpty() ? optional.get().getKey() : null; 
    }

    /**
     * Retourne la vitesse du robot sur le terrain donné.
     * @param terrain Le type terrain considéré
     * @return La vitesse sur ce type de terrain en km/h retournée en double
     */
    public abstract double getVitesse(NatureTerrain terrain);

    /**
     * Retourne la capacité maximale de ce robot.
     * @return La capacité maximale de ce robot en litre sous forme d'un int
     */
    public abstract int getCapaciteMax();

    /**
     * Retoune le débit maximal de l'appareil.
     * @return le debit en litre/s
     */
    public abstract double getDebitMax();

    /**
     * Retourne le temps nécessaire pour le remplissage total du réservoir.
     * @return le temps en s.
     */
    public abstract double getTempsRemplissage();

    /**
     * Retourne le temps nécessaire à un robot pour se rendre sur une case
     * donnée.
     * @param t La case d'arrivée.
     * @return Le temps nécessaire pour atteindre la case cible, ou -1 si aucun
     * chemin n'existe.
     */
    public double getTimeToReach(Case t) {
        // On appelle la méthode findPlusCC pour obtenir le chemin avec les
        // coûts temporels.
        LinkedList<Noeud> pathWithCosts = AlgoPlusCC.findPlusCC(this.position,
                t, this);
        // Si aucun chemin n'a été trouvé, retourner -1
        if (pathWithCosts.isEmpty()) {
            return -1;
        }
        return pathWithCosts.getLast().getTemps();
    }

    /**
     * Déplace un robot dans une direction donnée.
     * @param dir la direction dans laquelle le robot va bouger.
     */
    public void deplacerVers(final Direction dir) {
        Case c = this.carte.getVoisin(position, dir);
        if (c == null) {
            throw new RobotSortiCarteException(this, dir);
        }
        this.setPosition(c);
    }
}
