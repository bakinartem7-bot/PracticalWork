package strategy;

import sort.BubbleSort;

import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategy<T> implements SortStrategy<T> {
    private final BubbleSort<T> bubbleSort = new BubbleSort<>();

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        bubbleSort.sort(list, comparator);
    }
}