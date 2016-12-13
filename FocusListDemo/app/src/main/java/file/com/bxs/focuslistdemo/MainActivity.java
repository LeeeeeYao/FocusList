package file.com.bxs.focuslistdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImgRollView imgRollView;
    private List<FocusAdBean> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this); //初始化
        setContentView(R.layout.activity_main);
        mData = new ArrayList<FocusAdBean>();
        imgRollView = (ImgRollView) findViewById(R.id.imgrollview);
        for (int i = 0; i < 5; i++) {
            FocusAdBean f = new FocusAdBean();
            f.setId(i + "");
            if(i == 0){
               f.setImg("http://pic.58pic.com/58pic/17/73/56/76658PICPmc_1024.jpg");
            }
            if(i == 1){
                f.setImg("http://pic74.nipic.com/file/20150805/12033066_235333091000_2.jpg");
            }
            if(i == 2){
                f.setImg("http://pic2.ooopic.com/13/58/40/86bOOOPIC48_1024.jpg");
            }
            if(i == 3){
                f.setImg("http://pic2.ooopic.com/11/72/21/20bOOOPIC2c_1024.jpg");
            }
            if(i == 4){
                f.setImg("http://pic.58pic.com/58pic/17/44/19/79E58PIC5q4_1024.jpg");
            }
            f.setTitle("标题");
            f.setType("0");
            f.setVal("10");
            mData.add(f);
        }
        imgRollView.updateData(mData);
        imgRollView.setOnItemClickLisener(new ImgRollView.OnItemClickLisener() {
            @Override
            public void onItemClick(int i) {
                Toast.makeText(MainActivity.this,"你点击了第" + (i + 1) + "张图",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
