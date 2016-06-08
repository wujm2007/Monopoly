package biz_GUI;

import java.util.List;
import javax.swing.JOptionPane;

import entity.Player;
import entity.StockMarket.Stock;
import entity.StockMarket.StockTradeOpType;
import entity.StockMarket.StockTradeOperation;
import entity.buildings.Bank.BankOpType;
import entity.buildings.Bank.BankOperation;
import views.StocksDialog;
import entity.IOHelper;

@SuppressWarnings("serial")
public class IOHelper_GUI extends IOHelper {

	private static IOHelper instance;

	private IOHelper_GUI() {
	};

	public static IOHelper getInstance() {
		if (instance == null) {
			return (instance = new IOHelper_GUI());
		}
		return instance;
	}

	@Override
	public boolean inputYN(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "确认", JOptionPane.YES_NO_OPTION) == 0;
	}

	@Override
	public int inputInt(String msg) {
		try {
			String strInput = JOptionPane.showInputDialog(msg);
			if (strInput == null)
				return 0;
			int intInput = Integer.parseInt(strInput);
			return intInput;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "输入不合法！");
			return inputInt(msg);
		}
	}

	public StockTradeOperation inputStockOp(Stock s) {
		String[] options = { "买入", "卖出" };
		int response = JOptionPane.showOptionDialog(null, "请选择交易类型", "股票交易", JOptionPane.YES_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (response == 0) {
			int num = inputInt("股票: " + s.getName() + "\n单价: " + String.format("%.2f", s.getPrice()) + "\n请输入购买数量(股):");
			if (num > 0)
				return new StockTradeOperation(StockTradeOpType.BUY, s.getIndex(), num);
		} else if (response == 1) {
			int num = inputInt("股票: " + s.getName() + "\n单价: " + String.format("%.2f", s.getPrice()) + "\n请输入卖出数量(股):");
			if (num > 0)
				return new StockTradeOperation(StockTradeOpType.SELL, s.getIndex(), num);
		}
		return new StockTradeOperation(StockTradeOpType.QUIT, 0, 0);
	}

	@Override
	public void stockOperation(Player p) {
		new StocksDialog(p);
	}

	@Override
	public void alert(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public BankOperation getBankOperation(Player p) {
		Object[] options = { "离开", "取款", "存款" };
		int response = JOptionPane.showOptionDialog(null, "欢迎来到银行", "银行", JOptionPane.YES_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (response == 2) {
			int money = inputInt("请输入存款金额");
			if (money > 0)
				return new BankOperation(BankOpType.DEPOSITE, money);
		} else if (response == 1) {
			int money = inputInt("请输入取款金额");
			if (money > 0)
				return new BankOperation(BankOpType.WITHDRAW, money);
		}
		return new BankOperation(BankOpType.QUIT, 0);
	}

	@Override
	public Player chosePlayer(List<Player> playersNear) {
		Object[] players = playersNear.toArray(new Object[playersNear.size()]);
		return (Player) JOptionPane.showInputDialog(null, "请选择玩家", "选择", JOptionPane.PLAIN_MESSAGE, null, players,
				null);
	}

	// methods below havn't been implemented yet
	@Override
	public int getStoreCardIndex(Player p) {
		return 0;
	}

	@Override
	public void showLotteryInfo() {
	}

	@Override
	public int getLotteryOperation(Player p) {
		return -1;
	}

	@Override
	public void showLotteryWinner() {
	}

	@Override
	public void showBankAccountInfo(Player p) {
	}
}
