import java.util.ArrayList;
import java.util.Iterator;

class Dice extends ProgrammableDice {
    public int faces;
    public ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
    public ArrayList<DiceHelper> programsList = new ArrayList<DiceHelper>();
    public Iterator<Integer> it = randomNumbers.iterator();
        class DiceHelper {
            public int[] initializationSequence;
            public int interspace;
            public int[] outputSequence;
            public int repetitions;

            public DiceHelper(int[] initializationSequence, int interspace, int[] outputSequence, int repetitions){
                this.initializationSequence = initializationSequence;
                this.interspace = interspace;
                this.outputSequence = outputSequence;
                this.repetitions = repetitions;
            }
        }

    @Override
    public void setNumberOfFaces(int faces) {
        this.faces = faces;
    }

    @Override
    public void addProgram(int[] initializationSequence, int interspace, int[] outputSequence, int repetitions) {
        programsList.add(new DiceHelper(initializationSequence, interspace, outputSequence,repetitions));
    }

    @Override
    public int get() {
        int randomNumber = get(faces);
        randomNumbers.add(randomNumber);
        System.out.println(randomNumbers);
        return randomNumber;
    }
}