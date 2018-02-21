public class Random {
    private static int[] numbers;
    private static int counter;

    public static void setNumbers( int ... numbers ) {
        Random.numbers = numbers;
        counter = 0;
    }

    int get(int max) {
        return numbers[(counter++) % numbers.length];
    }

    public int getCounter() {
        return counter;
    }
}
