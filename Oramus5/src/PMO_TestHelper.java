import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * TestHelper
 * 
 * @author oramus
 * @version 0.5
 */
public class PMO_TestHelper {
	public static void showException(Exception e) {
		PMO_SystemOutRedirect.returnToStandardStream();
		PMO_SystemOutRedirect.println("Przechwycono wyjatek: " + e.toString() );
		PMO_SystemOutRedirect.println("Stos:");
		e.printStackTrace();
	}

	public static void tryExecute(Supplier<Boolean> code, String methodName, boolean expected) {
		try {
			boolean result = code.get();
			if (expected)
				assertEquals("Wprowadzono poprawne dane, a metoda " + methodName + " zglasza blad ", true, result);
			else
				assertEquals(
						"Wprowadzono bledne dane, a metoda " + methodName + " zglasza poprawne zakonczenie operacji ",
						false, result);
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			PMO_TestHelper.showException(e);
			fail("W trakcie pracy metody " + methodName + " doszlo do wyjatku");
		}
	}

	public static <T> T tryExecute(Supplier<T> code, String methodName) {
		try {
			return code.get();
		} catch ( StackOverflowError e ) {
			fail("W trakcie pracy metody " + methodName + " pojawil sie StackOverflowError");			
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			PMO_TestHelper.showException(e);
			fail("W trakcie pracy metody " + methodName + " doszlo do wyjatku");
		}
		return null; // to dlatego, ze kompilator nie wie, ze fail zakonczy
						// prace programu
	}

	public static void tryExecute(Runnable code, String methodName) {
		try {
			code.run();
		}  catch ( StackOverflowError e ) {
			fail("W trakcie pracy metody " + methodName + " pojawil sie StackOverflowError");			
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			PMO_TestHelper.showException(e);
			fail("W trakcie pracy metody " + methodName + " doszlo do wyjatku");
		}
	}

	@FunctionalInterface
	public interface VoidOrException {
		public void run() throws Exception;
	}

	public static void tryVoidOrException(VoidOrException call, boolean exceptionExpected, String methodName) {
		try {
			call.run();
			if (exceptionExpected)
				fail("W trakcie pracy metody " + methodName + " nie doszlo do oczekiwanego wyjatku");
		} catch ( StackOverflowError e ) {
			fail("W trakcie pracy metody " + methodName + " pojawil sie StackOverflowError");			
		} catch (Exception e) {
			PMO_SystemOutRedirect.returnToStandardStream();
			PMO_TestHelper.showException(e);
			if (!exceptionExpected)
				fail("W trakcie pracy metody " + methodName + " doszlo do nieoczekiwanego wyjatku");
		}
	}

	public static Integer[] convert(int... table) {
		Integer[] result = new Integer[table.length];
		for (int i = 0; i < table.length; i++)
			result[i] = table[i];
		return result;
	}

	@SafeVarargs
	public static <T> T[] convert(T... table) {
		return table;
	}

	public static <T> List<T> convert2list(T[] table) {
		return Arrays.asList(table);
	}

	public static void showMethodName() {
		PMO_SystemOutRedirect.println("_____" + Thread.currentThread().getStackTrace()[2].getMethodName() + "_____");
	}

}
