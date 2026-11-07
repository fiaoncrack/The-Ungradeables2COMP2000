
package Model;

/**
 * Moves the player carrying the ball toward
 * the opposing team's goal.
 */
public class DribbleStrategy implements PlayerStrategy {

    @Override
    public void move(Player player, int targetX, int targetY, int speed) {
        PlayerMovement.moveToward(player, targetX, targetY, speed);
    }
}
