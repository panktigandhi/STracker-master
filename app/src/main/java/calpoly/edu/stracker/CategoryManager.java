package calpoly.edu.stracker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by makkabeus on 11/28/16.
 */

public class CategoryManager {

    static DatabaseHelper db;
    static Context appInfo;

    public static void categoryManagerInit(Context appContext) {
        appInfo = appContext;
        db = new DatabaseHelper(appInfo);
    }

    public static ArrayList<Category> getCategories() {

        ArrayList<Category> rtn = new ArrayList<Category>();
        Cursor res = db.getAllCategories();
        Category temp;
        byte[] byteArray;
        Bitmap bmp;

        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                temp = new Category();
                temp.id = res.getInt(0);
                byteArray = res.getBlob(1);
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                temp.icon = bmp;
                temp.title = res.getString(2);
                rtn.add(temp);
                res.moveToNext();
            }
        }

        if (rtn.size() == 0) {
            db.initCategories();
            return getCategories();
        }

        res.close();
        return rtn;

    }

}
