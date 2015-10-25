package net.petitviolet.monad.list;

import android.support.annotation.NonNull;

import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.maybe.Maybe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class ListM<A> extends ArrayList<A> {
    @NonNull
    @Override
    public ListM<A> subList(int start, int end) {
        List<A> sublist = super.subList(start, end);
        ListM<A> listM = ListM.unit();
        for(A item: sublist) {
            listM.add(item);
        }
        return listM;
    }


    public Maybe<A> maybeGet(int index) {
        return Maybe.of(get(index));
    }

    public A maybeGetOrElse(int index, A defaultValue) {
        return maybeGet(index).getOrElse(defaultValue);
    }

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
}