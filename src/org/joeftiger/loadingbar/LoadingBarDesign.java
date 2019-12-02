package org.joeftiger.loadingbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public final class LoadingBarDesign {

	/**
	 * Returns a padding of one space character.
	 */
	public static String PADDING = " ";

	/**
	 * Returns the name padded / cut down to length 19. (formatted to the left)
	 */
	public static Function<String, String> PACKAGE_NAME = (name) -> {
		if (name.length() <= 19) return name + " ".repeat(19 - name.length());
		return name.substring(0, 15) + "...";
	};

	/**
	 * Returns the given total size (in bytes) in a human readable string of length 10. (formatted to the right)
	 */
	public static Function<Long, String> TOTAL_SIZE = (bytes) -> {
		String total = SizeConverter.humanReadableByteCount(bytes);
		return " ".repeat(10 - total.length()) + total;
	};

	/**
	 * Returns the given speed (in bytes/s) in a human readable string of length 12. (formatted to the right)
	 */
	public static Function<Long, String> CURRENT_SPEED = (bytes) -> {
		String speed = SizeConverter.humanReadableByteCount(bytes) + "/s";
		return " ".repeat(12 - speed.length()) + speed;
	};

	/**
	 * Returns the given ETA (in milliseconds) in a human readable string of length 7. (formatted to the right)
	 */
	public static Function<Long, String> ETA = (milliseconds) -> {
		String eta = SizeConverter.millisToTime(milliseconds);
		return " ".repeat(7 - eta.length()) + eta;
	};

	/**
	 * Returns the given progress (in {@code [0, 1]} as a progress bar of length 19. (formatted to the right)
	 */
	public static Function<Double, String> PROGRESS_BAR = (progress) -> {
		int hashes = (int) (17 * progress);
		return "[" + "#".repeat(hashes) + " ".repeat(17 - hashes) + "]";
	};

	/**
	 * Returns the given progress (int {@code [0, 1]} in a human readable string of length 4. (formatted to the right)
	 */
	public static Function<Double, String> PERCENT = (progress) -> {
		long p = Math.round(100 * progress);
		return p < 100 ? " " + p + "%" : p + "%";
	};

// =====================================================================================================================

	/**
	 * Returns the name, total size, current speed, eta, progress and percent in the pacman-style manner.<br>
	 * Contains the {@code '\r'} (carriage return) at the beginning.
	 */
	public static Function<LoadingBar, String> DEFAULT_DESIGN = (bar) -> {
		String[] details = {
				PACKAGE_NAME.apply(bar.getName()),
				TOTAL_SIZE.apply(bar.getTotal()),
				CURRENT_SPEED.apply(bar.getCurrentSpeed_s()),
				ETA.apply(bar.getCurrentETA_ms()),
				PROGRESS_BAR.apply((double) bar.getCurrentProgress() / bar.getTotal()),
				PERCENT.apply((double) bar.getCurrentProgress() / bar.getTotal())
		};

		return "\r" + PADDING + String.join(PADDING, details);
	};
}
