package model;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatSpinner;

/**
 * Created by deepkotadia on 12/7/17.
 *
 * As per standard Android Spinner class, a same item cannot be selected back-to-back
 * So, two or more Renames or two or more Deletes cannot be done back-to-back
 * Therefore, the MySpinner class is an extension of the standard Spinner class that
 * allows the back-to-back same item selection functionality
 *
 * Source: https://stackoverflow.com/questions/10854329/spinner-onitemselected-not-called-when-selected-item-remains-the-same
 */

public class MySpinner extends AppCompatSpinner {

    OnItemSelectedListener listener;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (listener != null)
            listener.onItemSelected(null, null, position, 0);
    }

    public void setOnItemSelectedEvenIfUnchangedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

}
