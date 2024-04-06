package engine.serilaize;

import engine.Engine;

import java.io.Serializable;
import java.util.Date;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Engine system;
    private final Date savedDate;

    public SaveData(Engine system) {
        this.system = system;
        this.savedDate = new Date();
    }

    public Date getSavedDate() {
        return savedDate;
    }

    public Engine getSystem() {
        return system;
    }
}