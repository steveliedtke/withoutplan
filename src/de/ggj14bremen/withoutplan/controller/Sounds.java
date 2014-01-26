package de.ggj14bremen.withoutplan.controller;

import de.ggj14bremen.withoutplan.R;
import android.content.Context;
import android.media.MediaPlayer;

public class Sounds {
	
	private MediaPlayer tick;
	
	private MediaPlayer finalTick;
	
	private MediaPlayer spawn;
	
	private MediaPlayer destroyed;
	
	private MediaPlayer blackout;
	
	private MediaPlayer darker;
	
	private MediaPlayer move;

	public Sounds(Context context){
		this.tick = MediaPlayer.create(context, R.raw.beep);
		this.finalTick = MediaPlayer.create(context, R.raw.fail_2);
		this.spawn = MediaPlayer.create(context, R.raw.spawn); // or spawn_2
		this.destroyed = MediaPlayer.create(context, R.raw.destroy);
		this.blackout = MediaPlayer.create(context, R.raw.fail); // or fail_2
		this.darker = MediaPlayer.create(context, R.raw.counter_fade);
		this.move = MediaPlayer.create(context, R.raw.movement); // movement_2
	}
	
	public void tick(){
		tick.start();
	}
	
	public void finalTick(){
		finalTick.start();
	}
	
	public void enemySpawned(){
		this.spawn.start();
	}
	
	public void start(){
		// TODO
	}
	
	public void enemyDestroyed(){
		this.destroyed.start();
	}
	
	public void nextFigure(){
		// TODO 
	}
	
	public void darker(){
		this.darker.start();
	}
	
	public void blackout(){
		this.blackout.start();
	}
	
	public void move(){
		this.move.start();
	}
}
