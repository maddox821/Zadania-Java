public class PMO_ProgrammableRandom extends Random {
    private int[] numbers;
    private int counter;

    PMO_ProgrammableRandom(int ... numbers) {
        this.numbers = numbers;
    }

    @Override
    int get(int max) {
        return numbers[(counter++) % numbers.length];
    }

    public int getCounter() {
        return counter;
    }
}