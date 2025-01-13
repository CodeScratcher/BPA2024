package org.keefeteam.atlantis.util;

import com.badlogic.gdx.graphics.Texture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.SQLLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class Item {
    private String name;
    private String description;
    private Texture picture;
    private Map<Item, Item> recipes;
    private int id;

    //Simple constructor that gets info from the sql
    public Item(String n, String d, Texture p, Map<Item, Item> r, int i){
        this.name = n;
        this.description = d;
        this.picture = p;
        this.recipes =  r;
        this.id = i;
    }
    //Only use this constructor for inventory testing, everything else uses sql
    public Item(String n, String d){
        this.name = n;
        this.description = d;
        this.picture = null;
        this.recipes =  null;
        this.id = -1;
    }
    //This Constructor does all the sql inside of it
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
                this.picture = null;
                this.recipes =  null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
