
package Model;

/**
 * Strategy interface for player movement decisions.
 *
 * Different strategies can be selected at runtime
 * without changing the Player class.
 */
public interface PlayerStrategy {

    void move(Player player, int targetX, int targetY, int speed);
}
