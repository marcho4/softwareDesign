package com.finance.interfaces;

public interface Command extends Executable, HasName {
    public boolean execute();
    public String getName();
}
