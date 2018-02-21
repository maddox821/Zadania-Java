import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.junit.Rule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PMO_Test {

	//////////////////////////////////////////////////////////////////////////
	private static final Map<String, Double> tariff = new HashMap<>();

	static {
		tariff.put("singleInterval", 2.0);
		tariff.put("twoIntervals", 2.0);
		tariff.put("threeIntervals", 2.0);
	}

	public static double getTariff(String testName) {
		return tariff.get(testName);
	}
	//////////////////////////////////////////////////////////////////////////

	@Rule
	public TestName name = new TestName();

	private DiceHistogram code2test;
	private Histogram histogram;
	private Map<Integer, Integer> output;
	private Map<Integer, Integer> expected;
	private PMO_Dice myDice;

	private int findKeyToValue(Map<Integer, Integer> map, int value) {
		for (Map.Entry<Integer, Integer> entry : map.entrySet())
			if (entry.getValue() == value)
				return entry.getKey();
		return -1;
	}

	private List<Integer> map2dots(Map<Integer, Integer> map) {

		// odszukac najmniej liczebny klucz
		// zadbac, aby ostatnia liczba do wylosowania byla wlasnie dla tego klucza

		int valueMin = map.values().stream().mapToInt(i -> i).min().getAsInt();
		int keyMin = findKeyToValue(map, valueMin);

		List<Integer> dots = new ArrayList<>();
		int start;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getKey() == keyMin) {
				start = 1;
			} else {
				start = 0;
			}
			for (int i = start; i < entry.getValue(); i++) {
				dots.add(entry.getKey());
			}
		}
		Collections.shuffle(dots);
		dots.add(keyMin);
		return dots;
	}

	private void setDice() {
		PMO_TestHelper.tryExecute(() -> {
			code2test.setDice(myDice);
		}, "setDice");
	}

	private void getHistogram(final int atLeastCounts) {
		histogram = PMO_TestHelper.<Histogram>tryExecute(() -> {
			return code2test.getHistogram(atLeastCounts);
		}, "getHistogram");
	}

	private int getNumberOfBins() {
		return PMO_TestHelper.<Integer>tryExecute(() -> {
			return histogram.numberOfBins();
		}, "histogram.numberOfBins");
	}

	private int getNumberOfDots(int bin) {
		return PMO_TestHelper.<Integer>tryExecute(() -> {
			return histogram.getNumberOfDots(bin);
		}, "histogram.numberOfDots");
	}

	private int getNumberOfCounts(int bin) {
		return PMO_TestHelper.<Integer>tryExecute(() -> {
			return histogram.numberOfCounts(bin);
		}, "histogram.numberOfDots");
	}

	@Before
	public void create() {
		PMO_SystemOutRedirect.startRedirectionToNull();
		code2test = PMO_TestHelper.tryExecute(DiceHistogramImplementation::new,
				"Konstruktor klasy DiceHistogramImplementation");
		PMO_SystemOutRedirect.returnToStandardStream();
		myDice = new PMO_Dice();
		expected = new TreeMap<>();
		output = new TreeMap<>();
	}

	private void buildOutput(int numberOfBins) {
		int numberOfDots;
		int numberOfCounts;
		for (int i = 0; i < numberOfBins; i++) {
			numberOfCounts = getNumberOfCounts(i);
			numberOfDots = getNumberOfDots(i);
			output.put(numberOfDots, numberOfCounts);
		}
	}

	private void test() {
		assertNotNull("Zamiast histogramu jest null", histogram);
		assertEquals("Nieprawidlowa liczba przedzialow klasowych.", expected.keySet().size(), getNumberOfBins());
		buildOutput(expected.keySet().size());
		assertThat("Histogramy nie sa identyczne", output.entrySet(),
				org.hamcrest.Matchers.equalTo(expected.entrySet()));
	}

	@Test(timeout = 500)
	public void singleInterval() {
		expected.put(10, 7);
		expected.put(11, 8);
		expected.put(12, 18);

		List<Integer> dots = map2dots(expected);

		PMO_SystemOutRedirect.println(dots.toString());

		myDice.addDots(dots);
		myDice.addInterval(new PMO_Interval(10, 12));
		setDice();
		getHistogram(7);

		test();
	}

	@Test(timeout = 500)
	public void twoIntervals() {

		expected.put(0, 3);
		expected.put(1, 7);
		expected.put(2, 10);

		expected.put(10, 7);
		expected.put(11, 8);
		expected.put(12, 18);

		List<Integer> dots = map2dots(expected);

		PMO_SystemOutRedirect.println(dots.toString());

		myDice.addDots(dots);
		myDice.addInterval(new PMO_Interval(10, 12));
		myDice.addInterval(new PMO_Interval(0, 2));
		setDice();
		getHistogram(3);

		test();
	}

	@Test(timeout = 500)
	public void threeIntervals() {

		expected.put(0, 30);
		expected.put(1, 7);
		expected.put(2, 10);

		expected.put(10, 7);
		expected.put(11, 8);
		expected.put(12, 18);

		expected.put(20, 7);
		expected.put(21, 6);
		expected.put(22, 18);

		
		List<Integer> dots = map2dots(expected);

		PMO_SystemOutRedirect.println(dots.toString());

		myDice.addDots(dots);
		myDice.addInterval(new PMO_Interval(10, 12));
		myDice.addInterval(new PMO_Interval(0, 2));
		myDice.addInterval(new PMO_Interval(20, 22));
		setDice();
		getHistogram(6);

		test();
	}
}
