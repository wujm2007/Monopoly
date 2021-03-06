package biz_GUI;

import java.awt.EventQueue;
import entity.Game;
import views.GameFrame;

public class Main {

	static Game g = new Game(2, true, IOHelper_GUI.getInstance());

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame window = new GameFrame(g);
					g.setGameFrame(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
