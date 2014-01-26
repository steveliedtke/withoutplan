package de.ggj14bremen.withoutplan.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.SystemClock;
import android.util.Log;
import de.ggj14bremen.withoutplan.R;
import de.ggj14bremen.withoutplan.event.CellClicked;
import de.ggj14bremen.withoutplan.model.Board;
import de.ggj14bremen.withoutplan.model.Cell;
import de.ggj14bremen.withoutplan.model.Figure;
import de.ggj14bremen.withoutplan.model.Settings;
import de.ggj14bremen.withoutplan.model.Figure.Orientation;
import de.ggj14bremen.withoutplan.model.GameBoard;
import de.ggj14bremen.withoutplan.model.GameState;
import de.ggj14bremen.withoutplan.model.WPColor;
import de.ggj14bremen.withoutplan.util.Generator;

public class GameThread extends Thread implements Game{

	private static final int PAUSE_TIME = 1500;

	private final long SLEEP_TIME = 100L;
	
	private boolean running;
	
	private GameState state;
	
	private GameBoard board;
	
	private boolean reset = false;
	
	private Settings settings;
	
	private Settings newSettings;
	
	private List<Figure> figures;
	
	private int[] figureTurn;
	
	private int figureStep;
	
	private TimeScoreInfo timeScoreInfo;
	
	/**
	 * should be changed to true if end of state 
	 * is reached through timer or through event.
	 */
	private boolean next;
	
	public GameThread(Settings gameSettings){
		settings = gameSettings;
		running = true;
		board = new GameBoard(settings.getBoardSizeX(), settings.getBoardSizeY());
		next = false;
		figures = new ArrayList<Figure>(settings.getAmountFigures());
		figureStep = 0;
		this.state = GameState.INIT;
		figureTurn = new int[settings.getAmountFigures()];
		this.timeScoreInfo = new TimeScoreInfo(settings.getStepTime(), 0);
		this.start();
	}
	
	private void reinit(){
		this.settings = newSettings;
		this.reset = false;
		running = true;
		board = new GameBoard(settings.getBoardSizeX(), settings.getBoardSizeY());
		next = false;
		figures = new ArrayList<Figure>(settings.getAmountFigures());
		figureStep = 0;
		this.state = GameState.INIT;
		figureTurn = new int[settings.getAmountFigures()];
		this.timeScoreInfo = new TimeScoreInfo(settings.getStepTime(), 0);
		this.randomizeFigureTurn();
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
	private long lastSecondsRemaining = 0L;
	
	@Override
	public void run(){
		this.randomizeFigureTurn();
		while(running){
			
			boolean skipSwitch = false;
			
			if(reset){
				this.reinit();
			}else{
				if(this.state == GameState.MOVE || this.state == GameState.ORIENTATE){
					long newTime = SystemClock.elapsedRealtime();
					if(this.timeScoreInfo.reduceStepTime(newTime-time)){
						this.nextState(true);
						skipSwitch = true;
						this.board.moveFigure(this.getCurrentFigure(), this.getCurrentFigure().getX(), this.getCurrentFigure().getY());
						this.board.orientateFigure(this.getCurrentFigure(), this.getCurrentFigure().getOrientation());
					}
					final long secondsRemaining = timeScoreInfo.getStepTime()/1000;
					if(secondsRemaining!=lastSecondsRemaining){
						if(secondsRemaining==0L){
			    			 Sounds.playSound(R.raw.timer);
			    		 }else if(secondsRemaining<=3L){
			    			 Sounds.playSound(R.raw.timer_2);
			    		 }
			    		 lastSecondsRemaining = secondsRemaining;
					}
					time = newTime;
				}
				
				if(!skipSwitch){
				
					switch(this.state){
					case INIT:
						this.initFigures();
						final int amountEnemies = Generator.randomIntBetween(1, 3);
						this.timeScoreInfo.setInfoText("Figures created!");
						Sounds.playSound(R.raw.player_spawn);
						this.sleepFor(PAUSE_TIME);
						this.spawnEnemies(amountEnemies);
						this.next = true;
						this.timeScoreInfo.setInfoText("Enemies spawned");
						this.sleepFor(PAUSE_TIME);
						time = SystemClock.elapsedRealtime();
						break;
					case MOVE:
						this.board.showMoveTarget(this.getCurrentFigure());
						this.timeScoreInfo.setInfoText("Turn of Figure " + (this.figureTurn[this.figureStep]+1));
						break;
					case ORIENTATE:
						this.board.showOrientation(this.getCurrentFigure());
						break;
					case ANALYSIS:
						this.analyseRound();
						this.next = true;
						// TODO show info of analysis result
						this.timeScoreInfo.setInfoText("Analysed board!");
						this.sleepFor(PAUSE_TIME);
						break;
					case SPAWN:
						this.spawnEnemies(Generator.randomIntBetween(0, 2));
						// TODO show info of spawned enemies
						this.timeScoreInfo.setInfoText("Enemies spawned");
						this.sleepFor(PAUSE_TIME);
						this.next = true;
						break;
					case END:
						// TODO show end monitor
						this.running = false;
					}
					
				}
			}
			sleepFor(SLEEP_TIME);
			
			if(!skipSwitch && next){
				this.nextState(false);
				this.next = false;
			}
		}
	}

	private void sleepFor(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Log.e("GameThread", e.getMessage());
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
			this.timeScoreInfo.setInfoText("");
			this.timeScoreInfo.setTimeShowed(true);
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
			time = SystemClock.elapsedRealtime();
			this.timeScoreInfo.setTimeShowed(true);
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
			this.timeScoreInfo.setTimeShowed(false);
		}else{
			this.state = GameState.MOVE;
		}
		this.timeScoreInfo.setStepTime(settings.getStepTime());
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
		final Cell[][] cells = this.board.getCells();
		
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[i].length;j++){
				final Cell cell = cells[i][j];
				if(cell.hasEnemy()){
					if(!cell.getWatchingFigures().isEmpty()){
						final Set<WPColor> colors = new HashSet<WPColor>();
						for(final Figure figure : cell.getWatchingFigures()){
							colors.add(figure.getColor());
						}
						
						this.checkPotentialKill(cells, i, j, colors);
					}
				}
			}
		}
		
		boolean darkerSound = false;
		boolean blackoutSound = false;
		for(int i=0;i<cells.length;i++){
			for(int j=0;j<cells[i].length;j++){
				final Cell cell = cells[i][j];
				if(cell.hasEnemy() && cell.getEnemy().isAlive()){
					cell.getEnemy().increaseAge();
					if(cell.getEnemy().isAlive()){
						darkerSound = true;
					}else{
						blackoutSound = true;
					}
				}
			}
		}
		
		if(blackoutSound){
			Sounds.playSound(R.raw.fail);
		}else if(darkerSound){
			Sounds.playSound(R.raw.counter_fade);
		}
	}

	private void checkPotentialKill(Cell[][] cells, int x, int y,
			Set<WPColor> colors) {
		boolean playSound = false;
		for(int i=x-1;i<=x+1;i++){
			for(int j=y-1;j<=y+1;j++){
				if(this.lookForFigure(cells, i, j)){
					final Figure figure = cells[i][j].getFigure();
					if(colors.contains(figure.getColor().getContrary())){
						this.board.removeEnemy(i,j);
						this.timeScoreInfo.addScore();
						playSound = true;
					}
				}
			}
		}
		if(playSound)
			Sounds.playSound(R.raw.destroy);
	}
	

	private boolean lookForFigure(Cell[][] cells, int x, int y) {
		final boolean result;
		if(x<0 || x>=settings.getBoardSizeX() || y<0 || y>=settings.getBoardSizeY()){
			result = false;
		}else{
			result = (cells[x][y].getFigure() != null);
		}
		return result;
	}

	private void spawnEnemies(int amountEnemies) {
		boolean soundForSpawn = false;
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
			soundForSpawn = true;
		}
		if(soundForSpawn){
			Sounds.playSound(R.raw.spawn);
		}
	}

	@Override
	public Board getBoard() {
		return this.board;
	}

	@Override
	public TimeScoreInfo getTimeScoreInfo() {
		return this.timeScoreInfo;
	}

	@Override
	public void dispatchEvent(CellClicked event) {
		switch(this.state){
		case MOVE:
			if(this.board.getCell(event.getX(), event.getY()).isWalkable()){
				this.board.moveFigure(this.getCurrentFigure(), event.getX(), event.getY());
				Sounds.playSound(R.raw.movement);
				this.nextState(false);
			}
			break;
		case ORIENTATE:
			if(this.board.getCell(event.getX(), event.getY()).isVisible()){
				final Orientation orientation;
				if(event.getX()<this.getCurrentFigure().getX()){
					orientation = Orientation.RIGHT;
				}else if(event.getX()>this.getCurrentFigure().getX()){
					orientation = Orientation.LEFT;
				}else if(event.getY()<this.getCurrentFigure().getY()){
					orientation = Orientation.BOTTOM;
				}else if(event.getY()>this.getCurrentFigure().getY()){
					orientation = Orientation.TOP;
				}else{
					orientation = this.getCurrentFigure().getOrientation();
				}
				this.board.orientateFigure(this.getCurrentFigure(), orientation);
				this.nextState(false);
			}
			break;
			default:
				// not allowed
		}
	}

	public void reset(final Settings settings)
	{
		reset = true;
		newSettings = settings;
	}
}
