package com.dialog.vaseelaser.library;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
import static android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;

@SuppressWarnings({"Convert2Lambda"})
public class Utils {

    public static class Hex {
        public static final Charset DEFAULT_CHARSET;
        private static final char[] DIGITS_LOWER;
        private static final char[] DIGITS_UPPER;

        static {
            DEFAULT_CHARSET = StandardCharsets.UTF_8;
            DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        }

        public static String encodeHexString(byte[] data) {
            return new String(encodeHex(data));
        }

        public static char[] encodeHex(byte[] data) {
            return encodeHex(data, true);
        }

        public static char[] encodeHex(byte[] data, boolean toLowerCase) {
            return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
        }

        public static String generateHmacSHA256Signature(String data, String key) throws GeneralSecurityException {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            return Hex.encodeHexString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        }

        protected static char[] encodeHex(byte[] data, char[] toDigits) {
            int l = data.length;
            char[] out = new char[l << 1];
            int i = 0;

            for (int var5 = 0; i < l; ++i) {
                out[var5++] = toDigits[(240 & data[i]) >>> 4];
                out[var5++] = toDigits[15 & data[i]];
            }

            return out;
        }
    }

    public static class DateTools {

        public String getMonthFromMonthIndex(int index) {
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("LLL",Locale.getDefault());

            cal.set(Calendar.MONTH,index-1);

            return month_date.format(cal.getTime());
        }
        public int getCurrentDay() {
            String date = getDateTime();
            String year = date.substring(date.lastIndexOf("-")).replace("-", "");
            return Integer.parseInt(year);
        }

        public int getCurrentMonth() {
            String date = getDateTime();
            return Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
        }

        public int getCurrentYear() {
            String date = getDateTime();
            return Integer.parseInt(date.substring(0, date.indexOf("-")));

        }

        public String getDateTime() {
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
            Date dateTools = new Date();
            return dateFormat.format(dateTools);
        }

        public Date getCurrentDate() {
            try {
                // get current date
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
                String formattedDate = df.format(c);
                return df.parse(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    public static class DisplayTools {
        private final Activity mActivity;

        public DisplayTools(Activity act) {
            mActivity = act;
        }

        @SuppressLint("SourceLockedOrientationActivity")
        public void forceLandscape(boolean force) {
            mActivity.setRequestedOrientation(force ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        public void forceFullBrightness() {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.screenBrightness = BRIGHTNESS_OVERRIDE_FULL;
            mActivity.getWindow().setAttributes(lp);
        }

        public void revertFullBrightness() {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.screenBrightness = BRIGHTNESS_OVERRIDE_NONE;
            mActivity.getWindow().setAttributes(lp);
        }

        public Rect getScreenRectangle() {
            Rect displayRectangle = new Rect();
            Window window = mActivity.getWindow();

            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

            return displayRectangle;
        }

        // Point (width,height);
        public Point getScreenSize() {
            Point point = new Point();
            WindowMetrics windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(mActivity);
            point.x = windowMetrics.getBounds().width();
            point.y = windowMetrics.getBounds().height();
            return point;
        }


        public float convertPixelsToDp(float px) {
            final float density = mActivity.getResources().getDisplayMetrics().density;
            return (int) (px * density);
        }

        public int convertDpToPixel(float dp) {

            Resources r = mActivity.getResources();
            return (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    r.getDisplayMetrics()
            );

        }

    }

    public static class LocaleTools {
        //private static String mLocaleCode;

        public void setLocale(String localeCode, Context appContext) {
            java.util.Locale locale = new java.util.Locale(localeCode);
            //mLocaleCode = localeCode;
            java.util.Locale.setDefault(locale);
            Configuration config =
                    appContext.getResources().getConfiguration();
            //config.locale = locale;
            config.setLocale(locale);
            appContext.getResources().updateConfiguration(config, appContext.getResources().getDisplayMetrics());
        }

    }




    public static class ImageTools {

        private final long mMillis;
        private final static float IMAGE_SIZE_DIVIDER = 1; // imagewidth will be (imagewidth/IMAGE_SIZE_DIVIDER)
        private final static int IMAGE_RESOLUTION_REDUCE_PERCENTAGE = 70; // image quality will be reduced to {IMAGE_RESOLUTION_REDUCE_PERCENTAGE}% of the original
        private final AppCompatActivity mActivity;

        public ImageTools(AppCompatActivity act, long millis) {
            mActivity = act;
            mMillis = millis;
        }

        public File compressImage(File file) {
            Bitmap img = getBitmap(file.getPath());
            if (img != null) {
                img = Bitmap.createScaledBitmap(img, (int) (img.getWidth() / IMAGE_SIZE_DIVIDER), (int) (img.getHeight() / IMAGE_SIZE_DIVIDER), false);

                try {
                    //create a file to write bitmap data
                    File f = new File(mActivity.getCacheDir(), mMillis + ".jpg");
                    //noinspection ResultOfMethodCallIgnored
                    f.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.JPEG, IMAGE_RESOLUTION_REDUCE_PERCENTAGE /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    file = new File(mActivity.getCacheDir(), mMillis + ".jpg");
                    return file;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else return null;

        }

        public Bitmap getBitmap(String path) {

            Uri uri = Uri.fromFile(new File(path));
            InputStream in;
            try {
                final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
                in = mActivity.getContentResolver().openInputStream(uri);

                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in, null, o);
                in.close();


                int scale = 1;
                while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                        IMAGE_MAX_SIZE) {
                    scale++;
                }
                Log.e("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

                Bitmap b;
                in = mActivity.getContentResolver().openInputStream(uri);
                if (scale > 1) {
                    scale--;
                    // scale to max possible inSampleSize that still yields an image
                    // larger than target
                    o = new BitmapFactory.Options();
                    o.inSampleSize = scale;
                    b = BitmapFactory.decodeStream(in, null, o);

                    // resize to desired dimensions
                    int height = b.getHeight();
                    int width = b.getWidth();
                    Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                    double y = Math.sqrt(IMAGE_MAX_SIZE
                            / (((double) width) / height));
                    double x = (y / height) * width;

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                            (int) y, true);
                    b.recycle();
                    b = scaledBitmap;

                    System.gc();
                } else {
                    b = BitmapFactory.decodeStream(in);
                }
                in.close();

                Log.e("", "bitmap size - width: " + b.getWidth() + ", height: " + b.getHeight());
                return b;
            } catch (IOException | NullPointerException e) {
                Log.e("", e.getMessage(), e);
                return null;
            }
        }
    }

    public static class PermissionManager {

        public static void checkPermissions(Context context, String[] perms) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (final String s : perms)
                    if (context.checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED)
                        ((AppCompatActivity) context).requestPermissions(perms, 1111);
            }
        }

        public static boolean allPermsGranted(Context context, String[] perms) {
            int curPermsAllowed = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (final String s : perms)
                    if (context.checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED)
                        curPermsAllowed++;
                return curPermsAllowed == perms.length;
            }
            // backward compatibility
            return true;
        }



    }
}