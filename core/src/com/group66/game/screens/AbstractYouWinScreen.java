package com.group66.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.group66.game.helpers.AssetLoader;
import com.group66.game.settings.Config;
import com.group66.game.settings.DynamicSettings;
import com.group66.game.BustaMove;

/**
 * A Class for the MainMenuScreen of the game.
 */
public abstract class AbstractYouWinScreen implements Screen {

    protected Stage stage;
    protected Skin skin;
    
    protected DynamicSettings dynamicSettings;

    /**
     * Instantiates a new main menu screen.
     * @param dynamicSettings TODO
     */
    public AbstractYouWinScreen(DynamicSettings dynamicSettings) {
        this.dynamicSettings = dynamicSettings;
        AssetLoader.load();
        createScreen();
    }

    protected void createScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();

        // Store the default libgdx font under the name "default".
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(Config.BUTTON_WIDTH, Config.BUTTON_HEIGHT, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Configure a TextButtonStyle and name it "default". Skin resources are
        // stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        makeButtons(textButtonStyle);

    }

    protected void makeButtons(TextButtonStyle textButtonStyle) { };

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* Draw the background */
        BustaMove.getGameInstance().batch.begin();
        BustaMove.getGameInstance().batch.enableBlending();
        BustaMove.getGameInstance().batch.draw(AssetLoader.youwinbg, Config.SINGLE_PLAYER_OFFSET, 0, Config.LEVEL_WIDTH,
                Gdx.graphics.getHeight());
        BustaMove.getGameInstance().batch.end();

        stage.act();
        stage.draw();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#show()
     */
    @Override
    public void show() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        // game.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#pause()
     */
    @Override
    public void pause() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#resume()
     */
    @Override
    public void resume() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#hide()
     */
    @Override
    public void hide() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.Screen#dispose()
     */
    @Override
    public void dispose() {

    }
}