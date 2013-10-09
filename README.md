Infinitely Scrolling Carousel
=============================

An easily extendible, infinitely scrolling carousel for Android.

Download
--------

To use this library in your projects, clone the project to your computer and save it in your project
directory.

Usage
-----

The carousel extends an AdapterView, and as such can be populated via any adapter.

Declare the view in your layout xml file and use setAdapter in your code to populate the carousel.

```Java
InfiniteCarousel carousel = (InfiniteCarousel) findViewById(R.id.carousel);
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.items, android.R.layout.simple_list_item_1);
carousel.setAdapter(adapter);
```

```Java
/**
 * A very simple dynamics implementation with adjustible friction
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
```

```Java
carousel.setDynamics(new SimpleDynamics(0.9f));
```

Currently WRAP_CONTENT is not supported.

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.