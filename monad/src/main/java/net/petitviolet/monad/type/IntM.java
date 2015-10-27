package net.petitviolet.monad.type;

public class IntM implements Monoid<Integer> {
    private static final String TAG = IntM.class.getSimpleName();
    private final Integer mValue;

    private IntM(Integer mValue) {
        this.mValue = mValue;
    }

    public static IntM of(int i) {
        return new IntM(i);
    }

    @Override
    public Monoid<Integer> empty() {
        return IntM.of(0);
    }

    @Override
    public Monoid<Integer> add(Integer integer) {
        return IntM.of(mValue + integer);
    }

    @Override
    public Integer get() {
        return mValue;
    }
}