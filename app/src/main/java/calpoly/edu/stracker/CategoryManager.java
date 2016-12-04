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
    static ArrayList<Category> cList;

    public static void categoryManagerInit(Context appContext) {
        appInfo = appContext;
        db = new DatabaseHelper(appInfo);
    }

    public static ArrayList<Category> getCategories() {

        return db.getCategories();
    }

    public static Category findCategory(String title) {

        if (cList == null) {
            cList = getCategories();
        }

        for (Category cat : cList) {
            if (cat.title.equals(title)) {
                return cat;
            }
        }

        return null;

    }

}
