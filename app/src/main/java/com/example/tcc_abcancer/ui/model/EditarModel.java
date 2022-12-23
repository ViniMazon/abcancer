package com.example.tcc_abcancer.ui.model;

public class EditarModel {

    int imageDec;
    String teste2;

    public EditarModel(int image, String teste) {
        this.imageDec= image;
        this.teste2 = teste;
    }

    public int getImage() {
        return imageDec;
    }

    public String getTeste() {
        return teste2;
    }
}
