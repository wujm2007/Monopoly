package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import entity.Game;
import entity.Player;
import entity.StockMarket;

class StocksTableModel extends DefaultTableModel {
	public StocksTableModel(Vector genertateData, Vector genertateColNames) {
		super(genertateData, genertateColNames);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}

public class StocksDialog extends JFrame {
	JTable table;

	List<Player> players;
	private StockMarket stockMarket;

	public StocksDialog(Game g) {
		players = g.getPlayers(true);
		stockMarket = g.getStockMarket();
		table = new JTable();
		JScrollPane src = new JScrollPane(table);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String[] options = { "买入", "卖出" };
				int response = JOptionPane.showOptionDialog(null, "请选择交易类型", "股票交易", JOptionPane.YES_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			}
		});
		this.refresh();
		this.add(src);
		this.setSize(500, 300);
		this.setVisible(true);
	}

	public static void main(String args[]) {
		Game g = new Game(2, false, null);
		new StocksDialog(g);
	}

	public Vector genertateColNames() {
		List cols = new ArrayList() {
			{
				add("股票名");
				add("成交价");
				add("涨跌");
			}
		};
		players.stream().map(Player::getName).forEach(cols::add);
		return new Vector<String>(cols);
	}

	public Vector genertateData() {
		Vector dataVector = new Vector();
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