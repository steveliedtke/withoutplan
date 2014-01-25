package de.ggj14bremen.withoutplan.controller;

import android.util.Log;
import de.ggj14bremen.withoutplan.GameSettings;
import de.ggj14bremen.withoutplan.model.Board;
import de.ggj14bremen.withoutplan.model.GameBoard;
import de.ggj14bremen.withoutplan.model.GameState;
import de.ggj14bremen.withoutplan.util.Generator;

public class GameThread extends Thread implements Game{

	private boolean running;
	
	private GameState state;
	
	private GameBoard board;
	
	private GameSettings settings;
	
	public GameThread(GameSettings gameSettings){
		settings = gameSettings;
		running = true;
		board = new GameBoard();
	}
	
	@Override
	public void run(){
		while(running){
		
			switch(this.state){
			case INIT:
				this.board.init();
				final int amountEnemies = Generator.randomIntBetween(1, 3);
				this.spawnEnemies(amountEnemies);
				break;
			case MOVE:
				// TODO show move options
				break;
			case ORIENTATE:
				// TODO show orientation options
				break;
			case ANALYSIS:
				this.analyseRound();
				break;
			case SPAWN:
				this.spawnEnemies(Generator.randomIntBetween(0, 2));
				break;
			case END:
				// TODO show end monitor
			}
			
			// TODO go into next state
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				Log.e("GameThread", e.getMessage());
			}
		}
	}

	private void analyseRound() {
		// TODO analyse if enemy gets removed
		// TODO check if game is over
	}

	private void spawnEnemies(int amountEnemies) {
		for(int i=0; i<amountEnemies;i++){
			final int x = Generator.randomIntBetween(0, this.settings.getBoardSizeX()-1);
			final int y = Generator.randomIntBetween(0, this.settings.getBoardSizeY()-1);
			// TODO check if cell has already an enemy
			this.board.spawnEnemy(x, y);
		}
	}

	@Override
	public Board getBoard() {
		return board;
	}
	
	//TODO event to stop thread
}
