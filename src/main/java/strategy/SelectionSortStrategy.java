package strategy;

import sort.SelectionSort;

import java.util.Comparator;
import java.util.List;

public class SelectionSortStrategy<T> implements SortStrategy<T> {
    private final SelectionSort<T> selectionSort = new SelectionSort<>();

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        selectionSort.sort(list, comparator);
    }
}