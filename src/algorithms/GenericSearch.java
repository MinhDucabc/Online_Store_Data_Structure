package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class GenericSearch {

    // Linear search kiểu Comparator giống sort
    public static <T> List<T> linearSearch(List<T> list, String keyword, Comparator<String> comparator, 
                                        java.util.function.Function<T, String> fieldExtractor) {
        List<T> result = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (T item : list) {
            String fieldValue = fieldExtractor.apply(item).toLowerCase();
            if (comparator.compare(fieldValue, keyword) == 0) {
                result.add(item);
            }
        }
        return result;
    }


    public static <T> List<T> binarySearchAll(List<T> sortedList, T key, Comparator<T> comparator) {
        List<T> results = new ArrayList<>();
        int low = 0, high = sortedList.size() - 1;
        int foundIndex = -1;

        // Tìm 1 index match trước
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = comparator.compare(sortedList.get(mid), key);

            if (cmp == 0) {
                foundIndex = mid;
                break;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (foundIndex == -1) return results; // không tìm thấy

        // tìm tất cả match về bên trái
        int left = foundIndex;
        while (left >= 0 && comparator.compare(sortedList.get(left), key) == 0) {
            results.add(0, sortedList.get(left));
            left--;
        }

        // tìm tất cả match về bên phải
        int right = foundIndex + 1;
        while (right < sortedList.size() && comparator.compare(sortedList.get(right), key) == 0) {
            results.add(sortedList.get(right));
            right++;
        }

        return results;
    }
}
