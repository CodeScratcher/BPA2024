/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.entities.Player;
import org.keefeteam.atlantis.util.Item;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;
import java.util.Set;

/**
 *
 * @author afisher
 */
public class InventoryMenu implements Menu {
    private Player player;
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    private Table menu;
    public InventoryMenu(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState g) {
        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();

        BitmapFont font = new BitmapFont();
        Skin tempSkin = new Skin();
        tempSkin.add("default-font", font);
        tempSkin.add("default", new Label.LabelStyle(font, Color.WHITE));
        skin = tempSkin;
        //skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);
        menu = new Table();
        menu.setFillParent(true);

    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        if(inputEvents.contains(InputEvent.Inventory)){
            int size = player.getInventory().size();
            for(int i = 0; i < size; i++){
                Item current = player.getInventory().get(i);
                System.out.println(current.getName() + "-------" + current.getDescription());

                Label l = new Label(current.getName() + "-------" + current.getDescription(), skin);
                l.addListener(new ClickListener() {
                    @Override
                    public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                        // Put whatever you want the item to do here
                        System.out.println("Label clicked at: " + x + ", " + y);
                    }
                });

                menu.add(l);
            }
        }
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
