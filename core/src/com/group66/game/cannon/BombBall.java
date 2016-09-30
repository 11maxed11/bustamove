package com.group66.game.cannon;

/**
 * 
 * @author Jeroen
 *
 */
public class BombBall extends Ball {

    /**
     * Creates a new BombBall object
     * @param xpos
     * @param ypos
     * @param rad
     * @param speed
     * @param angle
     */
    public BombBall(float xpos, float ypos, float rad, int speed, float angle) {
        super(BallType.BOMB, xpos, ypos, rad, speed, angle);
    }
    
    
    /**
     * Checks if the balls are equal
     */
    public Boolean isEqual(Ball ball) {
        if (ball.getType().equals(this.getType())) {
            return true;
        }
        
        return false;
    }

}
