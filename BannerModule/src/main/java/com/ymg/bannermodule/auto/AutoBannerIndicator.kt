package com.ymg.bannermodule.auto

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.ymg.bannermodule.R


class AutoBannerIndicator : LinearLayout {

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

    // 인디케이터 사이 공간, 인디케이터 선택 Drawable, 인디케이터 미선택 Drawable
    private var indicatorSpace: Int = 0
    private var indicatorSelectedDrawable: Drawable? = null
    private var indicatorUnSelectedDrawable: Drawable? = null

    // 진짜 배너 카운트
    private var realBannerCount = 0

    // ViewPager
    private lateinit var viewPager: ViewPager

    // Indicator
    private var indicator: MutableList<ImageView> = mutableListOf()



    private fun init(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        val typedArray =
            context?.theme?.obtainStyledAttributes(
                attrs,
                R.styleable.AutoBannerIndicatorStyle,
                defStyleAttr,
                defStyleAttr
            )


        // 인디케이터 사이 공간
        val autoBannerIndicatorSpace =
            typedArray?.getDimension(
                R.styleable.AutoBannerIndicatorStyle_abiSpace,
                context.resources.getDimension(R.dimen.auto_banner_indicator_default_indicator_space)
            )?.toInt()

        // 선택 인디케이터 Drawable
        val autoBannerIndicatorSelectedDrawable =
            typedArray?.getResourceId(
                R.styleable.AutoBannerIndicatorStyle_abiSelectedDrawable,
                R.drawable.auto_banner_indicator_selected
            )

        // 미선택 인디케이터 Drawable
        val autoBannerIndicatorUnSelectedDrawable =
            typedArray?.getResourceId(
                R.styleable.AutoBannerIndicatorStyle_abiUnSelectedDrawable,
                R.drawable.auto_banner_indicator_un_selected
            )


        if (autoBannerIndicatorSpace != null && autoBannerIndicatorSelectedDrawable != null && autoBannerIndicatorUnSelectedDrawable != null) {
            setInit(autoBannerIndicatorSpace, autoBannerIndicatorSelectedDrawable, autoBannerIndicatorUnSelectedDrawable)
        }
    }



    /**
     * 설정
     */
    private fun setInit(
        autoBannerIndicatorSize: Int,
        autoBannerIndicatorSelectedDrawable: Int,
        autoBannerIndicatorUnSelectedDrawable: Int
    ) {
        indicatorSpace = autoBannerIndicatorSize

        ContextCompat.getDrawable(context, autoBannerIndicatorSelectedDrawable)?.let {
            indicatorSelectedDrawable = it
        }

        ContextCompat.getDrawable(context, autoBannerIndicatorUnSelectedDrawable)?.let {
            indicatorUnSelectedDrawable = it
        }
    }





    /**
     * ViewPager 에 인디케이터 연결
     */
    fun setConnectViewPager(viewPager: ViewPager, realBannerCount: Int) {
        this.viewPager = viewPager
        this.realBannerCount = realBannerCount
        bindView()
    }

    /**
     * 인디케이터 시작
     */
    fun setStartIndicator() {
        removeAllViews()
        setAddIndicator()
    }





    /**
     * BindView
     */
    private fun bindView() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { }

            override fun onPageSelected(position: Int) {
                setSelectIndicator(position % realBannerCount)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    /**
     * 인디케이터 추가
     */
    private fun setAddIndicator() {
        for (i in 0 until realBannerCount) {
            indicator.add(ImageView(context).apply {
                setPadding(indicatorSpace, 0, indicatorSpace, 0)
                isSelected = viewPager.currentItem == i
            })
            addView(indicator[i])
        }

        if (indicator.size != 0) {
            setSelectIndicator(viewPager.currentItem % realBannerCount)
        }
    }

    /**
     * 인디케이터 선택 처리
     */
    private fun setSelectIndicator(position: Int) {
        for (i in indicator.indices) {
            if (i == position) {
                indicator[i].setImageDrawable(indicatorSelectedDrawable)
            } else {
                indicator[i].setImageDrawable(indicatorUnSelectedDrawable)
            }
        }
    }
}