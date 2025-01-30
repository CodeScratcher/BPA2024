package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * An object that can be rendered
 */
public interface Renderable {
    /**
     * Render the object
     * @param batch The spritebatch to render onto
     */
    void render(SpriteBatch batch);
}
