package file.com.bxs.focuslistdemo;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 *适配器
 */
public class ImgRollAdapter extends PagerAdapter {

	private List<FocusAdBean> mData;
	private Context mContext;
	private List<SimpleDraweeView> mViews;
	private ImgRollView mView;
	private int type = 0;
	private int count = 1;
	public ImgRollAdapter(ImgRollView view, Context context,
			List<FocusAdBean> data , int type) {
		mView = view;
		mContext = context;
		mData = data;
		this.type = type;
		if(data != null && data.size() > 0){
            count = data.size();
        }
		mViews = new ArrayList<SimpleDraweeView>();
		int i = 0;
		for (FocusAdBean d : data) {
			String path = d.getImg();
			SimpleDraweeView iv = getImgView(i);
			iv.setImageURI(Uri.parse(path));
			mViews.add(iv);
			i++;
		}

	}

	@Override
	public int getCount() {
		if(mData.size() == 0){
			return 0;
		}
		if(mData.size() == 1){
			return 1;
		}
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		int newPosition = position % count;
		SimpleDraweeView view = mViews.get(newPosition);
		//不删除再添加  不知道为什么会显示空白。。。。
		ViewGroup parent = (ViewGroup) view.getParent();
		parent.removeView(view);
        container.addView(view);
		String path = mData.get(newPosition).getImg();
		view.setImageURI(Uri.parse(path));
		return view;
	}

	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// container.removeView(mViews.get(position));
	}

	private SimpleDraweeView getImgView(final int i) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.view_item,null);
		SimpleDraweeView iv = (SimpleDraweeView) view.findViewById(R.id.imgrollview);
		ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
		lp.width = ViewPager.LayoutParams.MATCH_PARENT;
		lp.height = ViewPager.LayoutParams.MATCH_PARENT;
		iv.setLayoutParams(lp);
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ImgRollView.OnItemClickLisener mListener = mView
						.getOnItemClickLisener();
				if (mListener != null)
					mListener.onItemClick(i);
			}
		});

		return iv;
	}

}
