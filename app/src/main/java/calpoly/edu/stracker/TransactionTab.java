package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransactionTab extends Fragment {

    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.transaction_tab,container,false);
        transactionRecyclerView = (RecyclerView) v.findViewById(R.id.transaction_recyclerview);
        transactionRecyclerView.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));
        transactionAdapter = new TransactionAdapter();
        transactionRecyclerView.setAdapter(transactionAdapter);

        return v;
    }
}