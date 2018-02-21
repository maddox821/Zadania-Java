
public abstract class ProgrammableDice extends Random {
    /**
     * Metoda ustawia liczbe scianek kostki.
     *
     * @param faces
     *            liczba scianek kostki.
     */
    public abstract void setNumberOfFaces(int faces);

    /**
     * Metoda wprowadza do kostki "program". Program sklada sie z sekwencji liczb,
     * ktora jesli pojawi sie w wyniku pracy metody get ma spowodowac wystapienie
     * zadanej sekwencji wynikowej po interspace losowaniach. Program ma ograniczona
     * liczbe wywolan. Po repetitions uruchomieniach program jest automatycznie
     * usuwany.
     *
     * @param initializationSequence
     *            sekwencja liczb, ktorej wystapienie aktywuje program
     * @param interspace
     *            liczba losowan przed wystapieniem pierwszej liczby z
     *            outputSequence
     * @param outputSequence
     *            sekwencja liczb, ktora pojawia sie jako wynik po aktywacji
     *            programu
     * @param repetitions
     *            limit uruchomien programu
     */
    public abstract void addProgram(int[] initializationSequence, int interspace, int[] outputSequence,
                                    int repetitions);

    /**
     * Metoda zwraca wynik losowania.
     *
     * @return Liczba w zakresie od 1 do faces.
     */
    public abstract int get();
}
