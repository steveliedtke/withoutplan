package de.ggj14bremen.withoutplan.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class FontHelper
{
	public	final Typeface 					font, fontBold;
	
	FontHelper(Context context)
	{
		//TODO set correct font
		//TODO make singleton
		font 		= Typeface.createFromAsset(context.getAssets(), "EHSMB.TTF");
    	fontBold	= Typeface.createFromAsset(context.getAssets(), "EHSMB.TTF");
   
	}
	
	/**
	 * Recursively iterates through the view hierarchie and sets the custom font
	 */
	public View setFont(View view)
	{
		if(view instanceof ViewGroup)
		{
			final int count = ((ViewGroup)view).getChildCount();
			for (int i = 0; i < count; i++)
			{
				setFont(((ViewGroup)view).getChildAt(i));
			}
		}
		else if (view instanceof TextView) 	
		{
				if 		(view instanceof EditText) ;//do nothing
				else if (((TextView)view).getTypeface()!=null && ((TextView)view).getTypeface().isBold())			
						((TextView)view).setTypeface(fontBold);			
				else 	((TextView)view).setTypeface(font);
		}
		else if (view instanceof RadioButton) 	((RadioButton)view).setTypeface(font);
		return view;
	}
}
