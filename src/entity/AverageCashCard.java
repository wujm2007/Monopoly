package entity;

public class AverageCashCard extends Card {
	private static final String NAME = "均富卡";
	private static final String DESCRIPTION = "将所有人的现金平均分配";

	@Override
	public int act(Player player) {
		int totalCash = player.getPeers(false).stream().map(p -> p.getCash()).reduce((a, b) -> a + b).orElse(0);
		int averageCash = totalCash / player.getPeers(false).size();
		player.getPeers(false).forEach(p -> {
			p.setCash(averageCash);
		});
		return 0;
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
