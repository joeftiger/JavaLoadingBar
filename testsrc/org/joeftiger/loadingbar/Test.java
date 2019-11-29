package org.joeftiger.loadingbar;

public class Test {
	private static long currentProgress = 0;
	private static final long total = 1024 * 32;
	private static final long progressStep = total / 16;

	public static void main(String[] args) throws InterruptedException {
		var bar = new LoadingBar("unknown total", Test::progress, total);

		do {
			System.out.print(bar);
			Thread.sleep(250);
		} while (!bar.hasFinished());
	}

	private static long progress() {
		long p = currentProgress;
		currentProgress += progressStep;
		currentProgress = Math.min(currentProgress, total);
		return p;
	}
}
