package org.keefeteam.atlantis.coordinates;

import com.badlogic.gdx.math.Vector2;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WorldCoordinate extends Coordinate {
    Vector2 coord;

    @Override
    public WorldCoordinate toWorldCoordinate() {
        return this;
    }

    @Override
    public void fromWorldCoordinate(WorldCoordinate coordinate) {
        this.coord = coordinate.getCoord();
    }

    public static WorldCoordinate addWorldCoordinates(WorldCoordinate a, WorldCoordinate b) {
        return new WorldCoordinate(a.getCoord().add(b.getCoord()));
    }
}
