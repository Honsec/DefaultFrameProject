package genius.baselib.baselib.inter;

import android.view.View;

import genius.baselib.inter.ClickFilter;

/**
 * Created by Hongsec on 2016-08-04.
 */
public abstract class FilterdOnClickListener implements View.OnClickListener {

    private genius.baselib.inter.ClickFilter clickFilter = new ClickFilter();

    @Override
    public void onClick(View v) {
        if(clickFilter.isClicked()) return;

        onFilterdClick(v);
    }


    public  abstract  void onFilterdClick(View v);
}
