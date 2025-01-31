package org.keefeteam.atlantis.util;

import com.badlogic.gdx.graphics.Texture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.SQLLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * This is an item that the player can use to interact with certain zones and craft with other items
 */
@Getter
@Setter
@EqualsAndHashCode

public class Item {
    private String name;
    private String description;
    private int id;

    /**
     * Simple constructor that gets hard coded info that is passed into the item
     * @param n the name of the item
     * @param d the description of the item
     * @param i the id of the item
     */
    public Item(String n, String d, int i){
        this.name = n;
        this.description = d;
        this.id = i;
    }

    //This Constructor does all the sql inside of it

    /**
     * This grabs only the name of the item from the parameter and the rest from the table
     * @param n The name of the item
     */
    public Item(String n){
        SQLLoader conn = new SQLLoader("itemsdb");
        ResultSet rslt = null;
        rslt = conn.select("SELECT * FROM items WHERE name=" + "\"" + n + "\"");
        try{
            while(rslt.next()){
                //This should always return one row due to the name not being ambiguous
                this.id = rslt.getInt(1);
                this.name = rslt.getString(2);
                this.description = rslt.getString(3);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
