package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tab3 extends Fragment {

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