
public enum Direction {
	NORTH {
		public Position2D nextPosition2D(Position2D old) {
			return new Position2D(old.getX(), old.getY() + 1);
		}
	},
	SOUTH {
		public Position2D nextPosition2D(Position2D old) {
			return new Position2D(old.getX(), old.getY() - 1);
		}
	},
	EAST {
		public Position2D nextPosition2D(Position2D old) {
			return new Position2D(old.getX() + 1, old.getY());
		}
	},
	WEST {
		public Position2D nextPosition2D(Position2D old) {
			return new Position2D(old.getX() - 1, old.getY());
		}
	};

	/**
	 * Metoda zwraca nastepna pozycje w stosunku do pozycji old, gdy ruch wykonywany jest w okreslonym
	 * kierunku.
	 * 
	 * @param old stara pozycja
	 * @return nowa pozycja 
	 */
	abstract public Position2D nextPosition2D(Position2D old);
}
