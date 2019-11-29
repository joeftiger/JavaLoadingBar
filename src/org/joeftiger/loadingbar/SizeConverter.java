package org.joeftiger.loadingbar;

import java.util.concurrent.TimeUnit;

public class SizeConverter {
	public static String humanReadableByteCount(long bytes) {
		return humanReadableByteCount(bytes, false);
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public static String millisToTime(long millis) {
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;

		if (hours != 0) return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		return String.format("%02d:%02d", minutes, seconds);
	}
}
