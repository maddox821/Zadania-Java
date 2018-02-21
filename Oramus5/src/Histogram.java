/**
 * Interfejs umozliwiajacy uzycie histogramu - zawiera metody pozwalajace na
 * pobranie informacji o liczbie przedzialow klasowych, ich liczebnosci i
 * liczbie oczek kostki jaka odpowiada danemu przedzialowi klasowemu.
 *
 */
public interface Histogram {
	/**
	 * Liczba przedzialow klasowych badanej cechy.
	 * 
	 * @return liczba przedzialow klasowych
	 */
	int numberOfBins();

	/**
	 * Liczba oczek odpowiadajaca danemu przedzialowi klasowemu.
	 * 
	 * @param bin
	 *            numer przedzialu klasowego od 0 do liczba przedzialow - 1.
	 * @return liczba oczek dla danego przedzialu klasowego
	 */
	int getNumberOfDots(int bin);

	/**
	 * Informacja ile razy wystapila liczba oczek przypisana dla danego przedzialu
	 * klasowego.
	 * 
	 * @param bin
	 *            numer przedzialu klasowego od 0 do liczba przedzialow - 1
	 * @return ilosc zliczen w danym przedziale klasowym
	 */
	int numberOfCounts(int bin);
}
