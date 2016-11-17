package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Tab3 extends Fragment {
    ListView list;
    String[] web = {
            "Shopping",
            "Eating Out",
            "Travel",
            "Entertainment",
            "Fuel",
            "General",
            "Gifts"
    };
    Integer[] imageId = {
            R.drawable.shopping,
            R.drawable.eating_out,
            R.drawable.travel,
            R.drawable.entertainment,
            R.drawable.fuel,
            R.drawable.general,
            R.drawable.gifts
    };
    ImageButton FAB1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);
        FAB1 = (ImageButton) v.findViewById(R.id.imageButton1);
        FAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategory.class);
                startActivity(intent);
            }
        });
//        CustomList adapter = new CustomList(MainActivity.this, web, imageId);
//        list=(ListView) v.findViewById(R.id.list);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//               // Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
        return v;

    }
}