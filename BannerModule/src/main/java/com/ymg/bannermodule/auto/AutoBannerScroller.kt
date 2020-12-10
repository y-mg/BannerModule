package com.ymg.bannermodule.auto

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller



/**
 * @author y-mg
 *
 * 이것은 자동 Scroller 입니다.
 * This is an automatic scroller.
 */
class AutoBannerScroller(
    context: Context, interpolator: Interpolator
) : Scroller(context, interpolator) {

    // Scroll Duration
    private var autoBannerScrollDuration = 1.0



    /**
     * Setting Scroll Duration
     */
    internal fun setAutoBannerScrollerDuration(autoBannerScrollDuration: Double) {
        this.autoBannerScrollDuration = autoBannerScrollDuration
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * autoBannerScrollDuration).toInt())
    }
}