package com.ackincolor.rangement.models;

import java.util.UUID;

public class ObjetSeparator extends Objet {
    private String rangementName;
    public ObjetSeparator(String id){
        this.rangementName = id;
        super.isSeparator = true;
    }

    public String getRangementName() {
        return rangementName;
    }
}
