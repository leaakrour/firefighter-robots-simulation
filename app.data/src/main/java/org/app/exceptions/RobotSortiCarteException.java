package org.app.exceptions;

import org.app.data.Direction;
import org.app.data.Robot;

public final class RobotSortiCarteException extends RuntimeException {

    private static final long serialVersionUID = 7192235202543664467L;

    /**
     * Le robot étant sorti de la carte.
     */
    private Robot robot;

    /**
     * La direction dans laquelle allait le robot.
     */
    private Direction dir;

    /**
     * Construit une exception de sortie de carte.
     * @param r
     * @param direction
     */
    public RobotSortiCarteException(final Robot r, final Direction direction) {
        this.robot = r;
        this.dir = direction;
    }

    @Override
    public String getMessage() {
        return "Le robot " + robot.toString()
                + " est sorti de la carte en allant vers : " + dir;
    }

}
