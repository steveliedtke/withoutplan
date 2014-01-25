package de.ggj14bremen.withoutplan.controller;

import de.ggj14bremen.withoutplan.model.Board;
import de.ggj14bremen.withoutplan.model.GameState;

public class GameThread extends Thread implements Game{

	private boolean running;
	
	private GameState state;
	
	private Board board;
	
	public GameThread(){
		running = true;
		board = new Board();
	}
	
	@Override
	public void run(){
		while(running){
		
			switch(this.state){
			case INIT:
				// TODO Init Board
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
				// TODO spawn new 'enemies'
				break;
			case END:
				// TODO show end monitor
			}
		}
	}
	
	//TODO event to stop thread
}
