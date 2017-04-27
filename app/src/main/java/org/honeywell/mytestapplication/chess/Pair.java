package org.honeywell.mytestapplication.chess;

/**
 * Created by yash on 14/04/17.
 */

public class Pair {
    public int x;
    public int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (x != pair.x) return false;
        return y == pair.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
