
package Testing;

import Model.MatchAnalytics;
import Model.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchAnalyticsTest {

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

        System.out.println("=== MATCH ANALYTICS TESTS ===");

        // Create three test players.
        Player p1 = new Player("Home_1", 100, 100, Color.BLUE);
        Player p2 = new Player("Home_2", 200, 100, Color.BLUE);
        Player p3 = new Player("Away_1", 100, 200, Color.RED);

        List<Player> players = Arrays.asList(p1, p2, p3);

        // Capture player positions.
        List<MatchAnalytics.Position> snapshot =
                MatchAnalytics.snapshotPlayers(players);

        // Ball is at (100, 100).
        // Distances: 0, 100, 100.
        // Expected average = 200 / 3.
        double expected = 200.0 / 3.0;

        double sequential =
                MatchAnalytics.averageDistanceSequential(
                        snapshot, 100, 100);

        double parallel =
                MatchAnalytics.averageDistanceParallel(
                        snapshot, 100, 100);

        // TEST 1
        check(
            "Sequential calculation is correct",
            Math.abs(sequential - expected) < 0.000001
        );

        // TEST 2
        check(
            "Parallel matches sequential result",
            Math.abs(parallel - sequential) < 0.000001
        );

        // TEST 3: Snapshot must not change when players move.
        p1.moveRight(20);

        check(
            "Snapshot is independent of player movement",
            snapshot.get(0).getX() == 100
        );

        // TEST 4: Empty collections return zero.
        List<MatchAnalytics.Position> empty = new ArrayList<>();

        check(
            "Empty collections are handled safely",
            MatchAnalytics.averageDistanceParallel(
                    empty, 100, 100) == 0.0
            && MatchAnalytics.averageDistanceSequential(
                    empty, 100, 100) == 0.0
        );

        // TEST 5: Parallel calculation does not change positions.
        int originalX = snapshot.get(1).getX();
        int originalY = snapshot.get(1).getY();

        MatchAnalytics.averageDistanceParallel(
                snapshot, 150, 150);

        check(
            "Parallel calculation does not modify positions",
            snapshot.get(1).getX() == originalX
            && snapshot.get(1).getY() == originalY
        );

        System.out.println();
        System.out.println(
            "Results: " + passed + " passed, "
            + failed + " failed."
        );

        if (failed > 0) {
            throw new AssertionError(
                failed + " analytics tests failed."
            );
        }
    }
}
