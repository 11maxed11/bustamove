package com.group66.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group66.game.helpers.AudioManager;
import com.group66.game.logging.ConsoleLogger;
import com.group66.game.logging.FileLogger;
import com.group66.game.logging.Logger;
import com.group66.game.logging.MessageType;
import com.group66.game.screens.MainMenuScreen;
import com.group66.game.settings.Config;

/**
 * The BustaMove main game class.
 */
public class BustaMove extends Game {   
    
    /** The batch. */
    public SpriteBatch batch;
    
    /** The logger. */
    public static Logger logger;
    
    /** Create the only object of this class */
    private static BustaMove game = new BustaMove();
   
    private BustaMove() {   
    }
    
    /**
     * Gets the only object available.
     * 
     * @return the only available object
     */
    public static BustaMove getGameInstance() {
        return game;
    }
    
    /**
     * Gets the game height.
     *
     * @return the game height
     */
    public int getGameHeight() {
        return Config.HEIGHT;
        //return Gdx.graphics.getHeight();
    }
    
    /**
     * Gets the game width.
     *
     * @return the game width
     */
    public int getGameWidth() {
        return Config.WIDTH;
        //return Gdx.graphics.getWidth();
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.ApplicationListener#create()
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        
        Logger fileLogger = new FileLogger(MessageType.Debug);
        Logger consoleLogger = new ConsoleLogger(MessageType.Info);

        fileLogger.nextLogger(consoleLogger);
        logger = fileLogger;
        
        /* Log start time */
        logger.log(MessageType.Default, "Game started");
        
        AudioManager.load();
        this.setScreen(new MainMenuScreen());
    }
}
