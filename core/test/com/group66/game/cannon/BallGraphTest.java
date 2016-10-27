package com.group66.game.cannon;
import com.group66.game.cannon.Ball;
import com.group66.game.cannon.BallType;
import com.group66.game.cannon.ballgraph.BallGraph;
import com.group66.game.settings.Config;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;


/**
 * The Class BallGraphTest.
 */
public class BallGraphTest {
    
    /**
     * Ball graph creation test.
     */
    @Test
    public void ballGraphCreationTest() {
        new BallGraph();       
    }
    
    /**
     * Basic Ball testing 1:
     * Tests ball insertion, ball type creation, numberOfBalls function,
     * ball removal test
     */
    @Test
    public void basicBallGraphTest() {
        BallGraph testBallGraph = new BallGraph();
        assertEquals(testBallGraph.numberOfBalls(), 0);
        Ball ball1 = new ColoredBall(BallType.BLUE, 10.0f, 5.0f, 0, 0);
        testBallGraph.insertBall(ball1);
        assertEquals(testBallGraph.numberOfBalls(), 1);
        Ball ball2 = new BombBall(10.0f, 5.0f, 0, 0);
        testBallGraph.insertBall(ball2);
        assertEquals(testBallGraph.numberOfBalls(), 2);
        testBallGraph.removeBall(ball1);
        assertEquals(testBallGraph.numberOfBalls(), 1);
        testBallGraph.removeBall(ball2);
        assertEquals(testBallGraph.numberOfBalls(), 0);      
    }
    
    /**
     * Advanced ball insertion test:
     * Tests null ball insertion, top row ball insertion
     */
    @Test
    public void insertBallTest() {
        BallGraph testBallGraph = new BallGraph();
        float ypos = Config.HEIGHT - Config.BORDER_SIZE_TOP - Config.BALL_RAD;
        Ball ball1 = new ColoredBall(BallType.GREEN, 50, ypos, 0, 0);
        testBallGraph.insertBall(ball1);
        assertEquals(testBallGraph.numberOfBalls(), 1);
        Ball ball2 = null;
        testBallGraph.insertBall(ball2);
        assertEquals(testBallGraph.numberOfBalls(), 1);
      }
    
    /**
     * Adjacent balls test:
     * Tests getting adjacent balls
     */
    @Test
    public void adjacentBallsTest() {
        //test the numberOfAdjacentColoredBalls
        BallGraph testBallGraph = new BallGraph();
        Ball ball1 = new ColoredBall(BallType.GREEN, 50, 50, 0, 0);
        testBallGraph.insertBall(ball1);
        Ball ball2 = new ColoredBall(BallType.GREEN, ball1.getX() + Config.BALL_DIAM, 50, 0, 0);
        testBallGraph.insertBall(ball2);
        assertEquals(testBallGraph.numberOfAdjacentColoredBalls(ball1), 2);
        //Tests the null check of numberOfAdjacentColoredBalls
        Ball nullBall = null;
        assertEquals(testBallGraph.numberOfAdjacentColoredBalls(nullBall), 0);
        
        // Tests getAdjacentColoredBalls
        ArrayList <Ball> ret = new ArrayList<Ball>();
        ret.add(ball1);
        ret.add(ball2);
        assertEquals(testBallGraph.getAdjacentColoredBalls(ball1), ret);
        
        // Tests null getAdjacentColoredBalls
        assertEquals(testBallGraph.getAdjacentColoredBalls(nullBall), null);
      }
    
    
    /**
     * BallGraph test:
     * Tests if place is taken
     */
    @Test
    public void placeTakenTest() {
        BallGraph testBallGraph = new BallGraph();
        Ball ball1 = new ColoredBall(BallType.GREEN, 50, 50, 0, 0);
        testBallGraph.insertBall(ball1);
        assertEquals(true, testBallGraph.placeTaken(ball1.getX(), ball1.getY()));
        assertEquals(false, testBallGraph.placeTaken(10.0f,  10.0f));
      }
   
}
