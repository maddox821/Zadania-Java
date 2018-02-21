import java.util.ArrayList;
import java.util.List;

public class PMO_Dice implements DiceInterface {

	private final List<Interval> intervals = new ArrayList<>();
	private List<Integer> dots;
	private int counter;

	public void addInterval(Interval interval) {
		intervals.add(interval);
	}

	public void addDots(List<Integer> dots) {
		this.dots = dots;
	}

	@Override
	public int getNumberOfDots() {
		return dots.get(counter++ % dots.size());
	}

	@Override
	public int getNumberOfIntervals() {
		return intervals.size();
	}

	@Override
	public Interval getInterval(int intervalID) {
		return intervals.get(intervalID);
	}

}
