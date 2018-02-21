import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class PMO_Food {
    protected Position2D position; // polozenie jedzenia
    protected SnakeInterface snake; // stad zdobedziemy informacje o pozycjach, jakie zajmuje waz
    protected int durability; // czas przetrzymania - w tick time
    protected int tickTime; // czas trwania jednego ruchu weza
    protected int maxDistanceFromSnakeHead; // maksymalna odleglosc jedzenia od glowy weza
    protected int minDistanceFromSnakeHead; // minimalna odleglosc jedzenia od glowy weza
    protected boolean stop;
    private Thread t; // watek generujacy jedzenie
    private Random rnd = ThreadLocalRandom.current();
    private int sizeX;
    private int sizeY;
    private Position2D minPosition; // polozenie o najmniejszych indeksach
    private Set<Position2D> prohibitetPositions; // polozenia zabronione
    private PMO_Board board;

    private int randomSing() {
        return rnd.nextBoolean() ? -1 : +1;
    }

    private void generateNewPosition() {
        assert prohibitetPositions != null;

        Position2D newPos;
        int i, j;
        int d;
        List<Position2D> snakePos = snake.getSnake();
        boolean wrong;
        int maxDistance = 1 + maxDistanceFromSnakeHead;

        do {
            do {
                do {
                    i = randomSing() * rnd.nextInt(maxDistance);
                    j = randomSing() * rnd.nextInt(maxDistance);

                    d = Math.abs(i) + Math.abs(j);
                } while ((d > maxDistanceFromSnakeHead) || (d < minDistanceFromSnakeHead));
                newPos = PMO_PositionHelper.shift(i, j, snake.getHeadPosition() );
            }
            while (! ( PMO_PositionHelper.notBigger(board.getMaxPosition(), newPos) && PMO_PositionHelper.notSmaller(board.getMinPosition(), newPos) ) );

            wrong = snakePos.contains(newPos) || prohibitetPositions.contains(newPos);

        } while (wrong);

        synchronized ( this ) {
            position = newPos;
        }
    }

    public PMO_Food(int durability, int tickTime, int minDistanceFormSnakeHead, int maxDistanceFormSnakeHead, PMO_Board board) {
        this.durability = durability;
        this.tickTime = tickTime;
        this.maxDistanceFromSnakeHead = maxDistanceFormSnakeHead;
        this.minDistanceFromSnakeHead = minDistanceFormSnakeHead;
        this.board = board;
        this.sizeX = board.getSizeX();
        this.sizeY = board.getSizeY();
        prohibitetPositions = board.getAllProhibitedPositions();
    }

    synchronized public void setDurability(int d) {
        durability = d;
    }

    public void setSnake(SnakeInterface si) {
        snake = si;
        generateNewPosition();
    }

    public void start() {
        if (t != null) {
            stop = true;
            do {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            } while (t.isAlive());  // oczekiwanie na zakonczenie pracy poprzedniego watku
        }

        stop = false;

        t = new Thread(new Runnable() {
            public void run() {
                do {
                    int d;
                    synchronized (this) {
                        generateNewPosition();
                        d = durability;  // pozwala na synchronizacje uzycia durability
                    }
                    try {
                        Thread.sleep(d * tickTime);
                    } catch (InterruptedException ie) {
                    }
                } while (!stop);
            } // run()
        });
        t.setDaemon(true);
        t.start();
    }

    synchronized public void stop() {
        stop = true;
    }

    synchronized public Position2D getFoodPosition() {
        return position.clone();
    }

    synchronized public boolean isRunning() {
        if (t == null) return false;
        else return t.isAlive();
    }

    synchronized public void foodHasBeenEaten() {
        if (t != null)
            if (t.isAlive()) t.interrupt();
    }
}
