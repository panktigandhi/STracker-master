package calpoly.edu.stracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**ArrayList
 * Created by makkabeus on 11/21/16.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    Context curContext;
    ArrayList<Transaction> mList;

    public TransactionAdapter(ArrayList<Transaction> tList, Context curContext) {
        mList = tList;
        this.curContext = curContext;
    }

    public int getItemViewType(int position) {
        return R.layout.list_single;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println(position);
        System.out.println(getItemCount());
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Transaction mTransaction;
        ImageView categoryImage;
        TextView itemTitle;


        public ViewHolder(View itemView) {
            super(itemView);

            categoryImage = (ImageView) itemView.findViewById(R.id.list_single_category_icon);
            itemTitle = (TextView) itemView.findViewById(R.id.list_single_item);

        }

        public void bind(Transaction e) {
            mTransaction = e;
            itemTitle.setText(mTransaction.getTitle());
        }

        public void onClick(View v) {

        }
    }

}
