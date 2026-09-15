package org.app.events.builtin;

import org.app.ai.ChefPompier;
import org.app.events.ConsommateurEvenement;
import org.app.events.Evenement;

public class InvoquerStrategieEvenement extends Evenement {

    private ChefPompier chef;

    private ConsommateurEvenement cons;

    public static final int DELTA = 10;

    public InvoquerStrategieEvenement(double date, ChefPompier chef,
            ConsommateurEvenement cons) {
        super(date);
        this.chef = chef;
        this.cons = cons;
    }

    @Override
    public void execute() {
        this.chef.strategie();
        if (!this.cons.simulationTerminee()) {
            this.cons.ajouteEvenement(
                    new InvoquerStrategieEvenement(date + DELTA, chef, cons));
        }
    }

}
