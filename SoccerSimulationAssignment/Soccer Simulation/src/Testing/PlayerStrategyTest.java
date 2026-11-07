
package Testing;

import java.awt.Color;

import Model.*;
import Rendering.ScreenSize;

/**
 * Automated tests for the four player movement strategies.
 * Uses standard Java without external dependencies.
 */
public class PlayerStrategyTest {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + name);
            passed++;
        } else {
            System.out.println("FAIL: " + name);
            failed++;
        }
    }

    public static void main(String[] args) {

        System.out.println("=== PLAYER STRATEGY TESTS ===");

        // TEST 1: ChaseBallStrategy
        Player chaser = new Player(
            "Home_1", 100, 100, Color.BLUE
        );

        PlayerStrategy chase = new ChaseBallStrategy();

        chase.move(chaser, 200, 200, 2);

        check(
            "Chaser moves toward the ball",
            chaser.getX() == 102 && chaser.getY() == 102
        );

        // TEST 2: DribbleStrategy
        int centreY = ScreenSize.height / 2;

        Player dribbler = new Player(
            "Home_2", 100, centreY - 30, Color.BLUE
        );

        PlayerStrategy dribble = new DribbleStrategy();

        dribble.move(
            dribbler, 125, dribbler.getY(), 1
        );

        check(
            "Dribbler advances toward opponent's goal",
            dribbler.getX() == 101
        );

        check(
            "Dribbler adjusts toward the goal centre",
            dribbler.getY() == centreY - 29
        );

        // TEST 3: PressingStrategy
        Player defender = new Player(
            "Home_3", 300, 200, Color.BLUE
        );

        PlayerStrategy pressing = new PressingStrategy();

        pressing.move(defender, 250, 200, 2);

        check(
            "Defender moves toward predicted interception point",
            defender.getX() == 298
                && defender.getY() == 200
        );

        // TEST 4: GoalkeeperStrategy
        int goalkeeperY = centreY;

        Player goalkeeper = new Player(
            "Home_GK",
            30,
            goalkeeperY,
            Color.BLUE,
            true
        );

        PlayerStrategy goalkeeperStrategy =
            new GoalkeeperStrategy();

        goalkeeperStrategy.move(
            goalkeeper, 400, centreY + 50, 1
        );

        check(
            "Goalkeeper tracks the ball vertically",
            goalkeeper.getY() == goalkeeperY + 1
        );

        check(
            "Goalkeeper maintains its horizontal position",
            goalkeeper.getX() == goalkeeper.getHomeX()
        );

        // TEST 5: No movement at zero speed
        Player stationary = new Player(
            "Home_4", 100, 100, Color.BLUE
        );

        chase.move(stationary, 200, 200, 0);

        check(
            "Zero speed prevents movement",
            stationary.getX() == 100
                && stationary.getY() == 100
        );

        // TEST 6: Goalkeeper stays within vertical bounds
        int minY =
            (ScreenSize.height - SoccerPitch.PENALTY_HEIGHT)
            / 2 + 15;

        int maxY =
            minY + SoccerPitch.PENALTY_HEIGHT - 30;

        for (int i = 0; i < 500; i++) {
            goalkeeperStrategy.move(
                goalkeeper, 400, ScreenSize.height, 1
            );
        }

        check(
            "Goalkeeper stays inside permitted vertical range",
            goalkeeper.getY() >= minY
                && goalkeeper.getY() <= maxY
        );

        System.out.println();
        System.out.println(
            "Results: " + passed + " passed, "
            + failed + " failed."
        );

        if (failed > 0) {
            throw new AssertionError(
                failed + " strategy tests failed."
            );
        }
    }
}
