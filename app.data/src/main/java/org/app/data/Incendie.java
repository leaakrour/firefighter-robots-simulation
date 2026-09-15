package org.app.data;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.app.io.ResourceConsumer;

import gui.GraphicalElement;

/**
 * Représente l'objet incendie.
 */
public class Incendie implements GraphicalElement {
    /**
     * La case sur laquelle se trouve l'incendie.
     */
    private Case caseIncendie;
    /**
     * Le nombre de litres necessaires pour éteindre le feu.
     */
    private int litreEau;

    /**
     * Image représentant un incendie.
     */
    private static BufferedImage image;

    static {
        try {
            image = ImageIO
                    .read(new ResourceConsumer("/textures/feu.jpg", "app.data")
                            .getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construit un incendie.
     * 
     * @param emplacement : Un Incendie est simplement défini par sa position
     * (une Case)
     * @param litres : litres d’eau nécessaires pour l’éteindre
     * @see org.app.data.Case
     */
    public Incendie(final Case emplacement, final int litres) {
        this.caseIncendie = emplacement;
        this.litreEau = litres;
    }

    /**
     * Getter de l'attribut case_incendie.
     * 
     * @return case_incendie
     */
    public Case getCaseIncendie() {
        return caseIncendie;
    }

    /**
     * Getter de l'attribut litres_eau.
     * 
     * @return litres_eau
     */
    public int getLitreEau() {
        return litreEau;
    }

    /**
     * Setter de l'attribut litreEau
     * 
     * @param litreEau nouvelle quantité
     */
    public void setLitreEau(int litreEau) {
        this.litreEau = litreEau;
    }

    @Override
    public final void paint(final Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        graphics.drawImage(image, 0, 0, bounds.width, bounds.height, null);
    }

    @Override
    public String toString() {
        return "Incendie à " + this.caseIncendie + " litre restant: "
                + this.litreEau;
    }
}
