package org.keefeteam.atlantis;

import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.coordinates.TileCoordinate;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Tilemap implements Entity {
    private Map<TileCoordinate, Tile> tiles;

    @Override
    public void update(GameState gameState, List<InputEvent> events) {

    }


}
