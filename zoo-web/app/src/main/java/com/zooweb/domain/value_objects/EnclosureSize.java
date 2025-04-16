package com.zooweb.domain.value_objects;


import lombok.Getter;

@Getter
public class EnclosureSize {
    private int width;
    private int height;
    private int length;

    public EnclosureSize(int width, int height, int length) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be greater than 0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public String toString() {
        return "EnclosureSize{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                '}';
    }
}