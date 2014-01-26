package de.ggj14bremen.withoutplan.model;

import de.ggj14bremen.withoutplan.MainActivity;
import android.content.Context;
import android.util.Log;

public class Settings {

	private static int boardSizeX = 6;
	
	private static int boardSizeY = 6;
	
	private static int amountFigures = 3;
	
	private static long stepTime = 10000;

	private static boolean muted = false;
	
	private static float volume = 1f;
	
	public Settings(final Context context)
	{
		/*SharedPreferences settings = context.getSharedPreferences("WithoutPlanPreferences", 0);
		this.setBoardSizeX(settings.getInt("boardSizeX", 6));
		this.setBoardSizeY(settings.getInt("boardSizeY", 6));
		this.setAmountFigures(settings.getInt("amountFigures", 3));*/
		//this.setStepTime(settings.getLong("stepTime", 10000));
	}

	public static int getBoardSizeX() {
		return boardSizeX;
	}

	public static void setBoardSizeX(int boardSizeX) {
		Settings.boardSizeX = boardSizeX;
	}

	public static int getBoardSizeY() {
		return boardSizeY;
	}

	public static void setBoardSizeY(int boardSizeY) {
		Settings.boardSizeY = boardSizeY;
	}

	public static  int getAmountFigures() {
		return amountFigures;
	}

	public static void setAmountFigures(int amountFigures) {
		Settings.amountFigures = amountFigures;
	}

	public static long getStepTime() {
		return stepTime;
	}

	public static void setStepTime(long stepTime) {
		Settings.stepTime = stepTime;
	}

	public static void setVolume(float volume)
	{
		if(MainActivity.DEBUG) Log.d(MainActivity.TAG, "GameSettings.setVolume() "+volume);
		
		Settings.volume = volume;
	}
	public static float getVolume()
	{
		return Settings.volume;
	}

	public static boolean isMuted()
	{
		return muted;
	}

	public static void setMuted(boolean muted)
	{
		Settings.muted = muted;
	}

	
}
