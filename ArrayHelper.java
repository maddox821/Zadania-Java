import java.util.*;

class ArrayHelper {

    public int[] combination(int[] collection, Random rnd, int resultSize) {
        Set<Integer> set = new TreeSet<Integer>();
        int[] result = new int[ resultSize ];
        int randomNumber = 0, randomRange = collection.length-1;
        if(collection.length == 0){
            System.out.println("Brak tablicy wejściowej!");
            return null;
        }
        for(int i = 0; i < result.length; i++) {
             while(set.contains(randomNumber))
                     randomNumber = rnd.get(randomRange);
             set.add(randomNumber);
             result[i] = collection[randomNumber];
        }
        return result;
    }

    /* public int[] interweaving(int[]... collections)
  Metoda zwraca tablice zawierajaca te same elementy,
  ktore przekazane zostaly za pomoca collections. Elementy sa ulozone
   w nastepujacej kolejnosci (zalozenie - przekazano 3 tablice, w kazdej po 3 elementy):
  indeks w tablicy wynikowej | indeks tablicy | indeks elementu tablicy

            0                            0                0
            1                            1                0  pierwsza
            2                            2                0
            3                            0                1
            4                            1                1  druga
            5                            2                1
            6                            0                2
            7                            1                2  trzecia
            8                            2                2

  Parameters:
  collections - przkazane do metody tablice. Wszystkie tablice maja ten sam rozmiar. Zadna z nich nie moze byc null.
  Returns:
  jednowymiarowa tablica zawierajaca odpowiednio ulozone elementy pobrane z przekazanych tablic.

 */

    public int[] interweaving(int[] ... collections) {
        int numberOfArrays = 0, k = 0, lengthOfArray = 0;
        for(int[] array : collections){
            lengthOfArray += array.length;
            numberOfArrays++;}
        if(numberOfArrays == 0){
            System.out.println("Brak tablic wejściowych!");
            return null;
        }
        int[] result = new int[lengthOfArray];
        for(int i = 0; i < numberOfArrays; i++) {
            for(int j = 0; j < numberOfArrays; j++) {
                result[k] = collections[j][i];
                k++;
            }
        }
        return result;
    }

    /* public int[] interweaving2(int[]... collections)
    Metoda zwraca tablice zawierajaca te same elementy, ktore przekazane zostaly
    za pomoca collections. Elementy sa ulozone w nastepujacej kolejnosci
    (zalozenie - przekazano 3 tablice, w tablicy 0 i 2 sa 3 elementy,
    w tablicy o indeksie 1 jest jeden element):
indeks w tablicy wynikowej | indeks tablicy | indeks elementu tablicy
        0                            0                0
        1                            1                0
        2                            2                0
        3                            0                1
        4                            2                1
        5                            0                2
        6                            2                2

Parameters:
collections - przkazane do metody tablice. Nie ma gwarancji,ze wszystkie tablice maja ten sam rozmiar. Zadna z nich nie moze byc null.
Returns:
jednowymiarowa tablica zawierajaca odpowiednio ulozone elementy pobrane z przekazanych tablic.

*/
    public int[] interweaving2(int[] ... collections) {
        int numberOfArrays = 0, position = 0, lengthOfArray = 0;
        int i = 0, j = 0;
        for(int[] array : collections){
            lengthOfArray += array.length;
            numberOfArrays++;}
        int[] result = new int[lengthOfArray];
        if(numberOfArrays == 0){
            System.out.println("Brak tablic wejściowych!");
            return null;}
        do {
            if(j == 3){
                j = 0;
                i++;}
            if(i == 3){
                i = 0;
                j++;}
            if(collections[j].length <= i){
                j++;
                continue;}
            else{
                result[position] = collections[j][i];
                j++;}
            position++;
        } while(position < lengthOfArray);
        return result;
    }
    public static void main(String[] args){
        ArrayHelper ah = new ArrayHelper();
        Random random = new Random();
        ah.interweaving2(new int[][] { { 0, 0, 0 }, { 1 }, { 2, 3, 4 } });
    }
}
