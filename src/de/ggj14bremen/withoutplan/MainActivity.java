package de.ggj14bremen.withoutplan;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import de.ggj14bremen.withoutplan.controller.GameThread;
import de.ggj14bremen.withoutplan.view.GLGameSurfaceView;

public class MainActivity extends Activity
{

	public static final String TAG = "NO_PLAN";
	/** The OpenGL view */
	private GLSurfaceView glSurfaceView;
	private GameThread gameThread;
	private GameSettings gameSettings;
	
	public static boolean DEBUG = true;
	
	private TextView textViewCountdown;
	
	private TextView infoTextView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		gameSettings	= new GameSettings(this);
		gameThread 		=  new GameThread(gameSettings);
		
		// Initiate the Open GL view and create an instance with this activity
		glSurfaceView = new GLGameSurfaceView(this, gameThread);

		// set our renderer to be the main renderer with the current activity context
		setContentView(R.layout.main);
		((ViewGroup)findViewById(R.id.glContainer)).addView(glSurfaceView);
		
		textViewCountdown = (TextView) findViewById(R.id.textViewCountdown);
		infoTextView = (TextView) findViewById(R.id.infoTextView);
	}
	
	private CountDownTimer timer;
	private String lastInfoText = "";
	
	@Override
	protected void onStart() {
		startTimer();
		super.onStart();
	}
	private void startTimer() {
		
		long time = 21000;
		timer = new CountDownTimer(time, 500) {

		     public void onTick(long millisUntilFinished) {
		    	 if(gameThread.getTimeScoreInfo().isTimeShowed()){
		    		 textViewCountdown.setText(String.valueOf((gameThread.getTimeScoreInfo().getStepTime() / 1000)));
		    	 }else{
		    		 textViewCountdown.setText("");
		    	 }
		    	 final String infoText = gameThread.getTimeScoreInfo().getInfoText();
		    	 if(!infoText.equals(lastInfoText)){
		    		 infoTextView.append("\n"+infoText);
		    		 lastInfoText = infoText;
		    	 }
		     }

		     public void onFinish() {
		    	 startTimer();
		     }
		  }.start();
	}
	
	/** Remember to resume the glSurface */
	@Override
	protected void onResume()
	{
		super.onResume();
		glSurfaceView.onResume();
	}

	/** Also pause the glSurface */
	@Override
	protected void onPause()
	{
		timer.cancel();
		super.onPause();
		glSurfaceView.onPause();
	}

	public void showDebugToast(String string)
	{
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();		
	}
}
