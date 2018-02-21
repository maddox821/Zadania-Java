
public class Position2D {
	private int x;
	private int y;

	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Zwraca wspolrzedna X punktu
	 * 
	 * @return - wspolrzedna x punktu
	 */
	public int getX() {
		return x;
	}

	/**
	 * Zwraca wspolrzedna Y punktu
	 * 
	 * @return - wspolrzedna y punktu
	 */
	public int getY() {
		return y;
	}

	/**
	 * Klonuje punkt.
	 * 
	 * @return - zwraca obiekt o identycznych wspolrzednych jak ten uzyty do
	 *         klonowania
	 */
	@Override
	public Position2D clone() {
		return new Position2D(this.getX(), this.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Porownuje dwa obiekty typu punkt. Sa rowne jesli wspolrzedne x i y sie
	 * pokrywaja
	 * 
	 * @return - prawda jesli dostarczony obiekt jest punktem i wspolrzedne obu
	 *         porownywanych punktow sa identyczne
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position2D other = (Position2D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[ " + x + ", " + y + " ] ";
	}
}
