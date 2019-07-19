package com.invoker.myapp.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.invoker.myapp.BuildConfig;
import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;
import com.invoker.myapp.request.RequestUtil;
import com.invoker.myapp.test.RetrofitTestActivityMy;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateActivity extends MyBaseActivity {
    ProgressDialog pd; // 进度条对话框
    Dialog dialog;    //  对话框
    @BindView(R.id.main_btn)
    Button main_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewActivity(RetrofitTestActivityMy.class);
            }
        });

        pd = new ProgressDialog(this);
//        pd.setCancelable(false);// 必须一直下载完，不可取消
        pd.setCancelable(true);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressNumberFormat("%1d kb/%2d kb");


    }

    @Override
    protected void onStart() {
        super.onStart();
        new CheckUpdateTask().start();
    }

    private final static int TASK_START = 0;
    private final static int SUCCESS = 1;
    private final static int SHOW_DIALOG = 2;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TASK_START: {
                    String message = (String) msg.obj;
                    pd.setTitle("版本升级");
                    pd.setMessage(message);
                    if (!pd.isShowing() && !isFinishing()) {
                        pd.show();
                    }
                    break;
                }
                case SUCCESS: {
                    String message = (String) msg.obj;
                    pd.setMessage(message);
                    if (!pd.isShowing() && !isFinishing()) {
                        pd.dismiss();
                    }
                    break;
                }
                case SHOW_DIALOG: {
                    final String message = (String) msg.obj;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new AlertDialog.Builder(UpdateActivity.this)
                                    .setTitle("发现新版本")
                                    .setMessage("最新版本: " + message)
                                    .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            showToast("下次再说");
                                        }
                                    })
                                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            showToast("立即更新");
                                            StartDownload();
                                        }
                                    })
                                    .create();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                    break;
                }
            }

        }
    };

    void sendMessage(int what, String msg) {
        Message message = handler.obtainMessage();
        message.what = what;
        message.obj = msg;
        message.sendToTarget();
    }

    public void StartDownload() {
        sendMessage(TASK_START, "正在安装升级,请稍等...");
        downloadApk(UpdateActivity.this, "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk", "alipay.apk");
    }

    public class CheckUpdateTask extends Thread {
        @Override
        public void run() {
            //TODO inflate the layout here.
            FileOutputStream output = null;
            InputStream input = null;
            try {
                //TODO
                String s;
                s = RequestUtil.requestText("http", "/plug-in/fkgl/ver_ytj.txt");

                if (Integer.valueOf(s.trim()) <= BuildConfig.VERSION_CODE) return;

                sendMessage(SHOW_DIALOG, s);
//                sendMessage(TASK_START, "正在安装升级,请稍等...");
//                downloadApk(UpdateActivity.this, "http://www.lfsoft.net:9017/plug-in/fkgl/latest.apk", "latest.apk");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (input != null)
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }


    public final static String SD_FOLDER = Environment.getExternalStorageDirectory() + "/VersionChecker/";

    /**
     * 从服务器中下载APK
     */
    public void downloadApk(final Context mContext, final String downURL, final String appName) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    File file = downloadFile(downURL, appName, pd);
                    sleep(3000);
                    installApk(mContext, file);
                    // 结束掉进度条对话框
                    sendMessage(SUCCESS, "完成!");
                    //pd.dismiss();
                } catch (Exception e) {
                    //pd.dismiss();
                }
            }
        }.start();
    }

    private static File downloadFile(String path, String appName, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            float a = bytes2kb(conn.getContentLength());
            pd.setMax((int) bytes2kb(conn.getContentLength()));
            InputStream is = conn.getInputStream();
            String fileName = SD_FOLDER + appName + ".apk";
            File file = new File(fileName);
            // 目录不存在创建目录
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress((int) bytes2kb(total));
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            throw new IOException("未发现有SD卡");
        }
    }

    /**
     * 安装apk
     */
    private static void installApk(Context mContext, File file) {
        Uri fileUri = Uri.fromFile(file);
        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        it.setDataAndType(fileUri, "application/vnd.android.package-archive");
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止打不开应用
        mContext.startActivity(it);
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static float bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal kilobyte = new BigDecimal(1024);
        float returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
        return (returnValue);
    }
}