package org.joeftiger.loadingbar;

public abstract class LoadingBarFormat {
	public static final LoadingBarFormat DEFAULT = new LoadingBarFormat() {
		@Override
		public int getNameStart() {
			return 1;
		}

		@Override
		public int getNameEnd() {
			return 20;
		}

		@Override
		public int getTotalEnd() {
			return 32;
		}

		@Override
		public int getSpeedEnd() {
			return 45;
		}

		@Override
		public int getEtaEnd() {
			return 54;
		}

		@Override
		public int getBarStart() {
			return 55;
		}

		@Override
		public int getBarEnd() {
			return 74;
		}

		@Override
		public int getProgressEnd() {
			return 80;
		}
	};

	public abstract int getNameStart();
	public abstract int getNameEnd();
	public final int getNameLength() {
		return this.getNameEnd() - this.getNameStart();
	}

	public final int getTotalStart() {
		return this.getTotalEnd() - 10;
	}
	public abstract int getTotalEnd();

	public final int getSpeedStart() {
		return getSpeedEnd() - 12;
	}
	public abstract int getSpeedEnd();

	public final int getEtaStart() {
		return getEtaEnd() - 8;
	}
	public abstract int getEtaEnd();

	public abstract int getBarStart();
	public abstract int getBarEnd();
	public final int getBarLength() {
		return this.getBarEnd() - this.getBarStart() + 1;
	}

	public final int getInnerBarStart() {
		return this.getBarStart() + 1;
	}
	public final int getInnerBarEnd() {
		return this.getBarEnd() - 1;
	}
	public final int getInnerBarLength() {
		return this.getInnerBarEnd() - this.getInnerBarStart() + 1;
	}

	public final int getProgressStart() {
		return this.getProgressEnd() - 4;
	}
	public abstract int getProgressEnd();

	public final int length() {
		return this.getProgressEnd();
	}
}
