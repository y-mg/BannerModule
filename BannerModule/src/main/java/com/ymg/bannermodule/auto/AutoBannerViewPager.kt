package com.ymg.bannermodule.auto

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Interpolator
import androidx.core.view.MotionEventCompat
import androidx.viewpager.widget.ViewPager
import java.lang.ref.WeakReference


class AutoBannerViewPager : ViewPager {

    companion object {
        const val AUTO_SCROLL_DIRECTION_LEFT = 0
        const val AUTO_SCROLL_DIRECTION_RIGHT = 1

        const val SCROLL_WHAT = 0
    }

    // 배너 보여주는 지속 시간
    private var showBannerDuration = 5000L

    // 자동 스크롤 방향
    private var autoScrollDirection = AUTO_SCROLL_DIRECTION_RIGHT

    // 자동 스크롤 애니메이션 지속 시간
    private var autoScrollAnimationDuration = 1.0

    // 스와이프 스크롤 애니메이션 지속 시간
    private var swipeScrollAnimationDuration = 1.0

    // 자동 스크롤 실행 여부, 자동 스크롤 중지 여부(배너 터치 시)
    private var isRunAutoScroll = false
    private var isStopAutoScroll = false

    // 배너 Handler
    private var autoBannerHandler = AutoBannerHandler(this)

    // 배너 스크롤러
    private var autoBannerScroller: AutoBannerScroller? = null



    constructor(paramContext: Context) : super(paramContext) {
        init()
    }

    constructor(paramContext: Context, paramAttributeSet: AttributeSet) : super(
        paramContext,
        paramAttributeSet
    ) {
        init()
    }

    private fun init() {
        autoBannerHandler = AutoBannerHandler(this)
        setAutoBannerScrollerInAutoBannerViewPager()
    }



    /**
     * 자동 스크롤 시작
     */
    fun setStartAutoScroll() {
        isRunAutoScroll = true
        autoBannerScroller?.let {
            setSendMessageToAutoBannerHandler((showBannerDuration + it.duration / autoScrollAnimationDuration * swipeScrollAnimationDuration).toLong())
        }
    }

    /**
     * 자동 스크롤 시작 및 첫 번째 스크롤 지연 시간 설정
     */
    fun setStartAutoScroll(delayTimeInMills: Int) {
        isRunAutoScroll = true
        setSendMessageToAutoBannerHandler(delayTimeInMills.toLong())
    }

    /**
     * 자동 스크롤 중지
     */
    private fun setStopAutoScroll() {
        isRunAutoScroll = false
        autoBannerHandler.removeMessages(SCROLL_WHAT)
    }



    /**
     * 배너 보여주는 지속 시간 설정
     */
    fun setShowBannerDuration(showBannerDuration: Int) {
        this.showBannerDuration = showBannerDuration.toLong()
    }

    /**
     * 자동 스크롤 시 애니메이션 속도 설정
     */
    fun setSwipeScrollAnimationDuration(swipeScrollAnimationDuration: Double) {
        this.swipeScrollAnimationDuration = swipeScrollAnimationDuration
    }

    /**
     * 스와프 시 애니메이션 속도 설정
     */
    fun setAutoScrollAnimationDuration(autoScrollAnimationDuration: Double) {
        this.autoScrollAnimationDuration = autoScrollAnimationDuration
    }

    /**
     * 스크롤 방향 설정
     */
    fun setAutoScrollDirection(autoScrollDirection: Int) {
        this.autoScrollDirection = autoScrollDirection
    }



    /**
     * 배너 Handler 에 메시지 전달
     */
    private fun setSendMessageToAutoBannerHandler(delayTimeInMills: Long) {
        autoBannerHandler.removeMessages(SCROLL_WHAT)
        autoBannerHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills)
    }



    /**
     * 배너 ViewPager 에 배너 스크롤러 설정 반영
     */
    private fun setAutoBannerScrollerInAutoBannerViewPager() {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true

            val interpolatorField = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolatorField.isAccessible = true

            autoBannerScroller =
                AutoBannerScroller(
                    context,
                    interpolatorField.get(null) as Interpolator
                )
            scrollerField.set(this, autoBannerScroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    /**
     * 자동 스크롤 아이템 설정
     */
    fun setAutoScrollItem() {
        val adapter = adapter
        var currentItem = currentItem

        val totalCount: Int? = adapter?.count

        totalCount?.let {
            if (it <= 1) {
                return
            }

            val nextItem = when (autoScrollDirection) {
                // 자동 스크롤 방향 왼쪽일 경우
                AUTO_SCROLL_DIRECTION_LEFT -> {
                    --currentItem
                }

                // 자동 스크롤 방향 오른쪽일 경우
                else -> {
                    ++currentItem
                }
            }

            when {
                nextItem < 0 -> {
                    setCurrentItem(it - 1, true)
                }

                nextItem == totalCount -> {
                    setCurrentItem(0, true)
                }

                else -> {
                    setCurrentItem(nextItem, true)
                }
            }
        }
    }



    @Suppress("DEPRECATION")
    override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        val action = MotionEventCompat.getActionMasked(motionEvent)

        when {
            // 배너가 터치 된 경우 자동 스크롤 중지
            action == MotionEvent.ACTION_DOWN && isRunAutoScroll -> {
                isStopAutoScroll = true
                setStopAutoScroll()
            }

            // 배너가 터치 된 이후 자동 스크롤 다시 시작
            motionEvent.action == MotionEvent.ACTION_UP && isStopAutoScroll -> {
                setStartAutoScroll()
            }
        }

        parent.requestDisallowInterceptTouchEvent(true)

        return super.dispatchTouchEvent(motionEvent)
    }





    /**
     * Handler
     */
    private class AutoBannerHandler(autoBannerViewPager: AutoBannerViewPager) : Handler(Looper.getMainLooper()) {

        private val autoBannerViewPager: WeakReference<AutoBannerViewPager> = WeakReference(autoBannerViewPager)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            when (msg.what) {
                SCROLL_WHAT -> {
                    val autoBannerViewPager = this.autoBannerViewPager.get()

                    autoBannerViewPager?.let { bannerViewPager ->
                        bannerViewPager.autoBannerScroller?.let { bannerScroller ->
                            bannerScroller.setAutoBannerScrollerDuration(bannerViewPager.autoScrollAnimationDuration)
                            bannerViewPager.setAutoScrollItem()

                            bannerScroller.setAutoBannerScrollerDuration(bannerViewPager.swipeScrollAnimationDuration)

                            bannerViewPager.setSendMessageToAutoBannerHandler(bannerViewPager.showBannerDuration + bannerScroller.duration)
                        }
                    }
                }

                else -> { }
            }
        }
    }
}