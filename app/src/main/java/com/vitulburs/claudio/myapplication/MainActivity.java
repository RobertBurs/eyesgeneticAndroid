package com.vitulburs.claudio.myapplication;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    PagerTabStrip tab_strp;
    SearchView searchView = null;

    /** Crea l'attività corrente e ne inizializza il layout
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PagerAdapter mapager = new PagerAdapter(getSupportFragmentManager());
        pager=(ViewPager)findViewById(R.id.pager);

        pager.setAdapter(mapager);
        tab_strp=(PagerTabStrip)findViewById(R.id.tab_strip);
        tab_strp.setTextColor(Color.BLUE);
    }

    /** Popola l'app bar
     * @param menu da popolare
     * @return booleano
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_main, menu);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchResult.class)));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    }

    /** Si occupa di pulire e chiudere la barra della ricerca
     * @param outState
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchView.setQuery("", false);
        searchView.setIconified(true);
    }

    /** Ritorna le opzioni di un elemento
     * @param item un elemento
     * @return ritorna le opzioni dell'elemento
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}

