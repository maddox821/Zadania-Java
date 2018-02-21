import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PMO_Test {

    //////////////////////////////////////////////////////////////////////////
    private static final Map<String, Double> tariff = new HashMap<>();

    static {
        tariff.put("singleProgramm", 2.0);
        tariff.put("singleProgrammSingleRunTest", 2.0);
        tariff.put("singleProgrammMultipleRunsTest", 2.0);
        tariff.put("singleProgrammMultipleSelfStarts", 2.0);
        tariff.put("multipleProgramms", 2.0);
        tariff.put("multipleProgramms2", 1.6);
        tariff.put("multipleProgramms3", 1.6);
    }

    public static double getTariff(String testName) {
        return tariff.get(testName);
    }
    //////////////////////////////////////////////////////////////////////////

    @Rule
    public TestName name = new TestName();

    private ProgrammableDice code2test;
    private List<Integer> output;

    @Before
    public void create() {
        PMO_SystemOutRedirect.startRedirectionToNull();
        code2test = PMO_TestHelper.tryExecute(Dice::new, "Konstruktor klasy Dice");
        output = new ArrayList<>();
        PMO_SystemOutRedirect.returnToStandardStream();
    }

    private void setNumberOfFaces(int value) {
        PMO_TestHelper.tryExecute(() -> code2test.setNumberOfFaces(value), "setNumberOfFaces");
    }

    private void setProgramm(int[] init, int delay, int[] result, int repetitions) {
        PMO_TestHelper.tryExecute(() -> code2test.addProgram(init, delay, result, repetitions), "addProgram");
    }

    private boolean found(List<Integer> input, int[] sequence, int idx) {
        for (int i = 0; i < sequence.length; i++)
            if (input.get(i + idx) != sequence[i])
                return false;
        return true;
    }

    private List<Integer> findSequence(List<Integer> input, int[] sequence) {
        List<Integer> idx = new ArrayList<>();

        for (int i = 0; i < input.size() - sequence.length; i++) {
            if (found(input, sequence, i))
                idx.add(i);
        }

        return idx;
    }

    private List<Integer> test(int outputs) {
        List<Integer> output = new ArrayList<>(outputs);

        for (int i = 0; i < outputs; i++) {
            output.add(PMO_TestHelper.tryExecute(() -> code2test.get(), "get"));
        }

        return output;
    }

    @Test(timeout = 500)
    public void singleProgramm() {
        setNumberOfFaces(6);
        int[] start = new int[] { 2, 3 };
        int delay = 3;
        int repetitions = 1;
        int[] result = new int[] { 3, 2 };

        setProgramm(start, delay, result, repetitions);

        int[] input4Random = new int[] { 1, 1, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 5, 6 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(input4Random.length);
        System.out.println(Arrays.toString(output.toArray()));

        List<Integer> startIdx = findSequence(output, start);
        List<Integer> resultIdx = findSequence(output, result);

        assertEquals("Problem sekwencji startowej", repetitions, startIdx.size());
        assertEquals("Programu nie uruchomiono poprawnie", repetitions, resultIdx.size());
        assertEquals("Opoznienie programu bledne", delay, resultIdx.get(0) - startIdx.get(0) - start.length);
    }

    @Test(timeout = 500)
    public void singleProgrammSingleRunTest() {
        setNumberOfFaces(6);
        int[] start = new int[] { 2, 3 };
        int delay = 3;
        int repetitions = 1;
        int[] result = new int[] { 3, 2 };

        setProgramm(start, delay, result, repetitions);

        int[] input4Random = new int[] { 1, 1, 1, 2, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 6, 6, 6 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(2 * input4Random.length);
        System.out.println(Arrays.toString(output.toArray()));

        List<Integer> resultIdx = findSequence(output, result);

        assertEquals("Program wykonal sie nielasciwa liczbe razy", repetitions, resultIdx.size());
    }

    @Test(timeout = 500)
    public void singleProgrammMultipleRunsTest() {
        setNumberOfFaces(6);
        int[] start = new int[] { 2, 3 };
        int delay = 2;
        int repetitions = 3;
        int[] result = new int[] { 3, 2 };

        setProgramm(start, delay, result, repetitions);

        int[] input4Random = new int[] { 1, 1, 1, 2, 3, 4, 4, 4, 4, 4, 1, 3, 3, 3 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(repetitions * input4Random.length);
        System.out.println(Arrays.toString(output.toArray()));

        List<Integer> resultIdx = findSequence(output, result);

        assertEquals("Program wykonal sie nielasciwa liczbe razy", repetitions, resultIdx.size());
    }

    @Test(timeout = 500)
    public void singleProgrammMultipleSelfStarts() {
        setNumberOfFaces(6);
        int[] start = new int[] { 2, 3 };
        int delay = 2;
        int repetitions = 3;
        int[] result = new int[] { 3, 2, 3 };

        setProgramm(start, delay, result, repetitions);

        int[] input4Random = new int[] { 1, 2, 3, 0, 0, 0, 0, 0, 1, 1, 2, 2, 4, 4, 5, 4, 3, 2, 1, 1, 1 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(repetitions * input4Random.length);
        System.out.println(Arrays.toString(output.toArray()));

        List<Integer> resultIdx = findSequence(output, result);

        assertEquals("Program wykonal sie nielasciwa liczbe razy", repetitions, resultIdx.size());
    }

    @Test(timeout = 500)
    public void multipleProgramms() {
        setNumberOfFaces(6);
        int[] start1 = new int[] { 4, 2, 3 };
        int delay1 = 2;
        int repetitions1 = 1;
        int[] result1 = new int[] { 3, 2, 4 };

        int[] start2 = new int[] { 2, 3 };
        int delay2 = 2;
        int repetitions2 = 3;
        int[] result2 = new int[] { 5, 5, 5 };

        setProgramm(start1, delay1, result1, repetitions1);
        setProgramm(start2, delay2, result2, repetitions2);

        int[] input4Random = new int[] { 1, 1, 1, 4, 2, 3, 3, 3, 4, 4, 3, 3, 3, 1, 6, 6, 6 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(13);
        System.out.println(Arrays.toString(output.toArray()));
        List<Integer> resultIdx1 = findSequence(output, result1);
        List<Integer> resultIdx2 = findSequence(output, result2);

        assertEquals("Program 1 nie zostal uruchomiony", 1, resultIdx1.size());
        assertEquals("Program 2 nie powinien zostac uruchominy", 0, resultIdx2.size());
    }

    @Test(timeout = 500)
    public void multipleProgramms2() {
        setNumberOfFaces(6);
        int[] start1 = new int[] { 4, 2, 3 };
        int delay1 = 2;
        int repetitions1 = 1;
        int[] result1 = new int[] { 3, 2, 6 };

        int[] start2 = new int[] { 2, 6 };
        int delay2 = 2;
        int repetitions2 = 3;
        int[] result2 = new int[] { 5, 5, 5 };

        setProgramm(start1, delay1, result1, repetitions1);
        setProgramm(start2, delay2, result2, repetitions2);

        int[] input4Random = new int[] { 1, 1, 1, 4, 2, 3, 3, 3, 4, 4, 3, 3, 3, 1, 4, 4, 4, 4 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(17);
        System.out.println(Arrays.toString(output.toArray()));
        List<Integer> resultIdx1 = findSequence(output, result1);
        List<Integer> resultIdx2 = findSequence(output, result2);

        assertEquals("Program 1 nie zostal uruchomiony", 1, resultIdx1.size());
        assertEquals("Program 2 nie zostal uruchomiony", 1, resultIdx2.size());
    }

    @Test(timeout = 500)
    public void multipleProgramms3() {
        setNumberOfFaces(6);
        int[] start1 = new int[] { 1, 4, 2, 3 };
        int delay1 = 2;
        int repetitions1 = 1;
        int[] result1 = new int[] { 3, 2, 6 };

        int[] start2 = new int[] { 4, 2, 3 };
        int delay2 = 2;
        int repetitions2 = 3;
        int[] result2 = new int[] { 5, 5, 5 };

        int[] start3 = new int[] { 2, 3 };
        int delay3 = 2;
        int repetitions3 = 3;
        int[] result3 = new int[] { 6, 6, 6 };

        setProgramm(start1, delay1, result1, repetitions1);
        setProgramm(start2, delay2, result2, repetitions2);
        setProgramm(start3, delay3, result3, repetitions3);

        int[] input4Random = new int[] { 1, 1, 1, 4, 2, 3, 3, 3, 4, 4, 3, 3, 3, 1, 4, 4, 4, 4 };
        Random.setNumbers(input4Random);

        List<Integer> output = test(17);
        System.out.println(Arrays.toString(output.toArray()));
        List<Integer> resultIdx1 = findSequence(output, result1);
        List<Integer> resultIdx2 = findSequence(output, result2);
        List<Integer> resultIdx3 = findSequence(output, result3);

        assertEquals("Program 1 nie zostal uruchomiony", 1, resultIdx1.size());
        assertEquals("Program 2 nie powinien zostalc uruchomiony", 0, resultIdx2.size());
        assertEquals("Program 3 nie powinien zostalc uruchomiony", 0, resultIdx3.size());
    }


}