package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import entity.Player;
import entity.StockMarket.Stock;
import entity.StockMarket.StockTradeOperation;
import entity.buildings.Bank.BankOperation;

@SuppressWarnings("serial")
public abstract class IOHelper implements Serializable {

	// return true if the input is Y and false if the input is N
	public abstract boolean InputYN(String msg);

	// return the integer input
	public abstract int InputInt(String msg);

	public abstract void showStocks(List<Stock> stocks);

	public abstract void printStockAccount(Map<Stock, Integer> account);

	// get StockTradeOperation (see entity.StockMarket.StockTradeOperation)
	public abstract StockTradeOperation InputStockOp();

	// Output the msg
	public abstract void alert(String msg);

	// Output the msg (same as alert() in command line version)
	public abstract void showInfo(String msg);

	public abstract void showBankAccountInfo(Player p);

	// get StockTradeOperation (see entity.Bank.BankOperation)
	public abstract BankOperation getBankOperation(Player p);

	public abstract void showLotteryInfo();

	// return the lottery index bought by p
	public abstract int getLotteryOperation(Player p);

	public abstract void showLotteryWinner();

	public abstract int getStoreCardIndex(Player p);

	public abstract Player chosePlayer(List<Player> playersNear);
}
