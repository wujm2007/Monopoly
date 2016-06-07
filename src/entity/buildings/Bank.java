package entity.buildings;

import entity.IOHelper;
import entity.Player;

@SuppressWarnings("serial")
public class Bank extends Building {
	private static final String ICON = "银";
	private static final String TYPE = "银行";

	public enum BankOpType {
		WITHDRAW, DEPOSITE, QUIT
	}

	public static class BankOperation {

		private BankOpType op;
		private int money;

		public BankOperation(BankOpType op, int money) {
			this.op = op;
			this.money = money;
		}

		public BankOpType getOpType() {
			return op;
		}

		public int getMoney() {
			return money;
		}
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
		bankOperation(p);
	}

	@Override
	public void stay(Player p) {
		bankOperation(p);
	}

	public static void payInterest(Player p) {
		p.setDeposit((int) (p.getDeposit() * 1.1));
	}

	private static void bankOperation(Player p) {
		IOHelper IO = p.getGame().io();
		IO.showBankAccountInfo(p);

		while (true) {
			BankOperation bkop = IO.getBankOperation(p);
			int money;

			switch (bkop.getOpType()) {
			case DEPOSITE:
				money = bkop.getMoney();
				if (p.costCash(money)) {
					p.addDeposit(money);
				} else {
					IO.alert("您的现金不足。");
				}
				break;
			case WITHDRAW:
				money = bkop.getMoney();
				if (p.costDeposit(money)) {
					p.addCash(money);
				} else {
					IO.alert("您的存款不足。");
				}
				break;
			case QUIT:
				return;
			}

			IO.showBankAccountInfo(p);
		}
	}

}
