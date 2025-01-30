package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import org.keefeteam.atlantis.util.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.entities.Entity;
import org.keefeteam.atlantis.entities.Player;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
public class PlayerTest {
    private final Player player = new Player(mock(Texture.class));

    // Make sure that the player doesn't move no matter the delta
    // Should be trivial
    // Still worth testing
    @Test public void updateStationary() {
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        GameState gameState = new GameState(entities);


        Set<InputEvent> inputs = new HashSet<>();

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, 0)));

        float[] toTest = {
          0, 1, 0.01f, 10000, 513.15f, 0.16f, 5136f
        };

        for (float i : toTest) {
            gameState.setDelta(i);
            player.update(gameState, inputs);
            assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, 0)));
        }


    }

    @Test public void updateLeft() {
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        GameState gameState = new GameState(entities);

        gameState.setDelta(1);

        Set<InputEvent> inputs = new HashSet<>();
        inputs.add(InputEvent.Left);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(-Player.PLAYER_SPEED, 0)));


        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(-Player.PLAYER_SPEED  * 2, 0)));

        gameState.setDelta(0.5f);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(-Player.PLAYER_SPEED * 2.5f, 0)));
    }

    @Test public void updateRight() {
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        GameState gameState = new GameState(entities);

        gameState.setDelta(1);

        Set<InputEvent> inputs = new HashSet<>();
        inputs.add(InputEvent.Right);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(Player.PLAYER_SPEED, 0)));

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(Player.PLAYER_SPEED * 2, 0)));

        gameState.setDelta(0.5f);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(Player.PLAYER_SPEED * 2.5f, 0)));
    }

    @Test public void updateUp() {
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        GameState gameState = new GameState(entities);

        gameState.setDelta(1);

        Set<InputEvent> inputs = new HashSet<>();
        inputs.add(InputEvent.Up);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, Player.PLAYER_SPEED)));

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, Player.PLAYER_SPEED * 2)));

        gameState.setDelta(0.5f);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, Player.PLAYER_SPEED * 2.5f)));
    }

    @Test public void updateDown() {
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        GameState gameState = new GameState(entities);

        gameState.setDelta(1);

        Set<InputEvent> inputs = new HashSet<>();
        inputs.add(InputEvent.Down);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, -Player.PLAYER_SPEED)));

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, -Player.PLAYER_SPEED * 2)));

        gameState.setDelta(0.5f);

        player.update(gameState, inputs);

        assertEquals(player.getPosition(), new WorldCoordinate(new Vector2(0, -Player.PLAYER_SPEED * 2.5f)));
    }

    // Ensure diagonal movement is constant speed
    @Test public void updateDiagonal() {
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        GameState gameState = new GameState(entities);

        gameState.setDelta(1);

        Set<InputEvent> inputs = new HashSet<>();
        inputs.add(InputEvent.Down);
        inputs.add(InputEvent.Left);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED * 2);

        player.setPosition(new WorldCoordinate(new Vector2(0, 0)));
        inputs.remove(InputEvent.Down);
        inputs.add(InputEvent.Up);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED * 2);

        player.setPosition(new WorldCoordinate(new Vector2(0, 0)));
        inputs.remove(InputEvent.Left);
        inputs.add(InputEvent.Right);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED * 2);

        player.setPosition(new WorldCoordinate(new Vector2(0, 0)));
        inputs.remove(InputEvent.Up);
        inputs.add(InputEvent.Down);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED);

        player.update(gameState, inputs);

        assertEquals(player.getPosition().getCoord().dst(new Vector2(0, 0)), Player.PLAYER_SPEED * 2);

    }
}
