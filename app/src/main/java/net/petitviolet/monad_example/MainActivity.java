package net.petitviolet.monad_example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.list.ListM;
import net.petitviolet.monad.maybe.Maybe;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            testMaybeWithLambda(3);
            testMaybeWithLambda(100);
            testMaybeOldStyle(null);
            testListMWithLambda();
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    private Maybe<Integer> findByNumber(Integer target) {
        List<Integer> sourceList = Arrays.asList(2, 3, 5, 7, 9, 11);
        return Maybe.of(sourceList.contains(target) ? sourceList.indexOf(target) : null);
    }

    private void testMaybeWithLambda(Integer x) {
        Log.d(TAG, "lambda start↓↓↓↓↓↓↓↓↓↓");
        Maybe<Integer> maybeInt = Maybe.of(x);
        Log.d(TAG, "maybeInt:" + maybeInt);
        maybeInt
                .flatMap(this::findByNumber)
                .map(integer -> integer + 5)
                .filter(integer -> integer % 2 == 0)
                .foreach(integer -> Log.d(TAG, "result: " + integer));
        Log.d(TAG, "lambda end↑↑↑↑↑↑↑↑↑↑");
    }

    private void testMaybeOldStyle(Integer x) {
        Log.d(TAG, "oldstyle start↓↓↓↓↓↓↓↓↓↓");

        Maybe<Integer> maybeInt = Maybe.of(x);
        Log.d(TAG, "maybeInt:" + maybeInt);
        maybeInt.flatMap(new Function.F1<Integer, Maybe<Integer>>() {
            @Override
            public Maybe<Integer> invoke(Integer integer) {
                return Maybe.of(integer * 2);
            }
        }).map(new Function.F1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer integer) {
                return integer + 5;
            }
        }).filter(new Function.F1<Integer, Boolean>() {
            @Override
            public Boolean invoke(Integer integer) {
                return integer % 2 == 0;
            }
        }).foreach(new Function.F<Integer>() {
            @Override
            public void invoke(Integer integer) {
                Log.d(TAG, "result: " + integer);
            }
        });

        Log.d(TAG, "oldstyle end↑↑↑↑↑↑↑↑↑↑");
    }

    private void testListMWithLambda() {
        ListM<Integer> listM = ListM.unit();
        listM.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ListM<String> seed = listM.filter(i -> i % 2 == 0)
                .flatMap(this::alphabets)
                .map(s -> s + "!")
                .map(s -> {
                    Log.d(TAG, s);
                    return s;
                });
        String rightResult = seed.foldRight((a, acc) -> a + ", " + acc, " <= end");
        String leftResult = seed.foldLeft("start -> ", (acc, a) -> acc + ", " + a);
        Log.d(TAG, "resultRight => " + rightResult);
        Log.d(TAG, "resultLeft => " + leftResult);
    }

    private static final ListM<String> ALPHABETS = ListM.unit();

    static {
        for (char a = 'a'; a <= 'z'; a++) {
            ALPHABETS.add(String.valueOf(a));
        }
    }

    private ListM<String> alphabets(int num) {
        return ALPHABETS.subList(0, num <= ALPHABETS.size() ? num : ALPHABETS.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
