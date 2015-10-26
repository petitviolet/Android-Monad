package net.petitviolet.monad.list;

import net.petitviolet.monad.func.Function;

class ArrayListM<A> extends ListM<A> {
    private static final String TAG = ArrayListM.class.getSimpleName();

    ArrayListM() {
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

}