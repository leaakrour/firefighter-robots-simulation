package org.app.events;

public abstract class Evenement implements Comparable<Evenement> {

    /**
     * Date à laquelle on éxécute l'évènement.
     */
    protected double date;

    /**
     * Date d'execution de l'évènement.
     * @param dateEvenement : date de l'évènement.
     */
    public Evenement(final double dateEvenement) {
        this.date = dateEvenement;
    }

    /**
     * Retourne la date de l'évènement.
     * @return la date de l'évènement en s.
     */
    public double getDate() {
        return date;
    }

    /**
     * Exécution de l'évènement.
     */
    public abstract void execute();
    
    @Override
    public String toString() {
        return "" + this.date;
    }

    @Override
    public final int compareTo(final Evenement o) {
        return (int) Double.compare(this.date, o.date);
    }

}
