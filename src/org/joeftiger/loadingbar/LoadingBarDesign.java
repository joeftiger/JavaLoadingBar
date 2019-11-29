package org.joeftiger.loadingbar;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class LoadingBarDesign {
	public static final BiConsumer<char[], Integer> DEFAULT = (bar, progress) -> {
		Arrays.fill(bar, ' ');
		bar[0] = '[';
		for (int i = 1; i <= progress; i++) {
			bar[i] = '#';
		}
		bar[bar.length - 1] = ']';
	};
}
