package entity;

import biz.IOHelper;

public class Bank extends Building {

	@Override
	public String getIcon() {
		return "银";
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

		IOHelper.showInfo("欢迎进入银行，您的资产情况如下：\n姓名：" + p.getName() + "\t现金：" + p.getCash() + "\t存款：" + p.getDeposit()
				+ "\t总资产：" + p.getAsset());

		while (IOHelper.InputYN("是否存取款？")) {
			if (IOHelper.InputYN("您是否需要存款？")) {
				int money = IOHelper.InputInt("请输入存款金额");
				if (p.getCash() >= money) {
					p.setCash(p.getCash() - money);
					p.setDeposit(p.getDeposit() + money);
				} else {
					IOHelper.alert("您的现金不足。");
				}
			}
			if (IOHelper.InputYN("您是否需要取款？")) {
				int money = IOHelper.InputInt("请输入取款金额");
				if (p.getDeposit() >= money) {
					p.setDeposit(p.getDeposit() - money);
					p.setCash(p.getCash() + money);
				} else {
					IOHelper.alert("您的存款不足。");
				}
			}
			IOHelper.showInfo("您的资产情况如下：\n姓名：" + p.getName() + "\t现金：" + p.getCash() + "\t存款：" + p.getDeposit()
					+ "\t总资产：" + p.getAsset() + "\t");
		}

	}

	@Override
	public String getType() {
		return "银行";
	}

}
