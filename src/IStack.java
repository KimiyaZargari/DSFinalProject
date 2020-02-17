/**
 * Created by Windows 10 on 1/20/2018.
 */
interface IStack<T> {
    IStack<T> push(T ele);
    T pop();
}
