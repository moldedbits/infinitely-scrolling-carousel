package com.moldedbits.infinitecarousel.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class CarouselFragment extends Fragment {

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
    }

    private class ImageAdapter extends ArrayAdapter<String> {

        public ImageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.listitem_image, parent, false);
            }

            assert convertView != null;

            ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            int src = position % 2 == 0 ? R.drawable.image : R.drawable.image_red;
            imageView.setImageResource(src);

            TextView text = (TextView) convertView.findViewById(R.id.list_item_image_text);
            text.setText(String.valueOf(position));

            return convertView;
        }
    }
}
