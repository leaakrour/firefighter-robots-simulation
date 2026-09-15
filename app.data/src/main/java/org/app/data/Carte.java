package org.app.data;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import gui.GraphicalElement;

/**
 * Représente une Carte.
 */
public class Carte implements GraphicalElement {
    /**
     * Taille d'une case (utilisé pour l'affichage des cases).
     */
    private int tailleCases;
    /**
     * Tableau 2D représentant la liste des cases.
     */
    private Case[][] carte;

    /**
     * Construit une Carte.
     * 
     * @param nbLignes : nombre de lignes de la carte
     * @param nbColonnes : nombre de colonnes de la carte
     * @param tailleCase : taille d'une case de la carte pour l'affichage
     */
    public Carte(final int nbLignes, final int nbColonnes,
            final int tailleCase) {
        this.carte = new Case[nbLignes][nbColonnes];
        this.tailleCases = tailleCase;
    }

    /**
     * Getter de l'attribut carte.
     * 
     * @return carte
     */
    public Case[][] getCarte() {
        return carte;
    }

    /**
     * Getter de l'attribut tailleCases.
     * 
     * @return tailleCases
     */
    public int getTailleCases() {
        return tailleCases;
    }

    /**
     * Determine le nombre de lignes de la carte.
     * 
     * @return nombre de lignes
     */
    public int getNbLignes() {
        return this.carte.length;
    }

    /**
     * Determine le nombre de colonnes de la carte.
     * 
     * @return nombre de colonnes
     */
    public int getNbColonnes() {
        return this.carte[0].length;
    }

    /**
     * Accéder à une case en fonction de ses coordonnées. Si ce n'est pas dans
     * les bornes, une exception est levée.
     * 
     * @exception IndexOutOfBoundsException
     * @param lig : numéro de la ligne
     * @param col : numéro de la colonne
     * @return case correspondante
     */
    public Case getCase(final int col, final int lig) {
        return this.carte[col][lig];
    }

    /**
     * Détermine si le voisin de la Case src à un voisin à la Direction dir.
     * 
     * @param src : La case dont on veut savoir si elle a un voisin
     * @param dir : La direction à laquelle on cherche un voisin
     * @return true si le voisin existe, false sinon
     */
    public boolean voisinExiste(final Case src, final Direction dir) {
        switch (dir) {
        case EST:
            if (src.getColonne() + 1 < this.getNbColonnes()) {
                return true;
            }
            break;
        case OUEST:
            if (src.getColonne() - 1 >= 0) {
                return true;
            }
            break;
        case NORD:
            if (src.getLigne() - 1 >= 0) {
                return true;
            }
            break;
        case SUD:
            if (src.getLigne() + 1 < this.getNbLignes()) {
                return true;
            }
            break;
        default:
            break;
        }
        return false;
    }

    /**
     * Renvoie la case voisine si elle existe. Si la case voisine n'existe pas,
     * on renvoie null
     * 
     * @param src : La case dont on veut savoir si elle a un voisin
     * @param dir : La direction à laquelle on cherche un voisin
     * @return voisin de la case src s'il existe, null sinon
     */
    public Case getVoisin(final Case src, final Direction dir) {
        if (voisinExiste(src, dir)) {
            switch (dir) {
            case EST:
                return getCase(src.getColonne() + 1, src.getLigne());
            case OUEST:
                return getCase(src.getColonne() - 1, src.getLigne());
            case NORD:
                return getCase(src.getColonne(), src.getLigne() - 1);
            case SUD:
                return getCase(src.getColonne(), src.getLigne() + 1);
            default:
                break;
            }
        }
        return null;
    }

    @Override
    public final void paint(final Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        int width = bounds.width / this.getNbColonnes();
        int height = bounds.height / this.getNbLignes();
        if (width > 0 && height > 0) {
            for (Case[] x : this.getCarte()) {
                for (Case y : x) {
                    BufferedImage subspace = new BufferedImage(width, height,
                            BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = subspace.createGraphics();
                    g.setClip(0, 0, width, height);
                    y.paint(g);
                    graphics.drawImage(subspace, width * y.getColonne(),
                            height * y.getLigne(), null);
                }
            }
        }
    }
}
