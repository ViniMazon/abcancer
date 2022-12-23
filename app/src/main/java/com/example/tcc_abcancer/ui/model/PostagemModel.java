package com.example.tcc_abcancer.ui.model;

import com.google.firebase.database.Exclude;

public class PostagemModel {
   // private String name;
    private String imagem;
    private String Key;
    private String StorageKey;
    private String nomeOng;
    private String unique;
    private String id;
    private String contato;
    private String email;
    private String horario;
    private String local;
    private String necessidade;
    private String sobre;
    private String tipo;
    private String Logo;
    private String dist;
    //private String uri;
    //private String imagens;
    private String link;


    public PostagemModel(){
    }

    public PostagemModel(String nomeOng,String imagem, String unique, String id, String contato, String email, String horario, String local,
                         String necessidade, String sobre, String tipo, String imagens, String uri, String Logo, String link, String dist
                         ){

        this.imagem = imagem;
        this.nomeOng = nomeOng;
        this.unique = unique;
        this.id = id;
        this.contato = contato;
        this.email = email;
        this.horario = horario;
        this.local = local;
        this.necessidade = necessidade;
        this.sobre = sobre;
        this.tipo = tipo;
        //this.uri = uri;
        this.Logo = Logo;
        this.link = link;
        this.dist = dist;

  //this.imagens = imagens;

    }
/*
    public String getImagens() {
        return imagens;
    }

    public void setImagens(String imagens) {
        this.imagens = imagens;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
 */

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNecessidade() {
        return necessidade;
    }

    public void setNecessidade(String necessidade) {
        this.necessidade = necessidade;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getNomeOng() {
        return nomeOng;
    }

    public void setNomeOng(String nomeOng) {
        this.nomeOng = nomeOng;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getStorageKey() {
        return StorageKey;
    }

    public void setStorageKey(String storageKey) {
        this.StorageKey = storageKey;
    }


}




