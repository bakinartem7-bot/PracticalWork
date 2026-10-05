package app;

import collection.SimpleArrayList;
import io.BusDataLoader;
import model.Bus;
import strategy.*;
import util.BusSorter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bus> currentList = new ArrayList<>();
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    currentList = fillCollection(scanner);
                    break;
                case "2":
                    System.out.println("Current list:");
                    BusSorter.printList(currentList);
                    break;
                case "3":
                    if (currentList.isEmpty()) {
                        System.out.println("List is empty. Fill it first.");
                    } else {
                        currentList = sortCollection(scanner, currentList);
                    }
                    break;
                case "4":
                    if (currentList.isEmpty()) {
                        System.out.println("List is empty. Fill it first.");
                    } else {
                        saveCollection(scanner, currentList);
                    }
                    break;
                case "5":
                    if (currentList.isEmpty()) {
                        System.out.println("List is empty. Fill it first.");
                    } else {
                        countOccurrences(scanner, currentList);
                    }
                    break;
                case "6":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Bus Sorting Application ===");
        System.out.println("1. Fill collection (file/random/manual)");
        System.out.println("2. Show current collection");
        System.out.println("3. Sort collection");
        System.out.println("4. Append sorted collection to file");
        System.out.println("5. Count occurrences (multithreaded)");
        System.out.println("6. Exit");
        System.out.print("Choice: ");
    }

    private static List<Bus> fillCollection(Scanner scanner) {
        System.out.println("Select fill method: ");
        System.out.println("1. From file");
        System.out.println("2. Random");
        System.out.println("3. Manual");
        System.out.print("Choice: ");
        String method = scanner.nextLine().trim();
        System.out.print("Enter length: ");
        String lengthStr = scanner.nextLine().trim();
        int length;
        try {
            length = Integer.parseInt(lengthStr);
            if (length < 0) length = 0;
        } catch (NumberFormatException e) {
            System.out.println("Invalid length. Using 0.");
            length = 0;
        }

        switch (method) {
            case "1":
                System.out.print("Enter file path: ");
                String filePath = scanner.nextLine().trim();
                try {
                    List<Bus> list = BusDataLoader.loadFromFile(filePath);
                    if (list.isEmpty()) {
                        System.out.println("No valid data loaded.");
                    } else {
                        System.out.println("Loaded " + list.size() + " buses.");
                    }
                    return list;
                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                    return new ArrayList<>();
                }
            case "2":
                List<Bus> randomList = BusDataLoader.loadRandom(length);
                System.out.println("Generated " + randomList.size() + " buses.");
                return randomList;
            case "3":
                List<Bus> manualList = BusDataLoader.loadManually(scanner, length);
                System.out.println("Entered " + manualList.size() + " buses.");
                return manualList;
            default:
                System.out.println("Invalid method. Empty list created.");
                return new ArrayList<>();
        }
    }

    private static List<Bus> sortCollection(Scanner scanner, List<Bus> currentList) {
        System.out.println("Select sort type: ");
        System.out.println("1. Basic sort by field");
        System.out.println("2. Even/odd by mileage (even sorted, odd keep positions)");
        System.out.print("Choice: ");
        String sortType = scanner.nextLine().trim();

        System.out.println("Select strategy: ");
        System.out.println("1. Bubble sort");
        System.out.println("2. Selection sort");
        System.out.println("3. Insertion sort");
        System.out.print("Choice: ");
        String strategyChoice = scanner.nextLine().trim();
        SortStrategy<Bus> strategy = getStrategy(strategyChoice);

        if ("1".equals(sortType)) {
            System.out.println("Select field: ");
            System.out.println("1. Number");
            System.out.println("2. Model");
            System.out.println("3. Mileage");
            System.out.print("Choice: ");
            String field = scanner.nextLine().trim();
            List<Bus> sorted = new ArrayList<>(currentList);
            switch (field) {
                case "1":
                    BusSorter.sortByNumber(sorted, strategy);
                    System.out.println("Sorted by number:");
                    break;
                case "2":
                    BusSorter.sortByModel(sorted, strategy);
                    System.out.println("Sorted by model:");
                    break;
                case "3":
                    BusSorter.sortByMileage(sorted, strategy);
                    System.out.println("Sorted by mileage:");
                    break;
                default:
                    System.out.println("Invalid field. Returning copy.");
                    return sorted;
            }
            BusSorter.printList(sorted);
            return sorted;
        } else if ("2".equals(sortType)) {
            List<Bus> sortedEvenOdd = BusSorter.sortByMileageEvenOddKeepOriginalPositions(new ArrayList<>(currentList), strategy);
            System.out.println("Even mileage sorted, odd kept in place:");
            BusSorter.printList(sortedEvenOdd);
            return sortedEvenOdd;
        } else {
            System.out.println("Invalid sort type.");
            return new ArrayList<>(currentList);
        }
    }

    private static SortStrategy<Bus> getStrategy(String choice) {
        switch (choice) {
            case "2":
                return new SelectionSortStrategy<>();
            case "3":
                return new InsertionSortStrategy<>();
            case "1":
            default:
                return new BubbleSortStrategy<>();
        }
    }

    private static void saveCollection(Scanner scanner, List<Bus> currentList) {
        System.out.print("Enter file path to append to: ");
        String filePath = scanner.nextLine().trim();
        try {
            BusDataLoader.appendToFile(currentList, filePath);
            System.out.println("Appended " + currentList.size() + " buses to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void countOccurrences(Scanner scanner, List<Bus> currentList) {
        System.out.println("Select field: ");
        System.out.println("1. Number");
        System.out.println("2. Model");
        System.out.println("3. Mileage");
        System.out.print("Choice: ");
        String fieldChoice = scanner.nextLine().trim();
        String field = "";
        switch (fieldChoice) {
            case "1":
                field = "number";
                System.out.print("Enter number value: ");
                break;
            case "2":
                field = "model";
                System.out.print("Enter model value: ");
                break;
            case "3":
                field = "mileage";
                System.out.print("Enter mileage value: ");
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }
        String value = scanner.nextLine().trim();
        System.out.print("Enter number of threads: ");
        String threadsStr = scanner.nextLine().trim();
        int threads;
        try {
            threads = Integer.parseInt(threadsStr);
            if (threads <= 0) threads = 1;
        } catch (NumberFormatException e) {
            threads = 1;
        }
        service.CountOccurrencesTask.countInMultipleThreads(currentList, field, value, threads);
    }
}