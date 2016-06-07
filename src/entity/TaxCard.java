package entity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TaxCard extends Card {

	private static TaxCard instance;

	private static final String NAME = "查税卡";
	private static final String DESCRIPTION = "使附近玩家缴纳个人所得税(30%存款)";

	private TaxCard() {
	};

	public static TaxCard getInstance() {
		if (instance == null) {
			return (instance = new TaxCard());
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
		if (!playersNear.isEmpty()) {
			playersNear.forEach(p -> {
				player.getGame().io().alert(p.getName() + "缴纳个人所得税:" + (int) (p.getDeposit() * 0.3));
				p.costDeposit((int) (p.getDeposit() * 0.3));
			});
			return 0;
		}
		player.getGame().io().alert("使用失败！附近没有玩家。");
		return -1;
	}

	@Override
	public int getPrice() {
		return 50;
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