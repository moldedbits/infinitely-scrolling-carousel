package com.moldedbits.infinitecarousel.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class SimpleDemoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InfiniteCarousel carousel = (InfiniteCarousel) getActivity().findViewById(R.id.carousel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.items, android.R.layout.simple_list_item_1);

        carousel.setAdapter(adapter);

        carousel.setDynamics(new SimpleDynamics(0.9f));
    }
}
