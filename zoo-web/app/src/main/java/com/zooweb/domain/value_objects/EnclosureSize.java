package com.zooweb.domain.value_objects;


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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
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