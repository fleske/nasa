package com.plenigo.nasa.model;

public enum NasaImageFormatEnum {

    PNG,
    JPG,
    THUMBS;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
