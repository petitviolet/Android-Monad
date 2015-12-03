package net.petitviolet.monad.list;

import android.support.annotation.NonNull;

import net.petitviolet.monad.Tuple;
import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.maybe.Maybe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListM<A> implements List<A> {
    private final List<A> mList;

    private ListM(List<A> mList) {
        this.mList = mList;
    }

    private ListM() {
        this.mList = new ArrayList<>();
    }

    public static <A> ListM<A> unit() {
        return new ListM<>();
    }

    @SafeVarargs
    public static <A> ListM<A> of(A... args) {
        ListM<A> listM = ListM.unit();
        Collections.addAll(listM, args);
        return listM;
    }

    public static <A> ListM<A> of(List<A> list) {
        return new ListM<>(list);
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ListM) || ((ListM) o).size() != this.size()) {
            return false;
        }
        for (int i = 0; i < ((ListM) o).size(); i++) {
            if (!((ListM) o).get(i).equals(this.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public A get(int location) {
        return mList.get(location);
    }

    @Override
    public int hashCode() {
        return mList.hashCode();
    }

    @Override
    public int indexOf(Object object) {
        return mList.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<A> iterator() {
        return mList.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return mList.lastIndexOf(object);
    }

    @Override
    public ListIterator<A> listIterator() {
        return mList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<A> listIterator(int location) {
        return mList.listIterator(location);
    }

    @Override
    public A remove(int location) {
        return mList.remove(location);
    }

    @Override
    public boolean remove(Object object) {
        return mList.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return mList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return mList.retainAll(collection);
    }

    @Override
    public A set(int location, A object) {
        return mList.set(location, object);
    }

    @Override
    public int size() {
        return mList.size();
    }

    @NonNull
    @Override
    public ListM<A> subList(int start, int end) {
        List<A> sublist = mList.subList(start, end);
        ListM<A> listM = ListM.unit();
        for (A item : sublist) {
            listM.add(item);
        }
        return listM;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return mList.toArray(array);
    }


    public Maybe<A> maybeGet(int index) {
        if (index >= this.size()) {
            return Maybe.of(null);
        }
        return Maybe.of(get(index));
    }

    public A getOrElse(int index, A defaultValue) {
        return maybeGet(index).getOrElse(defaultValue);
    }

    public <B> B foldLeft(B acc, Function.F2<? super B, ? super A, ? super B> func) {
        for (A item : this) {
            acc = (B) func.invoke(acc, item);
        }
        return acc;
    }

    public <B> B foldRight(Function.F2<? super A, ? super B, ? super B> func, B acc) {
        for (A item : this) {
            acc = (B) func.invoke(item, acc);
        }
        return acc;
    }

    public <B> B foldMap(B acc, Function.F1<? super A, ? extends B> converter,
                         Function.F2<? super B, ? super B, ? extends B> accumulator) {
        B result = acc;
        for (A item : this) {
            result = accumulator.invoke(result, converter.invoke(item));
        }
        return result;
    }

    public void foreach(Function.F<? super A> func) {
        for (A item : this) {
            func.invoke(item);
        }
    }

    public <B> ListM<B> map(Function.F1<? super A, ? extends B> func) {
        ListM<B> result = new ListM<>();
        for (A item : this) {
            result.add(func.invoke(item));
        }
        return result;
    }

    public <B> ListM<B> bindMaybe(Function.F1<? super A, Maybe<B>> func) {
        ListM<B> result = new ListM<>();
        for (A item : this) {
            Maybe<B> b = func.invoke(item);
            if (b.isPresent()) {
                result.add(b.get());
            }
        }
        return result;
    }

    public <B> ListM<B> flatMap(Function.F1<? super A, ? extends ListM<B>> func) {
        // use flatMap is not effective from the viewpoint of performance
//        return flatMap(new Function.F1<A, ListM<B>>() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public ListM<B> invoke(A a) {
//                return ListM.of(func.invoke(a));
//            }
//        });
        ListM<B> result = new ListM<>();
        for (A item : this) {
            result.addAll(func.invoke(item));
        }
        return result;
    }

    public ListM<A> filter(Function.F1<? super A, Boolean> func) {
        ListM<A> result = new ListM<>();
        for (A item : this) {
            if (func.invoke(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public ListM<A> filterNot(Function.F1<? super A, Boolean> func) {
        ListM<A> that = this;
        ListM<A> result = filter(func);
        that.removeAll(result);
        return that;
    }

    public Tuple<ListM<A>, ListM<A>> partition(final Function.F1<? super A, Boolean> func) {
        ListM<A> fst = this.filter(func);
        ListM<A> snd = this.filter(new Function.F1<A, Boolean>() {
            @Override
            public Boolean invoke(A a) {
                return !func.invoke(a);
            }
        });

        return Tuple.of(fst, snd);
    }

    @Override
    public void add(int location, A object) {
        mList.add(location, object);
    }

    @Override
    public boolean add(A object) {
        return mList.add(object);
    }

    @Override
    public boolean addAll(int location, Collection<? extends A> collection) {
        return mList.addAll(location, collection);
    }

    @Override
    public boolean addAll(Collection<? extends A> collection) {
        return mList.addAll(collection);
    }

    @Override
    public void clear() {
        mList.clear();
    }

    @Override
    public boolean contains(Object object) {
        return mList.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return mList.containsAll(collection);
    }

    @Override
    public String toString() {
        return "ListM{ " + Arrays.toString(mList.toArray()) + " }";
    }
}