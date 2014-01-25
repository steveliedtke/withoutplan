package de.ggj14bremen.withoutplan.controller;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import de.ggj14bremen.withoutplan.GameSettings;
import de.ggj14bremen.withoutplan.model.Board;
import de.ggj14bremen.withoutplan.model.Figure;
import de.ggj14bremen.withoutplan.model.GameBoard;
import de.ggj14bremen.withoutplan.model.GameState;
import de.ggj14bremen.withoutplan.util.Generator;

public class GameThread extends Thread implements Game{

	private boolean running;
	
	private GameState state;
	
	private GameBoard board;
	
	private GameSettings settings;
	
	private List<Figure> figures;
	
	private int[] figureTurn;
	
	private int figureStep;
	
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
		figures = new ArrayList<Figure>(settings.getAmountFigures());
		figureStep = 0;
		figureTurn = new int[settings.getAmountFigures()];
		// TODO maybe change the following code later ?!
		for(int i=0; i<settings.getAmountFigures(); i++){
			figureTurn[0] = i;
		}
	}
	
	@Override
	public void run(){
		while(running){
		
			switch(this.state){
			case INIT:
				this.board.init(settings.getBoardSizeX(), settings.getBoardSizeY());
				this.initFigures();
				final int amountEnemies = Generator.randomIntBetween(1, 3);
				this.spawnEnemies(amountEnemies);
				this.next = true;
				// TODO maybe implement small sleep for user
				break;
			case MOVE:
				this.board.showMoveTarget(this.getCurrentFigure());
				break;
			case ORIENTATE:
				this.board.showOrientation(this.getCurrentFigure());
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

	private void initFigures() {
		for(int i=0; i<settings.getAmountFigures(); i++){
			final Figure figure = new Figure();
			// TODO set color
			// TODO set other attributes ?!
			figures.add(figure);
			final int x = Generator.randomIntBetween(0, settings.getBoardSizeX()-1);
			final int y = Generator.randomIntBetween(0, settings.getBoardSizeY()-1);
			// TODO check if cell already has a figure
			this.board.moveFigure(figure, x, y);
		}
	}

	private void nextState() {
		switch(this.state){
		case INIT:
			this.state = GameState.MOVE;
			break;
		case MOVE:
			this.state = GameState.ORIENTATE;
			break;
		case ORIENTATE:
			final boolean allFiguresMoved = nextFigure();
			if(allFiguresMoved){
				this.state= GameState.ANALYSIS;
			}else{
				this.state = GameState.MOVE;
			}
			break;
		case ANALYSIS:
			this.state = GameState.SPAWN;
			// TODO if game ended this.state = GameState.END
			break;
		case SPAWN:
			this.state = GameState.MOVE;
			break;
		case END:
			// TODO show end monitor
			this.running = false;
		}
	}
	
	private Figure getCurrentFigure(){
		final int index = figureTurn[figureStep];
		return figures.get(index);
	}
	
	private boolean nextFigure(){
		final boolean allFiguresMoved;
		if(figureStep+1>=settings.getAmountFigures()){
			figureStep = 0;
			// TODO maybe randomize figureTurn here
			allFiguresMoved = true;
		}else{
			figureStep++;
			allFiguresMoved = false;
		}
		
		return allFiguresMoved;
		
	}

	private void analyseRound() {
		// TODO analyse if enemy gets removed
		// TODO check if game is over
	}

	private void spawnEnemies(int amountEnemies) {
		for(int i=0; i<amountEnemies;i++){
			final int x = Generator.randomIntBetween(0, this.settings.getBoardSizeX()-1);
			final int y = Generator.randomIntBetween(0, this.settings.getBoardSizeY()-1);
			// TODO check if cell has already an enemy or figure
			this.board.spawnEnemy(x, y);
		}
	}

	@Override
	public Board getBoard() {
		return board;
	}
	
	//TODO event to stop thread
}
