package com.moldedbits.infinitecarousel.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.moldedbits.infinitecarousel.InfiniteCarousel;
import com.moldedbits.infinitecarousel.demo.stages.SecondStageCarousel;

public class SecondStageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second_stage, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SecondStageCarousel carousel = (SecondStageCarousel) getActivity().findViewById(R.id.carousel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.items, android.R.layout.simple_list_item_1);

        carousel.setAdapter(adapter);
    }
}
