/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.entities.Player;
import org.keefeteam.atlantis.util.ClickableLabel;
import org.keefeteam.atlantis.util.Item;
import org.keefeteam.atlantis.util.input.InputEvent;


import java.util.List;
import java.util.Set;

public class InventoryMenu implements Menu {
    private Player player;
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    private Table menu;
    private Table descMenu;
    private boolean fframe;
    private boolean submenu;
    private Label controlsLabel;
    private  ClickableLabel descLabel;
    private boolean isHover;
    public InventoryMenu(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState g) {
        isHover = false;
        this.fframe = true;
        this.submenu = false;
        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();
        Skin tempSkin = new Skin();
        tempSkin.add("default-font", font);
        tempSkin.add("default", new Label.LabelStyle(font, Color.WHITE));
        skin = tempSkin;
        controlsLabel = new Label("Press Esc to Return", skin);
        //skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);
        menu = new Table();
        descMenu = new Table();

        menu.setFillParent(true);
        descMenu.setFillParent(true);
        menu.add(controlsLabel);
        menu.row();

        //adds three temporary items to the inventory for debugger purposes
        for(int i = 0; i < 3; i++){
            Item goofyTemp = new Item("ITEMNAME", "ITEMDESC");
            player.addItem(goofyTemp);
        }
        int size = player.getInventory().size();
        for(int i = 0; i < size; i++){
            Item current = player.getInventory().get(i);
            System.out.println(current.getName() + "-" + current.getDescription());
            ClickableLabel nameLabel = new ClickableLabel(current.getName(), skin);

            nameLabel.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    // Put whatever you want the item to do here
                    System.out.println("Label clicked at: " + x + ", " + y);
                    if(!nameLabel.getIsClicked()){
                        nameLabel.setIsClicked(true);
                        nameLabel.setColor(Color.RED);
                    }
                    else{
                        nameLabel.setIsClicked(false);
                        nameLabel.setColor(Color.WHITE);
                    }
                }
            });
            nameLabel.addListener(new InputListener() {
                @Override
                public boolean mouseMoved(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    if(!isHover && !nameLabel.getIsClicked()){
                        nameLabel.setColor(Color.BLUE);
                        descMenu.row();
                        descLabel = new ClickableLabel(current.getDescription(), skin);
                        descMenu.add(descLabel).padLeft(150);
                    }
                    isHover = true;
                    return true;
                }

                @Override
                public void exit(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (!nameLabel.getIsClicked()) { // Only reset color if not clicked
                        nameLabel.setColor(Color.WHITE);
                    }
                    //descLabel = new Label("", skin);
                    descMenu.removeActor(descLabel);
                    isHover = false;
                }
            });
            menu.add(nameLabel).padLeft(-150);
            menu.row();
        }
        stage.addActor(menu);
        stage.addActor(descMenu);

    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(inputEvents.contains(InputEvent.Inventory) && !fframe){

            gameState.setMenu(null);
            menu.remove();
            descMenu.remove();
            this.gameState.setPaused(false);


        }
        fframe = false;
    }

    @Override
    public void dispose() {stage.dispose();}

}
