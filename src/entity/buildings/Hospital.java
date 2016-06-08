package entity.buildings;

import entity.Player;

@SuppressWarnings("serial")
public class Hospital extends Building {
	private static final String ICON = "医";
	private static final String TYPE = "医院";
	private static int POSITION;

	private static Hospital instance;

	private Hospital() {
	};

	public static Hospital getInstance() {
		if (instance == null) {
			return (instance = new Hospital());
		}
		return instance;
	}

	public static int getPostition() {
		return POSITION;
	}

	public static void setPostition(int p) {
		POSITION = p;
	}

	@Override
	public String getIcon() {
		return ICON;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		return;
	}

}
