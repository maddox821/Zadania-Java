/**
 * Interfejs zrodla danych.
 * 
 * @author oramus
 *
 */
public interface SourceInterface {
	/**
	 * Metoda zwraca liczbe typu calkowitego lub wyjatek. Znaczenie wyjatkow opisane
	 * jest ponizej.
	 * 
	 * @return liczba typu calkowitego
	 * 
	 * @throws EndOfDataException
	 *             wyjatek, ktorym metoda informuje o tym, ze wiecej danych juz nie
	 *             ma. Wykonanie getValue po wystapieniu tego wyjatku traktowane
	 *             jest tako blad.
	 * 
	 * @throws TemporaryNoDataException
	 *             chwilowo zrodlo nie posiada danych. Nalezy powiowic probe
	 *             odebrania danych poprzez ponowne wywolanie metody getValue
	 * @throws WaitException
	 *             zrodlo danych jest przeciazony praca. Przez wskazany w wyjatku
	 *             okres czasu nie nalezy wykonywac metody getValue
	 * @throws UrgentException
	 *             poprzednia dana, ktora zostala odebrana ze strumienia jest bardzo
	 *             wazna dla odbiorcy, nalezy ja natychmiast do niego przekazac.
	 */
	int getValue() throws EndOfDataException, TemporaryNoDataException, WaitException, UrgentException;
}
