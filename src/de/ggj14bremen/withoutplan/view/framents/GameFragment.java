package de.ggj14bremen.withoutplan.view.framents;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import de.ggj14bremen.withoutplan.R;
import de.ggj14bremen.withoutplan.util.FontHelper;

public class GameFragment extends BaseFragment implements OnClickListener
{
	private Button btnPause;
	private TextView infoTextView;
	private CountDownTimer timer;
	private TextView textViewCountdown;
	private List<String> infoTextList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_game, container, false);
		v.findViewById(R.id.buttonReset).setOnClickListener(this);
		btnPause = (Button) v.findViewById(R.id.buttonPause);
		btnPause.setOnClickListener(this);
		infoTextView = (TextView) v.findViewById(R.id.infoTextView);
		textViewCountdown = (TextView) v.findViewById(R.id.textViewCountdown);

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

	private void startTimer()
	{
		long time = 21000;
		timer = new CountDownTimer(time, 500)
		{
			public void onTick(long millisUntilFinished)
			{
				if(!activity.gameThread.isRunning())
				{
					btnPause.setText("Start");
				}
				else if(activity.gameThread.isPaused())
				{
					btnPause.setText("Resume");
				}
				else
				{
					btnPause.setText("Pause");
				}
				if (activity.gameThread.getTimeScoreInfo().isTimeShowed())
				{
					final long secondsRemaining = activity.gameThread.getTimeScoreInfo().getStepTime() / 1000;
					textViewCountdown.setText(String.valueOf(secondsRemaining));
				}
				else
				{
					textViewCountdown.setText("");
				}
				infoTextView.setText(activity.gameThread.getTimeScoreInfo().getInfoText());
			}
			public void onFinish()
			{
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
			// builder.setMessage("Reset game?");
			TextView tv = new TextView(GameFragment.this.getActivity());
			tv.setText("Reset game?");
			tv.setGravity(Gravity.CENTER);
			FontHelper.setFont(tv);
			builder.setView(tv);
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
			if(!activity.gameThread.isRunning())
			{
				activity.gameThread.start();
				activity.gameThread.setPause(false);
			}
			else activity.gameThread.togglePause();
		}
	}
}
