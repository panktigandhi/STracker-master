package calpoly.edu.stracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * ArrayList
 * Created by makkabeus on 11/21/16.
 */


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context curContext;
    ArrayList<Transaction> mList;

    public TransactionAdapter(ArrayList<Transaction> tList, Context curContext) {
        mList = tList;
        this.curContext = curContext;
    }

    public int getItemViewType(int position) {
        return R.layout.transaction_item;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    public int getItemCount() {
        return mList.size();
    }

    public void dismissItem(int pos) {
        mList.remove(pos);
        this.notifyItemRemoved(pos);
    }

    public void remove(int index) {
        TransactionManager.removeTransactions(mList.remove(index).getId());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Transaction mTransaction;
        ImageView categoryImage;
        TextView itemTitle;
        TextView amount;
        TextView date;


        public ViewHolder(View itemView) {
            super(itemView);

            categoryImage = (ImageView) itemView.findViewById(R.id.transaction_item_category_icon);
            itemTitle = (TextView) itemView.findViewById(R.id.transaction_item_title);
            date = (TextView) itemView.findViewById(R.id.transaction_item_date);
            amount = (TextView) itemView.findViewById(R.id.transaction_item_amount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), EditActivity.class);
                    i.putExtra("position", mTransaction.getId());
                    i.putExtra("ItemTitle", itemTitle.getText().toString());
                    i.putExtra("date", date.getText().toString());
                    i.putExtra("amount", amount.getText().toString());
                    i.putExtra("category", mTransaction.getCategory());
                    v.getContext().startActivity(i);
                }
            });
        }

        public void bind(Transaction e) {
            mTransaction = e;
            itemTitle.setText(mTransaction.getTitle());
            amount.setText(DatabaseHelper.convertDoubleToHumanDecimal(mTransaction.getAmount()));
            date.setText(mTransaction.getDate());
            if (mTransaction.getAmount() < 0) {
                amount.setTextColor(Color.parseColor("#AF002A"));
            } else {
                amount.setTextColor(Color.parseColor("#008000"));
            }

            if (CategoryManager.findCategory(mTransaction.getCategory()).icon != null)
                categoryImage.setImageBitmap(CategoryManager.findCategory(mTransaction.getCategory()).icon);

        }

        public void onClick(View v) {

        }
    }

}