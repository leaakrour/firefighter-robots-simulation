package org.appai.event;

import org.app.data.EtatDuRobot;
import org.app.data.Robot;
import org.app.events.Evenement;

public final class LibreRobotEvenement extends Evenement {

    /**
     * Le Robot à libérer.
     */
    private Robot robot;

    /**
     * Evenement libérant le robot passé en paramètre.
     * @param date : Date à laquelle on libère le robot.
     * @param robotALiberer : Le robot à libérer.
     */
    public LibreRobotEvenement(final double date, final Robot robotALiberer) {
        super(date);
        this.robot = robotALiberer;
    }

    @Override
    public void execute() {
        this.robot.setEtat(EtatDuRobot.LIBRE);
    }

}
