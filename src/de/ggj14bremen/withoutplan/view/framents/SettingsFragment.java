package de.ggj14bremen.withoutplan.view.framents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import de.ggj14bremen.withoutplan.GameSettings;
import de.ggj14bremen.withoutplan.R;

public class SettingsFragment extends BaseFragment implements OnClickListener
{
	private EditText etAmount, etCols, etRows;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		v.findViewById(R.id.buttonApplySettings).setOnClickListener(this);
		etAmount 	= (EditText)v.findViewById(R.id.editTextAmountFigures);
		etAmount.setText(GameSettings.getAmountFigures()+"");
		etCols 		= ((EditText)v.findViewById(R.id.editTextColumns));
		etCols.setText(GameSettings.getBoardSizeX()+"");
		etRows 		= ((EditText)v.findViewById(R.id.editTextRows));
		etRows.setText(GameSettings.getBoardSizeY()+"");
		return v;
	}

	@Override
	public void onClick(View v)
	{
		//FIXME handle invalid input
		int nrOfFigures = Integer.valueOf(etAmount.getText().toString());
		int cols 		= Integer.valueOf(etCols.getText().toString());
		int rows 		= Integer.valueOf(etRows.getText().toString());
		GameSettings.setBoardSizeX(cols);
		GameSettings.setBoardSizeX(rows);
		GameSettings.setAmountFigures(nrOfFigures);
		GameSettings.setVolume(((SeekBar)getView().findViewById(R.id.seekBarVolume)).getProgress()/100f);
		// TODO set in game settings
		
	}
}
