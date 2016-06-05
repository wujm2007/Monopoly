package entity;

import java.util.Arrays;

public class LotterySpot extends Building {
	private static final String ICON = "彩";
	private static final String TYPE = "彩票点";
	public static final int LOTTERY_NUM = 20;
	public static final int LOTTERY_PRICE = 200;
	private static int jackpot;
	private static int prize = 0;

	public static int getPrize() {
		return prize;
	}

	private static Player owner[] = { null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null };

	@Override
	public String getIcon() {
		return ICON;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public static Player getLotteryOwner(int index) {
		if ((index >= 0) && (index < LOTTERY_NUM))
			return owner[index];
		else
			return null;
	}

	public static void setLotteryOwner(int index, Player p) {
		if ((index >= 0) && (index < LOTTERY_NUM))
			owner[index] = p;
		else
			return;
	}

	@Override
	public void passby(Player p) {
		return;
	}

	@Override
	public void stay(Player p) {
		lotteryOperation(p);
	}

	private static void lotteryOperation(Player p) {
		IOHelper IO = p.getGame().io();
		IO.showLotteryInfo();
		int i;
		if ((i = IO.getLotteryOperation(p)) != -1) {
			if (p.costCash(LOTTERY_PRICE)) {
				LotterySpot.setLotteryOwner(i, p);
				prize += LOTTERY_PRICE;
			} else {
				IO.alert("现金不足。");
			}
		}
	}

	public static void setJackpot(int j) {
		if ((j > 0) || (j <= LOTTERY_NUM))
			jackpot = j - 1;
	}

	public static void announceWinner(IOHelper IO) {
		if (jackpot == -1) {
			jackpot = (int) (Math.random() * LOTTERY_NUM);
		}
		IO.showLotteryWinner();
		if ((owner[jackpot] != null) && (!owner[jackpot].isBroke())) {
			owner[jackpot].addCash(prize);
			prize = 0;
		}
		Arrays.fill(owner, null);
		jackpot = -1;
	}

	public static int getJackpot() {
		return jackpot;
	}

}
