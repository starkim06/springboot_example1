package m.student.myapplication.Retrofit;

import android.content.Context;


import java.util.HashMap;
import java.util.List;

import m.student.myapplication.PhotoInfo;
import m.student.myapplication.Retrofit.RequestBody.RequestPut;
import m.student.myapplication.Retrofit.ResponseBody.ResponseGet;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sonchangwoo on 2017. 1. 6..
 */

public class RetroClient {

    private RetroBaseApiService apiService;
    public static String baseUrl = RetroBaseApiService.Base_URL;
    private static Context mContext;
    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetroClient INSTANCE = new RetroClient(mContext);
    }

    public static RetroClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    private RetroClient(Context context) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public RetroClient createBaseApi() {
        apiService = create(RetroBaseApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public  <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


    public void getFirst( final RetroCallback callback) {
        apiService.getFirst().enqueue(new Callback<List<ResponseGet>>() {
            @Override
            public void onResponse(Call<List<ResponseGet>> call, Response<List<ResponseGet>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseGet>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getSecond( final RetroCallback callback) {
        apiService.getSecond().enqueue(new Callback<List<ResponseGet>>() {
            @Override
            public void onResponse(Call<List<ResponseGet>> call, Response<List<ResponseGet>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.code(), response.body());
                } else {
                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseGet>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

//    public void postFirst(HashMap<String, Object> parameters, final RetroCallback callback) {
//        apiService.postFirst(parameters).enqueue(new Callback<ResponseGet>() {
//            @Override
//            public void onResponse(Call<ResponseGet> call, Response<ResponseGet> response) {
//                if (response.isSuccessful()) {
//                    callback.onSuccess(response.code(), response.body());
//                } else {
//                    callback.onFailure(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseGet> call, Throwable t) {
//                callback.onError(t);
//            }
//        });
//    }

}
