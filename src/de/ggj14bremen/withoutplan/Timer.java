package de.ggj14bremen.withoutplan;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class Timer
{
	
	private CountDownTimer timer;
	private TextView countDown;
	private MediaPlayer mp;
	private boolean paused = false;
	private long total = 0;


	private void startTimer(long time) {
		
		if(time<=0){
			time = 21000;
		}
		timer = new CountDownTimer(time, 1000) {

		     public void onTick(long millisUntilFinished) {
		         countDown.setText(String.valueOf((millisUntilFinished / 1000)-1));
		         Log.i("CountDownTimer", "millisUntilFinished: " + millisUntilFinished);
		         if((millisUntilFinished / 1000)-1 == 0){
		        	 mp.start();
		         }
		         total = millisUntilFinished;
		     }

		     public void onFinish() {
		    	 countDown.setText("0");
		    	 startTimer(0);
		     }
		  }.start();
	}
}
