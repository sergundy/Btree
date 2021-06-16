package new_sem.lab_3;

public class btest {
    
    public static void main(String[] args) {
        BTree<Integer, String> st = new BTree<Integer, String>();

        for(int i = 0; i < 50; i++){
            st.put(i,"a");
        }

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        System.out.print(st.toString());

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        // Демонстрация работы поиска по ключу
        System.out.println("Значение 1: " + st.get(1));
        System.out.println("Значение 2: " + st.get(2));

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        // Демонстрация работы метода для просчёта количества пар в дереве
        System.out.println("size:    " + st.size());

        // Демонстрация работы метода для просчёта высоты дерева
        System.out.println("height:  " + st.height());

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        System.out.println("All elements have been deleted: \n");

        // Демонстрация работы метода для удаления всех элементов
        st.deleteAll();
        //System.out.println("espn: " + st.get("www.espn.com"));
        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());

        /* Демонстрация работы метода для проверки на пустоту 
        (true если нет элементов, в противном случае false)*/
        System.out.println("empty check: " + st.isEmpty());

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

    }

}

