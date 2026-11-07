
package Model;

/**
 * Selects team tactics based on the current score.
 *
 * This class contains no mutable state, making
 * tactical decisions straightforward to test.
 */
public final class TacticalDecision {

    private TacticalDecision() {
        // Utility class.
    }

    public static TacticalMode selectMode(
            int teamScore,
            int opponentScore) {

        if (teamScore < opponentScore) {
            return TacticalMode.ATTACKING;
        }

        if (teamScore > opponentScore) {
            return TacticalMode.DEFENSIVE;
        }

        return TacticalMode.BALANCED;
    }

    /**
     * Calculates the horizontal tactical adjustment.
     *
     * Home team attacks right; away team attacks left.
     */
    public static int horizontalShift(
            TacticalMode mode,
            boolean homeTeam) {

        int direction = homeTeam ? 1 : -1;

        switch (mode) {
            case ATTACKING:
                return 45 * direction;

            case DEFENSIVE:
                return -35 * direction;

            case BALANCED:
            default:
                return 0;
        }
    }
}
