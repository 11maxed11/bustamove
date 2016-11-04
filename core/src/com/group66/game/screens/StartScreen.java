package com.group66.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.group66.game.BustaMove;
import com.group66.game.helpers.AudioManager;
import com.group66.game.input.InputHandler;
import com.group66.game.logging.MessageType;
import com.group66.game.settings.Config;

/**
 * A Class for the StarScreen of the game.
 */
public class StartScreen extends AbstractMenuScreen {

    private InputHandler inputHandler;
    
    /**
     * Instantiates a new start screen.
     */
    public StartScreen() {
        inputHandler = new InputHandler();
        setup_keys();
        BustaMove.getGameInstance().getHighScoreManager().loadData();
        createScreen();
        BustaMove.getGameInstance().log(MessageType.Info, "Loaded the startup menu screen");
    }

    /**
     * Creates a screen
     */
    private void createScreen() {
        loadRelatedGraphics();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        setupButtons();
    }

    /*
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta) {
        /* Handle input keys */
        inputHandler.run();

        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* Draw the background */
        SpriteBatch batch = BustaMove.getGameInstance().batch;
        batch.begin();
        batch.enableBlending();
        batch.draw(mmbg, Config.SINGLE_PLAYER_OFFSET, 0, Config.LEVEL_WIDTH, Gdx.graphics.getHeight());
        batch.end();
        
        stage.act();
        stage.draw();
    }

    /*
     * @see com.badlogic.gdx.Screen#hide()
     */
    @Override
    public void hide() {
    }
    
    /**
     * sets up buttons
     */
    public void setupButtons() {
        loadButtonMaterials();
        //all magic numbers in this section are offsets values adjusted to get better looks
        int yoffset = Gdx.graphics.getHeight() / 2 + 30;
        int centercol = (Gdx.graphics.getWidth() - Config.BUTTON_WIDTH) / 2;

        LabelStyle infoStyle = new LabelStyle(textButtonStyle.font, Color.BLACK);
        Label info = new Label("Enter your name:", infoStyle);
        info.setBounds(centercol, yoffset, Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT / 2);
        info.setAlignment(2);
        stage.addActor(info);
        
        TextFieldStyle fieldStyle = new TextFieldStyle();
        fieldStyle.fontColor = Color.WHITE;
        fieldStyle.background = textButtonStyle.over;
        fieldStyle.font = textButtonStyle.font;
        //fieldStyle.cursor =
        final TextField nameField = new TextField("Player", fieldStyle);
        nameField.setBounds(centercol, yoffset - Config.BUTTON_HEIGHT / 2, Config.BUTTON_WIDTH, 
                Config.BUTTON_HEIGHT / 2);
        //nameField
        stage.addActor(nameField);
        
        TextButton startButton = new TextButton("Continue", textButtonStyle);
        startButton.setPosition(centercol, yoffset - 2 * Config.BUTTON_HEIGHT);
        stage.addActor(startButton);
        
        // Add a listener to the button
        startButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                BustaMove.getGameInstance().getDynamicSettings().setName(nameField.getText(), false);
                BustaMove.getGameInstance().getProfileManager().readData(
                        BustaMove.getGameInstance().getDynamicSettings().getName(), 
                        BustaMove.getGameInstance().getDynamicSettings());
                dispose();
                BustaMove.getGameInstance().setScreen(new MainMenuScreen());
            }
        });
    }
    
    /**
     * Setup the keys used in the game screen keys.
     */
    private void setup_keys() {
        // Setup the game keys        
        inputHandler.registerKeyMap("Toggle mute", Keys.M);
        inputHandler.registerKeyJustPressedFunc("Toggle mute",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        AudioManager.toggleMute();
                    }
            });
    }

}