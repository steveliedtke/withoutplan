package de.ggj14bremen.withoutplan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import de.ggj14bremen.withoutplan.controller.GameThread;
import de.ggj14bremen.withoutplan.controller.Sounds;
import de.ggj14bremen.withoutplan.model.Settings;
import de.ggj14bremen.withoutplan.util.FontHelper;
import de.ggj14bremen.withoutplan.view.GLGameSurfaceView;
import de.ggj14bremen.withoutplan.view.framents.BaseFragment;
import de.ggj14bremen.withoutplan.view.framents.GameFragment;
import de.ggj14bremen.withoutplan.view.framents.SettingsFragment;

public class MainActivity extends Activity implements OnClickListener
{

	public static final String TAG = "NO_PLAN";
	/** The OpenGL view */
	private GLSurfaceView glSurfaceView;
	public GameThread gameThread;
	public Settings gameSettings;
	
	//UI elements
	private GameFragment gameFragment;
	private SettingsFragment settingsFragment;

	public static boolean DEBUG = true;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set our renderer to be the main renderer with the current activity context
		setContentView(R.layout.main);
		
		Sounds.init(this);
		FontHelper.init(this);
		
		settingsFragment 	= new SettingsFragment();
		gameFragment 		= new GameFragment();
		showFragment(gameFragment);
		
		findViewById(R.id.layoutSplashScreen).setOnClickListener(this);
		findViewById(R.id.buttonGame).setOnClickListener(this);
		FontHelper.setFont(findViewById(R.id.buttonGame));
		findViewById(R.id.buttonSettings).setOnClickListener(this);	
		FontHelper.setFont(findViewById(R.id.buttonSettings));
		
		//game stuff
		gameThread 		= new GameThread();
	
		// Initiate the Open GL view and create an instance with this activity
		glSurfaceView = new GLGameSurfaceView(this, gameThread);
		((ViewGroup)findViewById(R.id.glContainer)).addView(glSurfaceView);		
	}
	

	/** Remember to resume the glSurface */
	@Override
	protected void onResume()
	{
		super.onResume();
		glSurfaceView.onResume();
		Settings.setMuted(false);
		findViewById(R.id.layoutSplashScreen).setVisibility(View.VISIBLE);
		//gameThread.setPause(false);
	}

	/** Also pause the glSurface */
	@Override
	protected void onPause()
	{
		super.onPause();
		glSurfaceView.onPause();
		Settings.setMuted(true);
		gameThread.setPause(true);
	}
	/*public void onBackPressed() 
	{
		this.finish();
	};*/
	public void showDebugToast(String string)
	{
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onClick(View v)
	{
		if(v.getId() == R.id.buttonReset)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Reset game?");
			builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					gameThread.reset();
					showDebugToast("RESET");
					return;
				}
			});
			builder.create().show();
		}
		else if(v.getId() == R.id.buttonSettings)
		{
			showFragment(settingsFragment);
		}
		else if(v.getId() == R.id.buttonGame)
		{
			showFragment(gameFragment);
		}
		else if(v.getId() == R.id.layoutSplashScreen)
		{
			findViewById(R.id.layoutSplashScreen).setVisibility(View.GONE);
			gameThread.setPause(false);
		}
	}
	private final void showFragment(BaseFragment fragment)
	{
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();	
		fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.replace(R.id.layoutRight, fragment);
		fragmentTransaction.commit();
	}
}
