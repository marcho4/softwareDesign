package com.zooweb.domain.value_objects;


import lombok.Getter;

@Getter
public class EnclosureSize {
    private int width;
    private int height;
    private int length;

    public EnclosureSize(int width, int height, int length) {
        if (width <= 0) {
            throw new IllegalArgumentException("Ширина должна быть больше 0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Высота должна быть больше 0");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Длина должна быть больше 0");
        }
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Размер клетки: " +
                "Ширина=" + width +
                ", Высота=" + height +
                ", Длина=" + length +
                '}';
    }
}