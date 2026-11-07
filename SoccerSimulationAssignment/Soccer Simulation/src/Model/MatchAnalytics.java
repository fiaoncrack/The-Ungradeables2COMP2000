
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Calculates match statistics without modifying player state.
 *
 * Player coordinates are copied into immutable snapshots
 * before any parallel processing begins.
 */
public final class MatchAnalytics {

    private MatchAnalytics() {
        // Utility class: no instances required.
    }

    /**
     * Immutable copy of one player's position.
     */
    public static final class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    /**
     * Creates a snapshot of player positions.
     *
     * Call this on the simulation thread, after movement
     * updates are complete.
     */
    public static List<Position> snapshotPlayers(
            List<Player> players) {

        List<Position> snapshot = new ArrayList<>();

        for (Player player : players) {
            snapshot.add(
                new Position(player.getX(), player.getY())
            );
        }

        return Collections.unmodifiableList(snapshot);
    }

    /**
     * Calculates the average distance sequentially.
     * Used as a reference for correctness testing.
     */
    public static double averageDistanceSequential(
            List<Position> positions,
            int ballX,
            int ballY) {

        return positions.stream()
                .mapToDouble(p -> Math.hypot(
                    p.getX() - ballX,
                    p.getY() - ballY
                ))
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the same statistic using a parallel stream.
     *
     * Each worker only reads immutable position data.
     */
    public static double averageDistanceParallel(
            List<Position> positions,
            int ballX,
            int ballY) {

        return positions.parallelStream()
                .mapToDouble(p -> Math.hypot(
                    p.getX() - ballX,
                    p.getY() - ballY
                ))
                .average()
                .orElse(0.0);
    }
}
