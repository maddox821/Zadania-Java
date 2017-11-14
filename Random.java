
public class Random {
    private java.util.Random random = new java.util.Random();

    /**
     * Metoda zwraca losowa liczbe z zakresu od 0 do max wlacznie.
     * @param max ograniczenie gorne zwracenego wyniku
     * @return liczba losowa z zakresu od 0 do max wlacznie
     */
    int get( int max ) {
        return random.nextInt( max + 1 );
    }

    public static void main(String[] args) {
        Random rnd = new Random();

        System.out.println( rnd.get( 10 ));
        System.out.println( rnd.get( 10 ));
        System.out.println( rnd.get( 10 ));
        System.out.println( rnd.get( 10 ));
        System.out.println( rnd.get( 10 ));
    }
}