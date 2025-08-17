package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.function.Function;

public class GenericSearch {
    public enum SearchType {
        LINEAR,
        BINARY_TREE // thay vì binarySearchAll
    }

    // Linear search kiểu Comparator giống sort
    public static <T> List<T> linearSearch(List<T> list, String keyword,
            Function<T, String> fieldExtractor) {
        List<T> result = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (T item : list) {
            String fieldValue = fieldExtractor.apply(item).toLowerCase();
            if (fieldValue.startsWith(keyword)) {
                result.add(item);
            }
        }
        return result;
    }

    public static <T> List<T> binarySearchTree(
            List<T> sortedList,
            String keyword,
            Function<T, String> keyExtractor,
            Comparator<String> comparator) {

        List<T> results = new ArrayList<>();
        keyword = keyword.toLowerCase();

        // Find potential start point for prefix matches
        int low = 0;
        int high = sortedList.size() - 1;
        int potentialStart = -1;

        // Binary search to find first potential match
        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midValue = keyExtractor.apply(sortedList.get(mid)).toLowerCase();
            
            if (midValue.startsWith(keyword)) {
                potentialStart = mid;
                high = mid - 1; // Keep searching left for earlier matches
            } else {
                int comparison = midValue.compareTo(keyword);
                if (comparison < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        // If no potential match found in left half, try right half
        if (potentialStart == -1) {
            low = 0;
            high = sortedList.size() - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                String midValue = keyExtractor.apply(sortedList.get(mid)).toLowerCase();
                
                if (midValue.startsWith(keyword)) {
                    potentialStart = mid;
                    break;
                } else {
                    int comparison = midValue.compareTo(keyword);
                    if (comparison < 0) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            }
        }

        // If still no match found, return empty results
        if (potentialStart == -1) {
            return results;
        }

        // Scan left for prefix matches until we find a non-match
        for (int i = potentialStart - 1; i >= 0; i--) {
            String value = keyExtractor.apply(sortedList.get(i)).toLowerCase();
            if (value.startsWith(keyword)) {
                results.add(0, sortedList.get(i)); // Add at beginning to maintain order
            } else {
                break; // Stop when we find first non-match
            }
        }

        // Add the potential start match
        results.add(sortedList.get(potentialStart));

        // Scan right for prefix matches until we find a non-match
        for (int i = potentialStart + 1; i < sortedList.size(); i++) {
            String value = keyExtractor.apply(sortedList.get(i)).toLowerCase();
            if (value.startsWith(keyword)) {
                results.add(sortedList.get(i));
            } else {
                break; // Stop when we find first non-match
            }
        }

        return results;
    }
}
