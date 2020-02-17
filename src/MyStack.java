/**
 * Created by Windows 10 on 1/20/2018.
 */
public class MyStack<T> implements IStack<T> {

    private T[] arr;
    int total;

    public MyStack()
    {
        arr = (T[]) new Object[2];
    }

    private void resize(int capacity)
    {
        T[] tmp = (T[]) new Object[capacity];
        System.arraycopy(arr, 0, tmp, 0, total);
        arr = tmp;
    }

    public MyStack<T> push(T ele)
    {
        if (arr.length == total)
            resize(arr.length * 2);
        arr[total++] = ele;
        return this;
    }

    public T pop()
    {
        if (total == 0)
            throw new java.util.NoSuchElementException();
        T ele = arr[--total];
        arr[total] = null;
        if (total > 0 && total == arr.length / 4)
            resize(arr.length / 2);
        return ele;
    }



}