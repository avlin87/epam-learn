package liadov.mypackage.lesson3;

import java.util.*;

public class HashMapSortOperations<T, V> {

    public HashMap<T, V> getHashMapSortedByKey(HashMap<T, V> hashMap) {
        SortedMap<T, V> sortedTreeMap = new TreeMap<>(hashMap);
        LinkedHashMap<T, V> sortedHashMap = new LinkedHashMap<>(sortedTreeMap);
        return sortedHashMap;
    }

    public HashMap<T, V> getHashMapSortedByValue(HashMap<T, V> map) {
        LinkedHashMap<T, V> sortedHashMap = new LinkedHashMap<>();
        Set<V> sortingList = new TreeSet<>(map.values());
        for (V v : sortingList) {
            for (Map.Entry<T, V> pair : map.entrySet()) {
                if (v.equals(pair.getValue())) {
                    sortedHashMap.put(pair.getKey(), v);
                }
            }
        }
        return sortedHashMap;
    }
}
