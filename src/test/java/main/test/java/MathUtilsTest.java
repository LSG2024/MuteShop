package main.test.java;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

class MathUtils {
    public int add(int a, int b) {
        return a + b;
    }
}

public class MathUtilsTest {
    @Test
    public void testAdd() {
        MathUtils math = new MathUtils();
        assertEquals(5, math.add(2, 3));
        assertEquals(0, math.add(-1, 1));
    }
}