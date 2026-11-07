
package Testing;

import Model.TacticalDecision;
import Model.TacticalMode;

public class TacticalDecisionTest {

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

        System.out.println("=== TACTICAL DECISION TESTS ===");

        check(
            "Losing team selects ATTACKING",
            TacticalDecision.selectMode(0, 2)
                == TacticalMode.ATTACKING
        );

        check(
            "Winning team selects DEFENSIVE",
            TacticalDecision.selectMode(3, 1)
                == TacticalMode.DEFENSIVE
        );

        check(
            "Level scores select BALANCED",
            TacticalDecision.selectMode(1, 1)
                == TacticalMode.BALANCED
        );

        check(
            "Home attacking moves right",
            TacticalDecision.horizontalShift(
                TacticalMode.ATTACKING, true
            ) == 45
        );

        check(
            "Away attacking moves left",
            TacticalDecision.horizontalShift(
                TacticalMode.ATTACKING, false
            ) == -45
        );

        check(
            "Home defending moves left",
            TacticalDecision.horizontalShift(
                TacticalMode.DEFENSIVE, true
            ) == -35
        );

        check(
            "Away defending moves right",
            TacticalDecision.horizontalShift(
                TacticalMode.DEFENSIVE, false
            ) == 35
        );

        check(
            "Balanced mode has no tactical shift",
            TacticalDecision.horizontalShift(
                TacticalMode.BALANCED, true
            ) == 0
            && TacticalDecision.horizontalShift(
                TacticalMode.BALANCED, false
            ) == 0
        );

        System.out.println();
        System.out.println(
            "Results: " + passed + " passed, "
            + failed + " failed."
        );

        if (failed > 0) {
            throw new AssertionError(
                failed + " tactical tests failed."
            );
        }
    }
}
