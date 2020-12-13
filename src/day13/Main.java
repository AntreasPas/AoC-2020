package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Main {

    private static long part1(List<String> strInput) {
        int earliestTimestamp = Integer.parseInt(strInput.get(0));
        int[] ids = Arrays
                .stream(strInput.get(1).split(","))
                .filter(i -> !i.equals("x"))
                .mapToInt(Integer::parseInt)
                .toArray();
        // Timestamp -> Bus ID
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int id : ids) {
            int i = id;
            while (i < earliestTimestamp) i += id;
            map.put(i, id);
        }
        return (map.firstEntry().getKey() - earliestTimestamp) * map.firstEntry().getValue();
    }

    private static long part2(List<String> input) {
        String[] strBusInput = input.get(1).split(",");
        long acc = Long.parseLong(strBusInput[0]), result = 0;
        // Bus ID & Original Bus Index
        long[][] arr = IntStream
                .range(1, strBusInput.length)
                .filter(i -> !strBusInput[i].equals("x"))
                .mapToObj(i -> new long[]{Long.parseLong(strBusInput[i]), i})
                .toArray(long[][]::new);
        for (var entry : arr) {
            long id = entry[0];
            long index = entry[1];
            while ((result + index) % id != 0) result += acc;
            acc *= id;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("./src/day13/input.txt"));
        System.out.println("----- DAY 13 - PART 1 -----");
        System.out.printf("ID of the earliest bus * waiting time for bus: %d%n", part1(input));
        System.out.println("----- DAY 13 - PART 2 -----");
        System.out.printf("Earliest applicable timestamp: %d%n", part2(input));
    }

}
