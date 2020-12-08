package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private final Map<String, List<String>> map;

    public Main(Stream<String> inputStream) {
        map = inputStream.collect(Collectors.toMap(this::getColour, this::getContainedBags));
    }

    private String getColour(String input) {
        return input.split("contain")[0].replace("bags", "").trim();
    }

    private List<String> getContainedBags(String input) {
        String[] strContainedBags = input.split("contain")[1].split(",");
        Stream<String> streamContainedBags = Arrays
                .stream(strContainedBags)
                .map(str -> str.replaceAll("bags", ""))
                .map(str -> str.replaceAll("\\.", ""))
                .map(str -> str.replaceAll("bag", ""))
                .map(String::trim);

        return streamContainedBags.flatMap(strContainedBag -> {
            if (strContainedBag.equals("no other")) return Stream.empty();
            String[] containedBagTokens = strContainedBag.split(" ");
            int quantity = Integer.parseInt(containedBagTokens[0]);
            List<String> bagInstances = new ArrayList<>();
            for (int i = 0; i < quantity; i++)
                bagInstances.add(containedBagTokens[1] + " " + containedBagTokens[2]);
            return bagInstances.stream();
        }).collect(Collectors.toUnmodifiableList());
    }

    private void processBag(String bag, Map<String, Boolean> processedBags) {
        for (String containedBag : map.get(bag)) {
            if (containedBag.equals("shiny gold") || (processedBags.containsKey(containedBag) && processedBags.get(containedBag))) {
                processedBags.put(bag, true);
            } else {
                processBag(containedBag, processedBags);
            }
        }
        if (!processedBags.containsKey(bag))
            processedBags.put(bag, false);
    }

    public long part1() {
        Map<String, Boolean> processedBags = new HashMap<>();
        map.keySet().forEach(bag -> processBag(bag, processedBags));
        return processedBags.values().stream().filter(value -> value).count();
    }

    private long getContainedBagsCount(String bag) {
        return map.get(bag).stream().map(this::getContainedBagsCount).reduce((long) map.get(bag).size(), Long::sum);
    }

    public long part2() {
        return getContainedBagsCount("shiny gold");
    }

    public static void main(String[] args) throws IOException {
        Stream<String> inputStream = Files.lines(Path.of("./src/day7/input.txt"));
        Main main = new Main(inputStream);
        System.out.println("----- DAY 7 - PART 1 -----");
        System.out.printf("# of bags eventually containing shiny gold: %d%n", main.part1());
        System.out.println("----- DAY 7 - PART 2 -----");
        System.out.printf("# of bags contained in shiny gold: %d%n", main.part2());

    }
}

