package com.example.tcc_abcancer.ui.model;

public class CampanhaModel {

    String foto;
    String link;
    String desc;
    String id;
    String nome;
    String nomeOng;
    String Logo;

    public CampanhaModel() {

    }

    public CampanhaModel(String foto, String link, String desc, String id, String nome, String nomeOng, String Logo) {

        this.foto = foto;
        this.link = link;
        this.desc = desc;
        this.id = id;
        this.nome = nome;
        this.nomeOng = nomeOng;
        this.Logo = Logo;

    }

    public String getNomeOng() {
        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {
        this.nomeOng = nomeOng;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
