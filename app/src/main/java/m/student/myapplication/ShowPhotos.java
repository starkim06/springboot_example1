package m.student.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import m.student.myapplication.Retrofit.ResponseBody.ResponseGet;
import m.student.myapplication.Retrofit.RetroBaseApiService;
import m.student.myapplication.Retrofit.RetroCallback;
import m.student.myapplication.Retrofit.RetroClient;

public class ShowPhotos extends AppCompatActivity {
    RetroClient retroClient;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static String Base_URL = RetroBaseApiService.Base_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<PhotoInfo> photoInfoArrayList = new ArrayList<>();
        Intent intent = getIntent();
        String sort = intent.getExtras().getString("sort");
        if(sort.equals("time")){
            retroClient.getFirst(new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    Log.e("에러에러", t.toString());


                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    List<ResponseGet> data = (List<ResponseGet>) receivedData;
                    Log.e("완료코드 : ",String.valueOf(code));
                    if (!data.isEmpty()) {
                        for(int i=0;i<data.size();i++) {
                            Log.e("이번에 받은거 : ",data.get(i).title + " 링크 : "+data.get(i).url);
                            photoInfoArrayList.add(new  PhotoInfo(Base_URL+"photo/showPhoto/"+data.get(i).url,data.get(i).title));

                        }
                        Log.e("진짜완료", String.valueOf(photoInfoArrayList.size()));

                        MyAdapter myAdapter = new MyAdapter(photoInfoArrayList);

                        mRecyclerView.setAdapter(myAdapter);
                    } else {

                    }
                }
                @Override
                public void onFailure(int code) {

                }
            });
        } else {
            retroClient.getSecond(new RetroCallback() {
                @Override
                public void onError(Throwable t) {
                    Log.e("에러에러", t.toString());


                }

                @Override
                public void onSuccess(int code, Object receivedData) {
                    List<ResponseGet> data = (List<ResponseGet>) receivedData;
                    Log.e("완료코드 : ",String.valueOf(code));
                    if (!data.isEmpty()) {
                        for(int i=0;i<data.size();i++) {
                            Log.e("이번에 받은거 : ",data.get(i).title + " 링크 : "+data.get(i).url);
                            photoInfoArrayList.add(new  PhotoInfo(Base_URL+"photo/showPhoto/"+data.get(i).url,data.get(i).title));

                        }
                        Log.e("진짜완료", String.valueOf(photoInfoArrayList.size()));

                        MyAdapter myAdapter = new MyAdapter(photoInfoArrayList);

                        mRecyclerView.setAdapter(myAdapter);
                    } else {

                    }
                }
                @Override
                public void onFailure(int code) {

                }
            });
        }




    }
}
