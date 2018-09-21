package ski.app;

public class Coordinate {
	public final int x;
	public final int y;
	public final int elevation;
	
	public Coordinate(int x, int y, int elevation) {
		this.x = x;
		this.y = y;
		this.elevation = elevation;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("[").append("(" + x +"," + y + ") - " + elevation)
				.append("]").toString();
	}
}
