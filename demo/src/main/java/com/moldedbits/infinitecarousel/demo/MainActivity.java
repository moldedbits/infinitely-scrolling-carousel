package com.moldedbits.infinitecarousel.demo;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moldedbits.infinitecarousel.Dynamics;
import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfiniteCarousel carousel = (InfiniteCarousel) findViewById(R.id.carousel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_list_item_1);

        ImageAdapter imageAdapter = new ImageAdapter(this);
        for(int i=0; i<9; i++) {
            imageAdapter.add(String.valueOf(i));
        }

        //carousel.setAdapter(adapter);
        carousel.setAdapter(imageAdapter);

        carousel.setDynamics(new SimpleDynamics(0.9f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

            TextView text = (TextView) convertView.findViewById(R.id.list_item_image_text);
            text.setText(String.valueOf(position));

            return convertView;
        }
    }

    /**
     * A very simple dynamics implementation with spring-like behavior
     */
    class SimpleDynamics extends Dynamics {

        /** The friction factor */
        private float mFrictionFactor;

        /**
         * Creates a SimpleDynamics object
         *
         * @param frictionFactor The friction factor. Should be between 0 and 1.
         *            A higher number means a slower dissipating speed.
         */
        public SimpleDynamics(final float frictionFactor) {
            mFrictionFactor = frictionFactor;
        }

        @Override
        protected void onUpdate(final int dt) {
            // then update the position based on the current velocity
            mPosition += mVelocity * dt / 1000;

            // and finally, apply some friction to slow it down
            mVelocity *= mFrictionFactor;
        }
    }
}
