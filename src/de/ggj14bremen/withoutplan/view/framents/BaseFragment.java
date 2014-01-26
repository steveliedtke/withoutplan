package de.ggj14bremen.withoutplan.view.framents;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import de.ggj14bremen.withoutplan.MainActivity;
import de.ggj14bremen.withoutplan.util.FontHelper;

public class BaseFragment extends Fragment
{

	protected MainActivity activity;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		FontHelper.setFont(view);
	}
}
