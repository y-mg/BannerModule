package com.ymg.bannermodule.auto

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.ymg.bannermodule.R



/**
 * @author y-mg
 *
 * 이것은 AutoBannerViewPager 와 함께 사용되는 AutoBannerIndicator 입니다.
 * This is the AutoBannerIndicator used with AutoBannerViewPager.
 */
class AutoBannerIndicator : LinearLayout {

    private var space: Int = 0
    private var selected: Drawable? = null
    private var unSelected: Drawable? = null

    // Real Count
    private var realCount = 0

    private lateinit var viewPager: ViewPager
    private var indicator: MutableList<ImageView> = mutableListOf()



    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }



    private fun init(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        val typedArray =
            context?.theme?.obtainStyledAttributes(
                attrs,
                R.styleable.AutoBannerIndicatorStyle,
                defStyleAttr,
                defStyleAttr
            )

        // Indicator 사이의 공간을 설정한다.
        // Set the space between the indicators.
        val space =
            typedArray?.getDimension(
                R.styleable.AutoBannerIndicatorStyle_abiSpace,
                context.resources.getDimension(R.dimen.auto_banner_indicator_default_indicator_space)
            )?.toInt()

        // 선택된 Indicator 의 아이콘을 설정한다.
        // Sets the icon for the selected indicator.
        val selected =
            typedArray?.getResourceId(
                R.styleable.AutoBannerIndicatorStyle_abiSelected,
                R.drawable.auto_banner_indicator_selected
            )

        // 미선택된 Indicator 의 아이콘을 설정한다.
        // Set the icon for the unselected indicator.
        val unSelected =
            typedArray?.getResourceId(
                R.styleable.AutoBannerIndicatorStyle_abiUnSelected,
                R.drawable.auto_banner_indicator_un_selected
            )


        setInit(
            space = space ?: 1,
            selected = selected ?: R.drawable.auto_banner_indicator_selected,
            unSelected = unSelected ?: R.drawable.auto_banner_indicator_un_selected
        )
    }



    /**
     * Setting Init
     */
    private fun setInit(
        space: Int,
        selected: Int,
        unSelected: Int
    ) {
        this.space = space
        this.selected = ContextCompat.getDrawable(context, selected)
        this.unSelected = ContextCompat.getDrawable(context, unSelected)
    }



    /**
     * - AutoBannerViewPager 를 연결한다.
     * - Attach to AutoBannerViewPager.
     *
     * @param viewPager -> AutoBannerViewPager
     *
     * @param realCount -> Number of items in ViewPager
     */
    fun setAttachAutoBannerViewPager(viewPager: ViewPager, realCount: Int) {
        this.viewPager = viewPager
        this.realCount = realCount
        setViewPagerListener()
    }



    /**
     * - 인디케이터를 동작시킨다.
     * - Start the indicator.
     */
    fun setStartIndicator() {
        removeAllViews()
        setAddIndicator()
    }



    /**
     * Setting ViewPager Listener
     */
    private fun setViewPagerListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { }

            override fun onPageSelected(position: Int) {
                setSelectIndicator(position % realCount)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }



    /**
     * Setting Add Indicator
     */
    private fun setAddIndicator() {
        for (i in 0 until realCount) {
            indicator.add(ImageView(context).apply {
                setPadding(space, 0, space, 0)
                isSelected = viewPager.currentItem == i
            })
            addView(indicator[i])
        }

        if (indicator.size != 0) {
            setSelectIndicator(viewPager.currentItem % realCount)
        }
    }



    /**
     * Setting Select Indicator
     */
    private fun setSelectIndicator(position: Int) {
        for (i in indicator.indices) {
            if (i == position) {
                indicator[i].setImageDrawable(selected)
            } else {
                indicator[i].setImageDrawable(unSelected)
            }
        }
    }
}