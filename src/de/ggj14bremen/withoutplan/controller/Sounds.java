package de.ggj14bremen.withoutplan.controller;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import de.ggj14bremen.withoutplan.MainActivity;
import de.ggj14bremen.withoutplan.R;
import de.ggj14bremen.withoutplan.model.Settings;

public class Sounds 
{
	private static MediaPlayer mediaPlayer;

	private static SoundPool soundPool;
	

	private static boolean muted = false;

	private static HashMap<Integer, Integer> sounds = new HashMap<Integer, Integer>();
	
	private Sounds()
	{
		
	}
	
	public static void init(Context context)
	{
		mediaPlayer = MediaPlayer.create(context, R.raw.ambient_2);
		//mediaPlayer.setVolume(Settings.getVolume(), Settings.getVolume());
		mediaPlayer.start();
		
    	soundPool 	= new SoundPool(8, AudioManager.STREAM_MUSIC, 0);

    	sounds.put(R.raw.timer_2, soundPool.load(context, R.raw.timer_2, 1));
    	sounds.put(R.raw.timer, soundPool.load(context, R.raw.timer, 1));
    	sounds.put(R.raw.fail_2, soundPool.load(context, R.raw.fail_2, 1));
    	sounds.put(R.raw.spawn, soundPool.load(context, R.raw.spawn, 1));
    	sounds.put(R.raw.player_spawn, soundPool.load(context, R.raw.player_spawn, 1));
    	sounds.put(R.raw.destroy, soundPool.load(context, R.raw.destroy, 1));
    	sounds.put(R.raw.fail, soundPool.load(context, R.raw.fail, 1));
    	sounds.put(R.raw.counter_fade, soundPool.load(context, R.raw.counter_fade, 1));
    	sounds.put(R.raw.movement_5, soundPool.load(context, R.raw.movement_5, 1));
    	sounds.put(R.raw.orientation_3, soundPool.load(context, R.raw.orientation_3, 1));
	}
	public static final void destroy()
	{
		mediaPlayer.release();
		soundPool.release();
	}
	public static void playSound(int resID)
	{
		Integer soundID = sounds.get(resID);
		if(soundID != null) 
		{
			if(!muted)	soundPool.play(soundID, Settings.getVolume(),  Settings.getVolume(), 1, 0, 1f);
		}
		else if(MainActivity.DEBUG) Log.e(MainActivity.TAG, "Error loading sound.");
	}
	public static void playMusic()
	{
		if(!mediaPlayer.isPlaying() && !muted) mediaPlayer.start();
	}
	public static void pauseMusic()
	{
		mediaPlayer.pause();
	}
	public static boolean isMuted()
	{
		return muted;
	}

	public static void setMuted(boolean b)
	{
		Sounds.muted = b;	
	}
}