class FillBase {

    public static boolean isProtected(int[][] image, int[] colors, int newFirstIndex, int newSecondIndex) {
        boolean isAvailable = true;
        for(int i : colors){
            if(image[newFirstIndex][newSecondIndex] == i){
                isAvailable = false;break;}}
        return isAvailable;
    }

    public void fill( int[][] image, boolean [][] neighbours, int[] colors, int color, int firstIndex, int secondIndex ) {
        int newFirstIndex = 0, newSecondIndex = 0;
        image[firstIndex][secondIndex] = color;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(neighbours[i][j] == true){
                    switch(i){
                        case 0: newFirstIndex = firstIndex + (-1);break;
                        case 1: newFirstIndex = firstIndex;break;
                        case 2: newFirstIndex = firstIndex + 1;break;}
                    switch(j){
                        case 0: newSecondIndex = secondIndex + 1;break;
                        case 1: newSecondIndex = secondIndex;break;
                        case 2: newSecondIndex = secondIndex + (-1);break;}
                    if(newSecondIndex < image[i].length && newSecondIndex >= 0
                            && newFirstIndex < image.length && newFirstIndex >= 0
                            && image[newFirstIndex][newSecondIndex] != color
                            && isProtected(image, colors, newFirstIndex, newSecondIndex))
                        fill(image, neighbours, colors, color, newFirstIndex, newSecondIndex);
                }
            }
        }
    }
}