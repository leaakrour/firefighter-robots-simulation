package org.app.exceptions;

public final class AucuneIncendieException extends RuntimeException {

    private static final long serialVersionUID = 1525696658032210832L;

    /**
     * Le message de l'exception.
     */
    private static final String MESSAGE = "Aucune incendie n'a été "
            + "détectée sur la case du robot";

    /**
     * Construit une Incendie Exception.
     */
    public AucuneIncendieException() {
        super(MESSAGE);
    }
}
