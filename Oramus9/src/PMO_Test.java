
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PMO_Test {

	//////////////////////////////////////////////////////////////////////////
	private static final Map<String, Double> tariff = new HashMap<>();
	static {
		tariff.put("basicTest", 2.0);
		tariff.put("basicTest2", 2.0);
		tariff.put("temporaryNoData", 0.7);
		tariff.put("waitException", 0.7);
		tariff.put("endOfData", 0.7);
		tariff.put("urgentData", 0.7);
	}

	public static double getTariff(String testName) {
		return tariff.get(testName);
	}
	//////////////////////////////////////////////////////////////////////////

	private WrappingInterface wrapper;
	private Source source;

	private class IntegerOrException {
		private final Integer value;
		private final Exception exception;

		IntegerOrException(Integer value) {
			this.value = value;
			this.exception = null;
		}

		IntegerOrException(Exception exception) {
			this.exception = exception;
			this.value = null;
		}

		Integer get() throws Exception {
			if (exception != null)
				throw exception;
			return value;
		}
	}

	private class Source implements SourceInterface {

		private List<IntegerOrException> magazine = new ArrayList<>();
		private boolean eof = false;
		private int counter;
		private Integer lastValue;
		private Exception lastException;
		private long doNotCallBefore;

		public void add(Integer value) {
			magazine.add(new IntegerOrException(value));
		}

		public void add(Exception e) {
			magazine.add(new IntegerOrException(e));
		}

		@Override
		public int getValue() throws EndOfDataException, TemporaryNoDataException, WaitException, UrgentException {
			int value;
			lastValue = null;
			lastException = null;
			if (eof) {
				fail("Wywolano getValue(), choc wczesniej zgloszono EndOfDataException");
			}
			if (System.currentTimeMillis() < doNotCallBefore) {
				fail("Wywolano getValue() przed uplywem czasu wskazanego przez WaitException");
			}
			try {
				value = magazine.get(counter).get();
				lastValue = value;
				return value;
			} catch (EndOfDataException e) {
				eof = true;
				lastException = e;
				throw e;
			} catch (TemporaryNoDataException | UrgentException e) {
				lastException = e;
				throw e;
			} catch (WaitException e) {
				lastException = e;
				doNotCallBefore = e.doNothingTime + System.currentTimeMillis();
				throw e;
			} catch (Exception e) {
				return -1;
			} finally {
				counter++;
			}
		}
	}

	@Before
	public void preparation() {
		wrapper = (WrappingInterface) PMO_GeneralPurposeFabric.fabric("Wrapper", "WrappingInterface");
		source = new Source();
	}

	private void setSource(SourceInterface source) {
		PMO_TestHelper.tryExecute(() -> wrapper.setSource(source), "setSource()");
	}

	private void setNumberOfSlots(int slots) {
		PMO_TestHelper.tryExecute(() -> wrapper.setNumberOfSlots(slots), "setNumberOfSlots( " + slots + ")");
	}

	private Integer[] get() {
		return PMO_TestHelper.tryExecute(wrapper::get, "get()");
	}

	private Integer[] createPack(int size, int... values) {
		Integer[] pack = new Integer[size];

		for (int i = 0; i < values.length; i++) {
			pack[i] = values[i];
		}

		return pack;
	}

	private void testResult(Integer[] expected) {
		Integer[] result = get();
		assertArrayEquals("Wynik zwrocony przez metode get() jest bledny", expected, result);
	}

	@Test(timeout = 500)
	public void basicTest() {
		source.add(10);
		source.add(20);
		source.add(30);
		source.add(40);

		Integer[] pack = createPack(4, 10, 20, 30, 40);

		setSource(source);
		setNumberOfSlots(4);

		testResult(pack);
	}

	@Test(timeout = 500)
	public void basicTest2() {
		source.add(10);
		source.add(20);
		source.add(30);
		source.add(40);
		source.add(30);
		source.add(20);

		setSource(source);
		setNumberOfSlots(3);
		Integer[] pack = createPack(3, 10, 20, 30);
		testResult(pack);
		pack = createPack(3, 40, 30, 20);
		testResult(pack);
	}

	@Test(timeout = 500)
	public void temporaryNoData() {
		source.add(10);
		source.add(20);
		source.add(new TemporaryNoDataException());
		source.add(30);
		source.add(40);
		source.add(30);
		source.add(new TemporaryNoDataException());
		source.add(20);

		setSource(source);
		setNumberOfSlots(3);
		Integer[] pack = createPack(3, 10, 20, 30);
		testResult(pack);
		pack = createPack(3, 40, 30, 20);
		testResult(pack);
	}

	@Test(timeout = 750)
	public void waitException() {
		source.add(10);
		source.add(20);
		source.add(new WaitException(100));
		source.add(30);
		source.add(40);
		source.add(30);
		source.add(new WaitException(200));
		source.add(20);

		setSource(source);
		setNumberOfSlots(3);
		Integer[] pack = createPack(3, 10, 20, 30);
		testResult(pack);
		pack = createPack(3, 40, 30, 20);
		testResult(pack);
	}

	@Test(timeout = 750)
	public void endOfData() {
		source.add(10);
		source.add(20);
		source.add(new EndOfDataException());

		setSource(source);
		setNumberOfSlots(3);
		Integer[] pack = createPack(3, 10, 20);
		testResult(pack);
		pack = createPack(3);
		testResult(pack);
	}

	@Test(timeout = 750)
	public void urgentData() {
		source.add(10);
		source.add(20);
		source.add(new UrgentException());
		source.add(30);
		source.add(40);
		source.add(new UrgentException());
		source.add(30);
		source.add(20);
		source.add(new UrgentException());

		setSource(source);
		setNumberOfSlots(3);
		Integer[] pack = createPack(3, 10, 20);
		testResult(pack);
		pack = createPack(3, 30, 40);
		testResult(pack);
		pack = createPack(3, 30, 20);
		testResult(pack);
	}
}