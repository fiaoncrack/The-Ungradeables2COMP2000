
package Model;

import Rendering.ScreenSize;

/**
 * Goalkeeper positioning strategy.
 *
 * Tracks the ball vertically while remaining within
 * a safe range inside the penalty area.
 */
public class GoalkeeperStrategy implements PlayerStrategy {

    private static final int MARGIN = 15;

    @Override
    public void move(Player player, int targetX, int targetY, int speed) {

        if (speed <= 0) {
            return;
        }

        int minY =
                (ScreenSize.height - SoccerPitch.PENALTY_HEIGHT) / 2
                + MARGIN;

        int maxY =
                minY + SoccerPitch.PENALTY_HEIGHT
                - (MARGIN * 2);

        // Follow the ball without leaving the allowed vertical range.
        int safeTargetY = Math.max(
                minY,
                Math.min(maxY, targetY)
        );

        // Goalkeeper remains at its original horizontal position.
        int safeTargetX = player.getHomeX();

        PlayerMovement.moveToward(
                player,
                safeTargetX,
                safeTargetY,
                speed
        );
    }
}
