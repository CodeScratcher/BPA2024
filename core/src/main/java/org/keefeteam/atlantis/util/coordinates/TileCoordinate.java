package org.keefeteam.atlantis.util.coordinates;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A coordinate in tilespace (i.e a grid where x and y are given in units of tiles)
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TileCoordinate extends Coordinate {
    private int x, y;
    /**
     * The size of a tile in world space
     */
    public static final float TILE_SIZE = 16.0f;
    @Override
    public WorldCoordinate toWorldCoordinate() {
        return new WorldCoordinate(new Vector2(x * TILE_SIZE,y * TILE_SIZE));
    }

    @Override
    public void fromWorldCoordinate(WorldCoordinate coordinate) {
        x = (int)Math.floor(coordinate.getCoord().x / TILE_SIZE);
        y = (int)Math.floor(coordinate.getCoord().y / TILE_SIZE);
    }
}
