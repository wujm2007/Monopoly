package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import entity.Cell;
import entity.Player;
import entity.buildings.Estate;

@SuppressWarnings("serial")
public class RoadPanel extends JPanel {
	private static final int HEIGHT = 1;
	private static final int WIDTH = 1;
	private transient Image image;
	private Cell cell;
	private CellPanel cellPanel;

	protected void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public void setLocation(int x, int y) {
		this.setBorder(new LineBorder(Color.BLACK));
		setBounds(35 * x - 30, 35 * y - 30, WIDTH * 30, HEIGHT * 30);
	}

	public void setPlayerAvatar(Player p) {
		if (p == null)
			image = null;
		else
			image = p.getAvatar();
		refresh();
	}

	public void setRoadBlock() {
		image = (new ImageIcon("./image/barrage.png")).getImage();
		repaint();
	}

	public void clear() {
		image = null;
		refresh();
		cellPanel.refresh();
	}

	public void refresh() {
		paintImmediately(0, 0, this.getWidth(), this.getHeight());
		if ((this.cellPanel != null) && ((this.cell.getLastPlayer() == null) && (!this.cell.isBlocked()))
				|| (this.cellPanel instanceof EstatePanel))
			this.cellPanel.refresh();
	}

	public void setCell(Cell cell) {
		this.cell = cell;
		if (this.cell.getBuilding() instanceof Estate)
			((Estate) this.cell.getBuilding()).setPanel((EstatePanel) this.cellPanel);
		this.setPlayerAvatar(this.cell.getLastPlayer());
		if (cell.isBlocked())
			this.setRoadBlock();
		this.cell.setRoadPanel(this);
	}

	public void setCellPanel(CellPanel cellPanel) {
		this.cellPanel = cellPanel;
	}
}