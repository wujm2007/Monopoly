package entity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JPanel;

import view.BankPanel;
import view.CardSpotPanel;
import view.CellPanel;
import view.EstatePanel;
import view.NewsSpotPanel;
import view.RoadPanel;
import view.StorePanel;
import view.TicketSpotPanel;

public class MapGUI extends Map {

	private static final String STR_MAP = "5水星街,1,5金星街,3,5火星街,2,5木星街,1,5土星街,5,7,7,7,7,7,7";

	private static CellPanel cellPanels[] = { new EstatePanel(), new EstatePanel(), new EstatePanel(),
			new EstatePanel(), new EstatePanel(), new StorePanel(), new EstatePanel(), new EstatePanel(),
			new EstatePanel(), new EstatePanel(), new EstatePanel(), new NewsSpotPanel(), new EstatePanel(),
			new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(), new BankPanel(),
			new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(),
			new StorePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(), new EstatePanel(),
			new EstatePanel(), new CardSpotPanel(), new TicketSpotPanel(), new TicketSpotPanel(), new TicketSpotPanel(),
			new TicketSpotPanel(), new TicketSpotPanel(), new TicketSpotPanel() };

	private static RoadPanel roadPanels[] = { new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(), new RoadPanel(),
			new RoadPanel(), new RoadPanel() };

	private static int mapl[][] = { { 1, 3 }, { 1, 2 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 9, 1 }, { 10, 1 },
			{ 10, 4 }, { 11, 4 }, { 12, 4 }, { 13, 3 }, { 16, 3 }, { 18, 4 }, { 18, 5 }, { 18, 6 }, { 18, 7 },
			{ 16, 8 }, { 15, 9 }, { 14, 9 }, { 14, 6 }, { 13, 6 }, { 12, 6 }, { 9, 6 }, { 11, 9 }, { 10, 11 },
			{ 9, 11 }, { 8, 11 }, { 7, 11 }, { 4, 9 }, { 3, 10 }, { 2, 10 }, { 1, 8 }, { 1, 7 }, { 1, 6 }, { 1, 5 } };

	private static int roadl[][] = { { 2, 3 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 7, 2 }, { 9, 2 }, { 10, 2 },
			{ 10, 3 }, { 11, 3 }, { 12, 3 }, { 14, 3 }, { 16, 4 }, { 17, 4 }, { 17, 5 }, { 17, 6 }, { 17, 7 },
			{ 17, 9 }, { 15, 8 }, { 14, 8 }, { 14, 7 }, { 13, 7 }, { 12, 7 }, { 10, 7 }, { 10, 9 }, { 10, 10 },
			{ 9, 10 }, { 8, 10 }, { 7, 10 }, { 5, 10 }, { 3, 9 }, { 2, 9 }, { 2, 8 }, { 2, 7 }, { 2, 6 }, { 2, 5 } };

	public MapGUI(Game game) {
		super(game);

		BufferedReader r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(STR_MAP.getBytes())));
		String line = null;
		try {
			line = r.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] cells = line.split(",");
		int count = 0;
		for (String c : cells) {
			switch (c) {
			case "1":
				addCell(new Cell(this, count++, new Store()));
				break;
			case "2":
				addCell(new Cell(this, count++, new Bank()));
				break;
			case "3":
				addCell(new Cell(this, count++, new NewsSpot()));
				break;
			case "4":
				addCell(new Cell(this, count++, new LotterySpot()));
				break;
			case "5":
				addCell(new Cell(this, count++, new CardSpot()));
				break;
			case "6":
				addCell(new Cell(this, count++, new EmptySpot()));
				break;
			case "7":
				addCell(new Cell(this, count++, new TicketSpot()));
				break;
			default:
				int num = 0;
				try {
					num = Integer.parseInt(c.substring(0, 1));
				} catch (Exception e) {
					break;
				}
				for (int i = 0; i < num; i++) {
					Estate e = new Estate(c.substring(1), i + 1, 1.0);
					addCell(new Cell(this, count, e));
					e.setPanel((EstatePanel) cellPanels[count]);
					count++;
				}
				break;
			}
		}
	}

	public void init(JPanel mapPanel) {
		for (int i = 0; i < cellPanels.length; i++) {
			cellPanels[i].setLocation(mapl[i][0], mapl[i][1]);
			roadPanels[i].setLocation(roadl[i][0], roadl[i][1], 0);
			mapPanel.add(cellPanels[i]);
			mapPanel.add(roadPanels[i]);
		}
	}

	public void addPlayer(Player p, int pos) {
		roadPanels[pos].setPlayerAvatar(p);
	}

	public void addRoadBlock(int pos) {
		roadPanels[pos].setRoadBlock();
	}

	public void removeRoadBlock(int pos) {
		roadPanels[pos].clear();
	}

	public void removePlayer(Player p, int pos) {
		roadPanels[pos].clear();
		cellPanels[pos].repaint();
		if (getCells().get(pos).getAnotherPlayer(p) != null)
			roadPanels[pos].setPlayerAvatar(getCells().get(pos).getAnotherPlayer(p));
	}
}