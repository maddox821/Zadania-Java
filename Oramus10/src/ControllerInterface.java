
public interface ControllerInterface {
	/**
	 * Przekazuje referencje do obiektu gry
	 * @param gi referencja do obiektu gru
	 */
	public void setGame( GameInterface gi );
	
	/** 
	 * Przekazanie kontroli nad wezem do kontrolera.
	 */
	public void start();
}
