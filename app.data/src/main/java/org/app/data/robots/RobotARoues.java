package org.app.data.robots;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.app.data.Carte;
import org.app.data.Case;
import org.app.data.Direction;
import org.app.data.NatureTerrain;
import org.app.data.Robot;
import org.app.exceptions.RemplissageImpossibleException;
import org.app.io.ResourceConsumer;

/**
 * Représente un robot à roues qui hérite de la classe Robot.
 * @author bouabdal
 *
 */
public class RobotARoues extends Robot {

    /**
     * Vitesse du robot à roues sur les terrains libres ou habitat.
     */
    private static final double VITESSE_DE_BASE = 80;

    /**
     * Vitesse nulle pour les endroit où le robot n'a pas accès.
     */
    private static final double VITESSE_INTERDITE = -1;

    /**
     * Capacité maximale du reservoir en litres.
     */
    private static final int CAPACITE_MAX = 5000;

    /**
     * Intervention unitaire : 100 litres en 5 sec.
     */
    private static final double DEBIT = 20;
    
    /**
     * L'image représentant le robot a pattes.
     */
    private static BufferedImage image = null;

    static {
        try {
            image = ImageIO
                    .read(new ResourceConsumer("/textures/robot_roues.jpg",
                            "app.data").getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Créée un robot à roues sur la case donnée.
     * @param position Position initiale du robot.
     */
    public RobotARoues(final Case position, final Carte carte) {
        super(position, carte);
    }

    /**
     * Retourne la vitesse du robot selon le type de terrain donné. Ce robot ne
     * peut se déplacer que sur des terrains libres ou habitats.
     * @param terrain Le type de terrain considéré.
     * @return La vitesse du robot en fonction du terrain en km/h. 0 si le
     * terrain est non accessible.
     */
    @Override
    public double getVitesse(final NatureTerrain terrain) {
        if (terrain == NatureTerrain.TERRAIN_LIBRE
                || terrain == NatureTerrain.HABITAT) {
            return VITESSE_DE_BASE;
        } else {
            return VITESSE_INTERDITE;
        }
    }

    /**
     * Retourne la capacité maximale du réservoir du robot à roues.
     * @return La capacité maximale en litres.
     */
    @Override
    public int getCapaciteMax() {
        return CAPACITE_MAX;
    }

    /**
     * Retoune le débit maximal du robot à roue.
     * @return le débit 20 litres/s
     */
    @Override
    public double getDebitMax() {
        return DEBIT;
    }
    
    /**
     * Met le robot sur la case donnée.
     * @param positionRobot : La nouvelle case sur laquelle se trouve le robot
     */
    @Override
    public void setPosition(Case positionRobot) {
    	if(positionRobot.getNature() == NatureTerrain.TERRAIN_LIBRE 
    		|| positionRobot.getNature() == NatureTerrain.HABITAT ) {
    		super.setPosition(positionRobot);
    	}
    }
    
    @Override
    public double getTempsRemplissage() {
    	return (this.getCapaciteMax()-this.getVolumeEau())*10*60/5000;   	
    }
    
    @Override
    public void paint(Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        graphics.drawImage(image, 0, 0, bounds.width, bounds.height, null);
    }
}