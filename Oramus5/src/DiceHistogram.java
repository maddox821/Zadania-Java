/**
 * Interfejs umozliwiajacy wygenerowanie histogramu czestosci wystepowania
 * roznych liczb oczek (punktow).
 */
public interface DiceHistogram {
	/**
	 * Metoda umozliwia przekazanie obiektu-kostki. Przekazany obiekt bedzie od tej
	 * chwili uzywany do wygenerowania histogramow.
	 * 
	 * @param di
	 *            referencja do obiektu-kostki
	 */
	void setDice(DiceInterface di);

	/**
	 * Metoda wykonuje wielokrotne losowanie (rzuty) kostka. Rzuty wykonywane sa az
	 * do uzyskania co najmniej podanej w wywolaniu liczby zliczen dla kazdej
	 * badanej liczby oczek. Np. jesli parametr atLeastCounts otrzyma wartosc 10,
	 * program pracuje do chwili, w ktorej dla kazdej mozliwej do wyrzucena liczby
	 * oczek, bedzie co najmniej 10 zliczen. Metoda konczy prace w chwili, gdy
	 * wymagana liczba zliczen zostanie osiagnieta. Prawidlowy histogram dla
	 * atLeastCounts=10 to np. 11,12,15,10 bledne to 9,10,12,10 oraz 12,14,17,12 - w
	 * pierwszym jest za malo zliczen, w drugim metoda powinna zakonczyc prace
	 * wczesniej.
	 * 
	 * @param atLeastCounts
	 *            liczba zliczen, ktora musi zostac osiagnieta dla kazdej mozliwej
	 *            do wyrzucenia liczby oczek.
	 * @return Obiekt zawierajacy informacje ile razy dana liczba oczek wystapila w
	 *         trakcie generowania histogramu. Histogram nie moze zawierac pozycji
	 *         dla liczby oczek, ktorych rzucajac kostka nie mozna wyrzucic. Np.
	 *         jesli kostka pozwala na uzyskanie liczby oczek z dwoch przedzialow:
	 *         [1,3] i [6,9], to histogram ma zawierac informacje o tym ile razy
	 *         wyrzucono 1,2,3,6,7,8 i 9 czyli miec rozmiar (numberOfBins) rowny 7.
	 */
	Histogram getHistogram(int atLeastCounts);
}
