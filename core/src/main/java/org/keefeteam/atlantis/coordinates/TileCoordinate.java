package org.keefeteam.atlantis.coordinates;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TileCoordinate extends Coordinate {
    int x, y;
    private static final float TILE_SIZE = 64.0f;
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
