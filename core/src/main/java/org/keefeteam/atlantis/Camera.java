package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;

import java.util.List;


public class Camera implements Entity {
    int follow;
    WorldCoordinate pos;
    public void renderImage(SpriteBatch batch, Texture texture, WorldCoordinate position) {
        // TODO: Roman
        batch.draw(texture, position.getCoord().x, position.getCoord().y);
        //X is a coord on the screen not the world. WorldCoodinate is for the world, translate the world coordinate to
        //the x and y

        //look up libgdx screen coordinates
        //screen cordinates start at top left
        //world coodinates start at bottom left

        //camera has its own coordinates (WorldCoordinate pos)

    }

    @Override
    public void update(GameState gameState, List<InputEvent> events) {
        this.updateTheCamera();
    }
    public void updateTheCamera(WorldCoordinate c){

    }
}
