package calpoly.edu.stracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by makkabeus on 11/28/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{


    Context curContext;
    ArrayList<Category> mList;

    public CategoryAdapter(ArrayList<Category> tList, Context curContext) {
        mList = tList;
        this.curContext = curContext;
    }

    public int getItemViewType(int position) {
        return R.layout.category_item;
    }

    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Category curCategory;
        ImageView icon;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.category_item_image);
            title = (TextView) itemView.findViewById(R.id.category_item_title);
        }

        public void bind(Category e) {
            curCategory = e;
            title.setText(curCategory.title);
            icon.setImageBitmap(curCategory.icon);
        }

        public void onClick(View v) {

        }
    }
}
