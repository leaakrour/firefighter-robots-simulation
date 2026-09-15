package org.app.data.robots;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.app.data.Carte;
import org.app.data.Case;
import org.app.data.Direction;
import org.app.data.NatureTerrain;
import org.app.data.Robot;
import org.app.exceptions.RemplissageImpossibleException;
import org.app.io.ResourceConsumer;

import gui.GraphicalElement;

/**
 * Représente un robot à pattes.
 */
public class RoboAPattes extends Robot implements GraphicalElement {
    /**
     * Vitesse de base du robot à pattes en km/h.
     */
    private static final int VITESSE_DE_BASE = 30;

    /**
     * Vitesse du Robot à pattes sur les rochers en km/h.
     */
    private static final int VITESSE_ROCHER = 10;

    /**
     * Le robot à patte ne peut pas se rendre dans l'eau.
     */
    private static final int VITESSE_EAU = -1;

    /**
     * Intervention unitaire : 10 litres en 1 sec.
     */
    private static final double DEBIT = 10 / 1;

    /**
     * L'image représentant le robot a pattes.
     */
    private static BufferedImage image = null;

    static {
        try {
            image = ImageIO
                    .read(new ResourceConsumer("/textures/robot_pattes.jpg",
                            "app.data").getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un robot à pattes sur la case donnée.
     * @param position Position du robot à pattes
     */
    public RoboAPattes(final Case position, final Carte carte) {
        super(position, carte);
        this.volumeEau = Integer.MAX_VALUE;
    }

    /**
     * Attribution d'une vitesse au robot à pattes selon la nature du terrain.
     * @param terrain : Nature du terrain sur lequel est le robot à pattes
     * @return vitesse du robot à pattes
     */
    @Override
    public double getVitesse(final NatureTerrain terrain) {
        return switch (terrain) {
        case ROCHE -> VITESSE_ROCHER;
        case EAU -> VITESSE_EAU;
        default -> VITESSE_DE_BASE;
        };
    }

    /**
     * Retourne la capacité maximale du robot à pattes.
     * @return La capacité maximale du robot à pattes en litre sous forme d'un
     * int
     */
    @Override
    public int getCapaciteMax() {
        return Integer.MAX_VALUE;
    }

    /**
     * Retoune le débit maximal du robot à pattes.
     * @return le débit 10litres/s
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
    	if(positionRobot.getNature() != NatureTerrain.EAU) {
    		super.setPosition(positionRobot);
    	}
    }
    
    /**
     * Remplit le réservoir jusqu'à la quantité maximale.
     */
    public void remplirReservoir() {
    	this.volumeEau = this.getCapaciteMax();   
    }

    
    @Override
    public double getTempsRemplissage() {
    	return 0;
    }
    
    @Override
    public void setVolumeEau(int volumeEau) {
    	this.volumeEau = Integer.MAX_VALUE;
    }
    
    @Override
    public int getVolumeEau() {
    	return Integer.MAX_VALUE;
    }
    
    /**
     * Dessine un robot a pattes.
     */
    @Override
    public void paint(final Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        graphics.drawImage(image, 0, 0, bounds.width, bounds.height, null);
    }

}
