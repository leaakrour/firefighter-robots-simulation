package org.app.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;

public final class ResourceConsumer implements Consumer<Module> {

    /**
     * Le Stream associé à la ressource.
     */
    private InputStream inputStream = null;

    /**
     * Le path lié à la ressource.
     */
    private String path;

    /**
     * Le nom du module dans lequel se trouve la ressource.
     */
    private String module;

    /**
     * Contruit un ResourceConsumer.
     * @param chemin : Le chemin vers la ressource.
     * @param nomModule : Le nom du module considéré.
     */
    public ResourceConsumer(final String chemin, final String nomModule) {
        this.path = chemin;
        this.module = nomModule;
    }

    /**
     * Retourne le stream associé à la ressource.
     * @return le stream associé à la resource.
     */
    public InputStream getResources() {
        Optional<Module> specificModule = ModuleLayer.boot().findModule(module);
        specificModule.ifPresent(this);
        return inputStream;
    }

    @Override
    public void accept(final Module mod) {
        try {
            this.inputStream = mod.getResourceAsStream(this.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
