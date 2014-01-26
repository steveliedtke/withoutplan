package de.ggj14bremen.withoutplan.view.framents;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ggj14bremen.withoutplan.R;

public class GameFragment extends BaseFragment implements OnClickListener
{

	private TextView infoTextView;
	private CountDownTimer timer;
	private String lastInfoText = "";
	private TextView textViewCountdown;
	private List<String> infoTextList = new ArrayList<String>(); 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_game, container, false);
		v.findViewById(R.id.buttonReset).setOnClickListener(this);
		v.findViewById(R.id.buttonPause).setOnClickListener(this);
		infoTextView 		= (TextView) v.findViewById(R.id.infoTextView);
		textViewCountdown 	= (TextView) v.findViewById(R.id.textViewCountdown);

		return v;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		startTimer();
	}

	@Override
	public void onPause()
	{
		super.onPause();
		timer.cancel();
	}

private void startTimer() {
		
		long time = 21000;
		timer = new CountDownTimer(time, 500) {

		    public void onTick(long millisUntilFinished) {
		    	 if(activity.gameThread.getTimeScoreInfo().isTimeShowed()){
		    		 final long secondsRemaining = activity.gameThread.getTimeScoreInfo().getStepTime() / 1000;
		    		 textViewCountdown.setText(String.valueOf(secondsRemaining));
			     }else{
		    		 textViewCountdown.setText("");
		    	 }
		    	 final String infoText = activity.gameThread.getTimeScoreInfo().getInfoText();
		    	 if(!infoText.equals(lastInfoText)){
		    		 if(infoTextList.size()>=7){
		    			 //infoTextList.remove(0);
		    		 }
		    		 infoTextList.add(infoText);
		    		 this.displayInfoText();
		    		 lastInfoText = infoText;
		    	 }
		     }

		     private void displayInfoText() {
				StringBuilder text = new StringBuilder();
		    	for(int i=infoTextList.size()-1;i>=0;i--){
					text.append(infoTextList.get(i));
					text.append("\n");
				}
		    	 infoTextView.setText(text.toString());
			}

			public void onFinish() {
		    	 startTimer();
		     }
		  }.start();
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.buttonReset)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage("Reset game?");
			builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					activity.gameThread.reset();
					infoTextList.clear();
					return;
				}
			});
			builder.create().show();
		}
		else if (v.getId() == R.id.buttonPause)
		{
			activity.gameThread.togglePause();
		}

	}
}
