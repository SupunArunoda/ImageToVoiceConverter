
package com.yalantis.cameramodule.model;

public enum HDRMode {

    NONE(0, "None"), ON(1, "On"), OFF(2, "Off");

    private int id;
    private String name;

    HDRMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public static HDRMode getHDRModeById(int id) {
        for (HDRMode mode : values()) {
            if (mode.id == id) {
                return mode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

}
