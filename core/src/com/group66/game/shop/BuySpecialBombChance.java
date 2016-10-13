package com.group66.game.shop;

import com.group66.game.BustaMove;
import com.group66.game.logging.MessageType;
import com.group66.game.settings.Config;
import com.group66.game.settings.DynamicSettings;

/**
 * The Class BuySpecialBombChance State Machine.
 */
public class BuySpecialBombChance extends BuyStateInstance {
    
    /**
     * Instantiates a new buy special bomb chance State Machine.
     */
    public BuySpecialBombChance() {
        this.setCurrent(new None());
    }
    
    /**
     * The State None.
     */
    class None implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (dynamicSettings.getCurrency() >= Config.BOMB_INCR_COST) {
                dynamicSettings.addCurrency(-1 * Config.BOMB_INCR_COST);
                dynamicSettings.setSpecialBombChanceMultiplier(1.05);
                instance.setCurrent(new Chancep5());
                BustaMove.logger.log(MessageType.Info, "Set new state to Chancep5");
                BustaMove.logger.log(MessageType.Info, "Money: " + dynamicSettings.getCurrency());
            } else {
                BustaMove.logger.log(MessageType.Info, "Not enough money");
            }
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo(BuyStateInstance instance) {
            return "+5%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost(BuyStateInstance instance) {
            return Config.BOMB_INCR_COST;
        }
    }
    
    /**
     * The State Chancep5.
     */
    class Chancep5 implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (dynamicSettings.getCurrency() >= Config.BOMB_INCR_COST) {
                dynamicSettings.addCurrency(-1 * Config.BOMB_INCR_COST);
                dynamicSettings.setSpecialBombChanceMultiplier(1.1);
                instance.setCurrent(new Chancep10());
                BustaMove.logger.log(MessageType.Info, "Set new state to Chancep10");
                BustaMove.logger.log(MessageType.Info, "Money: " + dynamicSettings.getCurrency());
            } else {
                BustaMove.logger.log(MessageType.Info, "Not enough money");
            }
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo(BuyStateInstance instance) {
            return "+10%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost(BuyStateInstance instance) {
            return Config.BOMB_INCR_COST;
        }
    }
    
    /**
     * The State Chancep10.
     */
    class Chancep10 implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            if (dynamicSettings.getCurrency() >= Config.BOMB_INCR_COST) {
                dynamicSettings.addCurrency(-1 * Config.BOMB_INCR_COST);
                dynamicSettings.setSpecialBombChanceMultiplier(1.2);
                instance.setCurrent(new Chancep20());
                instance.setIsFinalState(true);
                BustaMove.logger.log(MessageType.Info, "Set new state to Chancep20");
                BustaMove.logger.log(MessageType.Info, "Money: " + dynamicSettings.getCurrency());
            } else {
                BustaMove.logger.log(MessageType.Info, "Not enough money");
            }
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo(BuyStateInstance instance) {
            return "+20%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost(BuyStateInstance instance) {
            return Config.BOMB_INCR_COST;
        }
    }
    
    /**
     * The State Chancep20.
     */
    class Chancep20 implements BuyState {
        
        /* (non-Javadoc)
         */
        public void buy(BuyStateInstance instance, DynamicSettings dynamicSettings) {
            instance.setCurrent(this);
            BustaMove.logger.log(MessageType.Info, "Stay in the top state (Chancep20)");
        }
        
        /* (non-Javadoc)
         */
        public String getNextStateInfo(BuyStateInstance instance) {
            return "+20%";
        }
        
        /* (non-Javadoc)
         */
        public int getNextStateCost(BuyStateInstance instance) {
            return 0;
        }
    }
}
