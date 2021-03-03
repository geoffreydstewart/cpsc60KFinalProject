package org.gds.disc;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.gds.Constants;

public class UIDisc extends Circle implements Disc {
    private final boolean red;

    public UIDisc(boolean red) {
        super(Constants.TILE_RADIUS, red ? Color.RED : Color.YELLOW);
        this.red = red;
        setCenterX(Constants.TILE_RADIUS);
        setCenterY(Constants.TILE_RADIUS);
    }

    @Override
    public boolean isRed() {
        return red;
    }
}
