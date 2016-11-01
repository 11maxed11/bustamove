package com.group66.game.shop;

import com.group66.game.BustaMove;
import com.group66.game.logging.MessageType;
import com.group66.game.settings.Config;
import com.group66.game.settings.DynamicSettings;

/**
 * The Class BuyScoreMultiplier Sate Machine.
 */
public class BuyScoreMultiplier extends BuyStateInstance {
    
    /**
     * Instantiates a new buy score multiplier State Machine.
     */
    public BuyScoreMultiplier() {
        this.setCurrent(new None());
    }
    
    /**
     * The State None.
     */
    static class None implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (instance == null || dynamicSettings == null) {
                return;
            }
            if (dynamicSettings.getCurrency() >= Config.SCORE_INCR_COST) {
                dynamicSettings.addCurrency(-1 * Config.SCORE_INCR_COST, true);
                dynamicSettings.setScoreMultiplier(1.05, true);
                instance.setCurrent(new Mulp5());
                BustaMove.getGameInstance().log(MessageType.Info, "Set new state to Mulp5");
                BustaMove.getGameInstance().log(MessageType.Info, "Money: " + dynamicSettings.getCurrency());
            } else {
                BustaMove.getGameInstance().log(MessageType.Info, "Not enough money");
            }
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo() {
            return "+5%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost() {
            return Config.SCORE_INCR_COST;
        }
    }
    
    /**
     * The State Mulp5.
     */
    static class Mulp5 implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (instance == null || dynamicSettings == null) {
                return;
            }
            if (dynamicSettings.getCurrency() >= Config.SCORE_INCR_COST) {
                dynamicSettings.addCurrency(-1 * Config.SCORE_INCR_COST, true);
                dynamicSettings.setScoreMultiplier(1.1, true);
                instance.setCurrent(new Mulp10());
                BustaMove.getGameInstance().log(MessageType.Info, "Set new state to Mulp10");
                BustaMove.getGameInstance().log(MessageType.Info, "Money: " + dynamicSettings.getCurrency());
            } else {
                BustaMove.getGameInstance().log(MessageType.Info, "Not enough money");
            }
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo() {
            return "+10%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost() {
            return Config.SCORE_INCR_COST;
        }
    }
    
    /**
     * The State Mulp10.
     */
    static class Mulp10 implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (instance == null || dynamicSettings == null) {
                return;
            }
            if (dynamicSettings.getCurrency() >= Config.SCORE_INCR_COST) {
                dynamicSettings.addCurrency(-1 * Config.SCORE_INCR_COST, true);
                dynamicSettings.setScoreMultiplier(1.2, true);
                instance.setCurrent(new Mulp20());
                instance.setIsFinalState(true);
                BustaMove.getGameInstance().log(MessageType.Info, "Set new state to Mulp20");
                BustaMove.getGameInstance().log(MessageType.Info, "Money: " + dynamicSettings.getCurrency());
            } else {
                BustaMove.getGameInstance().log(MessageType.Info, "Not enough money");
            }
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo() {
            return "+20%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost() {
            return Config.SCORE_INCR_COST;
        }
    }
    
    /**
     * The State Mulp20.
     */
    static class Mulp20 implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (instance == null) {
                return;
            }
            instance.setCurrent(this);
            BustaMove.getGameInstance().log(MessageType.Info, "Stay in the top state (Mulp20)");
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo() {
            return "+20%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost() {
            return 0;
        }
    }
}
