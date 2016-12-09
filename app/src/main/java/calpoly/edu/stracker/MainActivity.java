package calpoly.edu.stracker;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseHelper mydb;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Summary", "Transactions", "Categories"};
    int Numboftabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CategoryManager.categoryManagerInit(getApplicationContext());
        TransactionManager.transactionManagerInit(getApplicationContext());
        mydb = new DatabaseHelper(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Spending Tracker");
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (Fragment rtn : getSupportFragmentManager().getFragments()) {
                    if (rtn instanceof SummaryTab)
                        ((SummaryTab) rtn).setTotals();
                    else if (rtn instanceof TransactionTab)
                        ((TransactionTab) rtn).updateRange();
                    else if (rtn instanceof CategoryTab)
                        ((CategoryTab) rtn).hashCode();
                    else
                        System.out.println("PageError");
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_credits:
                Intent intent = new Intent(this, CreditforIcon.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

}