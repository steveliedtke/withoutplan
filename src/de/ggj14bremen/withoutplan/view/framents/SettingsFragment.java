package de.ggj14bremen.withoutplan.view.framents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import de.ggj14bremen.withoutplan.R;

public class SettingsFragment extends BaseFragment implements OnClickListener
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		v.findViewById(R.id.buttonApplySettings).setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v)
	{
		//FIXME handle invalid input
		int nrOfFigures = Integer.valueOf(((EditText)getView().findViewById(R.id.editTextAmountFigures)).getText().toString());
		int cols 		= Integer.valueOf(((EditText)getView().findViewById(R.id.editTextColumns)).getText().toString());
		int rows 		= Integer.valueOf(((EditText)getView().findViewById(R.id.editTextRows)).getText().toString());
		activity.gameSettings.setBoardSizeX(cols);
		activity.gameSettings.setBoardSizeX(rows);
		activity.gameSettings.setAmountFigures(nrOfFigures);
		// TODO set in game settings
		
	}
}
