import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.junit.Rule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PMO_Test {

	//////////////////////////////////////////////////////////////////////////
	private static final Map<String, Double> tariff = new HashMap<>();

	static {
		tariff.put("setPositionTest", 2.0);
		tariff.put("upTest", 2.0);
		tariff.put("downTest", 2.0);
		tariff.put("leftTest", 2.0);
		tariff.put("rightTest", 2.0);
		tariff.put("repeatTest", 1.0);
		tariff.put("undoTest1", 0.5);
		tariff.put("undoTest2", 0.5);
		tariff.put("undoTest3", 0.2);
		tariff.put("redoTest", 0.2);
	}

	public static double getTariff(String testName) {
		return tariff.get(testName);
	}
	//////////////////////////////////////////////////////////////////////////

	private ImageGeneratorConfigurationInterface configuration;
	private ImageGeneratorInterface generator;

	private boolean[][] canvas;
	private List<String> expected = new ArrayList<>();

	private boolean[][] list2table(List<String> data) {
		int size = data.size();
		boolean[][] canvas = new boolean[size][size];

		for (int row = 0; row < size; row++) {
			String oneRow = data.get(size - row - 1);
			for (int col = 0; col < size; col++)
				if (oneRow.charAt(col) == '#') {
					canvas[col][row] = true;
				} else {
					canvas[col][row] = false;
				}
		}
		return canvas;
	}

	private void showCanvas(boolean[][] canvas) {
		System.out.println(table2String(canvas));
	}

	private boolean[][] createEmptyCanvas(int size) {
		return new boolean[size][size];
	}

	private String table2String(boolean[][] canvas) {
		StringBuilder sb = new StringBuilder();
		int size = canvas.length;

		for (int row = size - 1; row >= 0; row--) {
			for (int col = 0; col < size; col++)
				sb.append(canvas[col][row] ? " # " : " . ");
			sb.append("\n");
		}
		return sb.toString();
	}

	@Rule
	public TestName name = new TestName();

	private void setCanvas() {
		PMO_TestHelper.tryExecute(() -> {
			configuration.setCanvas(canvas);
		}, "setCanvas");
	}

	private void setInitialPosition(int col, int row) {
		PMO_TestHelper.tryExecute(() -> {
			configuration.setInitialPosition(col, row);
		}, "setInitialPosition");
	}

	private void up(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.up(steps);
		}, "up");
	}

	private void down(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.down(steps);
		}, "down");
	}

	private void left(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.left(steps);
		}, "left");
	}

	private void right(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.right(steps);
		}, "right");
	}

	private void repeat(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.repeat(steps);
		}, "repeat");
	}

	private void undo(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.undo(steps);
		}, "undo");
	}

	private void redo(int steps) {
		PMO_TestHelper.tryExecute(() -> {
			generator.redo(steps);
		}, "redo");
	}

	private void setMaxUndoRedoRepeatCommands(int commands) {
		PMO_TestHelper.tryExecute(() -> {
			configuration.maxUndoRedoRepeatCommands(commands);
		}, "maxUndoRedoRepeatCommands");
	}

	private void test(String txt) {
		boolean[][] expectedCanvas = list2table(expected);
		int size = expectedCanvas.length;

		System.out.println("\n--------------------------------\nWynik testu " + name.getMethodName() + ":");
		showCanvas(canvas);
		System.out.println("Oczekiwano:");
		showCanvas(list2table(expected));

		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				assertEquals(txt + "; blad na pozycji canvas[" + col + "][" + row + "]", expectedCanvas[col][row],
						canvas[col][row]);
	}

	@Before
	public void create() {
		ImageGenerator ig = PMO_TestHelper.tryExecute(ImageGenerator::new, "Konstruktor ImageGenerator");
		configuration = ig;
		generator = ig;
		setMaxUndoRedoRepeatCommands(25);
	}

	@Test(timeout = 500)
	public void setPositionTest() {
		canvas = createEmptyCanvas(4);
		setCanvas();
		setInitialPosition(1, 3);
		expected.add(".#..");
		expected.add("....");
		expected.add("....");
		expected.add("....");
		test("Test metody setPosition");
	}

	@Test(timeout = 500)
	public void downTest() {
		canvas = createEmptyCanvas(4);
		setCanvas();
		setInitialPosition(1, 3);
		expected.add(".#..");
		expected.add(".#..");
		expected.add(".#..");
		expected.add(".#..");
		down(3);
		test("Test metody down(3)");
	}

	@Test(timeout = 500)
	public void upTest() {
		canvas = createEmptyCanvas(5);
		setCanvas();
		setInitialPosition(2, 1);
		expected.add(".....");
		expected.add("..#..");
		expected.add("..#..");
		expected.add("..#..");
		expected.add(".....");
		up(2);
		test("Test metody up(2)");
	}

	@Test(timeout = 500)
	public void leftTest() {
		canvas = createEmptyCanvas(5);
		setCanvas();
		setInitialPosition(2, 1);
		expected.add(".....");
		expected.add(".....");
		expected.add(".....");
		expected.add("###..");
		expected.add(".....");
		left(2);
		test("Test metody left(2)");
	}

	@Test(timeout = 500)
	public void rightTest() {
		canvas = createEmptyCanvas(5);
		setCanvas();
		setInitialPosition(2, 1);
		expected.add(".....");
		expected.add(".....");
		expected.add(".....");
		expected.add("..###");
		expected.add(".....");
		right(2);
		test("Test metody right(2)");
	}

	@Test(timeout = 500)
	public void repeatTest() {
		canvas = createEmptyCanvas(6);
		setCanvas();
		setInitialPosition(2, 1);
		expected.add("...##.");
		expected.add("...#..");
		expected.add("..##..");
		expected.add("..#...");
		expected.add("..#...");
		expected.add("......");
		up(2);
		right(1);
		repeat(2);
		test("Test metody repeat(2)");
	}

	@Test(timeout = 500)
	public void undoTest1() {
		canvas = createEmptyCanvas(6);
		setCanvas();
		setInitialPosition(2, 1);
		expected.add("......");
		expected.add("......");
		expected.add("..##..");
		expected.add("..#...");
		expected.add("..#...");
		expected.add("......");
		up(2);
		right(1);
		repeat(2);
		undo(1);
		test("Test metody undo(1)");
	}

	@Test(timeout = 500)
	public void undoTest2() {
		canvas = createEmptyCanvas(6);
		setCanvas();
		setInitialPosition(2, 1);
		expected.add("......");
		expected.add("......");
		expected.add("..#...");
		expected.add("..#...");
		expected.add("..#...");
		expected.add("......");
		up(2);
		right(1);
		repeat(2);
		undo(2);
		test("Test metody undo(2)");
	}

	@Test(timeout = 500)
	public void undoTest3() {
		canvas = createEmptyCanvas(6);
		setCanvas();
		expected.add("......");
		expected.add("......");
		expected.add("..#...");
		expected.add("#.#...");
		expected.add("..#...");
		expected.add("......");
		setInitialPosition(2, 1);
		up(2);
		setInitialPosition(0, 2);
		right(4);
		undo(1);
		test("Test metody undo");
	}

	@Test(timeout = 500)
	public void redoTest() {
		canvas = createEmptyCanvas(6);
		setCanvas();
		expected.add("......");
		expected.add("#####.");
		expected.add("#...#.");
		expected.add("#...#.");
		expected.add("..###.");
		expected.add("......");
		setInitialPosition(0, 2);
		up(2);
		right(4);
		down(3);
		left(1);
		repeat(1);
		undo(2);
		undo(1);
		redo(2);
		redo(1);
		test("Test metody redo");
	}
}