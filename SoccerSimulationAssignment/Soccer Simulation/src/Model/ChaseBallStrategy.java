
package Model;

/**
 * Moves a player toward the current ball position.
 */
public class ChaseBallStrategy implements PlayerStrategy {

    @Override
    public void move(Player player, int ballX, int ballY, int speed) {
        PlayerMovement.moveToward(player, ballX, ballY, speed);
    }
}
