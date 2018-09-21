package ski.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Objects;

public class SkiMap {
	public final int xBound;
	public final int yBound;
	public final int[][] coords;

	public SkiMap(int lb, int ub, int[][] points) {
		xBound = lb;
		yBound = ub;
		coords = points;
	}

	public static SkiMap buildMapFromFile(String pathtoMap) throws IOException {
		Objects.requireNonNull(pathtoMap);
		Iterator<String> sanitizedLines = Files.lines(new File(pathtoMap).toPath()).map(s -> s.trim())
				.filter(s -> !s.isEmpty()).iterator();

		var firstLine = sanitizedLines.next().split(" ");
		int row = Integer.parseInt(firstLine[0]);
		int col = Integer.parseInt(firstLine[1]);
		var arr = new int[row][col];
		int i = 0;
		while (sanitizedLines.hasNext()) {
			var elevations = sanitizedLines.next().split(" ");
			for (int j = 0; j < col; j++) {
				arr[i][j] = Integer.parseInt(elevations[j]);
			}
			i++;
		}
		return new SkiMap(row, col, arr);
	}
}
