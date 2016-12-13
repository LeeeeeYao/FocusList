package file.com.bxs.focuslistdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义布局
 */
public class ImgRollView extends RelativeLayout {

    private final long ROLL_DELAY = 5000;

    private ViewPager mViewPager;
    private ImgRollAdapter mAdapter;
    private List<FocusAdBean> mData;
    private List<View> indicators;
    private LinearLayout mIndicatorView;
    private int wh, padding;
    private int type = 0;
    private int type_2 = 0;

    public ImgRollView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.imgrollview, this, true);
        init();
    }

    public ImgRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.imgrollview, this, true);
        init();
    }

    public ImgRollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.imgrollview, this, true);
        init();
    }

    private void init() {
        indicators = new ArrayList<View>();
        padding = ScreenUtil.getPixel(getContext(), 1);
        wh = ScreenUtil.getPixel(getContext(), 5);
        initViews();
        initData();
    }

    private void initViews() {
        mIndicatorView = (LinearLayout) findViewById(R.id.contanierIndicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mData = new ArrayList<FocusAdBean>();
        mAdapter = new ImgRollAdapter(this, getContext(), mData, type);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int size = mData.size();
                if (size == 0) {
                    size = 1;
                }
                int newPosition = position % size;
                for (int i = 0; i < indicators.size(); i++) {
                    indicators.get(i).setBackgroundResource(R.drawable.img_unselect_icon);
                    if (i == newPosition) {
                        indicators.get(i).setBackgroundResource(R.drawable.img_select_icon);
                    }
                }
                restartHandler();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void initIndicators() {
        mIndicatorView.removeAllViews();
        indicators.clear();

        if (mData.size() > 1) {
            for (int i = 0; i < mData.size(); i++) {
                View v = createIndicator();
                if (i == 0) {
                    v.setBackgroundResource(R.drawable.img_select_icon);
                } else {
                    v.setBackgroundResource(R.drawable.img_unselect_icon);
                }
                indicators.add(v);
                mIndicatorView.addView(v);
            }
        }

    }

    private View createIndicator() {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(wh, wh);
        lp.leftMargin = padding;
        lp.rightMargin = padding;
        tv.setLayoutParams(lp);
        return tv;
    }

    private void initData() {
        setRollAnim();
    }

    //每隔5s循环
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(0, ROLL_DELAY);

        }
    };

    //	重新开始5s循环
    protected void restartHandler() {
        // TODO Auto-generated method stub
        mHandler.removeMessages(0);
        if (mData.size() > 1) {
            mHandler.sendEmptyMessageDelayed(0, ROLL_DELAY);
        }
    }

    private void setRollAnim() {
        // 设置动画
        if (mData.size() > 1) {
            mHandler.sendEmptyMessageDelayed(0, ROLL_DELAY);
        }

    }

    public void updateData(List<FocusAdBean> data) {
        mData.clear();
        mData.addAll(data);
        initIndicators();
        mAdapter = new ImgRollAdapter(this, getContext(), mData, type);
        mViewPager.setAdapter(mAdapter);
        restartHandler();

    }

    private OnItemClickLisener mListener;

    public void setOnItemClickLisener(OnItemClickLisener lisener) {
        mListener = lisener;
    }

    public OnItemClickLisener getOnItemClickLisener() {
        return mListener;
    }

    public interface OnItemClickLisener {
        void onItemClick(int i);
    }
}
