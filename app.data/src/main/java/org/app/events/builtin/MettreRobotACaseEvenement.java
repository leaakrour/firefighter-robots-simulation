package org.app.events.builtin;

import org.app.data.Case;
import org.app.data.Robot;
import org.app.events.Evenement;

public class MettreRobotACaseEvenement extends Evenement {

    private Case dest;
    private Robot robot;

    public MettreRobotACaseEvenement(double date, Case dest, Robot robot) {
        super(date);
        this.dest = dest;
        this.robot = robot;
    }

    @Override
    public void execute() {
        this.robot.setPosition(dest);
    }

    @Override
    public String toString() {
        return "| (" + this.dest.getColonne() + ", " + dest.getLigne()
                + ") at time " + this.getDate();
    }

}
