package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.function.Function;

public class GenericSearch {

    // Linear search kiểu Comparator giống sort
    public static <T> List<T> linearSearch(List<T> list, String keyword, 
                                                Function<T, String> fieldExtractor) {
        List<T> result = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (T item : list) {
            String fieldValue = fieldExtractor.apply(item).toLowerCase();
            if (fieldValue.contains(keyword)) {
                result.add(item);
            }
        }
        return result;
    }


    public static <T> List<T> binarySearchAll(List<T> sortedList, T key, Comparator<T> comparator) {
        List<T> results = new ArrayList<>();
        int low = 0, high = sortedList.size() - 1;
        int foundIndex = -1;

        // 1️⃣ Tìm một index match đầu tiên bằng binary search
        while (low <= high) {
            int mid = low + (high - low) / 2;
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

        // Không tìm thấy
        if (foundIndex == -1)
            return results;

        // 2️⃣ Tìm tất cả match về bên trái
        int left = foundIndex - 1;
        while (left >= 0 && comparator.compare(sortedList.get(left), key) == 0) {
            left--;
        }
        // left + 1 là index đầu tiên match

        // 3️⃣ Tìm tất cả match về bên phải
        int right = foundIndex + 1;
        while (right < sortedList.size() && comparator.compare(sortedList.get(right), key) == 0) {
            right++;
        }
        // right - 1 là index cuối cùng match

        // 4️⃣ Thêm tất cả phần tử match vào kết quả
        for (int i = left + 1; i < right; i++) {
            results.add(sortedList.get(i));
        }

        return results;
    }
}
