package org.keefeteam.atlantis.util;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.Setter;

//THIS ONLY EXISTS SO WE CAN GIVE AN ISCLICKED PROPERTY FOR EACH LABEL
//ALSO SO EACH LABEL IN THE MENU CAN HOLD AN ITEM

/**
 * This class is a normal label that you can click on to set it to either true or false
 * also it has an item linked to it for item combining
 */
public class ClickableLabel extends Label {
    @Setter
    @Getter
    private boolean isClicked;
    @Getter
    private Item linkedItem;

    /**
     * This constructor sets up the label as normal but with an item attached for combining
     * @param t This is the text to display
     * @param s This is the skin/font of the text
     * @param i This is the item linked to the label
     */
    public ClickableLabel(String t, Skin s, Item i) {
        super(t, s);
        isClicked = false;
        linkedItem = i;
    }

}
