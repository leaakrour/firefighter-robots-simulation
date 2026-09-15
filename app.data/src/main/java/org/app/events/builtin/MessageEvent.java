package org.app.events.builtin;

import org.app.events.Evenement;

public class MessageEvent extends Evenement {

    private String message;

    public MessageEvent(long date, String message) {
        super(date);
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println(String.format("MESSAGE AT %d: %s", date, message));
    }

}
