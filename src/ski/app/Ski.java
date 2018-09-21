package ski.app;

import java.io.IOException;

public class Ski {
	private SkiMap map;

	Ski(SkiMap map) {
		this.map = map;
	}

	public void start() {
		var path = findBestPath();
		System.out.println("Skiing along: " + path);
		System.out.println("Skiing Ditance: " + path.distance());
		System.out.println("Skiing Elevation: " + path.slope());
	}

	private Path findBestPath() {
		Path currentBestPath = Path.emptyPath();
		// Try to Traverse from every point 
		for (int i = 0; i < map.xBound; i++) {
			for (int j = 0; j < map.yBound; j++) { // Skips points with very low elevations.
				if (currentBestPath.distance() < map.coords[i][j]) {
					Path aSkiPath = traverse(new Path(new Coordinate(i, j, map.coords[i][j])));
					//System.out.println(aSkiPath + "===" + aSkiPath.distance() + String.valueOf(aSkiPath.slope()));
					if(aSkiPath.isBetterThan(currentBestPath)) {
						currentBestPath = aSkiPath;
					}
				}
			}
		}
		return currentBestPath;
	}

	private Path traverse(Path pathTread) {
		var curCoordinate = pathTread.currenPoint;
		var curentElevation = curCoordinate.elevation;

		var childPath = Path.emptyPath();
		var elevationNorth = ((curCoordinate.x - 1) < 0) ? Integer.MAX_VALUE
				: map.coords[curCoordinate.x - 1][curCoordinate.y];
		var elevationSouth = ((curCoordinate.x + 1) >= map.xBound) ? Integer.MAX_VALUE
				: map.coords[curCoordinate.x + 1][curCoordinate.y];
		var elevationWest = ((curCoordinate.y - 1) < 0) ? Integer.MAX_VALUE
				: map.coords[curCoordinate.x][curCoordinate.y - 1];
		var elevationEast = ((curCoordinate.y + 1) >= map.yBound) ? Integer.MAX_VALUE
				: map.coords[curCoordinate.x][curCoordinate.y + 1];

		if (curentElevation > elevationNorth) { // Move North if elevation is low
			// Find sub path from here.
			Path subPath = traverse(new Path(pathTread.north(elevationNorth))); 
			if (subPath.isBetterThan(childPath)) {
				childPath = subPath;
			}
		}

		if (curentElevation > elevationSouth) {
			Path subPath = traverse(new Path(pathTread.south(elevationSouth)));
			if (subPath.isBetterThan(childPath)) {
				childPath = subPath;
			}
		}

		if (curentElevation > elevationWest) {
			Path subPath = traverse(new Path(pathTread.west(elevationWest)));
			if (subPath.isBetterThan(childPath)) {
				childPath = subPath;
			}
		}

		if (curentElevation > elevationEast) {
			Path subPath = traverse(new Path(pathTread.east(elevationEast)));
			if (subPath.isBetterThan(childPath)) {
				childPath = subPath;
			}
		}

		//Merge sub Path
		if (childPath.distance() > 0) {
			pathTread.merge(childPath);
		}
		return pathTread;
	}

	public static void main(String[] args) {
		try {
			/*
			 * Provide Path to Your Map File. Please ensure that the file is as described in
			 * http://geeks.redmart.com/2015/01/07/skiing-in-singapore-a-coding-diversion/
			 */
			new Ski(SkiMap.buildMapFromFile(args[0])

			).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
