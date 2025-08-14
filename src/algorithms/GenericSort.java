package algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenericSort {

    // Generic Insertion Sort
    public static <T> void insertionSort(List<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    // Generic Selection Sort
    public static <T> void selectionSort(List<T> list, Comparator<T> comparator) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(list, i, minIndex);
        }
    }
}
