package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;
import net.petitviolet.monad.maybe.Maybe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test cases for Maybe(Just/None)
 */
public class MaybeTest {
    private Maybe<String> just = Maybe.of("hoge");
    private Maybe<String> none = Maybe.none();

    @Test
    public void getJust() {
        assert just.get() == "hoge";
    }

    @Test
    public void getNone() {
        assert none.get() == null;
    }

    @Test
    public void getOrElseJust() {
        assert just.getOrElse("foo") == "hoge";
    }

    @Test
    public void getOrElseNone() {
        assert none.getOrElse("foo") == "foo";
    }

    @Test
    public void equalToJust() {
        assert just.equals(Maybe.of("hoge")) == true;
        assert just.equals(Maybe.of("foo")) == false;
        assert just.equals(Maybe.of(1)) == false;
        assert just.equals(Maybe.of(null)) == false;
    }

    @Test
    public void equalToNone() {
        assert none.equals(Maybe.of("hoge")) == false;
        assert none.equals(Maybe.of("foo")) == false;
        assert none.equals(Maybe.of(1)) == false;
        assert none.equals(Maybe.of(null)) == true;
    }

    @Test
    public void foreachJust() {
        final List<String> list = new ArrayList<>();
        just.foreach(new Function.F<String>() {
            @Override
            public void invoke(String s) {
                list.add(s);
            }
        });
        assert list.size() == 1;
    }

    @Test
    public void foreachNone() {
        final List<String> list = new ArrayList<>();
        none.foreach(new Function.F<String>() {
            @Override
            public void invoke(String s) {
                list.add(s);
            }
        });
        assert list.size() == 0;
    }

    @Test
    public void mapJust() {
        Maybe<String> result = just.map(new Function.F1<String, String>() {
            @Override
            public String invoke(String s) {
                return s + "foo";
            }
        });
        assert result.equals(Maybe.of("hogefoo")) == true;
    }

    @Test
    public void mapNone() {
        Maybe<String> result = none.map(new Function.F1<String, String>() {
            @Override
            public String invoke(String s) {
                return s + "foo";
            }
        });
        assert result.equals(Maybe.of(null)) == true;
    }

    @Test
    public void flatMapJust() {
        Maybe<String> resultJust = just.flatMap(new Function.F1<String, Maybe<String>>() {
            @Override
            public Maybe<String> invoke(String s) {
                return Maybe.of(s + "bar");
            }
        });
        assert resultJust.equals(Maybe.of("hogebar")) == true;
        Maybe<String> resultNone = just.flatMap(new Function.F1<String, Maybe<String>>() {
            @Override
            public Maybe<String> invoke(String s) {
                return Maybe.of(null);
            }
        });
        assert resultNone.equals(Maybe.of(null)) == true;
    }

    @Test
    public void flatMapNone() {
        Maybe<String> resultJust = none.flatMap(new Function.F1<String, Maybe<String>>() {
            @Override
            public Maybe<String> invoke(String s) {
                return Maybe.of(s + "bar");
            }
        });
        assert resultJust.equals(Maybe.of(null)) == true;
        Maybe<String> resultNone = just.flatMap(new Function.F1<String, Maybe<String>>() {
            @Override
            public Maybe<String> invoke(String s) {
                return Maybe.of(null);
            }
        });
        assert resultNone.equals(Maybe.of(null)) == true;
    }

    @Test
    public void isPresentJust() {
        assert just.isPresent() == true;
    }

    @Test
    public void isPresentNone() {
        assert none.isPresent() == false;
    }

    @Test
    public void filterJust() {
        Maybe<String> result = just.filter(new Function.F1<String, Boolean>() {
            @Override
            public Boolean invoke(String s) {
                return s.length() > 3;
            }
        });
        assert result.equals(just) == true;

        Maybe<String> result2 = just.filter(new Function.F1<String, Boolean>() {
            @Override
            public Boolean invoke(String s) {
                return s.length() > 30;
            }
        });
        assert result2.equals(just) == false;
    }

    @Test
    public void filterNone() {
        Maybe<String> result = none.filter(new Function.F1<String, Boolean>() {
            @Override
            public Boolean invoke(String s) {
                return s.length() > 3;
            }
        });
        assert result.equals(none) == true;

        Maybe<String> result2 = none.filter(new Function.F1<String, Boolean>() {
            @Override
            public Boolean invoke(String s) {
                return s.length() > 30;
            }
        });
        assert result2.equals(none) == true;
    }

    private static class AwesomeObj {
        public Map<Integer, String> map;

        public AwesomeObj() {
            this.map = new HashMap<>();
        }

        public Maybe<String> getValue(int key) {
            return Maybe.of(map.get(key));
        }
    }

    @Test
    public void practicalCase() {
        final AwesomeObj obj = new AwesomeObj();
        Map<Integer, String> map = new HashMap<>();

        assert obj.getValue(0).equals(Maybe.none()) == true;

        map.put(1, "good");
        obj.map = map;

        assert obj.getValue(1).equals(Maybe.of("good")) == true;
        assert obj.getValue(100).equals(Maybe.none()) == true;

        Maybe<String> result = Maybe.of(1).flatMap(new Function.F1<Integer, Maybe<String>>() {
            @Override
            public Maybe<String> invoke(Integer integer) {
                return obj.getValue(integer);
            }
        }).map(new Function.F1<String, String>() {
            @Override
            public String invoke(String str) {
                return str + "!";
            }
        });
        assert result.equals(Maybe.of("good!")) == true;
    }
}