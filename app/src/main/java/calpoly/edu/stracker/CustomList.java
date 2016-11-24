//package calpoly.edu.stracker;
//
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
///**
// * Created by panktigandhi on 10/15/16.
// */
//
//public class CustomList extends ArrayAdapter<String> {
//
//    Context context;
//    private String[] web;
//    private Integer[] imageId;
//
//    public CustomList(Context context,
//                      String[] web, Integer[] imageId) {
//        super(context, R.layout.list_single, web);
//        this.context = context;
//        this.web = web;
//        this.imageId = imageId;
//
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            convertView = inflater.inflate(R.layout.list_single, parent, false);
//        }
//        TextView txtTitle = (TextView) convertView.findViewById(R.id.txt);
//
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
//        txtTitle.setText(web[position]);
//
//        imageView.setImageResource(imageId[position]);
//        return convertView;
//    }
//}
