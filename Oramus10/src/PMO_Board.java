import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class PMO_Board {
    private Set<Position2D> allProhibitedPositions;
    private Set<Position2D> border;
    private Set<Position2D> internalProhibitedPositions;
    private Position2D minPosition;
    private Position2D maxPosition;
    private int sizeX;
    private int sizeY;

    public PMO_Board(Position2D minPosition, int sizeX, int sizeY, Set<Position2D> internalProhibitedPositions) {
        this.internalProhibitedPositions = internalProhibitedPositions;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.minPosition = minPosition;
        maxPosition = PMO_PositionHelper.shift(sizeX+1,sizeY+1, minPosition );

        generateBorder();
        allProhibitedPositions = new HashSet<>();
        allProhibitedPositions.addAll(internalProhibitedPositions);
        allProhibitedPositions.addAll(border);
        PMO_SystemOutRedirect.println( "Min position : " + minPosition );
        PMO_SystemOutRedirect.println( "Max position : " + maxPosition );
    }

    private void generateBorder() {
        border = new HashSet<>();
        Position2D helperPosition = new Position2D(minPosition.getX(), minPosition.getY() + sizeY+1);

        for (int x = 1; x <= sizeX; x++) {
            border.add(PMO_PositionHelper.shift(x, 0, minPosition));
            border.add(PMO_PositionHelper.shift(x, 0, helperPosition));
        }
        helperPosition = new Position2D(minPosition.getX() + sizeX+1, minPosition.getY());
        for (int y = 1; y <= sizeY; y++) {
            border.add(PMO_PositionHelper.shift(0, y, minPosition));
            border.add(PMO_PositionHelper.shift(0, y, helperPosition));
        }
    }

    public Set<Position2D> getAllProhibitedPositions() {
        return allProhibitedPositions;
    }

    public Set<Position2D> getBorder() {
        return border;
    }

    public Set<Position2D> getInternalProhibitedPositions() {
        return internalProhibitedPositions;
    }

    public Position2D getMinPosition() {
        return minPosition;
    }

    public Position2D getMaxPosition() {
        return maxPosition;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
