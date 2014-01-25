package de.ggj14bremen.withoutplan.controller;

import de.ggj14bremen.withoutplan.R;
import android.content.Context;
import android.media.MediaPlayer;

public class Sounds {
	
	private MediaPlayer tick;
	
	private MediaPlayer finalTick;

	public Sounds(Context context){
		this.tick = MediaPlayer.create(context, R.raw.beep);
		this.finalTick = MediaPlayer.create(context, R.raw.fail_2);
	}
	
	public void tick(){
		tick.start();
	}
	
	public void finalTick(){
		finalTick.start();
	}
}
