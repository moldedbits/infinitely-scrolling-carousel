package com.moldedbits.infinitecarousel.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.moldedbits.infinitecarousel.demo.stages.FirstStageCarousel;

public class FirstStageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_stage, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirstStageCarousel carousel = (FirstStageCarousel) getActivity().findViewById(R.id.carousel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.items, android.R.layout.simple_list_item_1);

        carousel.setAdapter(adapter);
    }
}
