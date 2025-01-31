package org.keefeteam.atlantis.util.coordinates;

import com.badlogic.gdx.math.Vector2;
import lombok.*;

/**
 * A coordinate in world space
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class WorldCoordinate extends Coordinate {
    /**
     * The underlying representation: a point represented as a vector
     */
    private Vector2 coord;

    @Override
    public WorldCoordinate toWorldCoordinate() {
        return this;
    }

    @Override
    public void fromWorldCoordinate(WorldCoordinate coordinate) {
        this.coord = coordinate.getCoord();
    }

    /**
     * Add two world coordinates together
     * @param a The first coordinate
     * @param b The second coordinate
     * @return A world coordinate that is both coordinates combined
     */
    public static WorldCoordinate addWorldCoordinates(WorldCoordinate a, WorldCoordinate b) {
        return new WorldCoordinate(a.getCoord().add(b.getCoord()));
    }
}
