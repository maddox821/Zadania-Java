
public interface PMO_AdminInterface {
	/**
	 * Czas przetrzymania pozywienia na planszy w ruchach weza.
	 * @param ticks
	 */
	void setFoodDurability( int ticks );
	
	/**
	 * Rozpoczyna gre
	 */
	void start();
	
	/**
	 * Konczy gre
	 */
	void stop();
}
