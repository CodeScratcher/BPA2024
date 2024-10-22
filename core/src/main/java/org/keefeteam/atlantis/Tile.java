package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.keefeteam.atlantis.util.Triangle;

import java.util.List;

@Getter
@AllArgsConstructor
public class Tile {
    private List<Triangle> colliders;
    private Texture texture;
}
