package org.keefeteam.atlantis.util;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

//THIS ONLY EXISTS SO I CAN GIVE AN ISCLICKED PROPERTY FOR EACH LABEL

public class ClickableLabel extends Label {
    private boolean isClicked;

    public ClickableLabel(String t, Skin s) {
        super(t, s);
        isClicked = false;
    }

    public boolean getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean c) {
        isClicked = c;
    }
}
