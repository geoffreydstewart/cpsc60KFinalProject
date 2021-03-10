package org.gds.model.disc;

public class VirtualDisc implements Disc {
    private final boolean red;

    public VirtualDisc(boolean red) {
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
