package com.example.usercamerapic;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private static final int CAMERA_RESULT = 0;
	private ImageView mImageView;
	private String mImageFilePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/pic.jpg";
		File file = new File(mImageFilePath);
		Uri imageFileUri = Uri.fromFile(file);
		
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(intent, CAMERA_RESULT);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
//			Bundle bundle =arg2.getExtras();
//			Bitmap bitmap = (Bitmap) bundle.get("data");
			mImageView=(ImageView)findViewById(R.id.iv_camera_return);
//			mImageView.setImageBitmap(bitmap);
			
			Display display = getWindowManager().getDefaultDisplay();
			int dw = display.getWidth();
			int dh = display.getHeight();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			
			Bitmap bitmap2 = BitmapFactory.decodeFile(mImageFilePath,options);
			int hightRio = (int)Math.ceil(options.outHeight/(float)dh);
			int withRio =(int)Math.ceil(options.outWidth/(float)dw);
			
			if (hightRio>1&&withRio>1) {
				if (hightRio>withRio) {
					options.inSampleSize = hightRio;
				}else {
					options.inSampleSize = withRio;
				}
			}
			options.inJustDecodeBounds =false;
			Bitmap bitmap = BitmapFactory.decodeFile(mImageFilePath , options);
			mImageView.setImageBitmap(bitmap);
		}
	}
}
