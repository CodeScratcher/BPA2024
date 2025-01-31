package org.keefeteam.atlantis.util.coordinates;

/**
 * A coordinate in any space
 */
public abstract class Coordinate {
    /**
     * Convert to coordinate in world space
     * @return A coordinate in world space
     */
    public abstract WorldCoordinate toWorldCoordinate();

    /**
     * Convert from a coordinate in world space
     * @param coordinate The world space coordinate to convert from
     */
    public abstract void fromWorldCoordinate(WorldCoordinate coordinate);

    /**
     * Add two coordinates together
     * @param a The first coordinate
     * @param b The second coordinate
     * @return A coordinate in any space
     */
    public static Coordinate addCoordinates(Coordinate a, Coordinate b) {
        return WorldCoordinate.addWorldCoordinates(a.toWorldCoordinate(), b.toWorldCoordinate());
    }

}
