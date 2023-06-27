package crow_collection.tree;

import crow_collection.map.Map;

public interface Tree<V> {
    interface Entry<V> {
        V getValue();

        V setValue();

        boolean equals(Object o);

        int hashCode();

    }

    // Query Operations

    //V get()

    // Modification Operations

    void put(V value);

}
