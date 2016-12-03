package calpoly.edu.stracker;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseHelper mydb;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Summary", "Transactions", "Categories"};
    int Numboftabs = 3;
    String shareData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TransactionManager.transactionManagerInit(getApplicationContext());
        CategoryManager.categoryManagerInit(getApplicationContext());
        mydb = new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Spending Tracker");
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);


        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);


        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);


        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);

    }
public String getShareData()
{
    //shareData="hello";
shareData=mydb.getTransactionasString();
    return shareData;
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                // shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Enter Subject");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getShareData());
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
                break;
            default:
                break;
        }
        return true;
    }

}