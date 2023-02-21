package com.example.sansgarden;

public class ArtikelModel {
    private String Judul;
    private String NamaPenulis;
    private String url;


   /* public ArtikelModel(String judul, String namaPenulis, String url) {
        Judul = judul;
        NamaPenulis = namaPenulis;
        this.url = url;
    }

    */

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getNamaPenulis() {
        return NamaPenulis;
    }

    public void setNamaPenulis(String namaPenulis) {
        NamaPenulis = namaPenulis;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
