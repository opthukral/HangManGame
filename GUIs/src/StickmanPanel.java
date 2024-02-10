import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

class StickmanPanel extends JPanel {
	private int partsDrawn;
	private int lives;

	public StickmanPanel(int lives) {
		partsDrawn = 0;
		this.lives = lives;

	}

	public void updateStickman(int remainingLives) {
		partsDrawn = 6 - remainingLives;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		int centerX = width / 2;
		int startY = height / 6 + 100; // Adjust the startY value by adding 300 pixels
		
		Stroke stroke = new BasicStroke(5f);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(stroke);
		switch (lives) {
		case 6:
			switch (partsDrawn) {
			case 1:
				drawHead(g2, centerX, startY);
				break;
			case 2:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				break;
			case 3:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				break;
			case 4:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				break;
			case 5:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				drawLeftArm(g2, centerX, startY + 30);

				break;
			case 6:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				drawLeftArm(g2, centerX, startY + 30);
				drawRightArm(g2, centerX, startY + 30);

				break;
			}

		}
		switch (lives) {
		case 5:
			switch (partsDrawn) {
			case 2:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				break;
			case 3:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				break;
			case 4:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				break;
			case 5:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				drawLeftArm(g2, centerX, startY + 30);
				break;
			case 6:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				drawLeftArm(g2, centerX, startY + 30);
				drawRightArm(g2, centerX, startY + 30);

			}
		}
		switch (lives) {
		case 4:
			switch (partsDrawn) {
			case 3:
				drawHead(g2, centerX, startY);
				break;
			case 4:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				break;
			case 5:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				break;
			case 6:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				drawLeftArm(g2, centerX, startY + 30);
				drawRightArm(g2, centerX, startY + 30);
				break;
			}
		}
		switch (lives) {
		case 3:
			switch (partsDrawn) {
			case 4:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				break;
			case 5:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				break;
			case 6:
				drawHead(g2, centerX, startY);
				drawBody(g2, centerX, startY + 40);
				drawLeftLeg(g2, centerX, startY + 100);
				drawRightLeg(g2, centerX, startY + 100);
				drawLeftArm(g2, centerX, startY + 30);
				drawRightArm(g2, centerX, startY + 30);
				break;

			}
		}

	}

	private void drawHead(Graphics g, int x, int y) {
		g.drawOval(x - 20, y, 40, 40);
	}

	private void drawBody(Graphics g, int x, int y) {
		g.drawLine(x, y, x, y + 70);
	}

	private void drawLeftLeg(Graphics g, int x, int y) {
		g.drawLine(x, y + 10, x - 20, y + 50);
	}

	private void drawRightLeg(Graphics g, int x, int y) {
		g.drawLine(x, y + 10, x + 20, y + 50);
	}

	private void drawLeftArm(Graphics g, int x, int y) {
		g.drawLine(x, y + 10, x - 20, y + 50);
	}

	private void drawRightArm(Graphics g, int x, int y) {
		g.drawLine(x, y + 10, x + 20, y + 50);
	}
}