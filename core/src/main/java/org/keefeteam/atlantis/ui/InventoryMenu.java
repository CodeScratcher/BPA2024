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
import org.keefeteam.atlantis.SQLLoader;
import org.keefeteam.atlantis.entities.Player;
import org.keefeteam.atlantis.util.ClickableLabel;
import org.keefeteam.atlantis.util.Item;
import org.keefeteam.atlantis.util.input.InputEvent;


import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Label descLabel;
    private boolean isHover;
    private Item[] craftSelect;
    private int selectedItems;
    private Label combineLabel;
    private int resultInt;
    public InventoryMenu(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState g) {
        craftSelect = new Item[2];
        selectedItems = 0;
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

        //skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        controlsLabel = new Label("Press Esc to Return", skin);

        Gdx.input.setInputProcessor(stage);
        menu = new Table();
        descMenu = new Table();

        menu.setFillParent(true);
        descMenu.setFillParent(true);
        menu.add(controlsLabel);
        menu.row();

        //adds three temporary items to the inventory for debugger purposes
        /*
        for(int i = 0; i < 3; i++){
            Item goofyTemp = new Item("ITEMNAME" + i, "ITEMDESC");
            player.addItem(goofyTemp);
        }
        */
        int size = player.getInventory().size();
        for(int i = 0; i < size; i++){
            Item current = player.getInventory().get(i);
            System.out.println(current.getName() + "-" + current.getDescription());
            ClickableLabel nameLabel = new ClickableLabel(current.getName(), skin, current);

            nameLabel.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    // Put whatever you want the item to do here
                    System.out.println("Label clicked at: " + x + ", " + y);
                    if(!nameLabel.getIsClicked()){
                        if(selectedItems < 2){
                            if(craftSelect[0] == null){
                                craftSelect[0] = nameLabel.getLinkedItem();
                            }
                            else if(craftSelect[1] == null){
                                craftSelect[1] = nameLabel.getLinkedItem();
                            }
                            nameLabel.setIsClicked(true);
                            nameLabel.setColor(Color.RED);
                            selectedItems++;
                        }

                    }
                    else{
                        for(int i = 0; i < craftSelect.length; i++){
                            if(craftSelect[i] != null){
                                if(craftSelect[i].getName().equals(nameLabel.getLinkedItem().getName())){
                                    craftSelect[i] = null;
                                    break;
                                }
                            }
                        }
                        nameLabel.setIsClicked(false);
                        nameLabel.setColor(Color.WHITE);
                        selectedItems--;
                    }
                    if(craftSelect[0] != null && craftSelect[1] != null){
                        //Check to see if items can combine
                        int rowCount = 0;
                        SQLLoader conn = new SQLLoader("itemsdb");
                        ResultSet rslt = null;
                        rslt = conn.select("SELECT * FROM recipes WHERE item_id = "
                            + craftSelect[0].getId() + " AND combines_with_id =" + craftSelect[1].getId());
                        try{
                            while(rslt.next()){
                                rowCount++;
                                resultInt = rslt.getInt(1);
                                System.out.println(rslt.getInt(2));
                                System.out.println(rslt.getInt(3));
                            }
                        }
                        catch(SQLException e){
                            e.printStackTrace();
                        }
                        if(rowCount == 0){
                            // try again but swap the vars
                            rslt = conn.select("SELECT * FROM recipes WHERE item_id = "
                                + craftSelect[1].getId() + " AND combines_with_id =" + craftSelect[0].getId());
                            try{
                                while(rslt.next()){
                                    resultInt = rslt.getInt(1);
                                    System.out.println(rslt.getInt(2));
                                    System.out.println(rslt.getInt(3));
                                }
                            }
                            catch(SQLException e){
                                e.printStackTrace();
                            }
                        }
                        if(rowCount != 0){
                            combineLabel = new Label("COMBINE", skin);
                            combineLabel.addListener(new ClickListener() {
                                @Override
                                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                                    System.out.println("CLICKED COMBINE");
                                    SQLLoader conn = new SQLLoader("itemsdb");
                                    ResultSet rslt = null;
                                    rslt = conn.select("SELECT * FROM items WHERE id = " + resultInt);
                                    try{
                                        while(rslt.next()){
                                            System.out.println(rslt.getString(2));
                                            player.removeItem(craftSelect[0]);
                                            player.removeItem(craftSelect[1]);
                                            craftSelect[0] = null;
                                            craftSelect[1] = null;
                                            Item newItem = new Item(rslt.getString(2));
                                            player.addItem(newItem);
                                            dispose();
                                            initialize(g);
                                        }
                                    }
                                    catch(SQLException e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                            menu.add(combineLabel);
                            menu.row();
                        }
                    }
                    else if(combineLabel != null){
                        menu.removeActor(combineLabel);
                    }
                }
            });
            nameLabel.addListener(new InputListener() {
                @Override
                public boolean mouseMoved(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    if(!isHover){
                        if(!nameLabel.getIsClicked()){
                            nameLabel.setColor(Color.BLUE);
                        }
                        descMenu.row();
                        descLabel = new Label(current.getDescription(), skin);
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
