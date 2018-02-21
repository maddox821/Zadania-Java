import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class PMO_Snake implements SnakeInterface {
    private Deque<Position2D> snake;
    final private PMO_Food f;
    private AtomicBoolean snakeAlive;
    private AtomicInteger score = new AtomicInteger(0);
    private int sizeReductionAfterSteps;
    private int sizeReductionProtectionSteps;
    private int sizeStep;
    private Timer snakeTimer;
    private AtomicReference<Position2D> snakeHead = new AtomicReference<>();
    private AtomicReference<List<Position2D>> snakeBody = new AtomicReference<>();
    private AtomicReference<Direction> snakeDirection = new AtomicReference<>();
    private PMO_Board board;
    private final Object lock = new Object();

    // idea zycia weza
    // co N krokow jego rozmiar maleje,
    // chyba, ze cos zje, wtedy jego rozmiar rosnie o 1 i
    // nie zmaleje przez M krokow

    public PMO_Snake(int initialSize, Position2D initialPosition, Direction initialDirection,
                     PMO_Food f, PMO_Board board,
                     int sizeReductionAfterSteps, int sizeReductionProtectionSteps) {
        snake = new LinkedList<>();
        this.f = f;
        this.board = board;
        setDirection(initialDirection);
        snake.addFirst(initialPosition);
        for (int i = 1; i < initialSize; i++) // waz rosnie do poczatkowego rozmiaru
            snake.addFirst(snakeDirection.get().nextPosition2D(snake.getFirst()));
        setHeadAndBody();

        snakeAlive = new AtomicBoolean(true);

        this.sizeReductionAfterSteps = sizeReductionAfterSteps;
        this.sizeReductionProtectionSteps = sizeReductionProtectionSteps;
        sizeStep = sizeReductionProtectionSteps;
    }

    private void setHeadAndBody() {
        snakeHead.set(snake.getFirst());
        snakeBody.set(new ArrayList<>(snake));
    }

     private void oneMove() {
        boolean moveTail = true;

        synchronized ( lock ) {

            Position2D newHead = snakeDirection.get().nextPosition2D(getHeadPosition());

            // if nowa pozycja pokrywa sie z istniejaca w wezu - smierc
            if (snake.contains(newHead)) {
                PMO_SystemOutRedirect.println( "BLAD: waz ugryzl sam siebie!");
                snakeAlive.set(false);
                return;
            }

            if ( board.getAllProhibitedPositions().contains( newHead )) {
                PMO_SystemOutRedirect.println( "BLAD: waz trafil w przeszkode/sciane");
                snakeAlive.set(false);
                return;
            }

            // obsluga jedzenia
            synchronized (f) { // synchronizacja na obiekcie - jedzenia
                Position2D foodPosition = f.getFoodPosition();
                if ( newHead.equals( foodPosition ) ) {
                    f.foodHasBeenEaten();
                    moveTail = false;
                    sizeStep += sizeReductionProtectionSteps;
                    score.addAndGet( snake.size() );
                }
            }

            sizeStep--;

            if (sizeStep == 0) {
                if (snake.size() == 1) {
                    PMO_SystemOutRedirect.println( "BLAD: waz umarl z glodu");
                    snakeAlive.set(false); // waz padl z glodu
                    return;
                } else {
                    snake.removeLast(); // waz maleje, bo nie je
                    sizeStep = sizeReductionAfterSteps;
                }
            }

            snake.addFirst(newHead);  // nowa pozycja glowy
            if (moveTail) snake.removeLast(); // waz przesuwa ogon do przodu, chyba, ze cos zjadl

            setHeadAndBody();

            score.addAndGet( snake.size());
        } // synchronizacja

    }

    public int getScore() {
        return score.get();
    }

    public void start(int delay, int tickTime) {
        snakeTimer = new Timer();
        long deadlineShift = 2 * tickTime;
        AtomicLong nextCallDeadline = new AtomicLong(System.currentTimeMillis() + 10 * delay);
        TimerTask tt = new TimerTask() {
            public void run() {
                long startAt = System.currentTimeMillis();
                if (startAt > nextCallDeadline.get()) {
                    PMO_SystemOutRedirect.println("BLAD: wywolanie ruchu weza z opoznieniem.");
                    snakeAlive.set(false);
                }

                if (snakeAlive.get()) oneMove();
                else snakeTimer.cancel();
                nextCallDeadline.set(System.currentTimeMillis() + deadlineShift);
            }
        };

        snakeTimer.scheduleAtFixedRate(tt, delay, tickTime);
    }

    public boolean isAlive() {
        return snakeAlive.get();
    }

    public void stopSnake() {
        snakeAlive.set(false);
    }

    @Override
    public void setDirection(Direction d) {
        snakeDirection.set(d);
    }

    @Override
    public Direction getDirection() {
        return snakeDirection.get();
    }

    @Override
    public List<Position2D> getSnake() {
        return snakeBody.get();
    }

    @Override
    public Position2D getHeadPosition() {
        return snakeHead.get();
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        Position2D pos;
        for (int i = board.getSizeY()+1; i >= 0; i--) {
            for (int j = 0; j <= board.getSizeX()+1; j++) {
                pos = PMO_PositionHelper.shift(j,i,board.getMinPosition());
                if (board.getAllProhibitedPositions().contains(pos)) {
                    tmp.append('#');
                    continue;
                }
                if (snake.contains(pos)) {
                    if (pos.equals(snake.getFirst())) {
                        switch (snakeDirection.get()) {
                            case NORTH: {
                                tmp.append("^");
                                break;
                            }
                            case SOUTH: {
                                tmp.append("v");
                                break;
                            }
                            case EAST: {
                                tmp.append(">");
                                break;
                            }
                            case WEST: {
                                tmp.append("<");
                            }
                        }
                    } else {
                        tmp.append("o");
                    }
                    continue;
                }

                if ((f.getFoodPosition() != null) && (pos.equals(f.getFoodPosition()))) {
                    tmp.append("F");
                    continue;
                }

                tmp.append('.');
            }
            tmp.append("\n");
        }
        return tmp.toString();
    }

}
