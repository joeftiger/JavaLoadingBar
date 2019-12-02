package org.joeftiger.loadingbar;

import java.util.concurrent.TimeUnit;

public class SizeConverter {

	/**
	 * Converts the given bytes into base-1024 human readable format (KiB, MiB, ...)
	 *
	 * @param bytes bytes
	 * @return human readable byte count
	 */
	public static String humanReadableByteCount(long bytes) {
		return humanReadableByteCount(bytes, false);
	}

	/**
	 * Converts the given bytes into base-1024 human readable format (KiB, MiB, ...)
	 *
	 * @param bytes bytes
	 * @param si if {@code true} (1000 B = 1 KB)<br>
	 *           if {@code false} (1024 B = 1 KiB)
	 * @return human readable byte count
	 */
	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	/**
	 * Converts the given milliseconds into a human readable time format (hh:mm:ss). If {@code hours == 0},
	 * the hour display will be omitted.
	 *
	 * @param millis milliseconds
	 * @return human readable time format
	 */
	public static String millisToTime(long millis) {
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;

		if (hours != 0) return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		return String.format("%02d:%02d", minutes, seconds);
	}
}
