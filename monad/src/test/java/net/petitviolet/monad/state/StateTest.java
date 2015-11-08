package net.petitviolet.monad.state;

import net.petitviolet.monad.Tuple;
import net.petitviolet.monad.func.Function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases for State monad
 */
public class StateTest {
    @Test
    public void testInit() {
        System.out.println("testUnit");
        Tuple<Integer, Integer> result = State.<Integer, Integer>init(new Function.F1<Integer, Tuple<Integer, Integer>>() {
            @Override
            public Tuple<Integer, Integer> invoke(Integer integer) {
                return Tuple.of(1, integer);
            }
        }).apply(100);
        System.out.println("testInit");
        System.out.println(result);
        assert result._1 == 1;
        assert result._2 == 100;
    }

    @Test
    public void testUnit() {
        Tuple<Integer, Integer> result = State.<Integer, Integer>unit(1).apply(100);
        System.out.println(result);
        assert result._1 == 1;
        assert result._2 == 100;
    }

    @Test
    public void testMap() {
        System.out.println("testMap");
        Tuple<Integer, Integer> result = State.<Integer, Integer>unit(1)
                .map(new Function.F1<Integer, Integer>() {
                    @Override
                    public Integer invoke(Integer integer) {
                        return integer * 2;
                    }
                }).apply(100);
        System.out.println(result);
        assert result._1 == 2;
        assert result._2 == 100;
    }

    @Test
    public void testFlatMap() {
        System.out.println("testFlatMap");
        Tuple<Integer, Integer> result = State.<Integer, Integer>unit(1)
                .flatMap(new Function.F1<Integer, State<Integer, Integer>>() {
                    @Override
                    public State<Integer, Integer> invoke(Integer integer) {
                        return State.unit(integer * 2);
                    }
                }).apply(100);
        System.out.println(result);
        assert result._1 == 2;
        assert result._2 == 100;
    }

    @Test
    public void testCombined() {
        System.out.println("testCombined");

        Tuple<Integer, List<String>> result = State.init(new Function.F1<List<String>, Tuple<Integer, List<String>>>() {
            @Override
            public Tuple<Integer, List<String>> invoke(List<String> strings) {
                strings.add("nice");
                return Tuple.of(1, strings);
            }
        }).map(new Function.F1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer i) {
                System.out.println("map: " + i);
                return i + 10;
            }
        }).map(new Function.F1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer i) {
                System.out.println("map: " + i);
                return i * 2;
            }
        }).flatMap(new Function.F1<Integer, State<List<String>, Integer>>() {
            @Override
            public State<List<String>, Integer> invoke(final Integer integer) {
                return State.init(new F1<List<String>, Tuple<Integer, List<String>>>() {
                    @Override
                    public Tuple<Integer, List<String>> invoke(List<String> strings) {
                        if (integer % 2 == 0) {
                            strings.add("awesome");
                            return Tuple.of(integer, strings);
                        } else {
                            strings.add("Oops");
                            return Tuple.of(-100, strings);
                        }
                    }
                });
            }
        }).apply(new ArrayList<String>());

        System.out.println(result);

        assert result._1 == 22;
        assert result._2.size() == 2;
        assert result._2.get(0).equals("nice");
        assert result._2.get(1).equals("awesome");
    }
}
