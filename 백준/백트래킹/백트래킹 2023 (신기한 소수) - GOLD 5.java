import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	static int N;
	static StringBuilder sb;

	public static void main(String[] args) throws Exception {
		SetData();
		System.out.println(sb.toString());
	}

	private static void SetData() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		sb = new StringBuilder();

		dfs("");
	}

	private static void dfs(String s) {
		if (s.length() == N) {
			sb.append(s + '\n');
			return;
		}
		for (int i = 1; i <= 9; i++) {
			if (Prime(Integer.parseInt(s + i))) {
				dfs(s + i);
			}
		}

	}

	private static boolean Prime(int num) {
		if (num == 1)
			return false;

		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}
}