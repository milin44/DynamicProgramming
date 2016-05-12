import java.util.*;

public class DeathStar {

    static int[][] optimalForNonRepeatableMissions(final int totalTime, final int[] missionTimes,
                                                   final int[] missionDamages) {
        int totalNrOfAvailableMissions = missionTimes.length;
        int damages[][] = new int[totalTime + 1][totalNrOfAvailableMissions + 1];
        for (int missionNr = 1; missionNr <= totalNrOfAvailableMissions; missionNr++) {
            int missionTime = missionTimes[missionNr - 1];
            int damage = missionDamages[missionNr - 1];
            for (int currentTotalTime = 1; currentTotalTime <= totalTime; currentTotalTime++) {
                damages[currentTotalTime][missionNr] = damages[currentTotalTime][missionNr - 1];
                if (missionTime <= currentTotalTime) {
                    int damageForAddingCurrentMission = damages[currentTotalTime - missionTime][missionNr - 1] + damage;
                    if (damages[currentTotalTime][missionNr] < damageForAddingCurrentMission) {
                        damages[currentTotalTime][missionNr] = damageForAddingCurrentMission;

                    }
                }
            }
        }
        return damages;
    }

    static int[][] optimalForRepeatableMissions(final int totalTime, final int[] missionTimes,
                                                final int[] missionDamages) {
        int totalNrOfAvailableMissions = missionTimes.length;
        int damages[][] = new int[totalTime + 1][2]; // add one extra dimension for storing index for used mission

        for (int currentTotalTime = 1; currentTotalTime <= totalTime; currentTotalTime++) {
            damages[currentTotalTime][1] = -1;
            for (int missionNr = 1; missionNr <= totalNrOfAvailableMissions; missionNr++) {
                int missionTime = missionTimes[missionNr - 1];
                int damage = missionDamages[missionNr - 1];
                if (missionTime <= currentTotalTime) {
                    int damageForAddingCurrentMission = damages[currentTotalTime - missionTime][0] + damage;
                    if (damages[currentTotalTime][0] < damageForAddingCurrentMission) {
                        damages[currentTotalTime][0] = damageForAddingCurrentMission;
                        damages[currentTotalTime][1] = missionNr;
                    }
                }
            }
        }
        return damages;
    }

    private static void printDamagesMatrice(final int[][] tbl) {
        System.out.println();
        for (int c = 0; c < tbl[0].length; c++) {
            for (int r = 0; r < tbl.length; r++) {
                System.out.print(String.format("%02d ", tbl[r][c]));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printDamagesVector(final int[][] damages) {
        System.out.println();
        for (int d = 0; d < damages.length; d++) {
            System.out.print(String.format("%02d ", damages[d][0]));
        }
        System.out.println();
    }

    private static void printExecutedMissionsVector(final int[][] damages, final int[] missionTimes) {
        List<Integer> sequence = new ArrayList<>();
        int damagesIndex = damages.length - 1;
        int currentMissionNr = damages[damagesIndex][1];
        while (currentMissionNr > 0) {
            sequence.add(Integer.valueOf((currentMissionNr)));
            damagesIndex -= missionTimes[currentMissionNr - 1];
            currentMissionNr = damages[damagesIndex][1];
        }
        Collections.reverse(sequence);

        for (Integer missionNr : sequence) {
            System.out.print(missionNr + " ");
        }
    }

    public static void main(String[] args) {
        final int[] missionTimes = new int[]{2, 4, 6, 7};
        final int[] missionDamages = new int[]{4, 10, 12, 14};
        final int totalTime = 10;

        int[][] damagesRepeatable = optimalForRepeatableMissions(totalTime, missionTimes, missionDamages); // = 24
        System.out.println("Maximum damage for repeatable missions is: " + damagesRepeatable[damagesRepeatable.length - 1][0]);
        System.out.println();
        System.out.println("Damage vector:");
        printDamagesVector(damagesRepeatable);
        System.out.println();
        System.out.println("Executed missions:");
        printExecutedMissionsVector(damagesRepeatable, missionTimes);
        System.out.println();
        System.out.println();

        int[][] damagesNonRepeatable = optimalForNonRepeatableMissions(totalTime, missionTimes, missionDamages); // = 22
        System.out.println("Maximum damage for non-repeatable missions is: " + damagesNonRepeatable[totalTime][missionTimes.length]);
        System.out.println();
        System.out.println("Damage matrice:");
        printDamagesMatrice(damagesNonRepeatable);


    }
}
