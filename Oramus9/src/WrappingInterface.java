/**
 * Interfejs narzedzia laczacego dane pobierane z SourceInterface w paczki
 * (tablice) o podanym rozmiarze.
 * 
 */
public interface WrappingInterface {
	/**
	 * Metoda okresla ilosc slotow w paczce bedacej wynikiem pracy metody get.
	 * 
	 * @param slots
	 *            liczba danych w jednej paczce
	 */
	void setNumberOfSlots(int slots);

	/**
	 * Metoda pozwala przekazac dostep do zrodla, z ktorego beda 
	 * nastepnie pobierane dane.
	 * 
	 * @param stream
	 *            strumien-zrodlo danych
	 */
	void setSource(SourceInterface stream);

	/**
	 * Metoda zwraca slots danych odebranych ze zrodla. Dane przekazywane sa
	 * uzytkownikowi w postaci tablicy o slots elementach. Mozliwe jest przekazanie
	 * tablicy, w ktorej nie wszystkie pozycje sa uzyte - cos takiego moze nastapic
	 * wylacznie na skutek wystapienia wyjatkow {@link EndOfDataException} lub
	 * {@link UrgentException}. <br><b>Uwaga</b>: tablica zawsze ma miec rozmiar slots
	 * elementow.
	 * 
	 * @return tablica z danymi pobranymi ze strumienia
	 */
	Integer[] get();
}
