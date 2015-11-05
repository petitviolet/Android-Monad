package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.list.ListM;
import net.petitviolet.monad.maybe.Maybe;

import org.junit.Test;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases for ListM(ArrayListM)
 */
public class ListMTest {
    private ListM<String> mListM = ListM.of("a", "bb", "ccc");

    @Test
    public void testMaybeGetJust() {
        assert mListM.maybeGet(0).equals(Maybe.of("a"));
    }

    @Test
    public void testMaybeGetNone() {
        assert mListM.maybeGet(100).equals(Maybe.<String>of(null));
    }

    @Test
    public void testGetOrElseGet() {
        assert mListM.getOrElse(0, "foo").equals("a");
    }

    @Test
    public void testGetOrElseElse() {
        assert mListM.getOrElse(100, "foo").equals("foo");
    }

    @Test
    public void testMap() {
        ListM<Integer> result = mListM.map(new Function.F1<String, Integer>() {
            @Override
            public Integer invoke(String s) {
                return s.length();
            }
        });
        assert result.equals(ListM.of(1, 2, 3));
    }

    @Test
    public void testFlatMap() {
        ListM<Integer> result = mListM.flatMap(new Function.F1<String, ListM<Integer>>() {
            @Override
            public ListM<Integer> invoke(String s) {
                return ListM.of(s.length());
            }
        });
        assert result.equals(ListM.of(1, 2, 3));
    }

    @Test
    public void testForeach() {
        final List<String> box = new ArrayList<>();
        mListM.foreach(new Function.F<String>() {
            @Override
            public void invoke(String s) {
                box.add(s);
            }
        });
        assert box.size() == mListM.size();
    }

    @Test
    public void testFilter() {
        ListM<String> result = mListM.filter(new Function.F1<String, Boolean>() {
            @Override
            public Boolean invoke(String s) {
                return s.length() % 2 == 0;
            }
        });
        assert result.equals(ListM.of("bb"));
    }

    @Test
    public void testFoldLeft() {
        Integer result = mListM.foldLeft(0, new Function.F2<Integer, String, Integer>() {
            @Override
            public Integer invoke(Integer integer, String s) {
                return integer + s.length();
            }
        });
        assert result == 6;
    }

    @Test
    public void testFoldRight() {
        Integer result = mListM.foldRight(new Function.F2<String, Integer, Integer>() {
            @Override
            public Integer invoke(String s, Integer integer) {
                return integer + s.length();
            }
        }, 0);
        assert result == 6;
    }

    @Test
    public void testEqualsTrue() {
        assert mListM.equals(ListM.of("a", "bb", "ccc")) == true;
    }

    @Test
    public void testEqualsFalseType() {
        assert mListM.equals(Arrays.asList("a", "bb", "ccc")) == false;
    }
    @Test
    public void testEqualsFalseType2() {
        assert mListM.equals(1) == false;
    }

    @Test
    public void testEqualsFalseSize() {
        assert mListM.equals(ListM.of("a", "bb", "ccc", "dddd")) == false;
    }

    @Test
    public void testEqualsFalseContents() {
        assert mListM.equals(ListM.of("a", "bb", "cc")) == false;
    }
}