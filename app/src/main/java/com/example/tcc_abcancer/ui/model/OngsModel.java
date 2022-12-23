package com.example.tcc_abcancer.ui.model;

public class OngsModel {

    String NomeONG;
    String Email;
    String Cnpj;
    String Tipo;
    String Tel;
    String Cel;
    String Senha;
    String Uf;
    String Cd;
    String Logo;

    public OngsModel() {
    }

    public OngsModel(String nome, String email, String cnpj, String tipo, String tel, String cel, String senha, String uf, String cd, String logo) {
        NomeONG = nome;
        Email = email;
        Cnpj = cnpj;
        Tipo = tipo;
        Tel = tel;
        Cel = cel;
        Senha = senha;
        Uf = uf;
        Cd = cd;
        this.Logo = logo;

    }
    public String getNomeONG() {
        return NomeONG;
    }
    public void setNomeONG(String nomeONG) {
        NomeONG = nomeONG;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCnpj() {
        return Cnpj;
    }

    public void setCnpj(String cnpj) {
        Cnpj = cnpj;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getCel() {
        return Cel;
    }

    public void setCel(String cel) {
        Cel = cel;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getUf() {
        return Uf;
    }

    public void setUf(String uf) {
        Uf = uf;
    }

    public String getCd() {
        return Cd;
    }

    public void setCd(String cd) {
        Cd = cd;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }
}
