package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import biz_GUI.IOHelper_GUI;
import entity.Game;
import entity.Player;
import entity.StockMarket;
import entity.StockMarket.StockTradeOperation;

@SuppressWarnings("serial")
class StocksTableModel extends DefaultTableModel {
	public StocksTableModel(Vector<Vector<String>> vector, Vector<String> genertateColNames) {
		super(vector, genertateColNames);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}

@SuppressWarnings("serial")
public class StocksDialog extends JFrame {
	JTable table;

	List<Player> players;
	private StockMarket stockMarket;

	public StocksDialog(Player p) {
		Game g = p.getGame();
		players = g.getPlayers(true);
		stockMarket = g.getStockMarket();
		table = new JTable();
		JScrollPane src = new JScrollPane(table);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
				if (index != -1) {
					StockTradeOperation op = ((IOHelper_GUI) g.io()).inputStockOp(stockMarket.getStock(index + 1));
					stockMarket.trade(op, p);
					refresh();
				}
			}
		});
		this.refresh();
		this.add(src);
		this.setTitle("股票交易");
		this.setSize(800, 300);
		this.setResizable(false);
		this.setVisible(true);
	}

	public Vector<String> genertateColNames() {
		List<String> cols = new ArrayList<String>() {
			{
				add("股票名");
				add("成交价");
				add("涨跌");
			}
		};
		players.stream().forEach(p -> {
			cols.add(p.getName() + "持股");
			cols.add(p.getName() + "平均成本");
		});
		return new Vector<String>(cols);
	}

	public Vector<Vector<String>> genertateData() {
		Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
		stockMarket.getStocks().forEach(s -> {
			dataVector.addElement(stockMarket.generateStackTableRow(s));
		});
		return dataVector;
	}

	public void refresh() {
		StocksTableModel model = new StocksTableModel(genertateData(), genertateColNames());
		table.setModel(model);
		table.repaint();
	}
}