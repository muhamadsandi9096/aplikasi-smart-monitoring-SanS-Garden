package com.example.sansgarden;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRefArtikel extends RecyclerView.Adapter<AdapterRefArtikel.HolderArtikel> {
    List<ArtikelModel> listArtikel;
    LayoutInflater inflater;
    Context context;
    RecyclerClickOnListener recyclerClickOnListener;


    public AdapterRefArtikel(Context context, List<ArtikelModel> listArtikel, RecyclerClickOnListener recyclerClickOnListener) {
        this.listArtikel = listArtikel;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.recyclerClickOnListener = recyclerClickOnListener;
    }

    @NonNull
    @Override
    public HolderArtikel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data_artikel, parent, false);
        return new HolderArtikel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRefArtikel.HolderArtikel holder, @SuppressLint("RecyclerView") int position) {
        ArtikelModel artikelModel = listArtikel.get(position);
        holder.mJudulArtikel.setText(artikelModel.getJudul());
        holder.mNamaPenulis.setText(artikelModel.getNamaPenulis());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerClickOnListener.onItemClicked(listArtikel.get(position));
                String link = listArtikel.get(position).getUrl();
                Intent i = new Intent(context, ArtikelView.class);
                i.putExtra("link", link);
                context.startActivity(i);


            }
        });



    }

    @Override
    public int getItemCount() {
        return listArtikel.size();
    }

    public class HolderArtikel extends RecyclerView.ViewHolder{
        TextView mJudulArtikel, mNamaPenulis;
        CardView cardView;

        public HolderArtikel(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.kotakArtikel);
            mJudulArtikel = itemView.findViewById(R.id.JudulArtikel);
            mNamaPenulis = itemView.findViewById(R.id.NamaPenulis);
        }
    }
}
