package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class CategoryTab extends Fragment {

    ImageButton FAB1;
    RadioGroup categoryRadio;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private int tabPos = 0;
    private DatabaseHelper db = CategoryManager.db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_tab, container, false);

        categoryRecyclerView = (RecyclerView) v.findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));

        categoryRadio = (RadioGroup) v.findViewById(R.id.radioGroup3);
        categoryRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioCategoryExpense:
                        tabPos = 0;
                        updateList();
                        break;
                    case R.id.radioCategoryIncome:
                        tabPos = 1;
                        updateList();
                        break;
                    default:
                        tabPos = 2;
                        updateList();
                        break;
                }
            }
        });

        categoryAdapter = new CategoryAdapter(tabPos == 0 ? db.getExpenseCategories() : db.getIncomeCategories(), getContext() );
        categoryRecyclerView.setAdapter(categoryAdapter);

        FAB1 = (ImageButton) v.findViewById(R.id.imageButton1);

        FAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategory.class);
                startActivity(intent);

            }
        });
        return v;

    }

    public void updateList() {
        categoryAdapter.mList = tabPos == 0 ? db.getExpenseCategories() : db.getIncomeCategories();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        CategoryManager.getCategories();
        categoryAdapter.notifyDataSetChanged();
    }
}