package net.petitviolet.monad;

import net.petitviolet.monad.Identity;
import net.petitviolet.monad.func.Function;

import org.junit.Test;

import java.lang.Integer;
import java.lang.Override;

import static org.junit.Assert.*;

public class IdentityTest {

    @Test
    public void testId() {
        Identity<Integer> id = Identity.id(1);
        assert id.value == 1;
    }

    @Test
    public void testEquals() {
        Identity<Integer> id1 = Identity.id(1);
        Identity<Integer> id2 = Identity.id(1);
        assert id1.equals(id2);
    }

    @Test
    public void testFlatMap() {
        Identity<String> id = Identity.id("hoge");
        Identity<Integer> result = id.flatMap(new Function.F1<String, Identity<Integer>>() {
            @Override
            public Identity<Integer> invoke(String s) {
                return Identity.id(s.length());
            }
        });
        assert result.value.equals(4);
    }

    @Test
    public void testMap() {
        Identity<Integer> id = Identity.id(1);
        Identity<Integer> result = id.map(new Function.F1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer integer) {
                return integer * 2;
            }
        });
        assert result.value == 2;
    }
}