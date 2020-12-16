package day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private final Set<Integer> invalidTicketIndexes = new HashSet<>();

    private int part1(List<String> input) {
        int i = 0;
        Set<Integer> allowedValues = new HashSet<>();
        for (; !input.get(i).isEmpty(); i++) {
            String[] strRanges = input.get(i).split(":")[1].split("or");
            for (String strRange : strRanges) {
                String[] tokens = strRange.split("-");
                int min = Integer.parseInt(tokens[0].trim());
                int max = Integer.parseInt(tokens[1].trim());
                for (int j = min; j <= max; j++) allowedValues.add(j);
            }
        }

        do {
            i++;
        }
        while (!input.get(i).equals("nearby tickets:"));
        int errorRate = 0;

        for (i = i + 1; i < input.size(); i++) {
            String[] ticketValues = input.get(i).split(",");
            int invalidFieldsSum = Arrays
                    .stream(ticketValues)
                    .mapToInt(Integer::parseInt)
                    .filter(ticketValue -> !allowedValues.contains(ticketValue))
                    .sum();
            errorRate += invalidFieldsSum;
            // Needed for part 2
            if (invalidFieldsSum > 0) invalidTicketIndexes.add(i);
        }

        return errorRate;
    }

    private List<String> getInputWithoutInvalidTickets(List<String> input) {
        return IntStream
                .range(0, input.size())
                .filter(i -> !invalidTicketIndexes.contains(i))
                .mapToObj(input::get)
                .collect(Collectors.toUnmodifiableList());
    }

    private static long part2(List<String> input) {
        // Field Name -> Valid Values
        Map<String, Set<Integer>> map = new HashMap<>();
        int i = 0;
        for (; !input.get(i).isEmpty(); i++) {
            String[] lineParts = input.get(i).split(":");
            String fieldName = lineParts[0];
            String[] strRanges = lineParts[1].split("or");
            Set<Integer> allowedValues = new HashSet<>();
            for (String strRange : strRanges) {
                String[] tokens = strRange.split("-");
                int min = Integer.parseInt(tokens[0].trim());
                int max = Integer.parseInt(tokens[1].trim());
                for (int j = min; j <= max; j++) allowedValues.add(j);
            }
            map.put(fieldName, allowedValues);
        }
        // Parse my ticket
        i += 2;
        int[] myTicket = Arrays.stream(input.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
        // Parse nearby tickets
        var tickets = IntStream
                .range(i + 3, input.size())
                .mapToObj(j -> Arrays
                        .stream(input.get(j).split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .collect(Collectors.toList());
        // Field Name -> Possible Indices
        Map<String, List<Integer>> possibleFieldMappings = new HashMap<>();
        for (String fieldName : map.keySet()) {
            Set<Integer> fieldAllowedValues = map.get(fieldName);
            for (int j = 0; j < myTicket.length; j++) {
                boolean validField = true;
                for (int[] ticketValues : tickets) {
                    int ticketValue = ticketValues[j];
                    if (!fieldAllowedValues.contains(ticketValue)) {
                        validField = false;
                        break;
                    }
                }
                if (validField) {
                    possibleFieldMappings.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(j);
                }
            }
        }
        // Convert to entry list sorted by possible indices list size ascending
        var sortedPossibleFieldMappings = possibleFieldMappings
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(stringListEntry -> stringListEntry.getValue().size()))
                .collect(Collectors.toList());

        // Field Name -> Index
        Map<String, Integer> fieldMappings = new HashMap<>();
        Set<Integer> usedIndicesSet = new HashSet<>();
        for (var entry : sortedPossibleFieldMappings) {
            var optionalIndex = entry.getValue()
                    .stream()
                    .filter(index -> !usedIndicesSet.contains(index))
                    .findFirst();
            // Could be on purpose but with the provided input, one field is left unmapped
            optionalIndex.ifPresent(index -> {
                fieldMappings.put(entry.getKey(), index);
                usedIndicesSet.add(index);
            });
        }
        // Calculate and return product of fields starting with departure
        long product = 1;
        for (var key : fieldMappings.keySet())
            if (key.startsWith("departure")) product *= myTicket[fieldMappings.get(key)];
        return product;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("./src/day16/input.txt"));
        Main main = new Main();
        System.out.println("----- DAY 13 - PART 1 -----");
        System.out.printf("Ticket scanning error rate: %d%n", main.part1(input));
        System.out.println("----- DAY 13 - PART 2 -----");
        List<String> part2Input = main.getInputWithoutInvalidTickets(input);
        System.out.printf("Departure fields product: %d%n", part2(part2Input));
    }

}
