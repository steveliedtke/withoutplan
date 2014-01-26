package de.ggj14bremen.withoutplan.view.framents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import de.ggj14bremen.withoutplan.R;
import de.ggj14bremen.withoutplan.model.Settings;

public class SettingsFragment extends BaseFragment implements OnClickListener
{
	private EditText etAmount, etCols, etRows;
	private SeekBar seekBarVolume;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		v.findViewById(R.id.buttonApplySettings).setOnClickListener(this);
		etAmount 	= (EditText)v.findViewById(R.id.editTextAmountFigures);
		etAmount.setText(Settings.getAmountFigures()+"");
		etCols 		= ((EditText)v.findViewById(R.id.editTextColumns));
		etCols.setText(Settings.getBoardSizeX()+"");
		etRows 		= ((EditText)v.findViewById(R.id.editTextRows));
		etRows.setText(Settings.getBoardSizeY()+"");
		seekBarVolume = ((SeekBar)v.findViewById(R.id.seekBarVolume));
		seekBarVolume.setProgress((int) (Settings.getVolume()*100));
		return v;
	}

	@Override
	public void onClick(View v)
	{
		//FIXME handle invalid input
		int nrOfFigures = Integer.valueOf(etAmount.getText().toString());
		int cols 		= Integer.valueOf(etCols.getText().toString());
		int rows 		= Integer.valueOf(etRows.getText().toString());
		Settings.setBoardSizeX(cols);
		Settings.setBoardSizeX(rows);
		Settings.setAmountFigures(nrOfFigures);
		Settings.setVolume(seekBarVolume.getProgress()/100f);
	}
}
