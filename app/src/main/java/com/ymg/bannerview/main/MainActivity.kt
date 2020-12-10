package com.ymg.bannerview.main

import android.os.Bundle
import com.ymg.bannermodule.auto.AutoBannerViewPager.Companion.AUTO_SCROLL_DIRECTION_RIGHT
import com.ymg.bannerview.databinding.ActivityMainBinding


class MainActivity : BasicActivity() {

    private lateinit var viewBinding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val mainAdapter = MainAdapter(this)

        // 왼쪽 또는 오른쪽으로 무한 스크롤이 되도록 currentItem 옵션을 주어 현재 위치를 가운데쯤에 위치하도록 설정할 것을 권장한다.
        // It is recommended that the current position be set to be centered by giving
        // the currentItem option to scroll left or right indefinitely.
        viewBinding.autoBannerViewPager.apply {
            adapter = mainAdapter
            setBannerDuration(1000) // 배너를 보여줄 지속 시간
            setDirection(AUTO_SCROLL_DIRECTION_RIGHT) // 자동 스크롤 방향
            setAutoScrollAnimationDuration(5.0) // 스크롤 애니메이션 지속 시간
            setSwipeScrollAnimationDuration(2.5) // 스와이프 애니메이션 지속 시간
            currentItem = 3 * 100 // 무한 스크롤을 위해 현재 아이템 위치를 가운데 쯤에 위치하다록 설정
        }.run {
            setStartAutoScroll(5000) // 자동 스크롤 시작 및 첫 배너 보여줄 시간 설정
        }

        viewBinding.autoBannerIndicator.apply {
            setAttachAutoBannerViewPager(viewBinding.autoBannerViewPager, 3) // 뷰페이저에 연결
            setStartIndicator() // 인디케이터 시작
        }
    }
}