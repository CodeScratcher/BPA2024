package org.keefeteam.atlantis.coordinates;

public abstract class Coordinate {
    public abstract WorldCoordinate toWorldCoordinate();
    public abstract void fromWorldCoordinate(WorldCoordinate coordinate);
    public static Coordinate addCoordinates(Coordinate a, Coordinate b) {
        return WorldCoordinate.addWorldCoordinates(a.toWorldCoordinate(), b.toWorldCoordinate());
    }
}
