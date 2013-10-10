package com.moldedbits.infinitecarousel.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class ImageDemoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InfiniteCarousel carousel = (InfiniteCarousel) getActivity().findViewById(R.id.carousel);

        String[] items = getResources().getStringArray(R.array.items);
        Adapter imageAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listitem_image,
                R.id.list_item_image_text, items);

        carousel.setAdapter(imageAdapter);

        carousel.setDynamics(new SimpleDynamics(0.9f));

        carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Item clicked: " + i, Toast.LENGTH_SHORT).show();
            }
        });

        carousel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Item long clicked: " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
