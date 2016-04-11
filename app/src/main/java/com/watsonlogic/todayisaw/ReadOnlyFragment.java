package com.watsonlogic.todayisaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ReadOnlyFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ReadOnlyFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReadOnlyFragment newInstance(int sectionNumber) {
        ReadOnlyFragment fragment = new ReadOnlyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_read_only, container, false);
        return rootView;
    }
}
