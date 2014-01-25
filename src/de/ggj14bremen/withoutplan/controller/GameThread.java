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
	
	/**
	 * should be changed to true if end of state 
	 * is reached through timer or through event.
	 */
	private boolean next;
	
	public GameThread(GameSettings gameSettings){
		settings = gameSettings;
		running = true;
		board = new GameBoard();
		next = false;
	}
	
	@Override
	public void run(){
		while(running){
		
			switch(this.state){
			case INIT:
				this.board.init(settings.getBoardSizeX(), settings.getBoardSizeY());
				final int amountEnemies = Generator.randomIntBetween(1, 3);
				this.spawnEnemies(amountEnemies);
				this.next = true;
				// TODO maybe implement small sleep for user
				break;
			case MOVE:
				// TODO show move options
				break;
			case ORIENTATE:
				// TODO show orientation options
				break;
			case ANALYSIS:
				this.analyseRound();
				this.next = true;
				// TODO maybe implement small sleep for user
				break;
			case SPAWN:
				this.spawnEnemies(Generator.randomIntBetween(0, 2));
				break;
			case END:
				// TODO show end monitor
				this.running = false;
			}
			
			if(next){
				this.nextState();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Log.e("GameThread", e.getMessage());
			}
		}
	}

	private void nextState() {
		switch(this.state){
		case INIT:
			this.state = GameState.MOVE;
			// TODO which figure ?
			break;
		case MOVE:
			this.state = GameState.ORIENTATE;
			break;
		case ORIENTATE:
			// TODO have all players orientated/moved?
			// TODO otherwise which figure ?
			break;
		case ANALYSIS:
			this.state = GameState.SPAWN;
			// TODO if game ended this.state = GameState.END
			break;
		case SPAWN:
			this.state = GameState.MOVE;
			// TODO which figure?
			break;
		case END:
			// TODO show end monitor
			this.running = false;
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
