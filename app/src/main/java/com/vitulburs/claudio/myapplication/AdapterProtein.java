package com.vitulburs.claudio.myapplication;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;

public class AdapterProtein extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataProtein> data= Collections.emptyList();
    DataProtein current;

    // create constructor to initialize context and data sent from MainActivity
    public AdapterProtein(Context context, List<DataProtein> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_protein, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataProtein current=data.get(position);
        myHolder.textNomeProteina.setText(current.nomeProteina);
        myHolder.textNomeCompletoProteina.setText("Nome Completo: " + current.nomeCompletoProteina);
        myHolder.textDescrizione.setText("Descrizione: " + current.descrizione);
        myHolder.textPathFile.setText("Documento: " + current.pathFile);
        if(current.pathFile.equals("No"))
        {
            myHolder.textPathFile.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        else myHolder.textPathFile.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textNomeProteina;
        TextView textNomeCompletoProteina;
        TextView textDescrizione;
        TextView textPathFile;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textNomeProteina= (TextView) itemView.findViewById(R.id.textNomeProteina);
            textNomeCompletoProteina = (TextView) itemView.findViewById(R.id.textNomeCompletoProteina);
            textDescrizione = (TextView) itemView.findViewById(R.id.textDescrizione);
            textPathFile = (TextView) itemView.findViewById(R.id.textPathFile);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

        }

    }

}
