package day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static int getNumberSpoken(String input, int endTurn) {
        int[] nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        // Number -> Last Turn Spoken
        Map<Integer, Integer> map = new HashMap<>();
        int turn = 1;
        for (; turn < nums.length; turn++)
            map.put(nums[turn - 1], turn);
        int lastNumber = nums[nums.length - 1];
        while (turn < endTurn) {
            int nextNumber = map.containsKey(lastNumber) ? turn - map.get(lastNumber) : 0;
            map.put(lastNumber, turn);
            lastNumber = nextNumber;
            turn++;
        }
        return lastNumber;
    }

    public static void main(String[] args) {
        String input = "16,12,1,0,15,7,11";
        System.out.println("----- DAY 13 - PART 1 -----");
        System.out.printf("2020th number spoken: %d%n", getNumberSpoken(input, 2020));
        System.out.println("----- DAY 13 - PART 2 -----");
        System.out.printf("30000000th number spoken: %d%n", getNumberSpoken(input, 30000000));
    }

}
