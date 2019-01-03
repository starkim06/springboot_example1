package m.student.myapplication;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import m.student.myapplication.Retrofit.RequestBody.UploadResponse;
import m.student.myapplication.Retrofit.ResponseBody.ResponseGet;
import m.student.myapplication.Retrofit.RetroBaseApiService;
import m.student.myapplication.Retrofit.RetroCallback;
import m.student.myapplication.Retrofit.RetroClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RetroClient retroClient;
    private static final int INTENT_REQUEST_CODE = 100;
    public static final String URL = RetroBaseApiService.Base_URL;
    public static String uploadTitle = "UploadTitle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retroClient = RetroClient.getInstance(this).createBaseApi();

        Button btn_show_upload = findViewById(R.id.btn_upload);
        Button btn_show_photo_time = findViewById(R.id.btn_show_photo_time);
        Button btn_show_photo_title = findViewById(R.id.btn_show_photo_title);

        //사진 업로드
        btn_show_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();

            }
        });

        //사진 보기
        btn_show_photo_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ShowPhotos.class);
                intent.putExtra("sort","time");
                startActivity(intent);

            }
        });

        btn_show_photo_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ShowPhotos.class);
                intent.putExtra("sort","title");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    uploadImage(getBytes(is));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(byte[] imageBytes){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroBaseApiService retroBaseApiService = retrofit.create(RetroBaseApiService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"),imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file","imagege.jpg",requestFile);
        Call<UploadResponse> call = retroBaseApiService.uploadImage(body, uploadTitle);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, retrofit2.Response<UploadResponse> response) {
                if(response.isSuccessful()){
                    UploadResponse responseBody = response.body();
                    Log.e("MainActivity : ","사진업로드 성공 : " + responseBody.toString());
//                    Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();

                } else {
                    ResponseBody errorBody = response.errorBody();
                    Log.e("MainActivity : ","사진업로드 실패 : " + errorBody.toString());
                }
            }
            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.d( "onFailure: ",t.getLocalizedMessage());
            }
        });
    }

    private void show(){
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 제목");
        builder.setMessage("제목을 입력하세요");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString() ,Toast.LENGTH_LONG).show();
                        uploadTitle=edittext.getText().toString();
//                        uploadTitle=uploadTitle.substring(1,uploadTitle.length()-2);
                        Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/jpeg");

                        try {
                            startActivityForResult(intent,INTENT_REQUEST_CODE);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }
}
