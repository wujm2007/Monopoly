package entity;

import java.util.Arrays;

import dao.IOHelper;

public class LotterySpot extends Building {
	private static final int LOTTERY_NUM = 20;
	private static final int LOTTERY_PRICE = 200;
	private static int jackpot;
	private static int prize = 0;

	private static Player owner[] = { null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null };

	@Override
	public String getIcon() {
		return "彩";
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		LotteryOperation(p);
	}

	@Override
	public Player getOwner() {
		return null;
	}

	private static void LotteryOperation(Player p) {
		for (int i = 0; i < LOTTERY_NUM; i++) {
			IOHelper.showInfo("No." + (i + 1) + ": " + (owner[i] == null ? "无人购买" : owner[i].getName()));
		}
		if (IOHelper.InputYN("是否购买彩票")) {
			int n;
			while ((n = IOHelper.InputInt("请输入想要购买的彩票编号(输入0退出)")) != 0) {
				n--;
				if ((n >= 0) && (n < LOTTERY_NUM)) {
					if (owner[n] == null) {
						if (LOTTERY_PRICE <= p.getCash()) {
							p.setCash(p.getCash() - LOTTERY_PRICE);
							LotterySpot.owner[n] = p;
							return;
						} else {
							IOHelper.alert("现金不足。");
							return;
						}
					} else
						IOHelper.alert(n + "号彩票已被购买。");
				} else {
					IOHelper.alert("请输入1-" + LOTTERY_NUM + "之内的数字。");
				}
			}
		}

	}

	public static int getJackpot() {
		return jackpot;
	}

	public static void generateJackpot() {
		jackpot = (int) (Math.random() * LOTTERY_NUM);
	}

	public static void announceWinner() {
		for (int i = 0; i < owner.length; i++) {
			if (owner[i] != null)
				prize += LOTTERY_PRICE;
		}
		IOHelper.showInfo("中奖号码是" + (jackpot + 1));
		if (owner[jackpot] != null) {
			IOHelper.showInfo("恭喜玩家" + owner[jackpot].getName() + "中奖，奖金" + prize + "元");
			owner[jackpot].setCash(owner[jackpot].getCash() + prize);
			prize = 0;
		} else {
			IOHelper.showInfo("\n无人中奖");
		}
		Arrays.fill(owner, null);
	}

}
