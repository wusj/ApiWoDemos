package org.wolink.android.apis.graphic;

import java.util.LinkedList;
import java.util.List;

import org.wolink.android.apis.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class GroupIconDemo extends Activity implements OnItemClickListener {
	
	GridView mGrid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        loadApps(); 

        setContentView(R.layout.groupicondemo);
        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setAdapter(new AppsAdapter());
        
        mGrid.setOnItemClickListener(this);
    }

    private List<ResolveInfo> mApps;
    private List<Bitmap> mBitmaps;

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
//        Paint mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setDither(true);
//        mPaint.setColor(0x88FF0000);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setStrokeWidth(2);
        
        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
        
        mBitmaps = new LinkedList<Bitmap>();
        for(int i = 0; i < mApps.size() / 9; i++) {
        	Bitmap bm = Bitmap.createBitmap(72, 72, Bitmap.Config.ARGB_8888);
        	bm.setDensity(240);
        	Canvas canvas = new Canvas(bm);
//        	canvas.drawRoundRect(new RectF(0f, 0f, 72f, 72f), 8.0f, 8.0f, mPaint);
        	Drawable d1 = getResources().getDrawable(R.drawable.icon_back);
        	d1.setBounds(new Rect(0, 0, 72, 72));
        	d1.draw(canvas);
        	for (int j = 0; j < 9; j++) {
        		int index = i*9 + j;
        		Drawable d = mApps.get(index).activityInfo.loadIcon(getPackageManager());
        		int y = 4 + 20 * (j / 3) + 2 * (j/3);
        		int x = 4 + 20 * (j % 3) + 2 * (j % 3);
        		d.setBounds(new Rect(x, y,  x+20, y+20));
        		d.draw(canvas);
        	}
        	mBitmaps.add(bm);
        }
    }

    public class AppsAdapter extends BaseAdapter {
        public AppsAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i;

            if (convertView == null) {
                i = new ImageView(GroupIconDemo.this);
                i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i.setLayoutParams(new GridView.LayoutParams(72, 72));
            } else {
                i = (ImageView) convertView;
            }

            Bitmap bm = mBitmaps.get(position);
            i.setImageDrawable(new BitmapDrawable(bm));

            return i;
        }


        public final int getCount() {
            return mBitmaps.size();
        }

        public final Object getItem(int position) {
            return mBitmaps.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
        final Intent intent = getIntent();
        final String action = intent.getAction();

        // If the intent is a request to create a shortcut, we'll do that and exit

        if (Intent.ACTION_CREATE_SHORTCUT.equals(action)) {
            setupShortcut(arg2);
            finish();
            return;
        }
		
	}	
	
    private void setupShortcut(int item) {
        // First, set up the shortcut intent.  For this example, we simply create an intent that
        // will bring us directly back to this activity.  A more typical implementation would use a 
        // data Uri in order to display a more specific result, or a custom action in order to 
        // launch a specific operation.

        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(this, this.getClass().getName());

        // Then, set up the container intent (the response to the caller)

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Tools");

        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, mBitmaps.get(item));

        // Now, return the result to the launcher

        setResult(RESULT_OK, intent);
    }
}
