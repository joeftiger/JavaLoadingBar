package org.joeftiger.loadingbar;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.LongSupplier;

public class LoadingBar {
	private String name;
	private LongSupplier progress;
	private LongSupplier total;

	private long progressValue;
	private long totalValue;

	private long startTime;

	private LoadingBarFormat format;

	public LoadingBar(String name, LongSupplier progress, final long total) {
		this(name, progress, () -> total);
	}

	public LoadingBar(String name, LongSupplier progress, LongSupplier total) {
		this(name, progress, total, LoadingBarFormat.DEFAULT);
	}

	public LoadingBar(String name, LongSupplier progress, LongSupplier total, LoadingBarFormat format) {
		this.name = name;
		this.progress = progress;
		this.total = total;
		this.format = format;

		resetStartTime();
	}

	public void resetStartTime() {
		startTime = System.currentTimeMillis();
	}

	private void updateProgress() {
		progressValue = progress.getAsLong();
	}

	private void updateTotal() {
		totalValue = total.getAsLong();
	}

	public boolean hasFinished() {
		return getProgress() == getTotal();
	}

	public long getProgress() {
		return progressValue;
	}

	public long getTotal() {
		return totalValue;
	}

	@Override
	public String toString() {
		return toString(LoadingBarDesign.DEFAULT);
	}

	public String toString(BiConsumer<char[], Integer> customLoadingBarDesign) {
		updateProgress();
		updateTotal();
		char[] out = new char[format.length()];
		Arrays.fill(out, ' ');

		// name
		if (name.length() > format.getNameLength()) {
			System.arraycopy((name.substring(0, format.getNameLength() - 3) + "...").toCharArray(), 0, out, format.getNameStart(), format.getNameLength());
		} else {
			System.arraycopy(name.toCharArray(), 0, out, format.getNameStart(), name.length());
		}

		// total
		String total = SizeConverter.humanReadableByteCount(getTotal());
		System.arraycopy(total.toCharArray(), 0, out, format.getTotalEnd() - total.length(), total.length());

		// speed
		String speed = SizeConverter.humanReadableByteCount(computeSpeed_s()) + "/s";
		System.arraycopy(speed.toCharArray(), 0, out, format.getSpeedEnd() - speed.length(), speed.length());

		// eta
		String eta = SizeConverter.millisToTime(computeETA_ms());
		System.arraycopy(eta.toCharArray(), 0, out, format.getEtaEnd() - eta.length(), eta.length());

		// bar
		double percentage = (double) getProgress() / getTotal();
		int progressIndex = (int) (format.getInnerBarLength() * percentage);
		char[] bar = new char[format.getBarLength()];
		customLoadingBarDesign.accept(bar, progressIndex);
		System.arraycopy(bar, 0, out, format.getBarStart(), bar.length);

		// progress
		String progress = String.valueOf((int) (percentage * 100)) + '%';
		System.arraycopy(progress.toCharArray(), 0, out, format.getProgressEnd() - progress.length(), progress.length());

		return '\r' + String.valueOf(out);
	}

	private long computeSpeed_s() {
		long time_ms = getTimeElapsed();
		return time_ms == 0 ? 0 : (getProgress() * 1_000 / time_ms);
	}

	public long getTimeElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	private long computeETA_ms() {
		double remaining = getTotal() - getProgress();
		long progress = getProgress();
		return progress == 0 ? 0 : (long) (remaining * getTimeElapsed() / getProgress());
	}
}
