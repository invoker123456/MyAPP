package com.invoker.myapp.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.invoker.myapp.R;
import com.invoker.myapp.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by invoker on 2018-06-21
 * Description: 加载图片测试Glide  和 Picasso
 */
public class PictureActivity extends MyBaseActivity {

    @BindView(R.id.picture_img_glide_round)
    ImageView picture_img_glide_round;
    @BindView(R.id.picture_img_glide_circle)
    ImageView picture_img_glide_circle;
    @BindView(R.id.picture_img_glide_gif)
    ImageView picture_img_glide_gif;

    @BindView(R.id.photo_view)
    PhotoView photo_view;

    String url = "https://ws1.sinaimg.cn/large/0065oQSqly1g04lsmmadlj31221vowz7.jpg";
    String url_gif = "http://imgsrc.baidu.com/forum/w%3D580/sign=fb05290269600c33f079dec02a4d5134/35146059252dd42ab852ebbd013b5bb5c9eab808.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        ButterKnife.bind(this);

//        photo_view.setImageResource(R.drawable.p4);

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.p4)
                .error(R.drawable.p1)
                .into(photo_view);

        // Glide 圆形 方法1
//        Glide.with(this)
//                .load(url)
//                .placeholder(R.drawable.p4)
//                .error(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.p1 : R.drawable.p2)
//                .transform(new GlideCircleTransform(this))
//                .into(img_glide);

        // Glide 圆角
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.p4)
                .error(R.drawable.p1)
                //.centerCrop() 千万不要加，加了就没有圆角效果了
                .transform(new CenterCrop(this), new GlideRoundTransform(this, 20))
                .into(picture_img_glide_round);

        // Glide 圆形 方法2
        Glide.with(this)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.p4)
                .error(R.drawable.p1)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                .into(new BitmapImageViewTarget(picture_img_glide_circle) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(PictureActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        picture_img_glide_circle.setImageDrawable(circularBitmapDrawable);
                    }
                });

        // Glide 加载 gif
        Glide.with(this)
                .load(R.drawable.timg)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  // 不加缓存策略不显示
                .into(picture_img_glide_gif);

    }

    /**
     * Glide-所使用显示圆形图片的转换类
     */
    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }

    }

    /**
     * Glide 圆角 Transform
     */
    public static class GlideRoundTransform extends BitmapTransformation {

        private static float radius = 0f;

        /**
         * 构造函数 默认圆角半径 4dp
         *
         * @param context Context
         */
        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        /**
         * 构造函数
         *
         * @param context Context
         * @param dp      圆角半径
         */
        public GlideRoundTransform(Context context, int dp) {
            super(context);
            radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

}
