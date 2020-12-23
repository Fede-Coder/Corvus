package com.srfairyox.Corvus;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Gate {
    private final SimpleStringProperty name;
    private final SimpleStringProperty parts;
    private final SimpleBooleanProperty ready;
    private final SimpleBooleanProperty onMap;
    private final SimpleIntegerProperty gatesBuilt;

    public Gate(String name, String parts, Boolean ready, Boolean onMap, int gatesBuilt) {
        this.name = new SimpleStringProperty(name);
        this.parts = new SimpleStringProperty(parts);
        this.ready = new SimpleBooleanProperty(ready);
        this.onMap = new SimpleBooleanProperty(onMap);
        this.gatesBuilt = new SimpleIntegerProperty(gatesBuilt);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getParts() {
        return parts.get();
    }

    public SimpleStringProperty partsProperty() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts.set(parts);
    }

    public boolean isReady() {
        return ready.get();
    }

    public SimpleBooleanProperty readyProperty() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready.set(ready);
    }

    public boolean isOnMap() {
        return onMap.get();
    }

    public SimpleBooleanProperty onMapProperty() {
        return onMap;
    }

    public void setOnMap(boolean onMap) {
        this.onMap.set(onMap);
    }

    public int getGatesBuilt() {
        return gatesBuilt.get();
    }

    public SimpleIntegerProperty gatesBuiltProperty() {
        return gatesBuilt;
    }

    public void setGatesBuilt(String gatesBuilt) {
        this.gatesBuilt.set(Integer.parseInt(gatesBuilt));
    }

    @Override
    public String toString() {
        return "Gate{" +
                "name=" + name +
                ", parts=" + parts +
                ", ready=" + ready +
                ", onMap=" + onMap +
                ", gatesBuilt=" + gatesBuilt +
                '}';
    }
}
