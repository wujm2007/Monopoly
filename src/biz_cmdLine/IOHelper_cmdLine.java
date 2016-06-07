package biz_cmdLine;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entity.Player;
import entity.StockMarket.Stock;
import entity.StockMarket.StockTradeOpType;
import entity.StockMarket.StockTradeOperation;
import entity.buildings.LotterySpot;
import entity.buildings.Bank.BankOpType;
import entity.buildings.Bank.BankOperation;
import entity.IOHelper;

@SuppressWarnings("serial")
public class IOHelper_cmdLine extends IOHelper {
	private static IOHelper instance;

	private IOHelper_cmdLine() {
	};

	public static IOHelper getInstance() {
		if (instance == null) {
			return (instance = new IOHelper_cmdLine());
		}
		return instance;
	}

	private Scanner sc;

	// return true if the input is Y and false if the input is N
	public boolean InputYN(String msg) {
		sc = new Scanner(System.in);
		System.out.println(msg + " (Y/N)");
		System.out.print("> ");
		String input = sc.next();
		if (input.toUpperCase().equals("Y"))
			return true;
		else if (input.toUpperCase().equals("N"))
			return false;
		else {
			System.out.println("请输入Y或N。");
			return InputYN(msg);
		}
	}

	// return the integer input
	public int InputInt(String msg) {
		sc = new Scanner(System.in);
		System.out.println(msg + ": ");
		System.out.print("> ");
		String strInput = sc.next();
		int input;
		try {
			input = Integer.parseInt(strInput);
		} catch (NumberFormatException e) {
			alert("请输入整数。");
			return InputInt(msg);
		}
		return input;
	}

	public void showStocks(List<Stock> stocks) {
		showInfo("股票行情：");
		stocks.stream().forEach(s -> {
			showInfo((stocks.indexOf(s) + 1) + ":\t" + s.getName() + "\t\t单价:\t" + String.format("%.2f", s.getPrice()));
		});
	}

	public void printStockAccount(Map<Stock, Integer> account) {
		showInfo("您的股票账户：");
		account.forEach((s, i) -> {
			if (i > 0)
				showInfo(s.getName() + ": " + i + "股, 价值：" + (int) (s.getPrice() * i) + "元。");
		});
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
		System.out.println(msg);
	}

	// Output the msg (same as alert() in command line version)
	public void showInfo(String msg) {
		System.out.println(msg);
	}

	public void showBankAccountInfo(Player p) {
		showInfo("您的资产情况如下：\n姓名：" + p.getName() + "\t现金：" + p.getCash() + "\t存款：" + p.getDeposit() + "\t总资产："
				+ p.getAsset());
	}

	// get StockTradeOperation (see entity.Bank.BankOperation)
	public BankOperation getBankOperation(Player p) {
		int money;
		if (InputYN("是否存取款？")) {
			if (InputYN("您是否需要存款？")) {
				money = InputInt("请输入存款金额");
				return new BankOperation(BankOpType.DEPOSITE, money);
			} else if (InputYN("您是否需要取款？")) {
				money = InputInt("请输入取款金额");
				return new BankOperation(BankOpType.WITHDRAW, money);
			}
		}
		return new BankOperation(BankOpType.QUIT, 0);
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
		// TODO Auto-generated method stub
		return null;
	}

}
