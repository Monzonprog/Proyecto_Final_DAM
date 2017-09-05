package com.example.jorge.gasolinator.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jorge.gasolinator.Fragments.ListaVehiculosFragment;
import com.example.jorge.gasolinator.Fragments.VehiculosFragment;

/**
 * Created by jorge on 10/04/17.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Vehículos", "Crear nuevo"};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ListaVehiculosFragment.newInstance();
            case 1:
                return VehiculosFragment.newInstance();
            default:
                return ListaVehiculosFragment.newInstance();
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}