package com.ftfl.myvisitingplaces.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class LoadImage extends AsyncTask<Object, Integer, Bitmap> {

	ImageView imv;
	String imagePath;

	/*
	 * constructor class to set image view and image path
	 */
	public LoadImage(ImageView eImv, String eImagePath, int serial) {
		imv = eImv;
		imagePath = eImagePath;
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		// TODO Auto-generated method stub

		Bitmap correctBmp = null;

		try {
			File f = new File(imagePath);
			ExifInterface exif = new ExifInterface(f.getPath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			int angle = 0;

			if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				angle = 90;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
				angle = 180;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				angle = 270;
			}

			Matrix mat = new Matrix();
			mat.postRotate(angle);
			BitmapFactory.Options option = new BitmapFactory.Options();
			option.inSampleSize = 8;
			Bitmap bmp1 = BitmapFactory.decodeStream(new FileInputStream(f),
					null, option);
			correctBmp = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(),
					bmp1.getHeight(), mat, true);
		} catch (IOException e) {
			Log.w("TAG", "-- Error in setting image");
		} catch (OutOfMemoryError oom) {
			Log.w("TAG", "-- OOM Error in setting image");
		}

		return correctBmp;

	}

	@Override
	protected void onPostExecute(Bitmap result) {

		try {
			if (result != null && imv != null) {
				imv.setVisibility(View.VISIBLE);
				imv.setImageBitmap(result);
			} else {
				imv.setVisibility(View.GONE);
			}
			this.cancel(false);
		} catch (Exception e) {
			System.out.print(e);
		}

	}

}
