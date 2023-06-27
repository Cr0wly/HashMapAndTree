import crow_collection.map.ChainHashMap;
import crow_collection.tree.AVLTree;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void ex() {
        ChainHashMap<Integer, String> map = new ChainHashMap<>();

        map.put(212133, "Лидия Аркадьевна Бубликова");
        map.put(162348, "Иван Михайлович Серебряков");
        map.put(8082771, "Дональд Джон Трамп");

        System.out.println(map.get(11111));
        System.out.println(map.get(8082771));


        AVLTree<String> tree = new AVLTree<>();

        tree.put("a");
        tree.put("b");
        tree.put("c");
        tree.put("d");

        ArrayList<String> result = tree.getMinThen("f");
        result.forEach(System.out::println);

        String path = "tree";

        try {
            tree.saveToPath(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("saved");

        try {
            AVLTree<? super String> loadedTree = AVLTree.<String>loadAVLTree(path);
            System.out.println("loaded");

            ArrayList<? super String> result2 = loadedTree.getMinThen("f");
            result2.forEach(System.out::println);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ex();

    }
}