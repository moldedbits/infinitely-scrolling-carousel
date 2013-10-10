package com.moldedbits.infinitecarousel.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class ClickCarouselFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InfiniteCarousel carousel = (InfiniteCarousel) getActivity().findViewById(R.id.carousel);

        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        for(int i=0; i<9; i++) {
            imageAdapter.add(String.valueOf(i));
        }

        carousel.setAdapter(imageAdapter);

        carousel.setDynamics(new SimpleDynamics(0.9f));

        carousel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Item clicked: " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ImageAdapter extends ArrayAdapter<String> {

        public ImageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.listitem_compound, parent, false);
            }

            assert convertView != null;

            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            int src = position % 2 == 0 ? R.drawable.image : R.drawable.image_red;
            imageView.setImageResource(src);

            Button button = (Button) convertView.findViewById(R.id.list_item_image_button);
            button.setOnClickListener(ClickCarouselFragment.this);

            return convertView;
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(), "Button clicked", Toast.LENGTH_SHORT).show();
    }
}
