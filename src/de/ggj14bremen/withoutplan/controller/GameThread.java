package de.ggj14bremen.withoutplan.controller;

import de.ggj14bremen.withoutplan.GameSettings;
import de.ggj14bremen.withoutplan.model.Board;
import de.ggj14bremen.withoutplan.model.GameBoard;
import de.ggj14bremen.withoutplan.model.GameState;

public class GameThread extends Thread implements Game{

	private boolean running;
	
	private GameState state;
	
	private GameBoard board;
	
	public GameThread(GameSettings gameSettings){
		running = true;
		board = new GameBoard();
	}
	
	@Override
	public void run(){
		while(running){
		
			switch(this.state){
			case INIT:
				this.board.init();
				break;
			case MOVE:
				// TODO show move options
				break;
			case ORIENTATE:
				// TODO show orientation options
				break;
			case ANALYSIS:
				// TODO analyse board
				break;
			case SPAWN:
				
				break;
			case END:
				// TODO show end monitor
			}
		}
	}

	@Override
	public Board getBoard() {
		return board;
	}
	
	//TODO event to stop thread
}
