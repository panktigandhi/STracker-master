package calpoly.edu.stracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddCategory extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText editimage;
    EditText editcategoryname;
    ImageView imageView;
    final Context context = this;
    int imageposition;
    private Integer[] mThumbIds = {
            R.drawable.shopping,
            R.drawable.eating_out,
            R.drawable.travel,
            R.drawable.entertainment,
            R.drawable.fuel,
            R.drawable.general,
            R.drawable.gifts
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mydb = new DatabaseHelper(this);
        getSupportActionBar().setTitle("Add Category");
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            imageposition = extras.getInt("image");
//        }

        imageView = (ImageView) findViewById(R.id.imageview);
        //imageView.setImageResource(mThumbIds[imageposition]);
        //  editimage = (EditText) findViewById(R.id.edittext_image);
        //editimage.setInputType(InputType.TYPE_NULL);


        editcategoryname = (EditText) findViewById(R.id.edittext_categoryname);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GridviewImage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addCategory() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        editimage.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, stream);

        boolean isInserted = mydb.insertCategory(stream.toByteArray(), editcategoryname.getText().toString());
        if (isInserted == true)
            Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
