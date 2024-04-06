package Model;

import engine.Engine;

public class Model {
    private static Model model;
    private final Engine engine;

    private Model() {
        this.engine = new Engine();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    public Engine getEngine() {
        return engine;
    }
}
