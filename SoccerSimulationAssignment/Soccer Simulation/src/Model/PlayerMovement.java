
package Model;

/**
 * Shared movement operations for player AI strategies.
 *
 * Keeps low-level movement separate from decisions about
 * where a player should move.
 */
public final class PlayerMovement {

    private PlayerMovement() {
        // Utility class: no instances required.
    }

    public static void moveToward(
            Player player,
            int targetX,
            int targetY,
            int speed) {

        if (speed <= 0) {
            return;
        }

        if (player.getX() < targetX) {
            player.moveRight(
                Math.min(speed, targetX - player.getX())
            );
        } else if (player.getX() > targetX) {
            player.moveLeft(
                Math.min(speed, player.getX() - targetX)
            );
        }

        if (player.getY() < targetY) {
            player.moveDown(
                Math.min(speed, targetY - player.getY())
            );
        } else if (player.getY() > targetY) {
            player.moveUp(
                Math.min(speed, player.getY() - targetY)
            );
        }
    }
}
