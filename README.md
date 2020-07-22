# BannerModule
<br/>
<br/>



## 1. AutoBannerViewPager

### Kotlin

| Option | Parameter | Description |
|:----------|:----------|:----------|
| setShowBannerDuration(duration: Int) | 설정할 지속 시간 | 배너 보여줄 지속 시간 |
| setAutoScrollDirection(<br/>&nbsp;&nbsp;&nbsp;&nbsp;AUTO_SCROLL_DIRECTION_LEFT<br/>&nbsp;&nbsp;&nbsp;&nbsp;or<br/>&nbsp;&nbsp;&nbsp;&nbsp;AUTO_SCROLL_DIRECTION_RIGHT<br/>) | 설정할 방향 | 배너 자동 스크롤 시 방향 |
| setAutoScrollAnimationDuration(<br/>&nbsp;&nbsp;&nbsp;&nbsp;duration: Double<br/>) | 설정할 지속 시간 | 스크롤 시 애니메이션 지속 시간 |
| setSwipeScrollAnimationDuration(<br/>&nbsp;&nbsp;&nbsp;&nbsp;duration: Double<br/>) | 설정할 지속 시간 | 스와이프 시 애니메이션 지속 시간 |
| setStartAutoScroll(<br/>&nbsp;&nbsp;&nbsp;&nbsp;duration: Double<br/>&nbsp;&nbsp;&nbsp;&nbsp;or<br/>&nbsp;&nbsp;&nbsp;&nbsp;Void<br/>) | 설정할 지속 시간 or Void | 자동 스크롤 시작 및 인자를 줄 경우 첫 배너를 보여줄 지속시간 설정 |

### Recommendation
왼쪽 or 오른쪽으로 무한 스크롤이 되도록 currentItem 옵션을 주어 현재 위치를 가운데쯤에 위치하도록 설정할 것을 권장함.
<br/>
<br/>



## 2. AutoBannerIndicator

### XML

| Option | Default | Description |
|:----------|:----------|:----------|
| abiSpace | 1.5dp | 인디케이터 사이 공간 |
| abiSelectedDrawable | auto_banner_indicator_selected.xml | 인디케이터 선택인 경우 Drawable |
| abiUnSelectedDrawable | auto_banner_indicator_un_selected.xml | 인디케이터 미선택일 경우 Drawable |

### Kotlin

| Function | Parameter | Description |
|:----------|:----------|:----------|
| setConnectViewPager(<br/>&nbsp;&nbsp;&nbsp;&nbsp;autoBannerViewPager: AutoBannerViewPager,<br/>&nbsp;&nbsp;&nbsp;&nbsp;bannerListSize: Int<br/>) | AutoBannerViewPager,<br/>배너 리스트 사이즈 | 인디케이터를 뷰페이저에 연결 |
| setStartIndicator() | Void | 인디케이터 시작 |
<br/>
<br/>


