import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PMO_Test {

    //////////////////////////////////////////////////////////////////////////
    private static final Map<String, Double> tariff = new HashMap<>();
    static {
        tariff.put("combinationTest", 2.0);
        tariff.put("combinationPrzyklad", 2.0);
        tariff.put("combinationTest2", 1.6);
        tariff.put("combinationTest3", 1.6);
        tariff.put("interweavingTest", 2.0);
        tariff.put("interweaving2Test", 1.6);
        tariff.put("interweaving2Test2", 1.5);
    }

    public static double getTariff(String testName) {
        return tariff.get(testName);
    }
    //////////////////////////////////////////////////////////////////////////

    private PMO_ProgrammableRandom random;
    private ArrayHelper ah;
    private int[] result;

    private void createAnonymizer() {
        ah = PMO_TestHelper.tryExecute(ArrayHelper::new, "Konstruktor klasy");
    }

    @Before
    public void createArrayParserObject() {
        createAnonymizer();
    }

    @org.junit.Test(timeout = 500)
    public void combinationPrzyklad() {
        random = new PMO_ProgrammableRandom(0,3,3,0,1);
        result = PMO_TestHelper.tryExecute(() -> ah.combination(new int[] { 1, 1, 0, 2, 3 }, random, 3),
                "combination");
        Assert.assertArrayEquals("Test niezaliczony - dane z przykladu z opisu", new int[] { 1, 2, 1 }, result);
    }

    @org.junit.Test(timeout = 500)
    public void combinationTest() {
        random = new PMO_ProgrammableRandom(0, 2, 4);
        result = PMO_TestHelper.tryExecute(() -> ah.combination(new int[] { 0, 1, 2, 3, 4, 5 }, random, 3),
                "combination");
        Assert.assertArrayEquals("Losowanie bez powtorzen, wynik nieprawidlowy", new int[] { 0, 2, 4 }, result);
    }

    @org.junit.Test(timeout = 500)
    public void combinationTest2() {
        random = new PMO_ProgrammableRandom(0, 0, 2, 2, 4);
        result = PMO_TestHelper.tryExecute(() -> ah.combination(new int[] { 0, 1, 2, 3, 4, 5 }, random, 3),
                "combination");
        Assert.assertArrayEquals("Random zwraca te same indeksy po 2x, wynik nie jest prawidlowy",
                new int[] { 0, 2, 4 }, result);
    }

    @org.junit.Test(timeout = 500)
    public void combinationTest3() {
        random = new PMO_ProgrammableRandom(0, 2, 0, 2, 0, 4);
        result = PMO_TestHelper.tryExecute(() -> ah.combination(new int[] { 0, 1, 2, 3, 4, 5 }, random, 3),
                "combination");
        Assert.assertArrayEquals("Random zwraca te same indeksy, wynik nie jest prawidlowy", new int[] { 0, 2, 4 },
                result);
    }

    @org.junit.Test(timeout = 500)
    public void interweavingTest() {
        result = PMO_TestHelper.tryExecute(() -> ah.interweaving(new int[][] { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 3, 4 } }),
                "interweaving");
        Assert.assertArrayEquals("Wynik nie jest prawidlowy", new int[] { 0, 1, 2, 0, 1, 3, 0, 1, 4 }, result);
    }

    @org.junit.Test(timeout = 500)
    public void interweaving2Test() {
        result = PMO_TestHelper.tryExecute(() -> ah.interweaving2(new int[][] { { 0, 0, 0 }, { 1 }, { 2, 3, 4 } }),
                "interweaving2");
        Assert.assertArrayEquals("Wynik nie jest prawidlowy", new int[] { 0, 1, 2, 0, 3, 0, 4 }, result);
    }

    @org.junit.Test(timeout = 500)
    public void interweaving2Test2() {
        result = PMO_TestHelper.tryExecute(() -> ah.interweaving2(new int[][] { { 0, 0, 0 }, {}, { 2, 3, 4 } }),
                "interweaving2");
        Assert.assertArrayEquals("Wynik nie jest prawidlowy", new int[] { 0, 2, 0, 3, 0, 4 }, result);
    }
}