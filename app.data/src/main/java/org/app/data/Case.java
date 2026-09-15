package org.app.data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.app.data.robots.RoboAPattes;
import org.app.io.ResourceConsumer;

import gui.GraphicalElement;

/**
 * Représente une case.
 */
public class Case implements GraphicalElement {
    /**
     * La ligne sur laquelle se trouve la case.
     */
    private int ligne;
    /**
     * La colonne sur laquelle se trouve la case.
     */
    private int colonne;
    /**
     * La Nature du terrain de la case.
     */
    private NatureTerrain nature;

    /**
     * Image représentant l'eau.
     */
    private static BufferedImage eau;

    /**
     * Image représentant la foret.
     */
    private static BufferedImage foret;

    /**
     * Image représentant la roche.
     */
    private static BufferedImage roche;

    /**
     * Image représentant terrain libre.
     */
    private static BufferedImage terrainLibre;

    /**
     * Image représentant les habitations.
     */
    private static BufferedImage habitat;

    static {
        try {
            eau = ImageIO
                    .read(new ResourceConsumer("/textures/eau.jpg", "app.data")
                            .getResources());
            foret = ImageIO.read(
                    new ResourceConsumer("/textures/arbre.jpg", "app.data")
                            .getResources());
            roche = ImageIO.read(
                    new ResourceConsumer("/textures/montagne.jpg", "app.data")
                            .getResources());
            terrainLibre = ImageIO
                    .read(new ResourceConsumer("/textures/sol.jpg", "app.data")
                            .getResources());
            habitat = ImageIO.read(
                    new ResourceConsumer("/textures/maison.jpg", "app.data")
                            .getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construit une case.
     * 
     * @param caseLigne : numéro de la ligne où se trouve la case
     * @param caseColonne : numéro de la colonne où se trouve la case
     * @param natureTerrain : nature du terrain
     * @see org.app.data.NatureTerrain
     */
    public Case(final int caseLigne, final int caseColonne,
            final NatureTerrain natureTerrain) {
        this.ligne = caseLigne;
        this.colonne = caseColonne;
        this.nature = natureTerrain;
    }

    /**
     * Getter de l'attribut ligne.
     * 
     * @return ligne
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Getter de l'attribut colonne.
     * 
     * @return colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Getter de l'attribut nature.
     * 
     * @return nature
     */
    public NatureTerrain getNature() {
        return nature;
    }

    @Override
    public final void paint(final Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        switch (this.nature) {
        case EAU -> {
            graphics.drawImage(eau, 0, 0, bounds.width, bounds.height, null);
        }
        case FORET -> {
            graphics.drawImage(foret, 0, 0, bounds.width, bounds.height, null);
        }
        case HABITAT -> {
            graphics.drawImage(habitat, 0, 0, bounds.width, bounds.height,
                    null);
        }
        case ROCHE -> {
            graphics.drawImage(roche, 0, 0, bounds.width, bounds.height, null);
        }
        case TERRAIN_LIBRE -> {
            graphics.drawImage(terrainLibre, 0, 0, bounds.width, bounds.height,
                    null);
        }
        default -> {
        }
        }
    }

    @Override
    public String toString() {
        return "(" + this.colonne + ", " + this.ligne + "); " + this.nature;
    }
}
