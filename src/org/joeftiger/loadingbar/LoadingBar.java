package org.joeftiger.loadingbar;

import java.util.function.Function;
import java.util.function.LongSupplier;

public class LoadingBar {
	private final String name;
	private final LongSupplier progress_B;
	private long total_B;

	private long currentProgress_B;
	private long currentETA_ms;
	private long currentSpeed_s;

	private long startTime;

	/**
	 * Creates a new loading bar.
	 *
	 * @param name name
	 * @param progress_B supplier for progress (in bytes)
	 * @param total_B supplier for progress (in bytes)
	 */
	public LoadingBar(String name, LongSupplier progress_B, final long total_B) {
		this.name = name;
		this.progress_B = progress_B;
		this.total_B = total_B;

		resetStartTime();
	}

	/**
	 * Resets the start timer.
	 */
	public void resetStartTime() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * Forces an update to the statistics of
	 * <ul>
	 *     <li>{@link #getCurrentProgress()}</li>
	 *     <li>{@link #getCurrentETA_ms()}</li>
	 *     <li>{@link #getCurrentSpeed_s()}</li>
	 * </ul>
	 */
	public void updateStatistics() {
		updateProgress();
		updateETA_ms();
		updateSpeed_s();
	}

	/**
	 * Updates the {@link #currentProgress_B}
	 */
	private void updateProgress() {
		currentProgress_B = progress_B.getAsLong();
	}

	/**
	 * Updates the {@link #currentSpeed_s}
	 */
	private void updateSpeed_s() {
		long time_ms = getTimeElapsed();
		currentSpeed_s = time_ms == 0 ? 0 : (getCurrentProgress() * 1_000 / time_ms);
	}

	/**
	 * Updates the {@link #currentETA_ms}
	 */
	private void updateETA_ms() {
		double remaining = getTotal() - getCurrentProgress();
		long progress = getCurrentProgress();
		currentETA_ms = progress == 0 ? 0 : (long) (remaining * getTimeElapsed() / getCurrentProgress());
	}

	/**
	 * Returns the current progress in bytes.
	 *
	 * @return progress
	 */
	public long getCurrentProgress() {
		return currentProgress_B;
	}

	/**
	 * Returns the total size in bytes.
	 *
	 * @return total
	 */
	public long getTotal() {
		return total_B;
	}

	/**
	 * Returns the elapsed time since the start (in milliseconds).
	 *
	 * @return elapsed time
	 */
	public long getTimeElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Returns whether the progress has reached the total.
	 *
	 * @return {@code true} if {@code progress = total}. {@code false} otherwise..
	 */
	public boolean hasFinished() {
		return getCurrentProgress() == getTotal();
	}

	/**
	 * Returns details to the progress of this loading bar in the {@link LoadingBarDesign#DEFAULT_DESIGN} design.
	 *
	 * @return printable loading bar
	 */
	@Override
	public String toString() {
		return toString(LoadingBarDesign.DEFAULT_DESIGN);
	}

	/**
	 * Updates the statistics and returns details to the progress of this loading bar in the given design design.
	 *
	 * @return printable loading bar
	 */
	public String toString(Function<LoadingBar, String> design) {
		updateStatistics();
		return design.apply(this);
	}

	/**
	 * Returns the previously computed ETA (in milliseconds).
	 *
	 * @return eta
	 */
	public long getCurrentETA_ms() {
		return currentETA_ms;
	}

	/**
	 * Returns the name of this loading bar.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the previously computed speed (bytes / second)
	 *
	 * @return speed
	 */
	public long getCurrentSpeed_s() {
		return currentSpeed_s;
	}
}
