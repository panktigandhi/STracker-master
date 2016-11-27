package calpoly.edu.stracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {
    DatabaseHelper mydb;
    //EditText editimage;
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
        //editimage = (EditText) findViewById(R.id.edittext_image);
        //editimage.setInputType(InputType.TYPE_NULL);
        imageView= (ImageView) findViewById(R.id.imageview);


        editcategoryname = (EditText) findViewById(R.id.edittext_categoryname);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GridviewImage.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            imageposition = extras.getInt("image");
        }
        imageView.setImageResource(mThumbIds[imageposition]);
    }
//    public void addCategory() {
//
//        boolean isInserted = mydb.insertCategory(editimage.getText().toString(), editcategoryname.getText().toString());
//        if (isInserted == true)
//            Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
//        else
//            Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_add) {
//            addCategory();
        }

        return super.onOptionsItemSelected(item);
    }
}
