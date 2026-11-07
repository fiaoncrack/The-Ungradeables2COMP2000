
package Model;

import Rendering.ScreenSize;

/**
 * Defensive strategy that anticipates the ball carrier's
 * forward movement instead of chasing their exact position.
 */
public class PressingStrategy implements PlayerStrategy {

    private static final int INTERCEPTION_DISTANCE = 12;

    @Override
    public void move(Player player, int targetX, int targetY, int speed) {

        if (speed <= 0) {
            return;
        }

        // Home players defend against away players moving left.
        // Away players defend against home players moving right.
        boolean homeDefender = player.getName().startsWith("Home_");

        int attackingDirection = homeDefender ? -1 : 1;

        // Aim slightly ahead of the opponent's current position.
        int interceptX = targetX
                + attackingDirection * INTERCEPTION_DISTANCE;

        // Keep the interception point within pitch boundaries.
        interceptX = Math.max(
                20,
                Math.min(ScreenSize.width - 20, interceptX)
        );

        int interceptY = Math.max(
                20,
                Math.min(ScreenSize.height - 20, targetY)
        );

        PlayerMovement.moveToward(
                player,
                interceptX,
                interceptY,
                speed
        );
    }
}
