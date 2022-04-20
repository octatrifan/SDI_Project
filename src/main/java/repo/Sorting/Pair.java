package repo.Sorting;

import java.io.Serializable;
import java.util.Objects;

/**
 * Comparable Pair class
 *
 * @author Liviu.
 */

public class Pair<T1 extends Comparable<T1>, T2 extends Comparable<T2>> implements Serializable, Comparable<Pair<T1, T2>> {

    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

    @Override
    public String toString(){
        return "" + this.first + "," + this.second;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public int compareTo(Pair<T1, T2> other) {
        int resultFirst = this.first.compareTo(other.getFirst());
        if(resultFirst != 0)
            return resultFirst;
        else
            return this.second.compareTo(other.getSecond());
    }
}