package com.ymg.bannerview.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.ymg.bannerview.R
import com.ymg.bannerview.databinding.ActivityMainAdapterBinding


class MainAdapter(
    private var context: Context,
    private val bannerList: IntArray = intArrayOf(
        R.drawable.banner_01,
        R.drawable.banner_02,
        R.drawable.banner_03
    )
) : PagerAdapter() {

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val realPosition: Int = position % bannerList.size // 배너 리스트의 진짜 포지션 값

        val viewBinding = ActivityMainAdapterBinding.inflate(inflater)

        viewBinding.bannerImage.setImageResource(bannerList[realPosition])

        container.addView(viewBinding.root)
        return viewBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return bannerList.size * 1000 // 무한 스크롤링을 위해 만들 여유값
    }
}
