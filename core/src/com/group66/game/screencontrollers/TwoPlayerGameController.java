package com.group66.game.screencontrollers;

import com.badlogic.gdx.Input.Keys;
import com.group66.game.BustaMove;
import com.group66.game.cannon.GameManager;
import com.group66.game.helpers.HighScoreManager;
import com.group66.game.helpers.LevelLoader;
import com.group66.game.input.InputHandler;
import com.group66.game.logging.MessageType;
import com.group66.game.screens.YouLoseScreenRandom;
import com.group66.game.screens.YouWinScreenRandom;
import com.group66.game.settings.Config;
import com.group66.game.settings.DynamicSettings;

public class TwoPlayerGameController extends AbstractGameController {
    private GameManager gameManager1, gameManager2;
    
    /**
     * Instantiates the controller for the two player game screen
     * @param randomLevel determines if a set level or a random level is used
     * @param level the specific level to load
     */
    public TwoPlayerGameController(Boolean randomLevel, int level) {
        gameState = GameState.RUNNING;
        gameManager1 = new GameManager(0, BustaMove.getGameInstance().getDynamicSettings());
        gameManager2 = new GameManager(2, BustaMove.getGameInstance().getDynamicSettings());
        inputHandler = new InputHandler();
        setupKeys();
        BustaMove.getGameInstance().getAudioManager().startMusic();

        if (!randomLevel) {
            LevelLoader.loadLevel(gameManager1.getBallManager(), level, true);
            gameManager2.shiftClone(gameManager1);
            BustaMove.getGameInstance().log(MessageType.Info, "Loaded a premade level");
        } else {
            LevelLoader.generateLevel(gameManager1.getBallManager(), true);
            gameManager2.shiftClone(gameManager1);
            BustaMove.getGameInstance().log(MessageType.Info, "Loaded a random level");
        }
        
        gameManager1.getBallManager().getBallsCannonManager().addRandomBallToCanon();
        gameManager2.getBallManager().getBallsCannonManager().addRandomBallToCanon();
    }

    @Override
    public void update(float delta) {
        /* Handle input keys */
        inputHandler.run();
        
        if (gameState == GameState.PAUSED) {
            /* Update the balls without letting them move*/
            gameManager1.update(0);
            gameManager2.update(0);
        } else {
            /* Update the balls */
            gameManager1.update(delta);
            gameManager2.update(delta);
    
            /* Check if game-over condition is reached */
            if (gameManager1.isGameOver() || gameManager2.isGameOver()) {
                BustaMove.getGameInstance().log(MessageType.Info, "Failed the level");
                DynamicSettings ds = gameManager1.getDynamicSettings();
                ds = (ds == null ? new DynamicSettings() : ds);
                if (ds.hasExtraLife()) {
                    ds.setExtraLife(false, true);
                    BustaMove.getGameInstance().log(MessageType.Info, "Keeping Dynamic Settings");
                } else {
                    ds.reset();
                    BustaMove.getGameInstance().log(MessageType.Info, "Resetting Dynamic Settings");
                }
                BustaMove.getGameInstance().setScreen(new YouLoseScreenRandom());
            }
            
            /* Check if game-complete condition is reached */
            if (gameManager1.isGameComplete() || gameManager2.isGameComplete()) {
                BustaMove.getGameInstance().log(MessageType.Info, "Completed the level");
                
                int score1 = gameManager1.scoreKeeper.getCurrentScore();
                int score2 = gameManager2.scoreKeeper.getCurrentScore();
                
                HighScoreManager highScoreManager = BustaMove.getGameInstance().getHighScoreManager();
                highScoreManager.addScore(score1);
                highScoreManager.addScore(score2);
                
                gameManager1.getDynamicSettings().addCurrency((score1 + score2) / 2 / Config.SCORE_CURRENCY_DIV, true);
                BustaMove.getGameInstance().setScreen(new YouWinScreenRandom());
            }
        }
    }
    
    /**
     * Setup the keys used in the game screen keys.
     */
    protected void setupKeys() {
        // Setup the game keys
        inputHandler.registerKeyMap("Shoot 1", Keys.W);
        inputHandler.registerKeyMap("Aim Left 1", Keys.A);
        inputHandler.registerKeyMap("Aim Right 1", Keys.D);
        inputHandler.registerKeyMap("Shoot 2", Keys.UP);
        inputHandler.registerKeyMap("Aim Left 2", Keys.LEFT);
        inputHandler.registerKeyMap("Aim Right 2", Keys.RIGHT);
        inputHandler.registerKeyMap("Toggle Pause", Keys.ESCAPE);
        inputHandler.registerKeyMap("Toggle mute", Keys.M);
        inputHandler.registerKeyMap("Shoot 3", Keys.I);
        inputHandler.registerKeyMap("Aim Left 3", Keys.J);
        inputHandler.registerKeyMap("Aim Right 3", Keys.L);
        
        /* Register key names to functions */
        inputHandler.registerKeyPressedFunc("Aim Left 1",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        try {
                            gameManager1.cannon.cannonAimAdjust(Config.CANNON_AIM_DELTA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        inputHandler.registerKeyPressedFunc("Aim Right 1",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        try {
                            gameManager1.cannon.cannonAimAdjust(-Config.CANNON_AIM_DELTA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        inputHandler.registerKeyJustPressedFunc("Shoot 1",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        try {
                            gameManager1.shootBall();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        
        inputHandler.registerKeyPressedFunc("Aim Left 2",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        try {
                            gameManager2.cannon.cannonAimAdjust(Config.CANNON_AIM_DELTA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        inputHandler.registerKeyPressedFunc("Aim Right 2",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        try {
                            gameManager2.cannon.cannonAimAdjust(-Config.CANNON_AIM_DELTA);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        inputHandler.registerKeyJustPressedFunc("Shoot 2",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        try {
                            gameManager2.shootBall();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        inputHandler.registerKeyJustPressedFunc("Toggle Pause",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        togglePause();
                    }
                });
        
        inputHandler.registerKeyJustPressedFunc("Toggle mute",
                new InputHandler.KeyCommand() {
                    public void runCommand() {
                        BustaMove.getGameInstance().getAudioManager().toggleMute();
                    }
                });
    }

    /**
     * Gets the first game manager
     * @return the game manager
     */
    @Override
    public GameManager getGameManager1() {
        return gameManager1;
    }
    
    /**
     * Gets the second game manager
     * @return the game manager
     */
    @Override
    public GameManager getGameManager2() {
        return gameManager2;
    }
}
