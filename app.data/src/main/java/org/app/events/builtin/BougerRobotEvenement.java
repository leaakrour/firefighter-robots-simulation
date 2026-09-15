package org.app.events.builtin;

import org.app.data.Direction;
import org.app.data.Robot;
import org.app.events.Evenement;

public class BougerRobotEvenement extends Evenement {

    private Robot robot;
    private Direction dir;

    public BougerRobotEvenement(double date, Robot robot, Direction dir) {
        super(date);
        this.robot = robot;
        this.dir = dir;
    }

    @Override
    public void execute() {
        this.robot.deplacerVers(dir);
    }

}
