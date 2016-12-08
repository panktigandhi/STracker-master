package calpoly.edu.stracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GridviewImage extends AppCompatActivity {
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_image);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, AddCategory.class);
                i.putExtra("Image",position);
                setResult(2,i);
                finish();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return mThumbIds[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setBackgroundResource(R.drawable.custom_bg);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        private Integer[] mThumbIds = {
                R.drawable.shopping,
                R.drawable.eating_out,
                R.drawable.travel,
                R.drawable.entertainment,
                R.drawable.fuel,
                R.drawable.general,
                R.drawable.gifts,
                R.drawable.ic_beach_access_black_36dp,
                R.drawable.ic_child_friendly_black_36dp,
                R.drawable.ic_edit_black_36dp,
                R.drawable.ic_fitness_center_black_36dp,
                R.drawable.ic_headset_black_36dp,
                R.drawable.ic_hotel_black_36dp,
                R.drawable.ic_kitchen_black_36dp,
                R.drawable.ic_laptop_mac_black_36dp,
                R.drawable.ic_local_drink_black_36dp,
                R.drawable.ic_local_hospital_black_36dp,
                R.drawable.ic_local_offer_black_36dp,
                R.drawable.ic_local_parking_black_36dp,
                R.drawable.ic_local_pizza_black_36dp,
                R.drawable.ic_phone_android_black_36dp,
                R.drawable.ic_photo_camera_black_36dp,
                R.drawable.ic_spa_black_36dp,
                R.drawable.ic_speaker_black_36dp,
                R.drawable.ic_tv_black_36dp,
                R.drawable.ic_watch_black_36dp,
                R.drawable.ic_show_chart_black_36dp,
                R.drawable.ic_airplanemode_active_black_36dp,
                R.drawable.ic_shopping_basket_black_36dp,
                R.drawable.ic_payment_black_36dp,
                R.drawable.ic_favorite_black_36dp,
                R.drawable.ic_explore_black_36dp,
                R.drawable.ic_call_black_36dp,
        };
    }
}

