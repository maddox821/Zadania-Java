import java.util.Set;

public class PMO_Game implements GameInterface, PMO_AdminInterface {

    private int sizeX, sizeY;
    private int tickTime;
    private int foodDurability;
    private int foodMinDistanceFromSnakeHead;
    private int foodMaxDistanceFromSnakeHead;
    private int snakeInitialSize;
    private int snakeSizeReductionAfterSteps;
    private int snakeSizeReductionProtectionSteps;

    private PMO_Food f;
    private PMO_Snake s;
    private PMO_Board board;

    public PMO_Game(PMO_Board board, int tickTime,
                    int foodDurability, int foodMinDistanceFromSnakeHead, int foodMaxDistanceFromSnakeHead,
                    int snakeInitialSize, int snakeSizeReductionAfterSteps, int snakeSizeReductionProtectionSteps) {
        this.board = board;
        this.sizeX = board.getSizeX();
        this.sizeY = board.getSizeY();
        this.tickTime = tickTime;
        this.foodDurability = foodDurability;
        this.foodMinDistanceFromSnakeHead = foodMinDistanceFromSnakeHead;
        this.foodMaxDistanceFromSnakeHead = foodMaxDistanceFromSnakeHead;
        this.snakeInitialSize = snakeInitialSize;
        this.snakeSizeReductionAfterSteps = snakeSizeReductionAfterSteps;
        this.snakeSizeReductionProtectionSteps = snakeSizeReductionProtectionSteps;
    }

    @Override
    public void start() {
        f = new PMO_Food(foodDurability, tickTime,
                foodMinDistanceFromSnakeHead, foodMaxDistanceFromSnakeHead, board );
        s = new PMO_Snake(snakeInitialSize,
                PMO_PositionHelper.shift(sizeX/2, sizeY/2, board.getMinPosition()),
                Direction.NORTH, f, board, snakeSizeReductionAfterSteps,
                snakeSizeReductionProtectionSteps);
        f.setSnake(s);
        f.start();
        s.start(1, tickTime);
    }

    @Override
    public void stop() {
        s.stopSnake();
        f.stop();
    }

    @Override
    public void setFoodDurability(int ticks) {
        foodDurability = ticks;
        f.setDurability(foodDurability);
    }

    @Override
    public Set<Position2D> getProhibitedPositions() {
        return board.getAllProhibitedPositions();
    }

    @Override
    public Position2D getFoodPosition() {
        return f.getFoodPosition();
    }

    @Override
    public int getScore() {
        return s.getScore();
    }

    @Override
    public long getTickTime() {
        return tickTime;
    }

    @Override
    public int getFoodDurability() {
        return foodDurability;
    }

    @Override
    public SnakeInterface getSnake() {
        return s;
    }

    boolean isAlive() {
        return s.isAlive();
    }

}
