package org.wolink.android.apis.graphic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wolink.android.apis.R;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BuiltInDrawable extends ListActivity {
	private List<Map<String, Object>> drList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		drList = new ArrayList<Map<String, Object>>();
		
		try {
			Class<?> c = Class.forName("android.R");
			Class<?>[] cset = c.getClasses();
			for (Class<?> ic : cset) {
				if (ic.getSimpleName().equals("drawable")) {
					Field[] fset = ic.getFields();
					for(Field f : fset) {
						HashMap<String, Object> hm = new HashMap<String, Object>();
						hm.put("dr", getResources().getDrawable(f.getInt(null)));
						hm.put("name", f.getName());
						drList.add(hm);				
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setContentView(R.layout.builtindrawable);
		
		getListView().setAdapter(new DrawableAdapter(
				this,
				drList,
				R.layout.builtindrawable_item,
				new String[] {},
				new int[] {}
		));
	}
	
	class DrawableAdapter extends SimpleAdapter{
		private LayoutInflater mInflater;
		
		public DrawableAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.builtindrawable_item, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.image);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.text.setText((String)drList.get(position).get("name"));
            holder.icon.setImageDrawable((Drawable)drList.get(position).get("dr"));
            
            return convertView;
        }

        class ViewHolder {
            TextView text;
            ImageView icon;
        }
	}
}
