package com.example.think.retrofitdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okhttputils.OkHttpClientManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_content;
    private String TAG = this.getClass().getSimpleName();
    private String itemName;
    private String STR_TAG = TAG + "--->***";
    protected OkHttpClient okHttpClient;
    protected Handler deliveryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_retrofit).setOnClickListener(this);
        tv_content = (TextView) findViewById(R.id.tv_content);

        okHttpClient = OkHttpClientManager.getInstance(this).getOkHttpClient();
        deliveryHandler = OkHttpClientManager.getInstance(MainActivity.this).getDeliveryHandler();
    }

    @Override
    public void onClick(View v) {
        //弹一个对话框，分类选择：
        show();

    }

    private void show() {
        //创建builder对象。
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置标题.
        builder.setTitle("Retrofit2操作");
        //设置列表内容,以及点击事件.
        //参数:1,String数组.2,点击事件.
        final String[] items = {
                "0指定无参数GET请求",
                "1指定@Path和@Query参数GET请求",
                "2指定@QueryMap参数集合GET请求",
                "3指定@Url参数GET请求",
                "4指定GET请求返回ResponseBody",
                "5同步GET请求",
                "6指定@Fild参数POST请求",
                "7指定@FildMap参数POST请求",
                "8指定@Part参数单文件上传",
                "9指定@Body多文件上传带参数",
                "10指定@Part通过多文件上传带参数",
                "11指定@PartMap多文件上传带参数",
                "12指定@Streaming大文件下载",
        };
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemName = items[i] + STR_TAG;
                switch (i) {
                    case 0:
                        get0();
                        break;
                    case 1:
                        get1();
                        break;
                    case 2:
                        get2();
                        break;
                    case 3:
                        get3();
                        break;
                    case 4:
                        get4();
                        break;
                    case 5:
                        get5();
                        break;
                    case 6:
                        get6();
                        break;
                    case 7:
                        get7();
                        break;
                    case 8:
                        get8();
                        break;
                    case 9:
                        get9();
                        break;
                    case 10:
                        get10();
                        break;
                    case 11:
                        get11();
                        break;
                    case 12:
                        get12();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void get11() {
        final File file = new File(Environment.getExternalStorageDirectory(), "1.txt");
        final File file1 = new File(Environment.getExternalStorageDirectory(), "2.png");
        final RequestBody body = MultipartBody.create(MultipartBody.FORM, file);
        final RequestBody body1 = MultipartBody.create(MultipartBody.FORM, file1);
        final RequestBody body2 =RequestBody.create(MultipartBody.FORM, "zxn001");

//        body =MultipartBody.Part.createFormData("file", file.getName(), body).body();
//        body1 =MultipartBody.Part.createFormData("file", file.getName(), body1).body();
//        final RequestBody body2 = MultipartBody.Part.createFormData("username", "zxn001").body();


//        String baseUrl = "http://192.168.1.102/";
        String baseUrl = "http://169.254.38.24/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .uploadFilesByPartMap(new HashMap<String, RequestBody>(){
                    {
                        put("file\"; filename=\""+file.getName(), body);
                        put("file\"; filename=\""+file1.getName(), body1);
                        put("username",body2);
                    }
                })
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            showResult("onResponse"+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showResult("onFailure"+t.getMessage());
                    }
                });
    }

    private void get12() {
        final String baseUrl = "http://1251603248.vod2.myqcloud.com/";
        final String downUrl = "http://1251603248.vod2.myqcloud.com/4c9adaa7vodtransgzp1251603248/30e0819d9031868223192061218/v.f40.mp4";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> response = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(okHttpClient)
                            .build()
                            .create(NetService.class)
                            .download(downUrl)
                            .execute();

                    if (response.isSuccessful()) {
                        deliveryHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showResult("服务器连接成功!");
                            }
                        });
                        boolean ok = writeDisk(response.body());
                        Log.i(TAG, STR_TAG + "run: 下载:" + ok);
                        if (ok) {
                            deliveryHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv_content.setText("下载完成!!");
                                }
                            });
                        }
                    } else {
                        deliveryHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showResult("下载失败!");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean writeDisk(ResponseBody body) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "1.mp4");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long loadSize = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                int len = -1;
                while ((len = inputStream.read(fileReader)) != -1) {
                    outputStream.write(fileReader, 0, len);
                    loadSize += len;
                    Log.i(TAG, STR_TAG + "已经下载: " + loadSize + "/总大小: " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void get10() {
        File file = new File(Environment.getExternalStorageDirectory(), "1.txt");
        File file1 = new File(Environment.getExternalStorageDirectory(), "2.png");
        List<MultipartBody.Part> parts = new ArrayList<>();
        RequestBody body = MultipartBody.create(MultipartBody.FORM, file);
        RequestBody body1 = MultipartBody.create(MultipartBody.FORM, file1);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("file", file1.getName(), body1);
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("username", "zxn001");
        parts.add(part);
        parts.add(part1);
        parts.add(part2);

//        String baseUrl = "http://192.168.1.102/";
        String baseUrl = "http://169.254.38.24/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .uploadFilesByPart(parts)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            showResult("onResponse" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });
    }

    private void get9() {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory(), "d.jpg");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody body = MultipartBody.create(MultipartBody.FORM, file);
        RequestBody body1 = MultipartBody.create(MultipartBody.FORM, file1);
        MultipartBody multipartBody = builder
                .addFormDataPart("file", file.getName(), body)
                .addFormDataPart("file", file1.getName(), body1)
                .addFormDataPart("username", "zxn123")
                .setType(MultipartBody.FORM)
                .build();

//        String baseUrl = "http://192.168.1.102/";
        String baseUrl = "http://169.254.38.24/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .uploadFilesByody(multipartBody)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            showResult("onResponse" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });

    }

    private void get8() {
        //指定上传文件
        File file = new File(Environment.getExternalStorageDirectory(), "3.jpg");
//        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        //封装请求体
//        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        RequestBody body = MultipartBody.create(MultipartBody.FORM, file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);


        //http://192.168.1.100/MyUploadServer/servlet/UpLoadFileServlet
//        String baseUrl = "http://192.168.1.102/";
        String baseUrl = "http://169.254.38.24/";

        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .postUpLoadFile(part)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            showResult("onResponse" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });
    }

    private void get7() {
//        String baseUrl = "http://v5.pc.duomi.com/search-ajaxsearch-searchall?kw=liedehua&pi=1&pz=10";
        String baseUrl = "http://v5.pc.duomi.com/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .getSearchByPost(new HashMap<String, String>() {
                    {
                        put("kw", "liedehua");
                        put("pi", "1");
                        put("pz", "15");
                    }
                }).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    showResult("onResponse" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showResult("onFailure" + t.getMessage());
            }
        });
    }

    private void get6() {
//        String baseUrl = "http://cache.video.iqiyi.com/jp/avlist/202861101/1/?callback=jsonp9";
        String baseUrl = "http://cache.video.iqiyi.com/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .getJpByPost("jsonp9")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            showResult("onResponse" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });

    }

    private void get4() {
        String baseUrl = "http://api.immedc.com/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .getStartBannerResponseBody()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            showResult("onResponse" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });

    }

    private void get5() {
        final String baseUrl = "http://www.weather.com.cn/";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Weather weather = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build()
                            .create(NetService.class)
                            .getWeather()
                            .execute().body();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showResult(weather.toString());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void get3() {
        String baseUrl = "http://www.weather.com.cn/";
//        String url = "http://www.weather.com.cn/data/sk/101010100.html";
        String url = "data/sk/101010100.html";
        new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetService.class)
                .getWeather(url)
                .enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        showResult("onResponse" + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });
    }

    private void get2() {
        String baseUrl = "http://www.kuaidi100.com/";
        new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetService.class)
                .getQueryInfo(new HashMap<String, String>() {
                    {
                        put("type", "yuantong");
                        put("postid", "500379523313");
                    }
                }).enqueue(new Callback<QueryInfo>() {
            @Override
            public void onResponse(Call<QueryInfo> call, Response<QueryInfo> response) {
                showResult("onResponse" + response.body().toString());
            }

            @Override
            public void onFailure(Call<QueryInfo> call, Throwable t) {
                showResult("onResponse" + t.getMessage());
            }
        });
    }

    private void get1() {
        String baseUrl = "http://m2.qiushibaike.com/";
        new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(NetService.class)
                .getArticles("latest", 1)
                .enqueue(new Callback<QiushiModel>() {
                    @Override
                    public void onResponse(Call<QiushiModel> call, Response<QiushiModel> response) {
                        showResult("onResponse" + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<QiushiModel> call, Throwable t) {
                        showResult("onFailure" + t.getMessage());
                    }
                });
    }

    private void get0() {
        String url = "http://www.weather.com.cn/";
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetService.class).getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather body = response.body();
                showResult(body.toString());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                showResult(t.getMessage());
            }
        });
    }

    private void showResult(String result) {
        String mResult = itemName + result;
        tv_content.setText(mResult);
        Toast.makeText(this, mResult, Toast.LENGTH_SHORT).show();
        Log.i(TAG, mResult);
    }
}
