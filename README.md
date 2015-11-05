# Android-Monad


[![Download](https://api.bintray.com/packages/petitviolet/maven/android-monad/images/download.svg) ](https://bintray.com/petitviolet/maven/android-monad/\_latestVersion)

This repository is Yet Another Android-M.

Implemented "Monadic" data structures.

# Set up

```groovy
repositories {
    // ...
    jcenter()
}

// ...

dependencies {
    compile 'net.petitviolet.android:monad:0.1.0'
}
```

And, I strongly recommend to use [orfjackal/retrolambda](https://github.com/orfjackal/retrolambda) together.

# How to Use

## Maybe

The name of `Maybe` is derived from Haskell.
In Scala, `Option`.

```java
// with lambda
Maybe<Integer> maybeInt = Maybe.of(x);
maybeInt.flatMap(integer -> Maybe.of(integer % 2 == 0 ? integer : null))
        .map(integer -> integer + 5)
        .filter(integer -> integer % 3 == 0)
        .foreach(integer -> Log.d(TAG, "result: " + integer));

// without lambda
maybeInt.flatMap(new Function.F1<Integer, Maybe<Integer>>() {
    @Override
    public Maybe<Integer> invoke(Integer integer) {
        return Maybe.of(integer % 2 == 0 ? integer : null);
    }
}).map(new Function.F1<Integer, Integer>() {
    @Override
    public Integer invoke(Integer integer) {
        return integer + 5;
    }
}).filter(new Function.F1<Integer, Boolean>() {
    @Override
    public Boolean invoke(Integer integer) {
        return integer % 3 == 0;
    }
}).foreach(new Function.F<Integer>() {
    @Override
    public void invoke(Integer integer) {
        Log.d(TAG, "result: " + integer);
    }
});
```

More detailes of `Maybe` interface is shown in [MaybeTest.java](https://github.com/petitviolet/Android-Monad/blob/master/monad%2Fsrc%2Ftest%2Fjava%2Fnet%2Fpetitviolet%2Fmaybe%2FMaybeTest.java).

## ListM

Implemented `ListM` based on `ArrayList` as a implementation of `List` interface.

```java
ListM<Integer> listM = ListM.unit();
listM.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
ListM<String> result = listM.filter(i -> i % 3 == 0)
        .flatMap(i -> ListM.<Integer>of(i * 10))
        .map(s -> s + "!");
        .foldRight((a, acc) -> a + ", " + acc, " <= end");
// result -> 90!, 60!, 30!, <= end
```

More detailes of `ListM` interface is shown in [ListMTest.java](https://github.com/petitviolet/Android-Monad/blob/master/monad%2Fsrc%2Ftest%2Fjava%2Fnet%2Fpetitviolet%2Flist%2FListMTest.java).

# Lisence

This code is licensed under the Apache Software License 2.0.
