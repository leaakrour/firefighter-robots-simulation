package org.app.data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gui.GraphicalElement;

/**
 * Représente les données de simulation.
 */
public class DonneesSimulation implements GraphicalElement {

    /**
     * La Carte sur laquelle on fait les simulations.
     */
    private Carte carteSim;
    /**
     * Liste des incendies du scénario.
     */
    private Incendie[] incendiesSim;
    /**
     * Liste des robots du scénario.
     */
    private Robot[] robotsSim;

    /**
     * Nom du module.
     */
    private String name;

    /**
     * Construit les données de simulation.
     * 
     * @param carte : La carte utilisée pour la simulation
     * @param incendies : Un tableau regroupant les éventuels incendies (
     * @param robots : Un tableau regroupant les robots (au moins 1)
     */
    public DonneesSimulation(final String name, final Carte carte,
            final Incendie[] incendies, final Robot[] robots) {
        this.carteSim = carte;
        this.incendiesSim = incendies;
        this.robotsSim = robots;
        this.name = name;
    }

    /**
     * Copie les donnees de simulation.
     * 
     * @param donnee Les donnees a copier
     */
    public DonneesSimulation(final String name,
            final DonneesSimulation donnee) {
        this.carteSim = donnee.carteSim;
        this.incendiesSim = donnee.incendiesSim;
        this.robotsSim = donnee.robotsSim;
        this.name = name;
    }

    /**
     * Getter de carteSim.
     * 
     * @return carteSim
     */
    public Carte getCarteSim() {
        return carteSim;
    }

    /**
     * Getter de incendiesSim.
     * 
     * @return incendiesSim
     */
    public Incendie[] getIncendiesSim() {
        return incendiesSim;
    }

    /**
     * Getter de robotsSim.
     * 
     * @return robotsSim
     */
    public Robot[] getRobotsSim() {
        return robotsSim;
    }

    /**
     * Getter du nom.
     * 
     * @return le nom du set de données.
     */
    public String getName() {
        return name;
    }

    @Override
    public void paint(Graphics2D graphics) {
        this.carteSim.paint(graphics);
        Rectangle bounds = graphics.getClipBounds();
        int width = bounds.width / this.carteSim.getNbColonnes();
        int height = bounds.height / this.carteSim.getNbLignes();
        for (Robot robot : this.robotsSim) {
            if (robot != null) {
                BufferedImage subspace = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = subspace.createGraphics();
                g.setClip(0, 0, width, height);
                robot.paint(g);
                graphics.drawImage(subspace,
                        width * robot.getPosition().getColonne(),
                        height * robot.getPosition().getLigne(), null);
            }
        }
        for (Incendie incendie : this.incendiesSim) {
            if (incendie != null && incendie.getLitreEau() != 0) {
                BufferedImage subspace = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = subspace.createGraphics();
                g.setClip(0, 0, width, height);
                incendie.paint(g);
                graphics.drawImage(subspace,
                        width * incendie.getCaseIncendie().getColonne(),
                        height * incendie.getCaseIncendie().getLigne(), null);
            }
        }
    }
}
