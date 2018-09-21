package ski.app;

import java.util.ArrayList;
import java.util.Objects;

public class Path {
	public final ArrayList<Coordinate> steps; 
	public final Coordinate startingPoint;
	public Coordinate currenPoint;
	
	public Path(Coordinate cr) {
		Objects.requireNonNull(cr);
		steps = new ArrayList<>();
		steps.add(cr);
		startingPoint = cr;
		currenPoint = cr;
		
	}
	
	private Path() {
		steps = new ArrayList<>();
		startingPoint = null;
	}
	
	public static Path emptyPath() { return new Path(); } // convenience 
	
	public int distance() {
		return steps.size();
	}
	
	public int slope() {
		if(startingPoint == null)
			return Integer.MIN_VALUE;
		return startingPoint.elevation - currenPoint.elevation;
	}
	
	// Convenience methods to generate Coordinate, while movie in that direction.
	public Coordinate north(int elevation) {
		return new Coordinate(currenPoint.x - 1, currenPoint.y,  elevation);
	}
	
	public Coordinate south(int elevation) {
		return new Coordinate(currenPoint.x + 1, currenPoint.y,  elevation);
	}
	public Coordinate east(int elevation) {
		return new Coordinate(currenPoint.x, currenPoint.y + 1,  elevation);
	}
	public Coordinate west(int elevation) {
		return new Coordinate(currenPoint.x, currenPoint.y - 1,  elevation);
	}

	public void merge(Path subPath) {
		Objects.requireNonNull(subPath);
		steps.addAll(subPath.steps);
		currenPoint = steps.get(steps.size() - 1);
	}
	
	public boolean isBetterThan(Path anotherPath) {
		if (this.distance() > anotherPath.distance()) {
			return true;
		} else if (this.distance() == anotherPath.distance()) {
			if(this.steps.size() == 1 && anotherPath.steps.size() == 1 && this.currenPoint.elevation < 
					anotherPath.currenPoint.elevation) {
				return true;
			}
			if (this.slope() > anotherPath.slope()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		var sb = new StringBuilder("{");
		steps.forEach(sb::append);
		return sb.append("}").toString();
	}
}
