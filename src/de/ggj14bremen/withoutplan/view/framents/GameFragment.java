package de.ggj14bremen.withoutplan.view.framents;

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


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_game, container, false);
		v.findViewById(R.id.buttonReset).setOnClickListener(this);
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
				if (activity.gameThread.getTimeScoreInfo().isTimeShowed())
				{
					textViewCountdown.setText(String.valueOf((activity.gameThread.getTimeScoreInfo().getStepTime() / 1000)));
				} 
				else
				{
					textViewCountdown.setText("");
				}
				final String infoText = activity.gameThread.getTimeScoreInfo().getInfoText();
				if (!infoText.equals(lastInfoText) || lastInfoText.length()<=0)
				{
					infoTextView.append("\n" + infoText);
					lastInfoText = infoText;
				}
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
			builder.setMessage("Reset game?");
			builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					activity.gameThread.reset();
					return;
				}
			});
			builder.create().show();
		}

	}
}