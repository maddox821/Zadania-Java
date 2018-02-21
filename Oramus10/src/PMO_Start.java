import java.util.TreeSet;

public class PMO_Start {
    private static int games;
    private static int score;

    private static void oneGame(PMO_Board board, int tick_time, int food_durability,
                                int food_min_distance_from_snake_head, int food_max_distance_from_snake_head,
                                int snake_initial_size, int snake_size_reduction_after, int snake_size_protection,
                                int game_time, int show_period) {

        final PMO_Game g = new PMO_Game( board, tick_time, food_durability,
                food_min_distance_from_snake_head, food_max_distance_from_snake_head,
                snake_initial_size, snake_size_reduction_after, snake_size_protection);
        g.start();

        final ControllerInterface ci = (ControllerInterface) PMO_GeneralPurposeFabric.fabric("Controller", "ControllerInterface");

        Thread ct = new Thread(new Runnable() {
            public void run() {
                ci.setGame(g);
                ci.start();
            }
        });
        ct.setDaemon(true);
        ct.start();

        int counter = game_time / show_period + 1;
        do {
            System.out.println(g.getSnake());
            System.out.println("Score " + g.getScore());
            try {
                Thread.sleep(show_period);
            } catch (Exception e) {
            }
            counter--;
            if (counter == 0) {
                g.stop();
            }
        } while (g.isAlive());

        games++;
        score += g.getScore();

        if (counter <= 0) System.out.println("Waz przetrwal gre !!!!!!");
        else System.out.println("- Requiescat in pace - ");
        System.out.println("Score        : " + g.getScore());
        System.out.println("Total score  : " + score);
        System.out.println("Mean score   : " + (score / games));
    }

    public static void main(String[] args) {

        final int GAME_TIME = 50000;
        final int SHOW_PERIOD = 1000;

        PMO_Board board = new PMO_Board(new Position2D(2,2), 25, 20, new TreeSet<>());

        oneGame(board, // size
                1000, // tick time ms
                25,  // food durability,
                2,   // food_min_distance_from_snake_head,
                5,  // food_max_distance_from_snake_head,
                4,   // snake_initial_size,
                12,  // snake_size_reduction_after,
                25,  // snake_size_protection,
                5 * GAME_TIME, // game_time in msec,
                SHOW_PERIOD);// show_period

        oneGame(board, // size
                750, // tick time ms
                20,  // food durability,
                2,   // food_min_distance_from_snake_head,
                6,  // food_max_distance_from_snake_head,
                2,   // snake_initial_size,
                12,  // snake_size_reduction_after,
                25,  // snake_size_protection,
                2 * GAME_TIME, // game_time in msec,
                SHOW_PERIOD);// show_period

        oneGame(board, // size
                500, // tick time ms
                15,  // food durability,
                3,   // food_min_distance_from_snake_head,
                7,  // food_max_distance_from_snake_head,
                3,   // snake_initial_size,
                12,  // snake_size_reduction_after,
                25,  // snake_size_protection,
                GAME_TIME, // game_time in msec,
                SHOW_PERIOD);// show_period

        oneGame(board, // size
                250, // tick time ms
                12,  // food durability,
                3,   // food_min_distance_from_snake_head,
                8,  // food_max_distance_from_snake_head,
                3,   // snake_initial_size,
                12,  // snake_size_reduction_after,
                25,  // snake_size_protection,
                GAME_TIME, // game_time in msec,
                SHOW_PERIOD);// show_period

        oneGame(board, // size
                125, // tick time ms
                12,  // food durability,
                3,   // food_min_distance_from_snake_head,
                8,  // food_max_distance_from_snake_head,
                3,   // snake_initial_size,
                12,  // snake_size_reduction_after,
                25,  // snake_size_protection,
                GAME_TIME, // game_time in msec,
                SHOW_PERIOD);// show_period

        oneGame(board, // size
                75, // tick time ms
                12,  // food durability,
                3,   // food_min_distance_from_snake_head,
                8,  // food_max_distance_from_snake_head,
                4,   // snake_initial_size,
                12,  // snake_size_reduction_after,
                25,  // snake_size_protection,
                GAME_TIME, // game_time in msec,
                SHOW_PERIOD);// show_period

    }

}
