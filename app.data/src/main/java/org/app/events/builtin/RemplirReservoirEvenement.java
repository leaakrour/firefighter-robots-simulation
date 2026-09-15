package org.app.events.builtin;

import org.app.data.Robot;
import org.app.events.Evenement;

public class RemplirReservoirEvenement extends Evenement {

    private Robot robot;

    public RemplirReservoirEvenement(Double date, Robot robot) {
        super(date + robot.getTempsRemplissage());
        this.robot = robot;
    }

    @Override
    public void execute() {
        this.robot.remplirReservoir();
    }

}
