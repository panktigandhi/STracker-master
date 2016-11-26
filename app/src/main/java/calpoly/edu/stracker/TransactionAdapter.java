package calpoly.edu.stracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by makkabeus on 11/21/16.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    Context curContext;
    ArrayList<Transaction> mList;

    public TransactionAdapter(ArrayList<Transaction> tList, Context curContext) {
        mList = tList;
        this.curContext = curContext;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Transaction mTransaction;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Transaction e) {
            mTransaction = e;
        }

        public void onClick(View v) {

        }
    }

}
