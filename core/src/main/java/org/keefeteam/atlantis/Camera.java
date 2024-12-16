package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;

import java.time.OffsetTime;
import java.util.List;
@Getter
public class Camera implements Entity{
    private int follow;
    private WorldCoordinate pos;
    private OrthographicCamera camera;

    public Camera(WorldCoordinate playerPos) {
        this.pos = playerPos;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // Set the viewport width and height.



    }
    public void update(GameState gameState, List<InputEvent> events){

        Vector2 camCoords = pos.getCoord();
        camera.position.set(camCoords.x, camCoords.y, 0);  // Set the camera position.
        camera.update();
    }

}
