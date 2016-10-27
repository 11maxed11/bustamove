package com.group66.game.screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.group66.game.BustaMove;
import com.group66.game.helpers.TextDrawer;
import com.group66.game.logging.MessageType;
import com.group66.game.settings.Config;
import com.group66.game.settings.DynamicSettings;

/**
 * A Class for the MainMenuScreen of the game.
 */
public class CareerScreen extends AbstractMenuScreen {
    private static DynamicSettings dynamicSettings = new DynamicSettings();

    /** The text drawer. */
    private TextDrawer textDrawer;

    private Screen ownInstance;

    /**
     * Instantiates a new main menu screen.
     */
    public CareerScreen() {
        BustaMove.getGameInstance().getHighScoreManager().loadData();
        ownInstance = this;
        createScreen();
        BustaMove.getGameInstance().log(MessageType.Info, "Loaded the main menu screen");
    }

    /**
     * Creates an instance of careerscreen
     * 
     * @param dynamicSettings
     */
    public CareerScreen(DynamicSettings dynamicSettings) {
        this();
    }

    private void createScreen() {
        loadRelatedGraphics();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        // Setup the text drawer to show the amount of coins
        textDrawer = new TextDrawer();
        textDrawer.myFont.setColor(Color.BLACK);
        setupButtons();
    }

    /*
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* Draw the background */
        SpriteBatch batch = BustaMove.getGameInstance().batch;
        batch.begin();
        batch.enableBlending();
        batch.draw(mmbg, Config.SINGLE_PLAYER_OFFSET, 0, Config.LEVEL_WIDTH, Gdx.graphics.getHeight());
        textDrawer.draw(batch, "You have cleared " + dynamicSettings.getLevelCleared() + " out of "
                + Config.NUMBER_OF_LEVELS + " levels!", 
                (Config.WIDTH - Config.LEVEL_WIDTH) / 2 + Config.CURRENCY_X - 100, Config.CURRENCY_Y - 40);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void setupButtons() {
        loadButtonMaterials();
        //all magic numbers in this section are offsets values adjusted to get better looks
        int yoffset = Gdx.graphics.getHeight() / 2 + 20;
        int centercol = (Gdx.graphics.getWidth() - Config.BUTTON_WIDTH) / 2;
        int leftcol = (Gdx.graphics.getWidth() - Config.BUTTON_WIDTH - 250) / 2;
        int rightcol = (Gdx.graphics.getWidth() - Config.BUTTON_WIDTH + 250) / 2;

        for (int i = 1; i <= Config.NUMBER_OF_LEVELS; i++) {
            addLevelButton(i);
        }
        
        TextButton shopButton = new TextButton("Shop", textButtonStyle);
        shopButton.setPosition(leftcol, yoffset - 3 * (Config.BUTTON_HEIGHT + Config.BUTTON_SPACING));
        stage.addActor(shopButton);

        TextButton resetButton = new TextButton("Reset career", textButtonStyle);
        resetButton.setPosition(rightcol, yoffset - 3 * (Config.BUTTON_HEIGHT + Config.BUTTON_SPACING));
        stage.addActor(resetButton);

        TextButton mainMenuButton = new TextButton("Back", textButtonStyle);
        mainMenuButton.setPosition(centercol, yoffset - 4 * (Config.BUTTON_HEIGHT + Config.BUTTON_SPACING));
        stage.addActor(mainMenuButton);
        
        // Add a listener to the buttons
        shopButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                BustaMove.getGameInstance().setScreen(new ShopScreen(dynamicSettings, ownInstance));
            }
        });
        resetButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dynamicSettings.reset();
            }
        });
        mainMenuButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                BustaMove.getGameInstance().setScreen(new MainMenuScreen());
            }
        });
    }
    
    /**
     * Adds a button for the selected level
     * @param level
     */
    private void addLevelButton(final int level) {
        String textureName = "levelimages/level" + new DecimalFormat("00").format(level) + ".png";
        if (dynamicSettings.getLevelCleared() < level - 1) {
            textureName = "levelimages/level" + new DecimalFormat("00").format(level) + "_grey.png";
        }
        if (!Gdx.files.internal(textureName).exists()) {
            //backup texture
            textureName = "ballTextures.png";
        }
        
        //int ypos = Gdx.graphics.getHeight() - 200 - level * 60;
        int xoffset = (Gdx.graphics.getWidth() - 500 - 4 * Config.BUTTON_SPACING) / 2;
        int xpos = xoffset + ((level - 1) % 5) * (100 + Config.BUTTON_SPACING);
        int ypos = Gdx.graphics.getHeight() / 2 - 10 - ((level - 1) / 5) * (100 + Config.BUTTON_SPACING);
        
        Texture myTexture = new Texture(Gdx.files.internal(textureName));
        TextureRegion myTextureRegion = new TextureRegion(myTexture, 100, 100);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        
        ImageButton imgButton = new ImageButton(myTexRegionDrawable);
        imgButton.setPosition(xpos, ypos);
        imgButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (dynamicSettings.getLevelCleared() >= level - 1) {
                    dispose();
                    dynamicSettings.setCurrentLevel(level);
                    BustaMove.getGameInstance().setScreen(new OnePlayerGameScreen(false, dynamicSettings));
                }
            }
        });
        stage.addActor(imgButton);
    }

}
