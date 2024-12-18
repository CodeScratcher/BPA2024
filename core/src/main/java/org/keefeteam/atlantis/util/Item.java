package org.keefeteam.atlantis.util;

import com.badlogic.gdx.graphics.Texture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.SQLLoader;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class Item {
    private String name;
    private String description;
    private Texture picture;
    private Map<Item, Item> recipes;
    public Item(){
        this.name = "";
        this.description = "";
        this.picture = null;
        this.recipes =  null;
    }
    public Item(String n, String d, Texture p, Map<Item, Item> m){
        this.name = n;
        this.description = d;
        this.picture = p;
        this.recipes =  m;
    }
    //Only use this constructor for inventory testing
    public Item(String n, String d){
        this.name = n;
        this.description = d;
        this.picture = null;
        this.recipes =  null;
    }
    public Item(String n){
        SQLLoader query = new SQLLoader("SELECT * FROM items WHERE name=" + "\"" + n + "\"");
    }
}
