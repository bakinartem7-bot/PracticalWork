package test;

import collection.SimpleArrayList;
import model.Bus;
import sort.BubbleSort;
import sort.InsertionSort;
import sort.SelectionSort;
import validation.BusValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimpleTests {
    public static void main(String[] args) {
        testSimpleArrayList();
        testBubbleSortByNumber();
        testBubbleSortByMileage();
        testSelectionSortByModel();
        testInsertionSortByMileage();
        testValidator();
        System.out.println("All simple tests passed.");
    }

    private static void testSimpleArrayList() {
        SimpleArrayList<Bus> list = new SimpleArrayList<>();
        Bus b1 = new Bus.Builder().number("B1").model("A").mileage(10).build();
        Bus b2 = new Bus.Builder().number("B2").model("B").mileage(20).build();
        list.add(b1);
        list.add(b2);
        if (list.size() != 2) throw new AssertionError("Size failed");
        if (!list.get(0).getNumber().equals("B1")) throw new AssertionError("Get failed");
        list.set(0, new Bus.Builder().number("B0").model("A").mileage(5).build());
        if (!list.get(0).getNumber().equals("B0")) throw new AssertionError("Set failed");
        list.clear();
        if (list.size() != 0) throw new AssertionError("Clear failed");
    }

    private static void testBubbleSortByNumber() {
        List<Bus> list = new ArrayList<>();
        list.add(new Bus.Builder().number("B3").model("X").mileage(30).build());
        list.add(new Bus.Builder().number("B1").model("Y").mileage(10).build());
        list.add(new Bus.Builder().number("B2").model("Z").mileage(20).build());
        BubbleSort<Bus> bubbleSort = new BubbleSort<>();
        bubbleSort.sort(list, Comparator.comparing(Bus::getNumber));
        if (!list.get(0).getNumber().equals("B1")) throw new AssertionError("Sort1 failed");
        if (!list.get(1).getNumber().equals("B2")) throw new AssertionError("Sort2 failed");
        if (!list.get(2).getNumber().equals("B3")) throw new AssertionError("Sort3 failed");
    }

    private static void testBubbleSortByMileage() {
        List<Bus> list = new ArrayList<>();
        list.add(new Bus.Builder().number("N1").model("M1").mileage(100).build());
        list.add(new Bus.Builder().number("N2").model("M2").mileage(50).build());
        list.add(new Bus.Builder().number("N3").model("M3").mileage(200).build());
        BubbleSort<Bus> bubbleSort = new BubbleSort<>();
        bubbleSort.sort(list, Comparator.comparingLong(Bus::getMileage));
        if (list.get(0).getMileage() != 50) throw new AssertionError("M1");
        if (list.get(1).getMileage() != 100) throw new AssertionError("M2");
        if (list.get(2).getMileage() != 200) throw new AssertionError("M3");
    }

    private static void testSelectionSortByModel() {
        List<Bus> list = new ArrayList<>();
        list.add(new Bus.Builder().number("1").model("B").mileage(1).build());
        list.add(new Bus.Builder().number("2").model("A").mileage(2).build());
        list.add(new Bus.Builder().number("3").model("C").mileage(3).build());
        SelectionSort<Bus> sort = new SelectionSort<>();
        sort.sort(list, Comparator.comparing(Bus::getModel));
        if (!"A".equals(list.get(0).getModel())) throw new AssertionError("Sel1");
        if (!"B".equals(list.get(1).getModel())) throw new AssertionError("Sel2");
        if (!"C".equals(list.get(2).getModel())) throw new AssertionError("Sel3");
    }

    private static void testInsertionSortByMileage() {
        List<Bus> list = new ArrayList<>();
        list.add(new Bus.Builder().number("1").model("X").mileage(5).build());
        list.add(new Bus.Builder().number("2").model("Y").mileage(1).build());
        list.add(new Bus.Builder().number("3").model("Z").mileage(3).build());
        InsertionSort<Bus> sort = new InsertionSort<>();
        sort.sort(list, Comparator.comparingLong(Bus::getMileage));
        if (list.get(0).getMileage() != 1) throw new AssertionError("Ins1");
        if (list.get(1).getMileage() != 3) throw new AssertionError("Ins2");
        if (list.get(2).getMileage() != 5) throw new AssertionError("Ins3");
    }

    private static void testValidator() {
        if (!BusValidator.isValidBus("A1", "Volvo", "100")) throw new AssertionError("V1");
        if (BusValidator.isValidMileage("-1")) throw new AssertionError("V2");
        if (BusValidator.isValidNumber("")) throw new AssertionError("V3");
        if (!BusValidator.isValidModel("X")) throw new AssertionError("V4");
    }
}