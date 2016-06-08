package entity.cards;

import entity.IOHelper;
import entity.Player;
import entity.buildings.LotterySpot;

@SuppressWarnings("serial")
public class LotteryCard extends Card {

	private static LotteryCard instance;

	private static final String NAME = "彩票卡";
	private static final String DESCRIPTION = "使用时可以操作本月的彩票开奖结果";

	private LotteryCard() {
	};

	public static LotteryCard getInstance() {
		if (instance == null) {
			return (instance = new LotteryCard());
		}
		return instance;
	}

	@Override
	public int act(Player p) {
		IOHelper IO = p.getGame().io();
		int n;
		while (((n = IO.inputInt("请输入位置本月彩票中奖号码：")) <= 0) || (n > LotterySpot.LOTTERY_NUM)) {
			IO.alert("请输入正确的彩票号码！");
			n = IO.inputInt("请输入位置本月彩票中奖号码：");
		}
		LotterySpot.setJackpot(n);
		IO.alert("本月彩票中奖号码为：" + (LotterySpot.getJackpot() + 1) + "。");
		return 0;
	}

	@Override
	public int getPrice() {
		return 30;
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
