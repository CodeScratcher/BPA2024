package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;

import java.util.List;

public class Camera {
    int follow;
    WorldCoordinate pos;
    public Camera(WorldCoordinate playerPos){
        this.pos = playerPos;
    }

    public void renderImage(Texture texture, WorldCoordinate position) {
        // TODO: Roman

    }
    public void update(GameState gameState, List<InputEvent> events){
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);  // Set the viewport width and height.
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);  // Set the camera position.
        camera.update();
    }
}
