package systemtests;

import static org.junit.Assert.assertTrue;

public class AssertionExample {

    public static void main(String[] args) {
        int x = 5;
        assert(x % 2 == 0);
        System.out.println(x);
    }
}
