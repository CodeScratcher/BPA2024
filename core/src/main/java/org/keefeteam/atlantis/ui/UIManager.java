package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;

public class UIManager {
    boolean inputControl;
    private Stage stage;
    private Table table;
    private Skin skin;

    public void createStage(){
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);

        Label label = new Label("thbjfvsehyuiewrtgwuihr", skin);

        table.add(label);
        stage.addActor(table);



    }
    public void update(List<InputEvent> events){

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
