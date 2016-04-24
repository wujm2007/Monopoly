package entity;

public class AverageCashCard extends Card {

	@Override
	public int act(Player p) {
		int totalCash = p.getGame().getPlayers(false).stream().map(player -> player.getCash()).reduce((a, b) -> a + b)
				.orElse(0);
		int averageCash = totalCash / p.getGame().getPlayers(false).size();
		p.getGame().getPlayers(false).forEach(player -> {
			player.setCash(averageCash);
		});
		return 0;
	}

	@Override
	public int getPrice() {
		return 50;
	}

	@Override
	public String getName() {
		return "均富卡";
	}

	@Override
	public String getDescription() {
		return "将所有人的现金平均分配";
	}

}
