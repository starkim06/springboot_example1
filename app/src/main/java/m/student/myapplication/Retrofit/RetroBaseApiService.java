package m.student.myapplication.Retrofit;

import java.util.HashMap;
import java.util.List;

import m.student.myapplication.PhotoInfo;
import m.student.myapplication.Retrofit.RequestBody.RequestPut;
import m.student.myapplication.Retrofit.RequestBody.UploadResponse;
import m.student.myapplication.Retrofit.ResponseBody.ResponseGet;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetroBaseApiService {
    public static final String Base_URL = "http://c9c1f2b3.ngrok.io/";

    @GET("/photo/showPicturesByTime")
    Call<List<ResponseGet>> getFirst();

    @GET("/photo/showPicturesByName")
    Call<List<ResponseGet>> getSecond();

    @Multipart
    @POST("/photo/upload")
    Call<UploadResponse> uploadImage(@Part MultipartBody.Part image ,@Part("title") String title);
}

