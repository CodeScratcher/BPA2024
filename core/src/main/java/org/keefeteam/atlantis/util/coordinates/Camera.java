package org.keefeteam.atlantis.util.coordinates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import org.keefeteam.atlantis.entities.Entity;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;
@Getter
public class Camera implements Entity {
    private int follow;
    private WorldCoordinate pos;
    private OrthographicCamera camera;

    public Camera(WorldCoordinate playerPos) {
        this.pos = playerPos;
        camera = new OrthographicCamera();
    }
    public void update(GameState gameState, List<InputEvent> events){

        Vector2 camCoords = pos.getCoord();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Set the width and height
        camera.position.set(camCoords.x + 31, camCoords.y + 31, 0);  // Set the camera position to the player
        camera.update();
    }

}
