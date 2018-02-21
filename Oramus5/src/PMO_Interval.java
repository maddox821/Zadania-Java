
public class PMO_Interval implements Interval {
	
	private final int lowerEnd;
	private final int upperEnd;

	public PMO_Interval( int lowerEnd, int upperEnd ) {
		this.lowerEnd = lowerEnd;
		this.upperEnd = upperEnd;
	}
	
	@Override
	public int lowerEndpoint() {
		return lowerEnd;
	}

	@Override
	public int upperEndpoint() {
		return upperEnd;
	}

}
