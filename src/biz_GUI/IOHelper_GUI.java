package biz_GUI;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JOptionPane;

import entity.LotterySpot;
import entity.Player;
import entity.StockMarket.Stock;
import entity.StockMarket.StockTradeOpType;
import entity.StockMarket.StockTradeOperation;
import entity.Bank.BankOpType;
import entity.Bank.BankOperation;
import entity.IOHelper;

@SuppressWarnings("serial")
public class IOHelper_GUI extends IOHelper {

	private transient Scanner sc;

	private static IOHelper instance;

	private IOHelper_GUI() {
	};

	public static IOHelper getInstance() {
		if (instance == null) {
			return (instance = new IOHelper_GUI());
		}
		return instance;
	}

	public boolean InputYN(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "确认", JOptionPane.YES_NO_OPTION) == 0;
	}

	// return the integer input
	public int InputInt(String msg) {
		try {
			String strInput = JOptionPane.showInputDialog(msg);
			int intInput = Integer.parseInt(strInput);
			return intInput;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "输入不合法！");
			return InputInt(msg);
		}
	}

	public void showStocks(List<Stock> stocks) {
		String info = "股票行情：\n";
		for (Stock s : stocks) {
			info += (stocks.indexOf(s) + 1) + ":\t" + s.getName() + "\t\t单价:\t" + String.format("%.2f", s.getPrice())
					+ "\n";
		}
		showInfo(info);
	}

	public void printStockAccount(Map<Stock, Integer> account) {
		String info = "您的股票账户：\n";
		for (Entry<Stock, Integer> e : account.entrySet()) {
			if (e.getValue() > 0)
				info += e.getKey().getName() + ": " + e.getValue() + "股, 价值："
						+ (int) (e.getKey().getPrice() * e.getValue()) + "元。\n";
		}
		showInfo(info);
	}

	// get StockTradeOperation (see entity.StockMarket.StockTradeOperation)
	public StockTradeOperation InputStockOp() {
		sc = new Scanner(System.in);
		System.out.println("请输入操作码,输入q退出(如：b 1 100 //买入1号股票100股)");
		System.out.print("> ");
		String strInput = sc.next().toLowerCase();
		String opType;
		int index, num;
		if (strInput.equals("q"))
			return new StockTradeOperation(StockTradeOpType.QUIT, 0, 0);
		else if (strInput.equals("b") || strInput.equals("s")) {
			opType = strInput;
			strInput = sc.next();
			try {
				index = Integer.parseInt(strInput);
			} catch (NumberFormatException e) {
				alert("请输入正确的股票代码。");
				return InputStockOp();
			}
			strInput = sc.next();
			try {
				num = Integer.parseInt(strInput);
				if (num < 100) {
					alert("请输入正确的股数(100股起)。");
					return InputStockOp();
				}
			} catch (NumberFormatException e) {
				alert("请输入正确的股数。");
				return InputStockOp();
			}
			switch (opType) {
			case "b":
				return new StockTradeOperation(StockTradeOpType.BUY, index, num);
			case "s":
				return new StockTradeOperation(StockTradeOpType.SELL, index, num);
			}
		} else {
			alert("请按照操作码格式输入。");
			return InputStockOp();
		}
		return null;
	}

	// Output the msg
	public void alert(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	// Output the msg (same as alert() in command line version)
	public void showInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void showBankAccountInfo(Player p) {
		p.getGame().updateInfo();
	}

	// get StockTradeOperation (see entity.Bank.BankOperation)
	public BankOperation getBankOperation(Player p) {
		Object[] options = { "离开", "取款", "存款" };

		int response = JOptionPane.showOptionDialog(null, "欢迎来到银行", "银行", JOptionPane.YES_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (response == 2) {
			int money = InputInt("请输入存款金额");
			return new BankOperation(BankOpType.DEPOSITE, money);
		} else if (response == 1) {
			int money = InputInt("请输入取款金额");
			return new BankOperation(BankOpType.WITHDRAW, money);
		} else {
			return new BankOperation(BankOpType.QUIT, 0);
		}
	}

	public void showLotteryInfo() {
		for (int i = 0; i < LotterySpot.LOTTERY_NUM; i++) {
			showInfo("No." + (i + 1) + ": "
					+ (LotterySpot.getLotteryOwner(i) == null ? "无人购买" : LotterySpot.getLotteryOwner(i).getName()));
		}
	}

	// return the lottery index bought by p
	public int getLotteryOperation(Player p) {
		if (InputYN("是否购买彩票")) {
			int n;
			while ((n = InputInt("请输入想要购买的彩票编号(输入0退出)")) != 0) {
				n--;
				if ((n >= 0) && (n < LotterySpot.LOTTERY_NUM)) {
					if (LotterySpot.getLotteryOwner(n) == null) {
						return n;
					} else {
						alert((n + 1) + "号彩票已被购买。");
						return getLotteryOperation(p);
					}
				} else {
					alert("请输入1-" + LotterySpot.LOTTERY_NUM + "之内的数字。");
					return getLotteryOperation(p);
				}
			}
		}
		return -1;
	}

	public void showLotteryWinner() {
		int jackpot = LotterySpot.getJackpot();
		showInfo("中奖号码是" + (jackpot + 1));
		if (LotterySpot.getLotteryOwner(jackpot) != null) {
			if (!LotterySpot.getLotteryOwner(jackpot).isBroke())
				showInfo("恭喜玩家" + LotterySpot.getLotteryOwner(jackpot).getName() + "中奖，奖金" + LotterySpot.getPrize()
						+ "元");
			else
				showInfo("玩家" + LotterySpot.getLotteryOwner(jackpot).getName() + "已破产，奖金" + LotterySpot.getPrize()
						+ "元计入奖池。");
		} else {
			showInfo("本月无人中奖");
		}
	}

	public int getStoreCardIndex(Player p) {
		showInfo("您拥有" + p.getTickets() + "点券。");
		return InputInt("请输入您要购买的卡片编号: ") - 1;
	}

	@Override
	public Player chosePlayer(List<Player> playersNear) {
		Object[] players = playersNear.toArray(new Object[playersNear.size()]);
		return (Player) JOptionPane.showInputDialog(null, "请选择玩家", "选择", JOptionPane.PLAIN_MESSAGE, null, players,
				null);
	}

}
