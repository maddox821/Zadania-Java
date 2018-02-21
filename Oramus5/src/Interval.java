/**
 * Interfejs reprezentujacy przedzial (liczbowy). Przedzial zawiera liczby od
 * lowerEndpoint po upperEndpoint <b>wlacznie</b>.
 */
public interface Interval {
	/**
	 * Metoda zwraca poczatek przedzialu.
	 * 
	 * @return poczatek przedzialu.
	 */
	public int lowerEndpoint();

	/**
	 * Metoda zwraca koniec przedzialu
	 * 
	 * @return koniec przedzialu
	 */
	public int upperEndpoint();
}
