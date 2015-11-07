package net.petitviolet.monad.list;

import android.support.annotation.NonNull;

import net.petitviolet.monad.Tuple;
import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.maybe.Maybe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ListM<A> extends ArrayList<A> {
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

    @NonNull
    @Override
    public ListM<A> subList(int start, int end) {
        List<A> sublist = super.subList(start, end);
        ListM<A> listM = ListM.unit();
        for (A item : sublist) {
            listM.add(item);
        }
        return listM;
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

    public abstract <B> B foldLeft(B acc, Function.F2<? super B, ? super A, ? super B> func);

    public abstract <B> B foldRight(Function.F2<? super A, ? super B, ? super B> func, B acc);

    public abstract <B> B foldMap(B acc, Function.F1<? super A, ? extends B> converter,
                         Function.F2<? super B, ? super B, ? extends B> accumulator);

    public abstract void foreach(Function.F<? super A> func);

    public abstract <B> ListM<B> map(Function.F1<? super A, ? extends B> func);

    public abstract <B> ListM<B> flatMap(Function.F1<? super A, ? extends ListM<B>> func);

    public abstract ListM<A> filter(final Function.F1<? super A, Boolean> func);

    public static <A> ListM<A> unit() {
        return new ArrayListM<>();
    }

    public static <A> ListM<A> of(A... args) {
        ListM<A> listM = ListM.unit();
        Collections.addAll(listM, args);
        return listM;
    }

    public abstract Tuple<ListM<A>, ListM<A>> partition(Function.F1<? super A, Boolean> func);

}