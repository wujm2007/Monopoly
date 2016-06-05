package entity;

import java.util.ArrayList;
import java.util.List;

public class TurnaroundCard extends Card {

	private static final String NAME = "转向卡";
	private static final String DESCRIPTION = "使玩家转向";
	private static TurnaroundCard instance;

	private TurnaroundCard() {
	};

	public static TurnaroundCard getInstance() {
		if (instance == null) {
			return (instance = new TurnaroundCard());
		}
		return instance;
	}

	@Override
	public int act(Player player) {
		int p1 = player.getPosition();
		int len = player.getMap().getLength();
		List<Player> playersNear = player.getPeers(false).stream()
				.filter(p2 -> (Math.abs(p1 - p2.getPosition()) <= 5 || Math.abs(p1 - p2.getPosition()) >= len - 5))
				.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		Player p = player.getGame().io().chosePlayer(playersNear);
		if (p != null) {
			p.setClockwise(!p.isClockwise());
			return 0;
		}
		return -1;
	}

	@Override
	public int getPrice() {
		return 20;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

}
