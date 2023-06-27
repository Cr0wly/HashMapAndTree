package crow_collection.map;

public interface Map<K,V> {

    interface Entry<K,V> {
        K getKey();

        V getValue();

        V setValue(V value);

        int hashCode();

        boolean equals(Object o);

    }

    // Query Operations

    V get(Object key);

    // Modification operations

    V put(K key, V value);

}
