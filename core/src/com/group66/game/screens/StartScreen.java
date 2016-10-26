package com.group66.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.group66.game.BustaMove;
import com.group66.game.helpers.AudioManager;
import com.group66.game.input.InputHandler;
import com.group66.game.input.NameInputListener;
import com.group66.game.logging.MessageType;
import com.group66.game.settings.Config;

/**
 * A Class for the StarScreen of the game.
 */
public class StartScreen extends AbstractMenuScreen {

    /** screen buttons */
    private TextButton setName;
    private TextButton startButton;
    /** The input handler. */
    private InputHandler inputHandler;
    
    /** variables used to calculate some drawing coordinates */
    private int yoffset;
    private int leftcol;
    private int rightcol;
    
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


    private void createScreen() {
        loadRelatedGraphics();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        setupButtons();
        stage.addActor(setName);
        stage.addActor(startButton);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta) {
        /* Handle input keys */
        inputHandler.run();

        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* Draw the background */
        BustaMove.getGameInstance().batch.begin();
        BustaMove.getGameInstance().batch.enableBlending();
        BustaMove.getGameInstance().batch.draw(mmbg, Config.SINGLE_PLAYER_OFFSET, 0, Config.LEVEL_WIDTH,
                Gdx.graphics.getHeight());
        BustaMove.getGameInstance().batch.end();
        stage.act();
        stage.draw();
    }

    /*
     * (non-Javadoc)
     * 
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
        yoffset = Gdx.graphics.getHeight() / 2 + Config.BUTTON_HEIGHT + Config.BUTTON_SPACING - 50;
        leftcol = (Gdx.graphics.getWidth() - Config.BUTTON_WIDTH - 250) / 2;
        rightcol = (Gdx.graphics.getWidth() - Config.BUTTON_WIDTH + 250) / 2;

        setName = new TextButton("Enter name", textButtonStyle);
        setName.setPosition(leftcol, yoffset);
        
        startButton = new TextButton("Start game!", textButtonStyle);
        startButton.setPosition(rightcol, yoffset);
        
        // Add a listener to the button. ChangeListener is fired when the
        // button's checked state changes, eg when clicked,
        // Button#setChecked() is called, via a key press, etc. If the
        // event.cancel() is called, the checked state will be reverted.
        // ClickListener could have been used, but would only fire when clicked.
        // Also, canceling a ClickListener event won't
        // revert the checked state.
        setName.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                NameInputListener listener = new NameInputListener(BustaMove.getGameInstance().getDynamicSettings());
                Gdx.input.getTextInput(listener, "Enter your name", "", "");
            }
        });
        startButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (!BustaMove.getGameInstance().getDynamicSettings().getName().equals("")) {
                    BustaMove.getGameInstance().getProfileManager().readData(
                            BustaMove.getGameInstance().getDynamicSettings().getName(), 
                            BustaMove.getGameInstance().getDynamicSettings());
                    dispose();
                    BustaMove.getGameInstance().setScreen(new MainMenuScreen());
                }
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