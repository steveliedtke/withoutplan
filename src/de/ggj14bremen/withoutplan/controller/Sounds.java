package de.ggj14bremen.withoutplan.controller;

import java.io.IOException;
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

	private static HashMap<Integer, Integer> sounds = new HashMap<Integer, Integer>();
	
	private Sounds()
	{
		
	}
	
	public static void init(Context context)
	{
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		try
		{
			mediaPlayer.prepare();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			if(!Settings.isMuted())	soundPool.play(soundID, Settings.getVolume(),  Settings.getVolume(), 1, 0, 1f);
		}
		else if(MainActivity.DEBUG) Log.e(MainActivity.TAG, "Error loading sound.");
	}
}