package com.vitulburs.claudio.myapplication;

/**
 * Created by Robert on 31/01/2017.
 */

import java.util.Locale;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                Bastoncello bastoncello = new Bastoncello();
                return bastoncello;
            case 1:
                Cono cono = new Cono();
                return cono;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }//set the number of tabs

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Bastoncello";
            case 1:

                return "Cono";
        }
        return null;
    }



}

