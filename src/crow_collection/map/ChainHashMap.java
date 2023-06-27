package crow_collection.map;

import java.util.Objects;

public class ChainHashMap<K,V> implements Map<K,V> {

    static final int DEFAULT_CAPASITY = 1 << 4;

    static class Node<K,V> implements Entry<K, V> {
        final K key;
        V value;
        final int hash;
        Node<K,V> next;

        public Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public final String toString() { return key + "=" + value; }

        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;

            return o instanceof Entry<?,?> e
                    && Objects.equals(key, e.getKey())
                    && Objects.equals(value, e.getValue());
        }
    }

    // Static Utilities

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // Fields

    Node<K,V>[] table;

    int size;

    // Query Operations

    @Override
    public V get(Object key) {
        if (table != null && table.length != 0) {
            int n, i, hash;
            n = table.length;
            hash = hash(key);
            i = (n - 1) & hash;

            if (table[i] != null) {
                Node<K,V> p = table[i];
                if (p.hash == hash && Objects.equals(key, p.key))
                    return p.getValue();
                else {
                    while (p.next != null) {
                        p = p.next;
                        if (p.hash == hash && Objects.equals(key, p.key))
                            return p.getValue();
                    }
                }
            }
        }
        return null;
    }

    // Modification Operations

    @Override
    public V put(K key, V value) {
        return put(hash(key), key, value);
    }

    public V put(int hash, K key, V value) {
        int n, i;
        if (table == null || table.length == 0)
            table = (Node<K, V>[]) new Node[DEFAULT_CAPASITY];
        n = table.length;
        i = (n - 1) & hash;

        if (table[i] == null)
            table[i] = new Node<>(hash, key, value, null);
        else {
            Node<K,V> p, e;
            p = table[i];
            if (p.hash == hash && Objects.equals(key, p.key))
                e = p;
            else {
                while (true) {
                    e = p.next;
                    if (e == null) {
                        p.next = new Node(hash, key, value, null);
                        break;
                    }
                    if (e.hash == hash && Objects.equals(key, e.key))
                        break;
                    p = e;
                }
            }
            if (e != null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        size++;
        return null;
    }

}
