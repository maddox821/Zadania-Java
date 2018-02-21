public class PMO_PositionHelper {
    public static boolean notSmaller( Position2D pos2compare, Position2D compared ) {
        if ( compared.getX() < pos2compare.getX() ) return false;
        if ( compared.getY() < pos2compare.getY() ) return false;
        return true;
    }

    public static boolean notBigger( Position2D pos2compare, Position2D compared ) {
        if ( compared.getX() > pos2compare.getX() ) return false;
        if ( compared.getY() > pos2compare.getY() ) return false;
        return true;
    }

    public static Position2D shift( int x, int y, Position2D shift ) {
        return new Position2D( x + shift.getX(), y + shift.getY() );
    }
}
