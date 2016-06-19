package views;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import entity.Date;

@SuppressWarnings("serial")
public class DatePanel extends JPanel {

	private JLabel lblDate, lblDay;

	public DatePanel() {
		init();
	}

	public void updateInfo(Date d) {
		lblDate.setText(String.format("%d/%d/%d", d.getYear(), d.getMonth(), d.getDay()));
		lblDay.setText(d.getDayOfWeek());
	}

	private void init() {
		this.setLayout(new GridLayout(2, 0, 0, 0));
		this.setBorder(new TitledBorder("游戏信息"));
		lblDate = new JLabel();
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay = new JLabel();
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblDate);
		this.add(lblDay);
	}

}