package test;

import java.util.LinkedList;

/**
 *
 */
public class Test1 {

    public static void main(String[] args) {
        Page p1 = new Page(5);
        Page p2 = new Page(7);

        LinkedList<Page> ll = new LinkedList<>();
        ll.add(p1);
        ll.add(p2);

        for (Page p : ll) {
            System.out.println(p.getCount());
        }
    }


}

class Page {
    private int count;

    public Page(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
