import java.util.Set;

public interface GameInterface {
	/**
	 * Plansza do gry. Metoda zwraca zbior pozycji, ktore beda stanowic sciany oraz inne elementy, w ktore
	 * nie moze uderzyc waz. Obszar gry na pewno bedzie obszarem zamknietym. Wewnatrz tego obszaru
	 * moga sie znajdowac dodatkowe zabronione pozycje.
	 * 
	 * @return - zbior zabronionych polozen, tj. pozycji, w ktore nie moze uderzyc waz.
	 */
	Set<Position2D> getProhibitedPositions();

	/**
	 * Zwraca polozenie jedzenia. Na planszy jest tylko jedno jedzenie jednoczesnie.
	 * 
	 * @return - polozenie jedzenia.
	 */
	Position2D getFoodPosition();

	/**
	 * Zwraca aktualny wynik punktowy gry
	 * 
	 * @return - wynik punktowy gry
	 */
	int getScore();

	/**
	 * Zwraca czas trwania jednego ruchu weza w msec. Dzieki tej metodzie wiadomo
	 * ile czasu ma program na decyzje o kolejnym ruchu weza.
	 * 
	 * @return - czas trwania jednego ruchu weza w msec.
	 */
	long getTickTime();

	/**
	 * Zwraca liczbe ruchow weza przed przeterminowaniem sie pozywienia. Po uplywie
	 * tego czasu pozywienie znika i pojawia sie nowe.
	 * 
	 * @return - liczba ruchow weza przed przeterminowaniem sie pozywienia
	 */
	int getFoodDurability();

	/**
	 * Zwraca referencje do obiektu typu SnakeI. Dzieki tej metodzie mozna rozpoczac
	 * sterowanie wezem.
	 * 
	 * @return - referencja do obiektu typu SnakeI.
	 */
	SnakeInterface getSnake();
}
