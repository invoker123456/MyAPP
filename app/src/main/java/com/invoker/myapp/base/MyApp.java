package com.invoker.myapp.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.invoker.myapp.db.DaoMaster;
import com.invoker.myapp.db.DaoSession;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by invoker on 2018/03/20.
 */
public class MyApp extends android.app.Application {
    private static Context mContext;

    Map<String, Object> headerMaps = new HashMap<>();

    public static MyApp instance;

    private static DaoSession daoSession;

    // 乌苏
    public static final String host = "sq.xjws.gov.cn";
    public static final String port = "9003";
    public static final String url = "http://sq.xjws.gov.cn:9003/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        initGreenDao();
        // Mob 短信验证 初始化
        MobSDK.init(this);
    }

    /**
     * 初始化 GreenDao
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "test.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    // 获取 DaoSession
    public static DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 解决 gson 有泛型 转换问题   jsonString -> list
     *
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getObjectList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Context getContext() {
        return mContext;
    }

    public SharedPreferences sp;
    public static final String PREF_HOST = "pref_host";
    public static final String PREF_PORT = "pref_port";
    public static final String PREF_REGISTERED = "pref_registered";
    public static final String PREF_DEVICE_ID = "pref_device_id";
    public static final String PREF_BIND_ADDR = "pref_bind_mac";
    public static final String PREF_BIND_NAME = "pref_bind_name";
    public static final String PREF_BIND_BLE = "pref_bind_ble";

    public String getHost() {
        if (!sp.contains(PREF_HOST)) {
            setHost(host);
            return host;
        }
        return sp.getString(PREF_HOST, host);
    }

    public void setHost(String value) {
        sp.edit().putString(PREF_HOST, value).commit();
    }

    public int getPort() {
        if (!sp.contains(PREF_PORT)) {
            setPort(port);
            return Integer.valueOf(port);
        }
        String s = sp.getString(PREF_PORT, port);
        return s.matches("\\d+") ? Integer.valueOf(s) : Integer.valueOf(port);
    }

    public void setPort(String value) {
        sp.edit().putString(PREF_PORT, value).commit();
    }

    public boolean isRegistered() {

        return sp.getBoolean(PREF_REGISTERED, false);
    }

    public void setRegistered(boolean value) {
        sp.edit().putBoolean(PREF_REGISTERED, value).commit();
    }

    public int getDeviceId() {
        return sp.getInt(PREF_DEVICE_ID, -1);
    }

    public void setDeviceId(int value) {
        sp.edit().putInt(PREF_DEVICE_ID, value).commit();
    }

    public String getAddress() {
        return sp.getString(PREF_BIND_ADDR, null);
    }

    public void setAddress(String value) {
        sp.edit().putString(PREF_BIND_ADDR, value).commit();
    }

    public String getName() {
        return instance.sp.getString(PREF_BIND_NAME, null);
    }

    public void setName(String value) {
        sp.edit().putString(PREF_BIND_NAME, value).commit();
    }

    public boolean getBle() {
        return sp.getBoolean(PREF_BIND_BLE, true);
    }

    public void setBle(boolean value) {
        sp.edit().putBoolean(PREF_BIND_BLE, value).commit();
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
