package com.example.items;

import com.example.interfaces.IInventory;

public abstract class Thing implements IInventory {
    private int _number;

    @Override
    public int getNumber() {
        return _number;
    }

    @Override
    public void setNumber(int x) {
        this._number = x;
    }
}
