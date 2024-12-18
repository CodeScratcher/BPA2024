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
    private boolean fframe;
    public InventoryMenu(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState g) {
        this.fframe = true;
        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();
        Skin tempSkin = new Skin();
        tempSkin.add("default-font", font);
        tempSkin.add("default", new Label.LabelStyle(font, Color.WHITE));
        skin = tempSkin;
        //skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);
        menu = new Table();

        menu.setFillParent(true);
        //adds three temporary items to the inventory for debugger purposes
        for(int i = 0; i < 3; i++){
            Item goofyTemp = new Item("Goofy", "Temporary item");
            player.addItem(goofyTemp);
        }
        int size = player.getInventory().size();
        for(int i = 0; i < size; i++){
            Item current = player.getInventory().get(i);
            System.out.println(current.getName() + "-" + current.getDescription());
            Label nameLabel = new Label(current.getName(), skin);
            Label midLabel = new Label("-", skin);
            Label descLabel = new Label(current.getDescription(), skin);
            nameLabel.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    // Put whatever you want the item to do here
                    System.out.println("Label clicked at: " + x + ", " + y);
                }
            });

            menu.add(nameLabel);
            menu.add(midLabel);
            menu.add(descLabel);
            menu.row();
        }
        stage.addActor(menu);

    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(inputEvents.contains(InputEvent.Inventory) && !fframe){
            gameState.setMenu(null);
            menu.remove();
            this.gameState.setPaused(false);
        }
        fframe = false;
    }

    @Override
    public void dispose() {stage.dispose();}

}
