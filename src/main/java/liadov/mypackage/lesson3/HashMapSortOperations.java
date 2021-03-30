package liadov.mypackage.lesson3;

import java.util.*;

public class HashMapSortOperations<T, V> {

    /**
     * 9. Method sort received hashMap by Key and returns LinkedHashMap object
     *
     * @param hashMap <key,value> this hashMap will be sorted by Key
     * @return LinkedHashMap<key, value> sorted by Key
     */
    public HashMap<T, V> getHashMapSortedByKey(HashMap<T, V> hashMap) {
        SortedMap<T, V> sortedTreeMap = new TreeMap<>(hashMap);
        LinkedHashMap<T, V> sortedHashMap = new LinkedHashMap<>(sortedTreeMap);
        return sortedHashMap;
    }

    /**
     * 10. Method sort received hashMap by Value and returns LinkedHashMap object
     *
     * @param hashMap <key,value> this hashMap will be sorted by Value
     * @return LinkedHashMap<key, value> sorted by Value
     */
    public HashMap<T, V> getHashMapSortedByValue(HashMap<T, V> hashMap) {
        LinkedHashMap<T, V> sortedHashMap = new LinkedHashMap<>();
        Set<V> sortList = new TreeSet<>(hashMap.values());
        for (V v : sortList) {
            for (Map.Entry<T, V> pair : hashMap.entrySet()) {
                if (v.equals(pair.getValue())) {
                    sortedHashMap.put(pair.getKey(), v);
                }
            }
        }
        return sortedHashMap;
    }
}
