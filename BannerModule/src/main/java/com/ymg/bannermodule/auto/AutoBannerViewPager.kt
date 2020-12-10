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



/**
 * @author y-mg
 *
 * 이것은 AutoBannerIndicator 와 함께 사용되는 AutoBannerViewPager 입니다.
 * This is the AutoBannerViewPager used with AutoBannerIndicator.
 */
class AutoBannerViewPager : ViewPager {

    companion object {
        const val AUTO_SCROLL_DIRECTION_LEFT = 0
        const val AUTO_SCROLL_DIRECTION_RIGHT = 1

        const val SCROLL_WHAT = 0
    }

    private var duration = 5000L
    private var direction = AUTO_SCROLL_DIRECTION_RIGHT
    private var scrollAnimationDuration = 1.0
    private var swipeAnimationDuration = 1.0
    private var isRunAutoScroll = false
    private var isStopAutoScroll = false

    // Handler
    private var autoBannerHandler = AutoBannerHandler(this)

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
        setAttachAutoBannerScroller()
    }



    /**
     * - 자동 스크롤을 시작한다.
     * - Start automatic scrolling.
     */
    fun setStartAutoScroll() {
        isRunAutoScroll = true
        autoBannerScroller?.let {
            setAutoBannerHandlerMessage((duration + it.duration / scrollAnimationDuration * swipeAnimationDuration).toLong())
        }
    }



    /**
     * - 자동 스크롤을 시작하고 지연시간을 설정한다.
     * - Start automatic scrolling and set the delay time.
     *
     * @param delayTimeInMills -> Delay Time
     */
    fun setStartAutoScroll(delayTimeInMills: Int) {
        isRunAutoScroll = true
        setAutoBannerHandlerMessage(delayTimeInMills.toLong())
    }



    /**
     * Setting Stop Auto Scroll
     */
    private fun setStopAutoScroll() {
        isRunAutoScroll = false
        autoBannerHandler.removeMessages(SCROLL_WHAT)
    }



    /**
     * - 배너를 보여줄 지속시간을 설정한다.
     * - Set the duration to show the banner.
     *
     * @param duration -> Duration to show the banner
     */
    fun setBannerDuration(duration: Int) {
        this.duration = duration.toLong()
    }



    /**
     * - 스와프 시 애니메이션 속도를 설정한다.
     * - Set animation speed when swapping.
     *
     * @param swipeAnimationDuration -> Animation speed when swiping.
     */
    fun setSwipeScrollAnimationDuration(swipeAnimationDuration: Double) {
        this.swipeAnimationDuration = swipeAnimationDuration
    }



    /**
     * - 스크롤 시 애니메이션 속도를 설정한다.
     * - Set animation speed when scrolling.
     *
     * @param scrollAnimationDuration -> Animation speed when scrolling
     */
    fun setAutoScrollAnimationDuration(scrollAnimationDuration: Double) {
        this.scrollAnimationDuration = scrollAnimationDuration
    }



    /**
     * - 스크롤 방향을 설정한다.
     * - Set the scroll direction.
     *
     * @param direction -> AUTO_SCROLL_DIRECTION_LEFT or AUTO_SCROLL_DIRECTION_RIGHT
     */
    fun setDirection(direction: Int) {
        this.direction = direction
    }



    /**
     * Setting AutoBannerHandler Message
     */
    private fun setAutoBannerHandlerMessage(delayTimeInMills: Long) {
        autoBannerHandler.removeMessages(SCROLL_WHAT)
        autoBannerHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills)
    }



    /**
     * Setting Attach AutoBannerScroller
     */
    private fun setAttachAutoBannerScroller() {
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
     * Setting Auto Scroll Item
     */
    private fun setAutoScrollItem() {
        val adapter = adapter
        var currentItem = currentItem

        val totalCount: Int? = adapter?.count

        totalCount?.let {
            if (it <= 1) {
                return
            }

            val nextItem = when (direction) {
                // Direction Left
                AUTO_SCROLL_DIRECTION_LEFT -> {
                    --currentItem
                }

                // Direction Right
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
            // Stop Auto Scroll
            action == MotionEvent.ACTION_DOWN && isRunAutoScroll -> {
                isStopAutoScroll = true
                setStopAutoScroll()
            }

            // Restart Auto Scroll
            motionEvent.action == MotionEvent.ACTION_UP && isStopAutoScroll -> {
                setStartAutoScroll()
            }
        }

        parent.requestDisallowInterceptTouchEvent(true)

        return super.dispatchTouchEvent(motionEvent)
    }





    /**
     * @author y-mg
     *
     * 이것은 AutoBanner 를 핸들링하는 클래스입니다.
     * This is a class that handles AutoBanner.
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
                            bannerScroller.setAutoBannerScrollerDuration(bannerViewPager.scrollAnimationDuration)
                            bannerViewPager.setAutoScrollItem()

                            bannerScroller.setAutoBannerScrollerDuration(bannerViewPager.swipeAnimationDuration)
                            bannerViewPager.setAutoBannerHandlerMessage(bannerViewPager.duration + bannerScroller.duration)
                        }
                    }
                }

                else -> { }
            }
        }
    }
}