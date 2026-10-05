package io;

import model.Bus;
import validation.BusValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BusDataLoader {
    private static final String[] MODELS = {"Volvo", "Mercedes", "Ikarus", "PAZ", "MAN", "Scania"};
    private static final Random RANDOM = new Random();

    public static List<Bus> loadFromFile(String filePath) throws IOException {
        List<Bus> buses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    continue;
                }
                String number = parts[0].trim();
                String model = parts[1].trim();
                String mileageStr = parts[2].trim();
                if (BusValidator.isValidBus(number, model, mileageStr)) {
                    Bus bus = new Bus.Builder()
                            .number(number)
                            .model(model)
                            .mileage(Long.parseLong(mileageStr))
                            .build();
                    buses.add(bus);
                }
            }
        }
        return buses;
    }

    public static List<Bus> loadRandom(int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> {
                    String number = "BUS-" + (i + 1) + "-" + RANDOM.nextInt(1000);
                    String model = MODELS[RANDOM.nextInt(MODELS.length)];
                    long mileage = Math.abs(RANDOM.nextLong()) % 200000;
                    return new Bus.Builder().number(number).model(model).mileage(mileage).build();
                })
                .collect(Collectors.toList());
    }

    public static List<Bus> loadManually(Scanner scanner, int length) {
        List<Bus> buses = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            System.out.println("Bus " + (i + 1) + ":");
            System.out.print("Number: ");
            String number = scanner.nextLine().trim();
            System.out.print("Model: ");
            String model = scanner.nextLine().trim();
            System.out.print("Mileage: ");
            String mileageStr = scanner.nextLine().trim();
            while (!BusValidator.isValidBus(number, model, mileageStr)) {
                System.out.println("Invalid data. Please re-enter.");
                System.out.print("Number: ");
                number = scanner.nextLine().trim();
                System.out.print("Model: ");
                model = scanner.nextLine().trim();
                System.out.print("Mileage: ");
                mileageStr = scanner.nextLine().trim();
            }
            Bus bus = new Bus.Builder()
                    .number(number)
                    .model(model)
                    .mileage(Long.parseLong(mileageStr))
                    .build();
            buses.add(bus);
        }
        return buses;
    }

    public static void saveToFile(List<Bus> buses, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Bus bus : buses) {
                bw.write(bus.getNumber() + "," + bus.getModel() + "," + bus.getMileage());
                bw.newLine();
            }
        }
    }

    public static void appendToFile(List<Bus> buses, String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            for (Bus bus : buses) {
                bw.write(bus.getNumber() + "," + bus.getModel() + "," + bus.getMileage());
                bw.newLine();
            }
        }
    }
}