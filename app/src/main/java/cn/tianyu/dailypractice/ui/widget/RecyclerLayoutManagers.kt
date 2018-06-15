package cn.tianyu.dailypractice.ui.widget

import android.support.v7.widget.RecyclerView
import android.view.View
import cn.tianyu.dailypractice.utils.LogUtil


class PagerLayoutManager : RecyclerView.LayoutManager() {

    private var mItemViewWidth: Int = 0
    private var mItemViewHeight: Int = 0
    private var mItemCount: Int = 0
    private var mScrollOffset = Int.MAX_VALUE
    private var mScale = 0.9f
    private var layoutInfos = HashMap<Int, ItemViewInfo>()

    companion object {
        val TAG = "PagerLayoutManager"
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
            RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0 || state.isPreLayout) return
        removeAndRecycleAllViews(recycler)
        mItemViewWidth = (getHorizontalSpace() * 0.87f).toInt()
        mItemViewHeight = (mItemViewWidth * 1.46f).toInt()
        mItemCount = itemCount
        //获取到所有Item的高度和
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset), mItemCount * mItemViewHeight)
        layoutChild(recycler)
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        if (null == recycler) {
            return super.scrollVerticallyBy(dy, recycler, state)
        }
        val pendingScrollOffset = mScrollOffset + dy
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset + dy), mItemCount * mItemViewHeight)
        layoutChild(recycler)
        return mScrollOffset - pendingScrollOffset + dy
    }

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        layoutInfos.clear()
        super.onDetachedFromWindow(view, recycler)
    }

    private fun layoutChild(recycler: RecyclerView.Recycler) {
        if (itemCount == 0) return
        //获取到最后一个Item的位置
        var bottomItemPosition = Math.floor(mScrollOffset.toDouble() / mItemViewHeight).toInt()
        //获取到出去一个完整的Item的高度，还剩余多少空间
        var remainSpace = getVerticalSpace() - mItemViewHeight
        //滑动的时候可以获取到最后一个Item在屏幕上还显示的高度
        val bottomItemVisibleHeight = mScrollOffset % mItemViewHeight
        //最后一个Item显示高度相对于本身的比例
        val lastOneVisiblePercent = bottomItemVisibleHeight * 1.0f / mItemViewHeight
        //把我们需要的Item添加到这个集合
        var j = 1
        for (i in bottomItemPosition - 1 downTo 0) {
            //计算偏移量
            val maxOffset = (getVerticalSpace() - mItemViewHeight) / 2 * Math.pow(0.8, j.toDouble())
            //这个Item的top值
            val start = (remainSpace - lastOneVisiblePercent * maxOffset).toInt()
            //这个Item需要缩放的比例
            val scaleXY = (Math.pow(mScale.toDouble(), (j - 1).toDouble()) * (1 - lastOneVisiblePercent * (1 - mScale))).toFloat()
            val positonOffset = lastOneVisiblePercent
            //Item上面的距离占RecyclerView可用高度的比例
            val layoutPercent = start * 1.0f / getVerticalSpace()
            var info = layoutInfos.get(j)
            if (info != null) {
                info.start = start
                info.top = start.toFloat()
                info.scaleXY = scaleXY
                info.positonOffset = positonOffset
                info.layoutPercent = layoutPercent
                info.mIsBottom = false
            } else {
                info = ItemViewInfo(start, scaleXY, positonOffset, layoutPercent, top = start.toFloat())
                layoutInfos.put(j, info)
            }
            remainSpace = (remainSpace - maxOffset).toInt()
            //在添加Item的同时，计算剩余空间是否可以容下下一个Item，如果不能的话，就不再添加了
            if (remainSpace <= 0) {
                info.top = (remainSpace + maxOffset).toFloat()
                info.positonOffset = 0f
                info.layoutPercent = info.top / getVerticalSpace().toFloat()
                info.scaleXY = Math.pow(mScale.toDouble(), (j - 1).toDouble()).toFloat()
                LogUtil.d(TAG, "layoutInfo count is ${j + 1}")
                break
            }
            j++
        }
        //底部item的信息
        if (bottomItemPosition < mItemCount) {
            val start = getVerticalSpace() - bottomItemVisibleHeight
            var item = layoutInfos.get(0)
            if (item == null) {
                item = ItemViewInfo(start.toInt(), 1f, bottomItemVisibleHeight * 1.0f / mItemViewHeight, (start * 1.0 / getVerticalSpace()).toFloat(), top = start.toFloat())
                        .setIsBottom()
                layoutInfos.put(0, item)
            } else {
                item.start = start
                item.scaleXY = 1f
                item.positonOffset = bottomItemVisibleHeight * 1.0f / mItemViewHeight
                item.layoutPercent = (start * 1.0 / getVerticalSpace()).toFloat()
                item.top = start.toFloat()
                item.setIsBottom()
            }
        } else {
            LogUtil.d(TAG, "index 0 unhandled")
            bottomItemPosition = bottomItemPosition - 1//99
        }
        //这里做的是回收处理
        val layoutCount = layoutInfos.size
        LogUtil.d(TAG, "layoutInfo size is $layoutCount")
        val startPos = Math.max(0, bottomItemPosition - (layoutCount - 1))
        val endPos = Math.max(startPos, bottomItemPosition)
        val childCount = childCount
        for (i in childCount - 1 downTo 0) {
            val childView = getChildAt(i)
            if (null == childView) {
                continue
            }
            val pos = getPosition(childView)
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler)
            }
        }
        detachAndScrapAttachedViews(recycler)
        //这里主要是对需要显示的Item进行排列以及缩放
        for (i in endPos - startPos downTo 0) {
            val view = recycler.getViewForPosition(endPos - i)
            if (null == view) {
                continue
            }
            val layoutInfo = layoutInfos.get(i)
            if (layoutInfo == null) {
                LogUtil.d(TAG, "index $i is empty one")
                continue
            }
            addView(view)
            measureChildWithExactlySize(view)
            val left = (getHorizontalSpace() - mItemViewWidth) / 2
            layoutDecoratedWithMargins(view, left, layoutInfo.top.toInt(), left + mItemViewWidth, layoutInfo.top.toInt() + mItemViewHeight)
            view.setPivotX(view.width / 2f)
            view.setPivotY(0f)
            view.scaleX = layoutInfo.scaleXY
            view.scaleY = layoutInfo.scaleXY
        }
    }

    /**
     * 测量itemview的确切大小
     */
    private fun measureChildWithExactlySize(child: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(mItemViewWidth, View.MeasureSpec.EXACTLY);
        val heightSpec = View.MeasureSpec.makeMeasureSpec(mItemViewHeight, View.MeasureSpec.EXACTLY);
        child.measure(widthSpec, heightSpec);
    }

    /**
     * 获取RecyclerView的显示高度
     */
    fun getVerticalSpace() = height - paddingTop - paddingBottom;

    /**
     * 获取RecyclerView的显示宽度
     */
    fun getHorizontalSpace() = width - paddingLeft - paddingRight;

    override fun canScrollVertically(): Boolean = true

    data class ItemViewInfo(var start: Int, var scaleXY: Float, var positonOffset: Float, var layoutPercent: Float,
                            var top: Float = 0f, var mIsBottom: Boolean = false) {

        /*var top = 0f
            get() = field
            set(value) {
                field = value
            }*/
//        var mIsBottom = false

        fun setIsBottom(): ItemViewInfo {
            mIsBottom = true
            return this
        }
    }
}