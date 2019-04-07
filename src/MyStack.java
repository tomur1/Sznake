import java.util.LinkedList;

public class MyStack<T> {

    private LinkedList<T> list;

    MyStack() {
        list = new LinkedList<T>();
    }

    public void add(T obj) {
        list.addFirst(obj);
    }

    public T next() {
        return list.removeFirst();
    }

    public long lenght() {
        return list.size();
    }

}
