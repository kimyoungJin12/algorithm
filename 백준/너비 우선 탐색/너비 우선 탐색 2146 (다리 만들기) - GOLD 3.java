import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithm {
	static int N, answer, array[][], distanceArray[][], groupArray[][];
	static int[] x = { 0, 0, 1, -1 };
	static int[] y = { 1, -1, 0, 0 };

	public static void main(String[] args) throws Exception {
		SetData();
		bfs();
		System.out.println(answer);
	}

	private static void SetData() throws Exception {
		InputReader in = new InputReader(System.in);

		N = in.nextInt();
		array = new int[N][N];
		distanceArray = new int[N][N];
		groupArray = new int[N][N];
		answer = Integer.MAX_VALUE;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				array[j][i] = in.nextInt();
			}
		}
	}

	private static void bfs() {
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (array[i][j] != 1 || groupArray[i][j] != 0)
					continue; // array가 1이고 g를 초기화 안했다면

				Queue<int[]> queue = new LinkedList<>();
				groupArray[i][j] = ++count; // g를 초기화
				queue.add(new int[] { i, j });
				while (!queue.isEmpty()) {
					int[] location = queue.remove();

					for (int k = 0; k < 4; k++) {
						int r = location[0] + x[k];
						int c = location[1] + y[k];
						if (r < 0 || r >= N || c < 0 || c >= N)
							continue;

						if (array[r][c] == 1 && groupArray[r][c] == 0) {
							queue.add(new int[] { r, c });
							groupArray[r][c] = count;
						}
					}
				}
			}
		}

		Queue<int[]> queue = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				distanceArray[i][j] = -1;
				if (array[i][j] == 1) {
					queue.add(new int[] { i, j });
					distanceArray[i][j] = 0;
				}
			}
		}
		while (!queue.isEmpty()) {
			int[] location = queue.remove();

			for (int k = 0; k < 4; k++) {
				int r = location[0] + x[k];
				int c = location[1] + y[k];

				if (r < 0 || r >= N || c < 0 || c >= N)
					continue;

				if (distanceArray[r][c] == -1) {
					distanceArray[r][c] = distanceArray[location[0]][location[1]] + 1;
					groupArray[r][c] = groupArray[location[0]][location[1]];
					queue.add(new int[] { r, c });
				}
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < 4; k++) {
					int r = i + x[k];
					int c = j + y[k];
					if (r < 0 || r >= N || c < 0 || c >= N)
						continue;

					if (groupArray[i][j] != groupArray[r][c])
						answer = Math.min(answer, distanceArray[i][j] + distanceArray[r][c]);

				}
			}
		}
	}
}

class InputReader {
	private final InputStream stream;
	private final byte[] buf = new byte[8192];
	private int curChar, snumChars;

	public InputReader(InputStream st) {
		this.stream = st;
	}

	public int read() {
		if (snumChars == -1)
			throw new InputMismatchException();
		if (curChar >= snumChars) {
			curChar = 0;
			try {
				snumChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (snumChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

	public int nextInt() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public long nextLong() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		long res = 0;
		do {
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public int[] nextIntArray(int n) {
		int a[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = nextInt();
		}
		return a;
	}

	public String nextLine() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isEndOfLine(c));
		return res.toString();
	}

	public boolean isSpaceChar(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	private boolean isEndOfLine(int c) {
		return c == '\n' || c == '\r' || c == -1;
	}
}