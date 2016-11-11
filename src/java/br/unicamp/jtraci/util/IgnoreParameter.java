package br.unicamp.jtraci.util;

/**
 * Created by Du on 10/11/16.
 */
public class IgnoreParameter {

    private int windowIgnore;

    public IgnoreParameter(int windowIgnore){
        this.setWindowIgnore(windowIgnore);
    }

    public int sumRange(int window){
        return this.getWindowIgnore() + window;
    }

    public int getWindowIgnore() {
        return windowIgnore;
    }

    private void setWindowIgnore(int windowIgnore) {
        this.windowIgnore = windowIgnore;
    }
}
