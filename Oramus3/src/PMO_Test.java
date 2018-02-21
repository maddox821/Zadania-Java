import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.junit.Rule;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PMO_Test {

    //////////////////////////////////////////////////////////////////////////
    private static final Map<String, Double> tariff = new HashMap<>();

    static {
        tariff.put("allFill", 2.0);
        tariff.put("allFill3", 2.0);
        tariff.put("fillH", 2.0);
        tariff.put("fillV", 2.0);
        tariff.put("fillVHoleProtected", 2.0);
        tariff.put("fillVHole", 1.6);
        tariff.put("fillManyColors", 1.6);
        tariff.put("fillPatternX", 2.0 );
        tariff.put("fillPatternXHole", 2.0 );
        tariff.put("fillAsymmetricPattern", 0.2 );
    }

    public static double getTariff(String testName) {
        return tariff.get(testName);
    }
    //////////////////////////////////////////////////////////////////////////

    @Rule
    public TestName name = new TestName();

    private FillBase fb;

    @Before
    public void create() {
        // PMO_SystemOutRedirect.startRedirectionToNull();
        fb = new FillBase();
        PMO_SystemOutRedirect.returnToStandardStream();
    }

    private void runFill(int[][] image, boolean[][] neighbours, int[] colors, int color, int firstIndex,
                         int secondIndex) {
        System.out.println("- - - - - - - - - - - - - - - - - - - - -");
        System.out.println("- - >  Kod testuje metoda: " + name.getMethodName());
        System.out.println("- - - - - - - - - - - - - - - - - - - - -");

        PMO_SystemOutRedirect.println("Before:");
        showNeighboursTable(neighbours);
        show(image, firstIndex, secondIndex);

        try {
            fb.fill(image, neighbours, colors, color, firstIndex, secondIndex);
        } catch ( StackOverflowError e ) {
            PMO_SystemOutRedirect.println("BLAD: W trakcie wykonania metody fill doszlo do wyjatku");
            PMO_SystemOutRedirect.println("BLAD: Zgloszono wyjatek " + e.toString());
            org.junit.Assert.fail("Pojawil sie StackOverflowError");
        } catch (Exception e) {
            PMO_SystemOutRedirect.println("BLAD: W trakcie wykonania metody fill doszlo do wyjatku");
            PMO_SystemOutRedirect.println("BLAD: Zgloszono wyjatek " + e.toString());
            org.junit.Assert.fail("Nie powinno dojsc do pojawienia sie wyjatku");
        }

        assertNotNull("Metoda fill nie moze zwracac null", image);
        PMO_SystemOutRedirect.println("After:");
        show(image, -1, -1);
    }

    private static void show(int[][] image, int firstIndex, int secondIndex) {

        String[] i2s = { " ", ".", "\u25A7", "x", "+", "#", "o", "@", "\u25A9", "\u25FC" };

        StringBuilder sb = new StringBuilder();
        for (int j = image[0].length-1; j >= 0; j--) {
            sb.append("|");
            for (int i = 0; i < image.length; i++) {
                if ((i == firstIndex) && (j == secondIndex)) {
                    sb.append("!");
                } else {
                    sb.append(i2s[image[i][j]]);
                }
            }
            sb.append("|\n");
        }

        System.out.println(sb.toString());
    }

    private static String boolConver(boolean b) {
        return b ? " T " : " F ";
    }

    private static void showNeighboursTable(boolean[][] n) {
        System.out
                .println("| " + boolConver(n[0][0]) + " | " + boolConver(n[1][0]) + " | " + boolConver(n[2][0]) + " |");
        System.out
                .println("| " + boolConver(n[0][1]) + " | " + boolConver(n[1][1]) + " | " + boolConver(n[2][1]) + " |");
        System.out
                .println("| " + boolConver(n[0][2]) + " | " + boolConver(n[1][2]) + " | " + boolConver(n[2][2]) + " |");
    }

    private void testRectangle(int col1, int col2, int row1, int row2, int color, int[][] image) {
        for (int row = row1; row <= row2; row++)
            for (int col = col1; col <= col2; col++) {
                assertEquals("Na pozycji [ " + col + " ][ " + row + " ] powinna byc inna wartosc", color,
                        image[col][row]);
            }
    }

    @Test(timeout = 500)
    public void allFill() {
        int[][] img = new int[10][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        runFill(img, ngbrs, new int[] { Integer.MAX_VALUE }, 1, 2, 2);

        testRectangle(0, 9, 0, 7, 1, img);
    }

    @Test(timeout = 500)
    public void allFill3() {
        int[][] img = new int[10][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        runFill(img, ngbrs, new int[] { Integer.MAX_VALUE }, 1, 2, 2);
        runFill(img, ngbrs, new int[] { Integer.MAX_VALUE }, 2, 2, 2);
        runFill(img, ngbrs, new int[] { Integer.MAX_VALUE }, 3, 2, 2);

        testRectangle(0, 9, 0, 7, 3, img);
    }


    @Test(timeout = 500)
    public void fillH() {
        int[][] img = new int[10][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        int protectedColor = 2;

        horizontalLine(img, 5, protectedColor);

        runFill(img, ngbrs, new int[] { protectedColor, Integer.MAX_VALUE }, 1, 2, 2);

        System.out.println("Testy");

        testRectangle(0, 9, 0, 4, 1, img);
        testRectangle(0, 9, 5, 5, protectedColor, img);
        testRectangle(0, 9, 6, 7, 0, img);
    }

    private void horizontalLine(int[][] img, int row, int protectedColor) {
        for (int i = 0; i < img.length; i++) {
            img[i][row] = protectedColor;
        }
    }

    private void verticalLine(int[][] img, int col, int protectedColor) {
        for (int i = 0; i < img[0].length; i++) {
            img[col][i] = protectedColor;
        }
    }

    @Test(timeout = 500)
    public void fillV() {
        int[][] img = new int[10][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        int protectedColor = 2;

        verticalLine(img, 5, protectedColor);

        runFill(img, ngbrs, new int[] { protectedColor, Integer.MAX_VALUE }, 1, 2, 2);
        testRectangle(0, 4, 0, 7, 1, img);
        testRectangle(6, 9, 0, 7, 0, img);
        testRectangle(5, 5, 0, 7, protectedColor, img);
    }


    @Test(timeout = 500)
    public void fillVHoleProtected() {
        int[][] img = new int[10][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        int protectedColor = 2;

        verticalLine(img, 5, protectedColor);
        img[ 5 ][ 5 ] = protectedColor + 1 ;

        runFill(img, ngbrs, new int[] { protectedColor, protectedColor + 1 }, 1, 2, 2);
        testRectangle(0, 4, 0, 7, 1, img);
        testRectangle(6, 9, 0, 7, 0, img);
        testRectangle(5, 5, 0, 4, protectedColor, img);
        testRectangle(5, 5, 5, 5, protectedColor+1, img);
        testRectangle(5, 5, 6, 7, protectedColor, img);
    }

    @Test(timeout = 500)
    public void fillVHole() {
        int[][] img = new int[10][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        int protectedColor = 3;

        verticalLine(img, 5, protectedColor);
        img[ 5 ][ 5 ] = 5;

        runFill(img, ngbrs, new int[] { protectedColor, protectedColor + 1 }, 2, 2, 2);
        testRectangle(0, 4, 0, 7, 2, img);
        testRectangle(6, 9, 0, 7, 2, img);
        testRectangle(5, 5, 0, 4, protectedColor, img);
        testRectangle(5, 5, 5, 5, 2, img);
        testRectangle(5, 5, 6, 7, protectedColor, img);
    }

    @Test(timeout = 500)
    public void fillManyColors() {
        int[][] img = new int[7][8];
        boolean[][] ngbrs = new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };

        int row = 4;
        int[] protectedColors = new int [ img.length ];

        for ( int i = 0; i < img.length; i++ ) {
            img[ i ][ row ] = i + 1;
            protectedColors[ i ] = i + 1;
        }

        runFill(img, ngbrs, protectedColors, img.length + 1, 3, 3);
        testRectangle(0, 6, 0, row-1, img.length + 1, img);
        testRectangle(0, 6, row+1, 7, 0, img);
    }

    @Test(timeout = 500)
    public void fillPatternX() {
        int[][] img = new int[7][7];
        boolean[][] ngbrs = new boolean[][] { { true, false, true }, { false, true, false }, { true, false, true } };

        int col = 4;
        int protectedColor = 2;
        int color = 8;
        verticalLine(img, col, protectedColor);

        runFill(img, ngbrs, new int[] { protectedColor, Integer.MAX_VALUE }, color, 2, 2);

        assertEquals( "Pozycja [0][0] powinna byc pomalowana", color, img[ 0 ][ 0 ]);
        assertEquals( "Pozycja [3][3] powinna byc pomalowana", color, img[ 3 ][ 3 ]);
        assertEquals( "Pozycja [0][6] powinna byc pomalowana", color, img[ 0 ][ 6 ]);

        assertEquals( "Pozycja [0][1] NIE powinna byc pomalowana", 0, img[ 0 ][ 1 ]);
        assertEquals( "Pozycja [0][1] NIE powinna byc pomalowana", 0, img[ 1 ][ 0 ]);
        assertEquals( "Pozycja [3][6] NIE powinna byc pomalowana", 0, img[ 3 ][ 6 ]);

        testRectangle(col+1, 6, 0, 6, 0, img);
    }

    @Test(timeout = 500)
    public void fillPatternXHole() {
        int[][] img = new int[7][7];
        boolean[][] ngbrs = new boolean[][] { { true, false, true }, { false, true, false }, { true, false, true } };

        int col = 4;
        int protectedColor = 2;
        int color = 8;
        verticalLine(img, col, protectedColor);
        img[ col ][ 2 ] = 4;

        runFill(img, ngbrs, new int[] { protectedColor, Integer.MAX_VALUE }, color, 2, 2);

        assertEquals( "Pozycja [0][0] powinna byc pomalowana", color, img[ 0 ][ 0 ]);
        assertEquals( "Pozycja [3][3] powinna byc pomalowana", color, img[ 3 ][ 3 ]);
        assertEquals( "Pozycja [0][6] powinna byc pomalowana", color, img[ 0 ][ 6 ]);
        assertEquals( "Pozycja [6][6] powinna byc pomalowana", color, img[ 6 ][ 6 ]);
        assertEquals( "Pozycja [6][0] powinna byc pomalowana", color, img[ 6 ][ 0 ]);

        assertEquals( "Pozycja [0][1] NIE powinna byc pomalowana", 0, img[ 0 ][ 1 ]);
        assertEquals( "Pozycja [0][1] NIE powinna byc pomalowana", 0, img[ 1 ][ 0 ]);
        assertEquals( "Pozycja [3][6] NIE powinna byc pomalowana", 0, img[ 3 ][ 6 ]);
        assertEquals( "Pozycja [6][1] NIE powinna byc pomalowana", 0, img[ 6 ][ 1 ]);
        assertEquals( "Pozycja [6][5] NIE powinna byc pomalowana", 0, img[ 6 ][ 5 ]);

    }

    @Test(timeout = 500)
    public void fillAsymmetricPattern() {
        int[][] img = new int[7][7];
        boolean[][] ngbrs = new boolean[3][3];
        ngbrs[0][0] = true; // ruch tylko w kierunku x-1, y+1

        int color = 7;

        runFill(img, ngbrs, new int[] { Integer.MAX_VALUE }, color, 2, 2);

        assertEquals("Pozycja [2][2] powinna byc pomalowana", color, img[2][2]);
        assertEquals("Pozycja [1][3] powinna byc pomalowana", color, img[1][3]);
        assertEquals("Pozycja [0][4] powinna byc pomalowana", color, img[0][4]);

        assertEquals("Pozycja [3][1] NIE powinna byc pomalowana", 0, img[3][1]);
        assertEquals("Pozycja [2][1] NIE powinna byc pomalowana", 0, img[2][1]);
        assertEquals("Pozycja [1][1] NIE powinna byc pomalowana", 0, img[1][1]);
        assertEquals("Pozycja [3][3] NIE powinna byc pomalowana", 0, img[3][3]);
    }
}