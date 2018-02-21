/**
 * Wyjatek pojawiający sie gdy serwis jest zbyt obciazony praca. Pole
 * doNothingTime okresla okres czasu, ktory musi zostac odczekany przed
 * ponowieniem komunikacji z serwisem.
 * 
 * Wygodny mechanizm usypiania programu w Java to zastosowanie metody
 * {@link Thread#sleep(long)}.
 */
public class WaitException extends Exception {
	private static final long serialVersionUID = -3831548346367132689L;
	final public long doNothingTime;

	public WaitException(long time) {
		doNothingTime = time;
	}
}
