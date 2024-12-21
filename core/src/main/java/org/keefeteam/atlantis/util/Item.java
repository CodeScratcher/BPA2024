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
    public Item(){
        this.id = -1;
        this.name = "";
        this.description = "";
        this.picture = null;
        this.recipes =  null;
    }
    public Item(String n, String d, Texture p, Map<Item, Item> m, int i){
        this.name = n;
        this.description = d;
        this.picture = p;
        this.recipes =  m;
        this.id = i;
    }
    //Only use this constructor for inventory testing
    public Item(String n, String d){
        this.name = n;
        this.description = d;
        this.picture = null;
        this.recipes =  null;
        this.id = -1;
    }
    public Item(String n){
        SQLLoader conn = new SQLLoader("itemsdb");
        ResultSet rslt = null;
        rslt = conn.select("SELECT * FROM items WHERE name=" + "\"" + n + "\"");
        try{
            while(rslt.next()){
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
