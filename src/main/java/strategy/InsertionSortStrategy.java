package strategy;

import sort.InsertionSort;

import java.util.Comparator;
import java.util.List;

public class InsertionSortStrategy<T> implements SortStrategy<T> {
    private final InsertionSort<T> insertionSort = new InsertionSort<>();

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        insertionSort.sort(list, comparator);
    }
}