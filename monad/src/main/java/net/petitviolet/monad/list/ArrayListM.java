package net.petitviolet.monad.list;

import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.type.Monoid;

class ArrayListM<A> extends ListM<A> {
    private static final String TAG = ArrayListM.class.getSimpleName();

    ArrayListM() {
    }

    @Override
    public <B> B foldLeft(B acc, Function.F2<? super B, ? super A, ? super B> func) {
        for(A item: this) {
            acc = (B) func.invoke(acc, item);
        }
        return acc;
    }

    @Override
    public <B> B foldRight(Function.F2<? super A, ? super B, ? super B> func, B acc) {
        for(A item: this) {
            acc = (B) func.invoke(item, acc);
        }
        return acc;
    }

    @Override
    public void foreach(Function.F<? super A> func) {
        for(A item: this) {
            func.invoke(item);
        }
    }

    @Override
    public <B> ListM<B> map(Function.F1<? super A, ? extends B> func) {
        ListM<B> result = new ArrayListM<>();
        for(A item: this) {
            result.add(func.invoke(item));
        }
        return result;
    }

    @Override
    public <B> ListM<B> flatMap(Function.F1<? super A, ? extends ListM<B>> func) {
        ListM<B> result = new ArrayListM<>();
        for(A item: this) {
            result.addAll(func.invoke(item));
        }
        return result;
    }

    @Override
    public ListM<A> filter(Function.F1<? super A, Boolean> func) {
        ListM<A> result = new ArrayListM<>();
        for(A item: this) {
            if (func.invoke(item)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public Tuple<ListM<A>, ListM<A>> partition(final Function.F1<? super A, Boolean> func) {
        ListM<A> fst = this.filter(func);
        ListM<A> snd = this.filter(new Function.F1<A, Boolean>() {
            @Override
            public Boolean invoke(A a) {
                return !func.invoke(a);
            }
        });

        return new Tuple<>(fst, snd);
    }

}