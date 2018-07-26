/*
 * Copyright (c) 2015 App Annie Inc. All rights reserved.
 */

package productlist.exam.productlistexam;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

public class PicassoWrapper {
    public static final String PICASSO_TAG = "picasso_tag";
    private static Transformation mTransformation;
    private static int sSideLength = 0;

    public static void loadUrlIntoImageView(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            if (mTransformation == null) {
                mTransformation = new RoundCornerTransformation(imageView.getResources()
                        .getDimensionPixelSize(R.dimen.round_corner_radius));
            }
            if (sSideLength == 0) {
                sSideLength = imageView.getResources().getDimensionPixelSize(R.dimen.product_icon_size);
            }
            try {
                ProductListApplication.getPicasso().load(url)
                        .resize(sSideLength, sSideLength)
                        .centerInside()
                        .config(Bitmap.Config.RGB_565)
                        .transform(mTransformation)
                        .tag(PICASSO_TAG)
                        .into(imageView);
            } catch (Exception ignored) {
            }
        }
    }

    public static class RoundCornerTransformation implements Transformation {
        private int radius;

        public RoundCornerTransformation(int radius) {
            this.radius = radius;
        }

        @Override
        public Bitmap transform(final Bitmap source) {
            if (source.getWidth() != source.getHeight()) {
                return source;
            }

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap
                    .Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius,
                    radius, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "round_corner";
        }
    }

    public static class RoundTransformation implements Transformation {
        private int radius;

        public RoundTransformation(int radius) {
            this.radius = radius;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            if (source.getWidth() != source.getHeight()) {
                return source;
            }
            int side = radius * 2;
            Bitmap input = Bitmap.createScaledBitmap(source, side, side, false);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(input, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(side, side, Bitmap
                    .Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(0, 0, side, side), radius, radius, paint);

            if (input != output) {
                input.recycle();
            }
            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "round";
        }
    }
}
