package service;

import model.Bus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountOccurrencesTask implements Runnable {
    private final List<Bus> buses;
    private final String field;
    private final Object targetValue;
    private long count;

    public CountOccurrencesTask(List<Bus> buses, String field, Object targetValue) {
        this.buses = buses;
        this.field = field;
        this.targetValue = targetValue;
    }

    @Override
    public void run() {
        count = 0;
        if (buses == null || targetValue == null) {
            return;
        }
        for (Bus bus : buses) {
            if (bus == null) continue;
            boolean matches = false;
            if ("number".equalsIgnoreCase(field)) {
                if (bus.getNumber() != null && bus.getNumber().equals(String.valueOf(targetValue))) {
                    matches = true;
                }
            } else if ("model".equalsIgnoreCase(field)) {
                if (bus.getModel() != null && bus.getModel().equals(String.valueOf(targetValue))) {
                    matches = true;
                }
            } else if ("mileage".equalsIgnoreCase(field)) {
                try {
                    long targetMileage = Long.parseLong(String.valueOf(targetValue));
                    if (bus.getMileage() == targetMileage) {
                        matches = true;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            if (matches) {
                count++;
            }
        }
    }

    public long getCount() {
        return count;
    }

    public static void countInMultipleThreads(List<Bus> buses, String field, Object targetValue, int threadsCount) {
        if (buses == null || buses.isEmpty()) {
            System.out.println("Collection is empty. Count: 0");
            return;
        }
        int threadCount = threadsCount > 0 ? threadsCount : 1;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountOccurrencesTask[] tasks = new CountOccurrencesTask[threadCount];
        int chunkSize = buses.size() / threadCount;
        int remainder = buses.size() % threadCount;
        int start = 0;
        for (int i = 0; i < threadCount; i++) {
            int end = start + chunkSize + (i < remainder ? 1 : 0);
            List<Bus> subList = buses.subList(start, end);
            tasks[i] = new CountOccurrencesTask(subList, field, targetValue);
            executor.execute(tasks[i]);
            start = end;
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        long total = 0;
        for (CountOccurrencesTask task : tasks) {
            if (task != null) {
                total += task.getCount();
            }
        }
        System.out.println("Total occurrences of '" + targetValue + "' in field '" + field + "': " + total);
    }
}