package com.vitulburs.claudio.myapplication;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVProteina;
    private AdapterProtein mAdapter;
    private TextView mTextEmpty;

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // adds item to action bar
        getMenuInflater().inflate(R.menu.search_main, menu);

        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) SearchResult.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchResult.this.getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setIconified(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query and create object of class AsyncFetch
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();
            searchView.setQuery("", false);// Nella ricerca svuota il campo
            searchView.setIconified(true);// e chiude il widget di ricerca
        }
    }


    // Create class AsyncFetch
    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(SearchResult.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery){
            this.searchQuery=searchQuery;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tCaricamento dei dati...");
            pdLoading.setCancelable(true);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                //url = new URL("http://192.168.1.6/mutazionidb/proteine_search.php");
                //url = new URL("http://192.168.1.7:8084/proteine_search.php");
                url = new URL("http://91.253.108.179:8084/proteine_search.php");
                //url = new URL("http://192.168.1.111/mutazionidb/proteine_search.php");
                //url = new URL("http://94.161.55.36//mutazionidb/proteine_search.php");
                //url = new URL("http://192.168.43.92/mutazionidb/proteine_search.php");
				//url = new URL("http://93.148.90.196:3306/mutazionidb/proteine_search.php");


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                //return e.toString();
                return "Errore interno. L' indirizzo del server potrebbe essere cambiato. Contattare l'amministratore dell'applicazione";
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput to true as we send and recieve data
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // add parameter to our above url
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("searchQuery", searchQuery);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
                //return "";
                //return "Errore! Non è stato possibile connettersi al server. L' indirizzo del server potrebbe essere cambiato. Contattare l'amministratore dell'applicazione";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return("Errore! Non è stato possibile connettersi al server");
                    //return "";
                }

            } catch (IOException e) {
                //e.printStackTrace();
                //return e.toString();
                //return "";
                return("Errore! Non è stato possibile connettersi al server");
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            if(!result.equals("") )// not sure if it works. if DB not connected, avoid json toast error.
            {
                //this method will be running on UI thread
                pdLoading.dismiss();
                List<DataProtein> data=new ArrayList<>();
                mRVProteina = (RecyclerView)findViewById(R.id.listaProteine);
                mTextEmpty = (TextView)findViewById(R.id.empty_view);
                pdLoading.dismiss();
                if(result.equals("no rows")) {
                    Toast.makeText(SearchResult.this, "Nessun risultato per la proteina ricercata", Toast.LENGTH_LONG).show();
                    mRVProteina.setVisibility(View.GONE);
                    mTextEmpty.setVisibility(View.VISIBLE);

                }else{

                    try {

                        JSONArray jArray = new JSONArray(result);

                        // Extract data from json and store into ArrayList as class objects
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            DataProtein proteinData = new DataProtein();
                            proteinData.nomeProteina = json_data.getString("nomeProteina");
                            proteinData.nomeCompletoProteina = json_data.getString("nomeCompletoProteina");
                            proteinData.descrizione = json_data.getString("descrizione");
                            proteinData.pathFile = json_data.getString("pathFile");
                            data.add(proteinData);
                        }

                        // Setup and Handover data to recyclerview
                        mRVProteina.setVisibility(View.VISIBLE);
                        mTextEmpty.setVisibility(View.GONE);
                        mRVProteina = (RecyclerView) findViewById(R.id.listaProteine);
                        mAdapter = new AdapterProtein(SearchResult.this, data);
                        mRVProteina.setAdapter(mAdapter);
                        mRVProteina.setLayoutManager(new LinearLayoutManager(SearchResult.this));

                    } catch (JSONException e) {
                        // You to understand what actually error is and handle it appropriately
                        Toast.makeText(SearchResult.this, e.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(SearchResult.this, result.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }
            else
            {
                Toast.makeText(SearchResult.this, "Errore! Non è stato possibile connettersi al server. L' indirizzo del server potrebbe essere cambiato. Contattare l'amministratore dell'applicazione", Toast.LENGTH_LONG).show();
            }
        }

    }
}
