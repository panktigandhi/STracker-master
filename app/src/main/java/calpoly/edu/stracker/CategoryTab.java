package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CategoryTab extends Fragment {

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
//    RadioGroup categoryRadio;
//    ArrayList<Category> mList1;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_tab, container, false);

        categoryRecyclerView = (RecyclerView) v.findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));
        //categoryRadio = (RadioGroup) v.findViewById(R.id.radioGroup3);
        //mList1 = new ArrayList<>();
//        categoryRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.radioExpense:
//                        mList1 = CategoryManager.getExpenseCategories();
//
//                        break;
//                    case R.id.radioIncome:
//                        mList1 = CategoryManager.getIncomeCategories();
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });
       // categoryAdapter = new CategoryAdapter(mList1, getContext());
        categoryAdapter = new CategoryAdapter(CategoryManager.getCategories(), getContext());
        categoryRecyclerView.setAdapter(categoryAdapter);

        FAB1 = (ImageButton) v.findViewById(R.id.imageButton1);
        mydb = new DatabaseHelper(getActivity());
        FAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategory.class);
                startActivity(intent);
            }
        });
        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
       categoryAdapter.notifyDataSetChanged();
    }
}