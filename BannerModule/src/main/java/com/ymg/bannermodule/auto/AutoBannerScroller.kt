package com.ymg.bannermodule.auto

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

class AutoBannerScroller(
    context: Context, interpolator: Interpolator
) : Scroller(context, interpolator) {

    // 배너 스크롤 지속 시간
    private var autoBannerScrollDuration = 1.0



    /**
     * 배너 스크롤 지속 시간 설정
     */
    fun setAutoBannerScrollerDuration(autoBannerScrollDuration: Double) {
        this.autoBannerScrollDuration = autoBannerScrollDuration
    }



    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * autoBannerScrollDuration).toInt())
    }
}