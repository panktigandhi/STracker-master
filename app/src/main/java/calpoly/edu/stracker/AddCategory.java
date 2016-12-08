package calpoly.edu.stracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddCategory extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText editcategoryname;
    ImageView imageView;
    final Context context = this;
    String categoryType;
    private RadioGroup radioGroup2;
    public ByteArrayOutputStream bos;
    public Bitmap bitmap;
    public byte[] bitmapdata;

    int imageposition;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mydb = new DatabaseHelper(this);
        getSupportActionBar().setTitle("Add Category");
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup1);
//        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.radioButtonExpense:
//
//                        categoryType="expense";
//                        //Toast.makeText(getApplicationContext(), "Expense RadioButton checked", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.radioButtonIncome:
//
//                        categoryType="income";
//                        // Toast.makeText(getApplicationContext(), "Income RadioButton checked", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });

        imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setImageResource(R.drawable.general);
        editcategoryname = (EditText) findViewById(R.id.edittext_categoryname);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GridviewImage.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                imageposition = data.getIntExtra("Image", 0);
                imageView.setImageResource(mThumbIds[imageposition]);
            }
        }
    }

    public void addCategory() {
        // CategoryManager.getCategories();
        bos = new ByteArrayOutputStream();
        bitmap = BitmapFactory.decodeResource(getResources(), mThumbIds[imageposition]);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bitmapdata = bos.toByteArray();
        boolean isInserted = mydb.insertCategory(bitmapdata, editcategoryname.getText().toString(), categoryType);
        if (isInserted == true)
            Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_add) {
            addCategory();
        }

        return super.onOptionsItemSelected(item);
    }

}
