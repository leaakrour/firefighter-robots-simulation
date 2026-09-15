package org.app.data.robots;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.app.ai.AlgoPlusCC;
import org.app.ai.Noeud;
import org.app.data.Carte;
import org.app.data.Case;
import org.app.data.Direction;
import org.app.data.NatureTerrain;
import org.app.data.Robot;
import org.app.exceptions.RemplissageImpossibleException;
import org.app.io.ResourceConsumer;

/**
 * Représente un drone.
 */
public class RobotDrone extends Robot {

    /**
     * Vitesse par défaut du drone sans les contraintes de terrain.
     */
    private double vitesseDeBase;

    /**
     * Vitesse par défaut dans le constructeur sans vitesse.
     */
    private static final double VITESSE_PAR_DEFAUT = 100;

    /**
     * Vitesse limite du drone.
     */
    private static final double VITESSE_LIMITE = 150;

    /**
     * Capacite en litre du drone.
     */
    private static final int CAPACITE = 10000;

    /**
     * Débit du robot drone.
     */
    private static final double DEBIT_MAXIMAL = 333.333;

    /**
     * L'image représentant le robot a pattes.
     */
    private static BufferedImage image = null;

    static {
        try {
            image = ImageIO
                    .read(new ResourceConsumer("/textures/drone.jpg",
                            "app.data").getResources());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un drone sur la case donnée.
     * @param position
     */
    public RobotDrone(final Case position, final Carte carte) {
        super(position, carte);
        this.vitesseDeBase = VITESSE_PAR_DEFAUT;
    }

    /**
     * Crée un drone sur la case donnée et lui attribue la vitesse souhaitée (
     * inf à 150 km/h).
     * @param position Position du drone
     * @param vitesse Vitesse du drone
     */
    public RobotDrone(final Case position, final double vitesse, final Carte carte) {
        super(position, carte);
        if (this.vitesseDeBase > VITESSE_LIMITE) {
            throw new IllegalArgumentException(
                    "Vitesse > " + VITESSE_LIMITE + " km/h");
        }
        this.vitesseDeBase = vitesse;
    }

    /**
     * Retourne la vitesse du drone sur le terrain donné.
     * @param terrain Le type terrain considéré
     * @return La vitesse sur ce type de terrain en km/h retournée en double
     */
    @Override
    public double getVitesse(final NatureTerrain terrain) {
        return this.vitesseDeBase;
    }
    
    @Override
    public void remplirReservoir() {
    	try {
    		super.remplirReservoir();
    	}
    	catch(RemplissageImpossibleException e) {
    		if(this.position.getNature() == NatureTerrain.EAU) {
    			this.volumeEau = this.getCapaciteMax();
    		}
    		else {
    			throw new RemplissageImpossibleException();
    		}
    	}
    }

    /**
     * Retourne la capacité maximale du drone.
     * @return La capacité maximale du drone en litres sour forme d'un int
     */
    @Override
    public int getCapaciteMax() {
        return CAPACITE;
    }

    /**
     * Retoune le debit maximal.
     * @return le débit
     */
    @Override
    public double getDebitMax() {
        return DEBIT_MAXIMAL;
    }
    
    /**
     * Retourne le temps nécessaire pour le remplissage total du réservoir.
     * @return le temps en min.
     */
    @Override
    public double getTempsRemplissage() {
    	return (this.getCapaciteMax()-this.getVolumeEau())*30*60/10000;	
    }
    
    @Override
    public Case eauLaPlusProche() {
        List<Case> caseEau = Stream
                .of(this.carte.getCarte())
                .flatMap(Stream::of)
                .filter(currentCase -> currentCase
                        .getNature() == NatureTerrain.EAU)
                .collect(Collectors.toList());
        HashMap<Case, Double> costs = new HashMap<Case, Double>();
        for (Case c : caseEau) {
            LinkedList<Noeud> noeuds = AlgoPlusCC.findPlusCC(this.getPosition(),
                    c, this);
            if (!noeuds.isEmpty()) {
                costs.put(c, noeuds.getLast().getTemps());
            }
        }
        Optional<Entry<Case, Double>> optional = costs.entrySet().stream().min((
                entry1,
                entry2) -> (int) (entry1.getValue() - entry2.getValue()));
        return !optional.isEmpty() ? optional.get().getKey() : null;
    }
    
    @Override
    public void paint(Graphics2D graphics) {
        Rectangle bounds = graphics.getClipBounds();
        graphics.drawImage(image, 0, 0, bounds.width, bounds.height, null);
    }
}
