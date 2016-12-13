package file.com.bxs.focuslistdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by bxs2 on 2016/11/11.
 */

public class GuaGuaKa extends View {

    private Bitmap mBgBitmap, mFgBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath;
    private float mBgBitmapWidth;
    private float LastX, dx, sumX;
    private boolean isFinish = false;

    public GuaGuaKa(Context context) {
        super(context);
        init();
    }

    public GuaGuaKa(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuaGuaKa(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化透明画笔
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(
                new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //初始化路径
        mPath = new Path();
        //初始化底层图片
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_loading);
        //获取底层宽度
        mBgBitmapWidth = mBgBitmap.getWidth();
        //创建顶层图片
        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(),
                mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //创建顶层画布
        mCanvas = new Canvas(mFgBitmap);
        //顶层画布画上灰色
        mCanvas.drawColor(Color.GRAY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                mCanvas.drawPath(mPath, mPaint);
                //重新绘制画面
                invalidate();

                dx = Math.abs(event.getX() - LastX);
                if (dx > 0) {
                    //监听左右滑
                    sumX += dx;
                }
                break;
            case MotionEvent.ACTION_UP:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (sumX > mBgBitmapWidth * 4) {
                                isFinish = true;
                                Thread.sleep(1000);
                                postInvalidate();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
        LastX = event.getX();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        if (!isFinish) {
            canvas.drawBitmap(mFgBitmap, 0, 0, null);
        }
    }
}
