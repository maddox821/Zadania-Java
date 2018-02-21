/**
 * Interfejs reprezentuje kostke do gry. Kostka moze miec dowolna liczbe
 * scianek. Kostka nie musi miec ciaglej numeracji oczek, czyli moze np.
 * pozwolic tylko na losowanie liczb 1, 2, 4, 5, 8, 998 i 999.
 *
 */
public interface DiceInterface {
	/**
	 * Wylosowana liczba oczek.
	 * 
	 * @return liczba wylosowanych oczek
	 */
	public int getNumberOfDots();

	/**
	 * Liczba przedzialow, z ktorych odbywa sie losowanie.
	 * 
	 * @return liczba przedzialow
	 */
	public int getNumberOfIntervals();

	/**
	 * Metoda zwraca przedzial o podanym numerze identyfikacyjnym. Poprawny numer
	 * identyfikacyjny miesci sie w przedziale od 0 do getNumberOfIntvals()-1.
	 * 
	 * @param intervalID
	 *            numer przedzialu
	 * @return przedzial
	 */
	public Interval getInterval(int intervalID);
}
