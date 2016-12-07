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

public class CategoryTab extends Fragment {

    ImageButton FAB1;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_tab, container, false);

        categoryRecyclerView = (RecyclerView) v.findViewById(R.id.category_recyclerview);
        categoryRecyclerView.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));

        categoryAdapter = new CategoryAdapter(CategoryManager.getCategories(), getContext());
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

    @Override
    public void onStart() {
        super.onStart();
        CategoryManager.getCategories();
        categoryAdapter.notifyDataSetChanged();
    }
}