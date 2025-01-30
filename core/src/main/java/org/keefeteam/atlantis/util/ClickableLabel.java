package org.keefeteam.atlantis.util;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.Setter;

//THIS ONLY EXISTS SO WE CAN GIVE AN ISCLICKED PROPERTY FOR EACH LABEL
//ALSO SO EACH LABEL IN THE MENU CAN HOLD AN ITEM

public class ClickableLabel extends Label {
    @Setter
    private boolean isClicked;
    @Getter
    private Item linkedItem;

    public ClickableLabel(String t, Skin s, Item i) {
        super(t, s);
        isClicked = false;
        linkedItem = i;
    }
    
    public boolean getIsClicked() {
        return isClicked;
    }

}
