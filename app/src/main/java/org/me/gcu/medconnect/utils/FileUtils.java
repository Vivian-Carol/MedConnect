package org.me.gcu.medconnect.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static File getFileFromUri(Context context, Uri uri) {
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            ContentResolver contentResolver = context.getContentResolver();
            String mimeType = contentResolver.getType(uri);
            String fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
            file = File.createTempFile("temp", "." + fileExtension, context.getCacheDir());

            inputStream = contentResolver.openInputStream(uri);
            outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File createFileFromBitmap(Context context, Bitmap bitmap) {
        File file = null;
        OutputStream outputStream = null;

        try {
            file = File.createTempFile("temp_image", ".jpg", context.getCacheDir());
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public static File createTempFileFromBitmap(Context context, Bitmap bitmap) {
        return null;
    }
}
