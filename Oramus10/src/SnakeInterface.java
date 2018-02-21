import java.util.List;

public interface SnakeInterface {
	/**
	 * Ustala nowy kierunek ruchu weza.
	 * 
	 * @param d
	 *            Nowy kierunek ruchu
	 */
	public void setDirection(Direction d);

	/**
	 * Zwraca aktualny kierunek ruchu weza.
	 * 
	 * @return Aktualny kierunek ruchu weza
	 */
	public Direction getDirection();

	/**
	 * Zwraca wszystkie pozycje zajmowane przez weza.
	 * 
	 * @return Lista polozen segmentow weza od glowy po ogon
	 */
	public List<Position2D> getSnake();

	/**
	 * Zwraca aktualne polozenie glowy weza.
	 * 
	 * @return Aktualne polozenie glowy weza
	 */
	public Position2D getHeadPosition();
	
	/**
	 * Metoda pozwala sprawdzic, czy waz wciaz zyje.
	 * 
	 * @return true - waz wciaz zyje
	 */
	public boolean isAlive();
}
