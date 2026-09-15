package org.app.exceptions;

public final class RemplissageImpossibleException extends RuntimeException {

    private static final long serialVersionUID = -222897895070769502L;

    /**
     * Le message de l'erreur.
     */
    private static final String MESSAGE = "Le robot ne peut pas"
            + " se remplir. Il faut changer sa position";

    /**
     * Remplissage d'un robot sur une case non autorisée.
     */
    public RemplissageImpossibleException() {
        super(MESSAGE);
    }
}
