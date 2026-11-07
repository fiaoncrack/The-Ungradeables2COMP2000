
package Model;

import Rendering.ScreenSize;

/**
 * Offensive movement strategy.
 *
 * A player in possession advances toward the opposing goal
 * while gradually adjusting toward the centre of the pitch.
 */
public class DribbleStrategy implements PlayerStrategy {

    @Override
    public void move(Player player, int targetX, int targetY, int speed) {

        if (speed <= 0) {
            return;
        }

        // The centre of the goal is the preferred shooting lane.
        int goalCentreY = ScreenSize.height / 2;

        // Move vertically toward the shooting lane gradually.
        // The maximum adjustment is one pixel per update.
        int verticalStep = Integer.compare(
            goalCentreY,
            player.getY()
        );

        int adjustedY = player.getY() + verticalStep;

        // Keep the movement target inside the pitch.
        adjustedY = Math.max(
            10,
            Math.min(ScreenSize.height - 10, adjustedY)
        );

        // Advance toward the target while correcting position.
        PlayerMovement.moveToward(
            player,
            targetX,
            adjustedY,
            speed
        );
    }
}
