package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            SummaryTab tab1 = new SummaryTab();
            return tab1;
        }
        else if(position==1)
        {
            Tab2 tab2 = new Tab2();
            return tab2;
        }
        else
        {
            Tab3 tab3 = new Tab3();
            return tab3;
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}