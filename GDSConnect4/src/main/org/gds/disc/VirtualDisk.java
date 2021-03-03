package org.gds.disc;

public class VirtualDisk implements Disc{
    private final boolean red;

    VirtualDisk(boolean red) {
        this.red = red;
    }

    @Override
    public boolean isRed() {
        return red;
    }

    @Override
    public void setTranslateX(double var1) {
        return;
    }

    @Override
    public void setTranslateY(double var1) {
        return;
    }

}
