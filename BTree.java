package new_sem.lab_3;

public class BTree<Key extends Comparable<Key>, Value>  {

    // данное значение должно быть >= 2
    private static final int M = 4;

    public Node root; // корень B-дерева       
    public int height; // высота B-дерева     
    public int n; // количество пар ключ-значение в B-дереве         

    private static final class Node {
        private int m; // число детей                           
        private Entry[] children = new Entry[M]; // массив детей   

        // создаем узел с k дочерними элементами
        private Node(int k) {
            m = k;
        }
    }
    
    // внутренние узлы: используют только key и next
    // внешние узлы: используют только key и val
    @SuppressWarnings("rawtypes")
    private static class Entry {
        private Comparable key;
        private Object val;
        private Node next;   

        public Entry(Comparable key, Object val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    // инициализируем пустое B-дерево.
    public BTree() {
        root = new Node(0);
    }

    // проверка на пустоту
    public boolean isEmpty() {
        return size() == 0;
    }

    // получение количества пар в дереве
    public int size() {
        return n;
    }

    // получение значения высоты дерева
    public int height() {
        return height;
    }

    // получение значения по ключу
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height);
    }

    @SuppressWarnings("unchecked")
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

         // внешний узел
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].val;
            }
        }

        // внутренний узел
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }

    // добавление пары ключ-значение
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = put(root, key, val, height); 
        n++;
        if (u == null) return;

        // деление узла
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    // добавление пары ключ-значение
    private Node put(Node h, Key key, Value val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        // внешний узел
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // внутренний узел
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = put(h.children[j++].next, key, val, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.val = null;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--)
            h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // разбиваем узел пополам
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    // метод для вывода дерева
    public String toString() {
        return toString(root, height, "") + "\n";
    }

    // метод для вывода дерева
    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " | " + children[j].val + "\n");
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s.append("\n" + indent + "(----------------)\n");
                s.append(toString(children[j].next, ht-1, indent + " "));
            }
        }
        return s.toString();
    }

    @SuppressWarnings("unchecked")
    // метод сравнения (необходим, чтобы не возникало ошибок в методах search и put)
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }
    // метод сравнения (необходим, чтобы не возникало ошибок в методах search и put)
    @SuppressWarnings("unchecked")
    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

    // Удаление всех элементов
    public void deleteAll() {
        root = null;
        height = 0;
        n = 0;
    }
}
