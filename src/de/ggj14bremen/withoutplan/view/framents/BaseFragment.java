package de.ggj14bremen.withoutplan.view.framents;

import android.app.Activity;
import android.app.Fragment;
import de.ggj14bremen.withoutplan.MainActivity;

public class BaseFragment extends Fragment
{

	protected MainActivity activity;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
	}
}
