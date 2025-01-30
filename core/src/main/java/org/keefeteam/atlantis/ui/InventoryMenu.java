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

/**
 * A menu for representing the inventory
 */
public class InventoryMenu implements Menu {
    private Player player;
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    //This is the main menu
    private Table menu;
    //The description menu is different for positioning
    private Table descMenu;
    //fframe checks for the first frame, this solves a bug where the menu doesn't stay open
    private boolean fframe;
    //A different label that tells you that pressing esc can close the menu
    private Label controlsLabel;
    //Same thing but with the description
    private Label descLabel;
    //Checks if the label is hovered to set it blue
    private boolean isHover;
    //simply holds the two items to combine
    private Item[] craftSelect;
    //counts the items selected
    private int selectedItems;
    //Says combine when two applicable items are selected
    private Label combineLabel;
    //ID of the crafted item
    private int resultInt;
    public InventoryMenu(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState g) {

        //initialize all of the variables
        craftSelect = new Item[2];
        selectedItems = 0;
        isHover = false;
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

        //set up the menu and the descMenu
        skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        controlsLabel = new Label("Press Q to Return", skin);

        Gdx.input.setInputProcessor(stage);
        menu = new Table();
        descMenu = new Table();

        menu.setFillParent(true);
        descMenu.setFillParent(true);
        menu.add(controlsLabel);
        menu.row();
        // Get the size of the inventory and then print all the items out with labels
        int size = player.getInventory().size();
        for(int i = 0; i < size; i++){
            Item current = player.getInventory().get(i);
            System.out.println(current.getName() + "-" + current.getDescription());
            ClickableLabel nameLabel = new ClickableLabel(current.getName(), skin, current);

            // Set up the "Clickable" part of the clickable label
            nameLabel.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    // Put whatever you want the item to do here
                    System.out.println("Label clicked at: " + x + ", " + y);
                    //first check its click status
                    if(!nameLabel.getIsClicked()){
                        // if it's not clicked
                        // check if there are less than two items
                        if(selectedItems < 2){
                            // if the first section is null, fill it. If not, fill the second one but only if its null
                            if(craftSelect[0] == null){
                                craftSelect[0] = nameLabel.getLinkedItem();
                            }
                            else if(craftSelect[1] == null){
                                craftSelect[1] = nameLabel.getLinkedItem();
                            }
                            nameLabel.setClicked(true);
                            nameLabel.setColor(Color.RED);
                            selectedItems++;
                        }

                    }
                    else{
                        // If it is clicked, remove it from  the array and then set it to not clicked
                        for(int i = 0; i < craftSelect.length; i++){
                            if(craftSelect[i] != null){
                                if(craftSelect[i].getName().equals(nameLabel.getLinkedItem().getName())){
                                    craftSelect[i] = null;
                                    break;
                                }
                            }
                        }
                        nameLabel.setClicked(false);
                        nameLabel.setColor(Color.WHITE);
                        selectedItems--;
                    }
                    if(craftSelect[0] != null && craftSelect[1] != null){
                        //Check to see if items can combine
                        int rowCount = 0;
                        SQLLoader conn = new SQLLoader("itemsdb");
                        ResultSet rslt = null;
                        rslt = conn.select("SELECT result_id, item_id, combines_with_id FROM recipes WHERE item_id = "
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
                            rslt = conn.select("SELECT result_id, item_id, combines_with_id FROM recipes WHERE item_id = "
                                + craftSelect[1].getId() + " AND combines_with_id =" + craftSelect[0].getId());
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
                        }
                        if(rowCount != 0){
                            //all this does is set up the combine label if the number of rows is not zero
                            combineLabel = new Label("COMBINE", skin);
                            combineLabel.addListener(new ClickListener() {
                                @Override
                                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                                    System.out.println("CLICKED COMBINE");
                                    SQLLoader conn = new SQLLoader("itemsdb");
                                    ResultSet rslt = null;
                                    //pulls from the items table the result of the combine
                                    rslt = conn.select("SELECT * FROM items WHERE id = " + resultInt);
                                    if(resultInt != 17){
                                        try{
                                            while(rslt.next()){
                                                System.out.println(rslt.getString(2));
                                                player.removeItem(craftSelect[0]);
                                                player.removeItem(craftSelect[1]);
                                                craftSelect[0] = null;
                                                craftSelect[1] = null;
                                                player.addItem(new Item(rslt.getString(2)));
                                                dispose();
                                                initialize(g);
                                            }
                                        }
                                        catch(SQLException e){
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        player.removeItem(craftSelect[0]);
                                        player.removeItem(craftSelect[1]);
                                        craftSelect[0] = null;
                                        craftSelect[1] = null;
                                        // Hard coded several item giver
                                        player.addItem(new Item("Spear End"));
                                        player.addItem(new Item("Pie Shaped object"));
                                        player.addItem(new Item("Stick"));

                                        dispose();
                                        initialize(g);
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
                    //just check if the label is hovered on
                    if(!isHover){
                        if(!nameLabel.getIsClicked()){
                            nameLabel.setColor(Color.BLUE);
                        }
                        descMenu.row();
                        descLabel = new Label(current.getDescription(), skin);
                        descMenu.padTop(50);
                        descMenu.add(descLabel).padLeft(450);
                    }

                    isHover = true;
                    return true;
                }

                @Override
                public void exit(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, Actor toActor) {
                    // Check when the mouse is no longer hovered on
                    if (!nameLabel.getIsClicked()) { // Only reset color if not clicked
                        nameLabel.setColor(Color.WHITE);
                    }
                    //descLabel = new Label("", skin);
                    descMenu.removeActor(descLabel);
                    isHover = false;
                }
            });
            menu.add(nameLabel).padRight(450);
            menu.row();
        }
        stage.addActor(menu);
        stage.addActor(descMenu);

    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //make it so the menu appears AFTER the first frame because that makes the menu not want to open properly
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
