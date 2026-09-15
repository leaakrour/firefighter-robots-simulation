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
 * Représente un robot à chenilles.
 */

public class RobotChenilles extends Robot {

    /**
     * Vitesse maximale à laquelle peut aller le robot en temps normal sans
     * contrainte de terrain.
     */
    private double vitesseDeBase;

    /**
     * Vitesse maximale prise lorsque qu'aucune vitesse n'est spécifiée.
     */
    private static final double VITESSE_PAR_DEFAUT = 60;

    /**
     * Capacité maximale du robot en litre.
     */
    private static final int CAPACITE_MAXIMALE = 2000;

    /**
     * Débit maximal du robot en l/s.
     */
    private static final double DEBIT_MAXIMAL = 12.5;

    /**
     * Vitesse limite du robot chenille.
     */
    private static final int VITESSE_LIMITE = 80;

    /**
     * L'image représentant le robot a pattes.
     */
    private static BufferedImage image = null;

    static {
        try {
            image = ImageIO
                    .read(new ResourceConsumer("/textures/robot_chenilles.jpg",
                            "app.data").getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Crée un robot sur la case donnée.
     * @param position Position du robot
     */
    public RobotChenilles(final Case position, final Carte carte) {
        super(position, carte);
        this.vitesseDeBase = VITESSE_PAR_DEFAUT;
    }

    /**
     * Crée un robot sur la case donnée et lui attribue la vitesse souhaitée (si
     * cette dernière est inférieure à 80 km/h).
     * @param position Position du robot
     * @param vitesse du robot
     */
    public RobotChenilles(final Case position, final double vitesse, final Carte carte) {
        super(position, carte);
        if (this.vitesseDeBase > VITESSE_LIMITE) {
            throw new IllegalArgumentException("Vitesse doit etre inferieur a "
                    + VITESSE_LIMITE + " km/h");
        }
        this.vitesseDeBase = vitesse;
    }

    /**
     * Retourne la vitesse du robot sur le terrain donné.
     * @param terrain Le type terrain considéré
     * @return La vitesse sur ce type de terrain en km/h retournée en double
     */
    @Override
    public double getVitesse(final NatureTerrain terrain) {
        return switch (terrain) {
        case FORET -> this.vitesseDeBase / 2;
        case ROCHE, EAU -> -1;
        default -> this.vitesseDeBase;
        };
    }

    /**
     * Retourne la capacité maximale de ce robot.
     * @return La capacité maximale de ce robot en litre sour forme d'un int
     */
    @Override
    public int getCapaciteMax() {
        return CAPACITE_MAXIMALE;
    }

    /**
     * Retoune le débit maximal de l'appareil.
     * @return le debit en litre/s
     */
    @Override
    public double getDebitMax() {
        return DEBIT_MAXIMAL;
    }
    
    /**
     * Met le robot sur la case donnée.
     * @param positionRobot : La nouvelle case sur laquelle se trouve le robot
     */
    @Override
    public void setPosition(Case positionRobot) {
    	if(positionRobot.getNature()==NatureTerrain.EAU 
    			|| positionRobot.getNature()==NatureTerrain.ROCHE) {
    		return;
    	}
    	super.setPosition(positionRobot);
    }

    
    /**
     * Retourne le temps nécessaire pour le remplissage total du réservoir.
     * @return le temps en min.
     */
    @Override
    public double getTempsRemplissage() {
    	return (this.getCapaciteMax()-this.getVolumeEau())*5*60/2000;
    }

    @Override
    public void paint(Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        graphics.drawImage(image, 0, 0, bounds.width, bounds.height, null);
    }

}