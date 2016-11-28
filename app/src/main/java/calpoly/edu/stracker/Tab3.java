package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tab3 extends Fragment {

//    String[] web = {
//            "Shopping",
//            "Eating Out",
//            "Travel",
//            "Entertainment",
//            "Fuel",
//            "General",
//            "Gifts"
//    };
//    Integer[] imageId = {
//            R.drawable.shopping,
//            R.drawable.eating_out,
//            R.drawable.travel,
//            R.drawable.entertainment,
//            R.drawable.fuel,
//            R.drawable.general,
//            R.drawable.gifts
//    };
    ImageButton FAB1;
    DatabaseHelper mydb;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);
        tv= (TextView) v.findViewById(R.id.textView);
        FAB1 = (ImageButton) v.findViewById(R.id.imageButton1);
        mydb = new DatabaseHelper(getActivity());
        FAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategory.class);
                startActivity(intent);
            }
        });
        Cursor res = mydb.getAllCategories();
        if (res.getCount() == 0) {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }
        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append("Image: " + res.getString(1) + "\n");
            buffer.append("Category: " + res.getString(2) + "\n");
        }
        tv.setText(buffer.toString());
//        List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//        for (int i = 0; i < web.length; i++) {
//            HashMap<String, String> hm = new HashMap<String, String>();
//            hm.put("txt", "Category : " + web[i]);
//            hm.put("img", "Image : " + imageId[i]);
//            categoryList.add(hm);
//        }
//        String[] from = {"txt", "img"};
//
//        int[] to = {R.id.txt, R.id.txt, R.id.img};
//        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), categoryList, R.layout.list_single, from, to);
//        ListView listView = (ListView) v.findViewById(R.id.list);
//        listView.setAdapter(adapter);
        return v;

    }
}