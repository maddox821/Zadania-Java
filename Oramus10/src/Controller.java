import java.util.Arrays;
import java.util.List;
import java.util.Set;

class Controller implements ControllerInterface {

    private GameInterface gi;
    private SnakeInterface snake;
    private Set<Position2D> prohibitedPositions;
    private Position2D foodPosition;
    private List<Position2D> snakePositions;

    public void setGame(GameInterface gi) {
        this.gi = gi;
    }

    public void start() {

        snake = gi.getSnake();

        while (snake.isAlive()) {

            prohibitedPositions = gi.getProhibitedPositions();
            foodPosition = gi.getFoodPosition();
            snake = gi.getSnake();
            snakePositions = snake.getSnake();

            snake.setDirection(shortestDirection());

        }
    }

    private Direction shortestDirection() {
        // wzor obliczajacy odleglosc : sqrt( (x2-x1)^2 + (y2-y1)^2 )

        double north, south, east, west;
        Position2D headSnake = snake.getHeadPosition();
        Position2D northPosition = new Position2D(headSnake.getX(), headSnake.getY() + 1);
        Position2D southPosition = new Position2D(headSnake.getX(), headSnake.getY() - 1);
        Position2D eastPosition = new Position2D(headSnake.getX() + 1, headSnake.getY());
        Position2D westPosition = new Position2D(headSnake.getX() - 1, headSnake.getY());

        int x1 = foodPosition.getX();
        int y1 = foodPosition.getY();

        // sprawdzam czy polozenie north jest dostepne
        if (!prohibitedPositions.contains(northPosition) && !snakePositions.contains(northPosition)) {

            int x2 = headSnake.getX();
            int y2 = headSnake.getY() + 1;

            north = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        } else {
            north = 2000;
        }

        // sprawdzam czy polozenie south jest dostepne
        if (!prohibitedPositions.contains(southPosition) && !snakePositions.contains(southPosition)) {

            int x2 = headSnake.getX();
            int y2 = headSnake.getY() - 1;

            south = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        } else {
            south = 2000;
        }

        // sprawdzam czy polozenie east jest dostepne
        if (!prohibitedPositions.contains(eastPosition) && !snakePositions.contains(eastPosition)) {

            int x2 = headSnake.getX() + 1;
            int y2 = headSnake.getY();

            east = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        } else {
            east = 2000;
        }

        // sprawdzam czy polozenie west jest dostepne
        if (!prohibitedPositions.contains(westPosition) && !snakePositions.contains(westPosition)) {

            int x2 = headSnake.getX() - 1;
            int y2 = headSnake.getY();

            west = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        } else {
            west = 2000;
        }

        double[] tab = {north, south, east, west};
        Arrays.sort(tab);

        if (tab[0] == north) {
            return Direction.NORTH;
        } else if (tab[0] == south) {
            return Direction.SOUTH;
        } else if (tab[0] == east) {
            return Direction.EAST;
        } else if (tab[0] == west) {
            return Direction.WEST;
        }

        // nigdy tu nie dojdzie, ale musi byc dla kompilatora.
        return null;
    }
}