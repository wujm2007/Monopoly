package entity;

import dao.IOHelper;

public class LotteryCard extends Card {

	@Override
	public int act(Player p) {
		int n;
		while (((n = IOHelper.InputInt("请输入位置本月彩票中奖号码：")) <= 0) || (n > LotterySpot.LOTTERY_NUM)) {
			IOHelper.alert("Invalid position");
			n = IOHelper.InputInt("请输入位置本月彩票中奖号码：");
		}
		LotterySpot.setJackpot(n);
		IOHelper.showInfo("本月彩票中奖号码为：" + (LotterySpot.getJackpot() + 1) + "。");
		return 0;
	}

	@Override
	public int getPrice() {
		return 30;
	}

	@Override
	public String getName() {
		return "彩票卡";
	}

}
