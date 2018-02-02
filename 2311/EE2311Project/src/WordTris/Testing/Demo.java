/*
 * Project Name: EE2311 Project - WordTris
 * Student Name:
 * Student ID:
 * 
 */
package WordTris.Testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import WordTris.Gem;
import WordTris.Resource.resourceSetting;
import game.GameConsole;

/**
 * Demo for the use of:
 *
 * @author Ray Cheung
 */
public class Demo {

	// create the game console for drawing
	// singleton, always return the same instance
	private GameConsole console = GameConsole.getInstance();

	public Demo() {
		// make the console visible
		console.show();
	}

	public static void main(String[] args) {
		// a more OO approach to write the main method
		Demo game = new Demo();
		game.startGame();
	}

	public void startGame() {
		console.clear();
		// GameConsole.getInstance().drawImage(500, 500, resourceSetting.GetEliminatingGIF());
		
		
		for (int i = 0; i < 20; i++) {
			console.drawSquare(0 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.WHITE);
			console.drawSquare(1 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.RED);
			console.drawSquare(2 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.DARK_GRAY);
			console.drawSquare(3 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.YELLOW);
			console.drawSquare(4 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.GREEN);
			console.drawSquare(5 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.BLUE);
			console.drawSquare(6 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.CYAN);
			console.drawSquare(7 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.GRAY);
			console.drawSquare(8 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.MAGENTA);
			console.drawSquare(9 * GameConsole.SQUARE_SIZE, i * GameConsole.SQUARE_SIZE, Color.PINK);
			console.update();
			console.idle(1);
		}

		BufferedImage img = null;
		//try {
			//img = ImageIO.read(new File("./constant/gemBlue.png"));
		//	e.printStackTrace();
		//}
		// console.drawImage(500, 150, img);

		Image focus = img;
		console.drawImage(500, 150, focus);

		Gem temp = null;
		try {
			//temp = new Gem("constant/gemYello.png", 5, 8);
		} catch (Exception e) {
			console.drawText(350, 250, e.toString(), new Font("Helvetica", Font.BOLD, 20), Color.white);
		}
		try {
			//temp.display();
		} catch (Exception e) {
			console.drawText(350, 280, e.toString(), new Font("Helvetica", Font.BOLD, 20), Color.white);
		}

		console.drawText(250, 150, "[TIME]", new Font("Helvetica", Font.BOLD, 20), Color.white);
		console.drawText(250, 180, "00:05:32", new Font("Helvetica", Font.PLAIN, 20), Color.white);
		console.drawText(250, 250, "[SCORE]", new Font("Helvetica", Font.BOLD, 20), Color.white);
		console.drawText(250, 280, "220", new Font("Helvetica", Font.PLAIN, 20), Color.white);
		console.update();

		// the idle time affects the no. of iterations per second which
		// should be larger than the frame rate
		// for fps at 25, it should not exceed 40ms
		console.idle(40);
	}
}
