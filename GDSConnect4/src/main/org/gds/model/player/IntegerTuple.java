package org.gds.model.player;

public class IntegerTuple {
    public Integer x;
    public Integer y;

    public IntegerTuple(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public IntegerTuple add(Integer var1, Integer var2) {
        return new IntegerTuple(this.x + var1, this.y + var2);
    }
}
