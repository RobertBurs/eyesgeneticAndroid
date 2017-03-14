package com.vitulburs.claudio.myapplication;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Cono extends Fragment {

    Button ButtonDisc;
    Button ButtonProt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_cono,container,false);
        ButtonDisc = (Button) view.findViewById(R.id.Button_Disc);//TODO cambia indicizzazione perchè punta a quello del bastoncello
        ButtonProt = (Button) view.findViewById(R.id.Button_Cilio);  //DEF BUTTON
        CreateListeners();
        return  view;
    }

    /** Crea I listner da assegnare ai bottoni
     *
     */
    private void CreateListeners() {
        ButtonDisc.setOnClickListener(new View.OnClickListener() {     // CLICK DISC
            @Override
            public void onClick(View view) {
                startDiscActivity(2);
            }
        });
        ButtonProt.setOnClickListener((new View.OnClickListener() {    //CLICK PROT
            @Override
            public void onClick(View view) {
                startCiliotActivity(3);
            }
        }));

    }

    /** Avvia l'attività disc premendo il relativo bottone
     * @param button numero del bottone premuto
     */
    public void startDiscActivity(int button) {
        Intent intent = new Intent(getActivity(), Disc_Activity.class);
        intent.putExtra("Disco", button);
        startActivity(intent);
    }

    /** Avvia l'attività Cilio premendo il relativo bottone
     * @param button numero del bottone premuto
     */
    public void startCiliotActivity(int button) {
        Intent intent = new Intent(getActivity(), Cilio_Activity.class);
        intent.putExtra("Cilio", button);
        startActivity(intent);
    }

}
