package com.dal.group7.tutorplus.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.ui.fragments.TutorAvailabilityFragment;


public class TutorPlusViewSectionAdapter extends FragmentPagerAdapter {
    private TutorModel tutorModel;
    private TutorDetailsFragment mFragment;

    public TutorPlusViewSectionAdapter(FragmentManager fm, TutorModel tutorModel) {
        super(fm);
        this.tutorModel = tutorModel;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                mFragment = TutorDetailsFragment.newInstance(tutorModel);
                return mFragment;

            case 1:
                return TutorAvailabilityFragment.newInstance(tutorModel);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Details";
            case 1:
                return "Availability";
        }
        return null;
    }

    public TutorDetailsFragment getFragment() {
        return mFragment;
    }
}
