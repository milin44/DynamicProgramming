import java.util.*;

public class DeathStar {

	static int optimalForNonRepeatableMissions(final int totalTime, final int[] missionTimes,
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
		printTable(damages);
		return damages[totalTime][totalNrOfAvailableMissions];
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

	private static void printTable(final int[][] tbl) {
		System.out.println();
		for (int r = 0; r < tbl.length; r++) {
			for (int c = 0; c < tbl[r].length; c++) {
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

	private static void printMissionsVector(final int[][] damages, final int[] missionTimes) {
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
		// System.out.println(optimalForNonRepeatableMissions(10, new int[] {2,
		// 4, 6, 7}, new int[] {4, 10, 12, 14})); // = 22
		final int[] missionTimes = new int[] { 2, 4, 6, 7 };
		final int[] missionDamages = new int[] { 4, 10, 12, 14 };
		int[][] damages = optimalForRepeatableMissions(10, missionTimes, missionDamages); // =
		System.out.println(damages[damages.length - 1][0]);
																							// 24
		printDamagesVector(damages);
		printMissionsVector(damages, missionTimes);

		// System.out.println(optimalMissions(10, new int[] {1, 4, 8}, new int[]
		// {2, 8, 16}));
		// System.out.println(optimalWeight(1, new int[] {1, 4, 8}));
		// System.out.println(optimalWeight(1, new int[] {4, 8}));
		// System.out.println(optimalWeight(1, new int[] {1}));
		// System.out.println(optimalWeight(1, new int[] {0}));
	}
}
