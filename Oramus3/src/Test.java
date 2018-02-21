public class Test {
    public static void main(String[] args){
        int[][] a = new int[7][8];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a[i].length; j++){
                a[i][j] = (int)(Math.random() * 11 + 10);
            }
        }
        for (int[] x : a)
        {
            for (int y : x)
                System.out.print(y + " ");
            System.out.println();
        }
        System.out.println(a[0][2]);
    }
}
