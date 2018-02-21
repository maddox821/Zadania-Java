
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class PMO_Test {

	//////////////////////////////////////////////////////////////////////////
	private static final Map<String, Double> tariff = new HashMap<>();
	static {
		tariff.put("setGetState", 2.0);
		tariff.put("setAndHistoryTest", 2.0);
		tariff.put("setAndCountersTest", 1.0);
		tariff.put("pathATest", 0.7);
		tariff.put("pathBTest", 0.7);
		tariff.put("pathCTest", 0.7);
		tariff.put("pathDTest", 0.7);
		tariff.put("pathETest", 0.7);
	}

	public static double getTariff(String testName) {
		return tariff.get(testName);
	}
	//////////////////////////////////////////////////////////////////////////

	private AirlockInterface airlock;
	private final AirlockInterface.State EDC = AirlockInterface.State.EXTERNAL_DOOR_CLOSED;
	private final AirlockInterface.State EDO = AirlockInterface.State.EXTERNAL_DOOR_OPENED;
	private final AirlockInterface.State IDC = AirlockInterface.State.INTERNAL_DOOR_CLOSED;
	private final AirlockInterface.State IDO = AirlockInterface.State.INTERNAL_DOOR_OPENED;
	private final AirlockInterface.State DIS = AirlockInterface.State.DISASTER;

	@Before
	public void preparation() {
		airlock = (AirlockInterface) PMO_GeneralPurposeFabric.fabric("Airlock", "AirlockInterface");
	}

	private void setState(AirlockInterface.State state2set) {
		PMO_TestHelper.tryExecute(() -> airlock.setState(state2set), "setState(" + state2set + ")");
	}

	private AirlockInterface.State getState() {
		return PMO_TestHelper.tryExecute(airlock::getState, "getState");
	}

	private void setAndTest(AirlockInterface.State state2set) {
		setState(state2set);
		AirlockInterface.State result = getState();

		assertEquals("Bledny odczyt stanu", state2set, result);
	}

	private List<AirlockInterface.State> getHistory() {
		return PMO_TestHelper.tryExecute(airlock::getHistory, "getHistory");
	}

	private Map<AirlockInterface.State, Integer> getCounters() {
		return PMO_TestHelper.tryExecute(airlock::getUsageCounters, "getUsageCounters");
	}

	public Map<AirlockInterface.State, Integer> createMapOfStates() {
		return new HashMap<AirlockInterface.State, Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3348650989466247830L;

			{
				put(AirlockInterface.State.DISASTER, 0);
				put(AirlockInterface.State.EXTERNAL_DOOR_CLOSED, 0);
				put(AirlockInterface.State.EXTERNAL_DOOR_OPENED, 0);
				put(AirlockInterface.State.INTERNAL_DOOR_CLOSED, 0);
				put(AirlockInterface.State.INTERNAL_DOOR_OPENED, 0);
			}
		};
	}

	private void setAndTestHistory(AirlockInterface.State state2set) {
		setAndTest(state2set);
		List<AirlockInterface.State> history = getHistory();
		assertNotNull("Historia nie powinna byc NULL", history);
		assertThat("Historia blednie zainicjowana", history, Matchers.contains(state2set));
	}

	private void testHistory(List<AirlockInterface.State> historyExpected) {
		List<AirlockInterface.State> history = getHistory();
		assertNotNull("Historia nie powinna byc NULL", history);
		assertThat("Historia zawiera bledne dane", history,
				Matchers.contains(historyExpected.toArray(new AirlockInterface.State[historyExpected.size()])));
	}

	private void testCounters(Map<AirlockInterface.State, Integer> countersExpected) {
		Map<AirlockInterface.State, Integer> counters = getCounters();
		assertNotNull("Mapa uzycia stanow nie powinna byc NULL", counters);

		assertThat("Mapa uzycia stanow blednie zainicjowana", counters.entrySet(),
				Matchers.equalTo(countersExpected.entrySet()));
	}

	private void setAndTestCounters(AirlockInterface.State state2set) {
		setAndTest(state2set);
		Map<AirlockInterface.State, Integer> countersExpected = createMapOfStates();
		countersExpected.replace(state2set, 1);
		testCounters(countersExpected);
	}

	private void oid() {
		PMO_TestHelper.tryExecute(airlock::openInternalDoor, "OpenInternalDoor");
	}

	private void oed() {
		PMO_TestHelper.tryExecute(airlock::openExternalDoor, "OpenExternalDoor");
	}

	private void cid() {
		PMO_TestHelper.tryExecute(airlock::closeInternalDoor, "CloseInternalDoor");
	}

	private void ced() {
		PMO_TestHelper.tryExecute(airlock::closeExternalDoor, "CloseExternalDoor");
	}

	private void incrementCounter(Map<AirlockInterface.State, Integer> countersExpected, AirlockInterface.State state) {
		countersExpected.replace(state, countersExpected.get(state) + 1);
	}

	private Map<AirlockInterface.State, Integer> history2counters(List<AirlockInterface.State> historyExpected) {
		Map<AirlockInterface.State, Integer> counters = createMapOfStates();
		historyExpected.forEach((state) -> {
			incrementCounter(counters, state);
		});
		return counters;
	}

	@Test(timeout = 500)
	public void setGetState() {
		setAndTest(AirlockInterface.State.DISASTER);
		setAndTest(AirlockInterface.State.EXTERNAL_DOOR_CLOSED);
		setAndTest(AirlockInterface.State.INTERNAL_DOOR_CLOSED);
		setAndTest(AirlockInterface.State.EXTERNAL_DOOR_OPENED);
		setAndTest(AirlockInterface.State.INTERNAL_DOOR_OPENED);
	}

	@Test(timeout = 500)
	public void setAndHistoryTest() {
		setAndTestHistory(AirlockInterface.State.DISASTER);
		setAndTestHistory(AirlockInterface.State.EXTERNAL_DOOR_CLOSED);
		setAndTestHistory(AirlockInterface.State.INTERNAL_DOOR_CLOSED);
		setAndTestHistory(AirlockInterface.State.EXTERNAL_DOOR_OPENED);
		setAndTestHistory(AirlockInterface.State.INTERNAL_DOOR_OPENED);
	}

	@Test(timeout = 500)
	public void setAndCountersTest() {
		setAndTestCounters(AirlockInterface.State.DISASTER);
		setAndTestCounters(AirlockInterface.State.EXTERNAL_DOOR_CLOSED);
		setAndTestCounters(AirlockInterface.State.INTERNAL_DOOR_CLOSED);
		setAndTestCounters(AirlockInterface.State.EXTERNAL_DOOR_OPENED);
		setAndTestCounters(AirlockInterface.State.INTERNAL_DOOR_OPENED);
	}

	@Test(timeout = 500)
	public void pathATest() {
		setAndTest(AirlockInterface.State.EXTERNAL_DOOR_CLOSED);
		ced();
		oid();
		oid();
		cid();
		oed();
		oid();

		List<AirlockInterface.State> historyExpected = Arrays.asList(EDC, EDC, IDO, IDO, IDC, EDO, DIS);
		Map<AirlockInterface.State, Integer> countersExpected = history2counters(historyExpected);

		testHistory(historyExpected);
		testCounters(countersExpected);
	}

	@Test(timeout = 500)
	public void pathBTest() {
		setAndTest(IDO);
		oid();
		cid();
		cid();
		ced();
		ced();
		oed();
		oed();
		oid();

		List<AirlockInterface.State> historyExpected = Arrays.asList(IDO, IDO, IDC, IDC, EDC, EDC, EDO, EDO, DIS);
		Map<AirlockInterface.State, Integer> countersExpected = history2counters(historyExpected);

		testHistory(historyExpected);
		testCounters(countersExpected);
	}

	@Test(timeout = 500)
	public void pathCTest() {
		setAndTest(EDO);
		ced();
		cid();
		oid();
		ced();
		oed();
		cid();

		List<AirlockInterface.State> historyExpected = Arrays.asList(EDO, EDC, IDC, IDO, EDC, EDO, IDC);
		Map<AirlockInterface.State, Integer> countersExpected = history2counters(historyExpected);

		testHistory(historyExpected);
		testCounters(countersExpected);
	}

	@Test(timeout = 500)
	public void pathDTest() {
		setAndTest(DIS);
		ced();
		cid();
		oid();
		oed();

		List<AirlockInterface.State> historyExpected = Arrays.asList(DIS, DIS, DIS, DIS, DIS);
		Map<AirlockInterface.State, Integer> countersExpected = history2counters(historyExpected);

		testHistory(historyExpected);
		testCounters(countersExpected);
	}

	
	@Test(timeout = 500)
	public void pathETest() {
		setAndTest(EDC);
		oid();
		cid();
		oed();
		ced();
		cid();
		oed();
		cid();

		List<AirlockInterface.State> historyExpected = Arrays.asList(EDC, IDO, IDC, EDO, EDC, IDC, EDO, IDC );
		Map<AirlockInterface.State, Integer> countersExpected = history2counters(historyExpected);

		testHistory(historyExpected);
		testCounters(countersExpected);
	}
	
}
