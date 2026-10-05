package util;

import model.Bus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BusSorter {
    public static void sortByNumber(List<Bus> list, strategy.SortStrategy<Bus> strategy) {
        strategy.sort(list, Comparator.comparing(Bus::getNumber));
    }

    public static void sortByModel(List<Bus> list, strategy.SortStrategy<Bus> strategy) {
        strategy.sort(list, Comparator.comparing(Bus::getModel));
    }

    public static void sortByMileage(List<Bus> list, strategy.SortStrategy<Bus> strategy) {
        strategy.sort(list, Comparator.comparingLong(Bus::getMileage));
    }

    public static List<Bus> sortByMileageEvenOddKeepOriginalPositions(List<Bus> list, strategy.SortStrategy<Bus> strategy) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> evenIndices = new ArrayList<>();
        List<Bus> evenList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Bus bus = list.get(i);
            if (bus != null && bus.getMileage() % 2 == 0) {
                evenIndices.add(i);
                evenList.add(bus);
            }
        }
        if (!evenList.isEmpty()) {
            strategy.sort(evenList, Comparator.comparingLong(Bus::getMileage));
        }
        List<Bus> result = new ArrayList<>(list);
        for (int j = 0; j < evenIndices.size(); j++) {
            result.set(evenIndices.get(j), evenList.get(j));
        }
        return result;
    }

    public static void printList(List<Bus> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
}