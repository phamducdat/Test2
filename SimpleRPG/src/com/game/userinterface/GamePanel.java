package com.game.userinterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.game.state.MenuState;
import com.game.state.State;

// Status: unfinished
// Con ham paint va ham run chua hoan thien

public class GamePanel extends JPanel implements KeyListener, Runnable {

	State gameState;
	
	KeyEventManager keyEventManager;
	
	private Thread thread;
	private boolean isRunning;
	
	public GamePanel() {
		
		gameState = new MenuState(this);
		keyEventManager = new KeyEventManager(gameState);
		
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
	}
	
	public void startGame() {
		if(thread == null) {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
	}
	
	public void setState(State state) {
		this.gameState = state;
		keyEventManager.setState(state);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("You pressed");
		keyEventManager.processKeyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("You released");
		keyEventManager.processKeyReleased(e.getKeyCode());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long FPS = 80;
		long period = 1000000000 / FPS;
		
		long previousTime = System.nanoTime();
		long sleepTime;
		long currentTime;
		
		while(isRunning) {
			//Update
			//Render
			
			repaint();
			currentTime = System.nanoTime();
			
			sleepTime = period - (currentTime - previousTime);
			
			try {
				if(sleepTime > 0)
					thread.sleep(sleepTime/1000000);
				else thread.sleep(period/2000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
