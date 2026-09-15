package org.app.ai;

import org.app.data.Case;

/**
 * Cette classe contient la case et le temps nécessaire pour arriver à cette
 * case.
 */
public class Noeud {

    /**
     * La case concernée.
     */
    protected Case caseN;

    /**
     * Le temps nécessaire pour arriver à la case.
     */
    protected double temps;

    /**
     * Crée un noeud.
     * 
     * @param caseN la case concernéé.
     * @param temps le temps nécessaire pour arriver à la case.
     */
    public Noeud(Case caseN, double temps) {
        this.caseN = caseN;
        this.temps = temps;
    }

    /**
     * Getter de l'attribut caseN.
     * 
     * @return la caseN.
     */
    public Case getCaseN() {
        return caseN;
    }

    /**
     * Getter de l'attribut temps.
     * 
     * @return le temps.
     */
    public double getTemps() {
        return temps;
    }
    
    /**
     * Obtenir le temps en secondes.
     * 
     * @return le temps.
     */
    public double getTempsSecondes() {
        return temps*60;
    }

    /**
     * Setter de l'attribut temps.
     * 
     * @param temps
     */
    public void setTemps(double temps) {
        this.temps = temps;
    }

    @Override
    public String toString() {
        return "(" + this.caseN.getColonne() + ", " + this.getCaseN().getLigne()
                + ")";
    }

}
