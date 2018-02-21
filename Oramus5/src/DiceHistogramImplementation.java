import java.util.*;

class DiceHistogramImplementation implements DiceHistogram {
    private Vector<Helper> objectVector = new Vector<>();
    private DiceInterface dice;
    private int getHistogramHelper(int biggestNumber) {
        int methodScore = 0, classCounter = 0;
        for(int i = 0; i < objectVector.size(); i++) {
            if (objectVector.get(i).randomNumbers >= biggestNumber)
                classCounter++;
            if(objectVector.size() == classCounter)
                methodScore++;}
        return methodScore;
    }
    public Histogram getHistogram(int atLeastCounts) {
        int histogramIdNumber = 0, randomNumber, i = 0;
        Helper helper = new Helper();
        while(i < dice.getNumberOfIntervals()){
            int start = dice.getInterval(i).lowerEndpoint();
            int end = dice.getInterval(i).upperEndpoint();
            i++;
                for(;start <= end;start++) {
                objectVector.add(new Helper(start,histogramIdNumber));
                histogramIdNumber++;}
        }
        do {
            randomNumber = dice.getNumberOfDots();
                for(int j = 0; j < objectVector.size(); j++){
                    if(randomNumber == objectVector.get(j).diceNumber){
                        objectVector.get(j).randomNumbers++;
                        break;}
            }
        } while(getHistogramHelper(atLeastCounts) == 0);
        helper.histogramObjects = objectVector;
        return helper;
    }
    public void setDice(DiceInterface di){
        this.dice = di;}
}
class Helper implements Histogram {
    Vector<Helper> histogramObjects;
    public int idNumber, diceNumber,randomNumbers;
    public Helper(int diceNumber, int idNumber) {
        this.diceNumber = diceNumber;
        this.idNumber = idNumber; }
    public Helper(){}
    public int numberOfBins(){
        return histogramObjects.size();}
    public int getNumberOfDots(int bin){
        return histogramObjects.get(bin).diceNumber;}
    public int numberOfCounts(int bin){
        return histogramObjects.get(bin).randomNumbers;}
}

