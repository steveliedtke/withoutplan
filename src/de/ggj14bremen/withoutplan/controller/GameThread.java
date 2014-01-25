package de.ggj14bremen.withoutplan.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.SystemClock;
import android.util.Log;
import de.ggj14bremen.withoutplan.GameSettings;
import de.ggj14bremen.withoutplan.event.CellClicked;
import de.ggj14bremen.withoutplan.model.Board;
import de.ggj14bremen.withoutplan.model.Cell;
import de.ggj14bremen.withoutplan.model.Figure;
import de.ggj14bremen.withoutplan.model.Figure.Orientation;
import de.ggj14bremen.withoutplan.model.GameBoard;
import de.ggj14bremen.withoutplan.model.GameState;
import de.ggj14bremen.withoutplan.model.WPColor;
import de.ggj14bremen.withoutplan.util.Generator;

public class GameThread extends Thread implements Game{

	private final long SLEEP_TIME = 100L;
	
	private boolean running;
	
	private GameState state;
	
	private GameBoard board;
	
	private GameSettings settings;
	
	private List<Figure> figures;
	
	private int[] figureTurn;
	
	private int figureStep;
	
	private TimeAndScore timeAndScore;
	
	/**
	 * should be changed to true if end of state 
	 * is reached through timer or through event.
	 */
	private boolean next;
	
	public GameThread(GameSettings gameSettings){
		settings = gameSettings;
		running = true;
		board = new GameBoard(settings.getBoardSizeX(), settings.getBoardSizeY());
		next = false;
		figures = new ArrayList<Figure>(settings.getAmountFigures());
		figureStep = 0;
		this.state = GameState.INIT;
		figureTurn = new int[settings.getAmountFigures()];
		this.timeAndScore = new TimeAndScore(settings.getStepTime(), 0);
		this.start();
	}

	private void randomizeFigureTurn() {
		int index = 0;
		final Set<Integer> alreadyInserted = new HashSet<Integer>();
		while(index<settings.getAmountFigures()){
			final int element = Generator.randomIntBetween(0, settings.getAmountFigures()-1);
			if(!alreadyInserted.contains(Integer.valueOf(element))){
				alreadyInserted.add(Integer.valueOf(element));
				figureTurn[index] = element;
				index++;
			}
		}
	}
	
	private long time;
	
	@Override
	public void run(){
		this.randomizeFigureTurn();
		time = SystemClock.elapsedRealtime();
		while(running){
			
			long newTime = SystemClock.elapsedRealtime();
			if((this.state == GameState.MOVE || this.state == GameState.ORIENTATE) 
					&& this.timeAndScore.reduceStepTime(newTime-time)){
				this.nextState(true);
				continue;
			}
			time = newTime;
			
			switch(this.state){
			case INIT:
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
				this.next = true;
				break;
			case END:
				// TODO show end monitor
				this.running = false;
			}
			
			if(next){
				this.nextState(false);
				this.next = false;
			}
			
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				Log.e("GameThread", e.getMessage());
			}
		}
	}

	private void initFigures() {
		for(int i=0; i<settings.getAmountFigures(); i++){
			final Figure figure = new Figure();
			figure.setColor(WPColor.values()[i%3]);
			figure.setOrientation(Orientation.values()[Generator.randomIntBetween(0, 3)]);
			figures.add(figure);
			boolean cellNotFound = true;
			while(cellNotFound){
				final int x = Generator.randomIntBetween(0, settings.getBoardSizeX()-1);
				final int y = Generator.randomIntBetween(0, settings.getBoardSizeY()-1);
				if(this.board.getCell(x, y).getFigure()==null){
					this.board.moveFigure(figure, x, y);
					cellNotFound = false;
				}
			}
		}
	}

	private void nextState(boolean timerFinished) {
		switch(this.state){
		case INIT:
			this.state = GameState.MOVE;
			break;
		case MOVE:
			if(timerFinished){
				afterOrientate();
			}else{
				this.state = GameState.ORIENTATE;
			}
			break;
		case ORIENTATE:
			afterOrientate();
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

	private void afterOrientate() {
		final boolean allFiguresMoved = nextFigure();
		if(allFiguresMoved){
			this.state= GameState.ANALYSIS;
		}else{
			this.state = GameState.MOVE;
		}
		this.timeAndScore.setStepTime(settings.getStepTime());
	}
	
	private Figure getCurrentFigure(){
		final int index = figureTurn[figureStep];
		return figures.get(index);
	}
	
	private boolean nextFigure(){
		final boolean allFiguresMoved;
		if(figureStep+1>=settings.getAmountFigures()){
			figureStep = 0;
			randomizeFigureTurn();
			allFiguresMoved = true;
		}else{
			figureStep++;
			allFiguresMoved = false;
		}
		
		return allFiguresMoved;
	}

	private void analyseRound() {
		// TODO analyse if enemy gets removed
		// if yes this.timeAndScore.addScore(enemyAmount)
		// TODO check if game is over
	}

	private void spawnEnemies(int amountEnemies) {
		for(int i=0; i<amountEnemies;i++){
			boolean cellNotFound = true;
			while(cellNotFound){
				final int x = Generator.randomIntBetween(0, this.settings.getBoardSizeX()-1);
				final int y = Generator.randomIntBetween(0, this.settings.getBoardSizeY()-1);
				final Cell cell = this.board.getCell(x, y);
				if(cell.getFigure()== null && cell.getEnemy()==null){
					this.board.spawnEnemy(x, y);
					cellNotFound = false;
				}
			}
		}
	}

	@Override
	public Board getBoard() {
		return this.board;
	}

	@Override
	public TimeAndScore getTimeAndScore() {
		return this.timeAndScore;
	}

	@Override
	public void dispatchEvent(CellClicked event) {
		switch(this.state){
		case MOVE:
			if(this.board.getCell(event.getX(), event.getY()).isWalkable()){
				this.board.moveFigure(this.getCurrentFigure(), event.getX(), event.getY());
				this.nextState(false);
			}
			break;
		case ORIENTATE:
			// TODO check if ok
			break;
			default:
				// not allowed
		}
	}
	
	//TODO event to stop thread
}