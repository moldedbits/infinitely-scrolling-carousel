package com.moldedbits.infinitecarousel.demo;

import com.moldedbits.infinitecarousel.Dynamics;

/**
 * A very simple dynamics implementation with adjustible friction
 */
public class SimpleDynamics extends Dynamics {
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
