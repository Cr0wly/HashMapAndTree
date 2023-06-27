package crow_collection.tree;

import java.io.*;
import java.util.ArrayList;

public class AVLTree<V extends Comparable<? super V>> implements Tree<V>, Serializable {

    static class Node<V> implements Entry<V>, Serializable {
        int height;
        V value;
        Node<V> left;
        Node<V> right;

        public Node(V value) {
            this.value = value;
        }

        @Override
        public final V getValue() {
            return null;
        }

        @Override
        public final V setValue() {
            return null;
        }
    }

    static public <V extends Comparable<? super V>> AVLTree<V> loadAVLTree(String path) throws IOException, ClassNotFoundException {
        try(
                FileInputStream inputStream=new FileInputStream(path);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            return (AVLTree<V>) objectInputStream.readObject();
        }
    }

    // Fields

    Node<V> root;

    // Utilities Operations

    int height(Node<V> p)
    {
        return (p == null) ? 0 : p.height;
    }

    void fixheight(Node<V> p) {
        int hl = height(p.left);
        int hr = height(p.right);
        p.height = Math.max(hl, hr) + 1;
    }

    int bfactor(Node<V> p)
    {
        return height(p.right)-height(p.left);
    }

    Node<V> rotateright(Node<V> p) // правый поворот вокруг p
    {
        Node<V> q = p.left;
        p.left = q.right;
        q.right = p;
        fixheight(p);
        fixheight(q);
        return q;
    }

    Node<V> rotateleft(Node<V> q) // левый поворот вокруг q
    {
        Node<V> p = q.right;
        q.right = p.left;
        p.left = q;
        fixheight(q);
        fixheight(p);
        return p;
    }

    public Node<V> balance(Node<V> p) {
        fixheight(p);
        if( bfactor(p)==1 )
        {
            if( bfactor(p.right) < 0 )
                p.right = rotateright(p.right);
            return rotateleft(p);
        }
        if( bfactor(p)==-1 )
        {
            if( bfactor(p.left) > 0  )
                p.left = rotateleft(p.left);
            return rotateright(p);
        }
        return p; // балансировка не нужна
    }

    // Query Operations

    public ArrayList<V> getMinThen(V value) {
        ArrayList<V> result = new ArrayList<>();
        if (root.value.compareTo(value) < 0)
            result.add(root.value);
        minToArrayList(root, result, value);
        return result;
    }

    private void minToArrayList(Node<V> p, ArrayList<V> list, V value) {
        if (p == null) {
            return;
        }
        if (p.left != null) {
            if (p.left.value.compareTo(value) < 0) {
                minToArrayList(p.left, list, value);
                list.add(p.left.value);
            }
        }
        if (p.right != null) {
            if (p.right.value.compareTo(value) < 0) {
                minToArrayList(p.right, list, value);
                list.add(p.right.value);
            }
        }
    }

    // Modification Operations

    @Override
    public void put(V value) {
        root = insert(root, value);
    }

    private Node<V> insert(Node<V> p, V value) {
        if (p == null) {
            return new Node<>(value);
        }
        if (value.compareTo(p.value) < 0)
            p.left = insert(p.left, value);
        else
            p.right = insert(p.right, value);
        return balance(p);
    }

    // Saving Operations

    public void saveToPath(String path) throws IOException {
        try(
            FileOutputStream outputStream=new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(this);
        }
    }

}
