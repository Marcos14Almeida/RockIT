package com.example.rockit.Classes;

public class Stars {
    //Usado no fragment Show
    private String nota1;
    private String nota2;
    private String nota3;
    private String nota4;
    private String nota5;
    private String nota6;

    //used in fragmento shows para mostrar scroll da pagina
    public Stars(String nota1, String nota2, String nota3, String nota4, String nota5, String nota6) {
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.nota5 = nota5;
        this.nota6 = nota6;
    }

    public Stars(){}

    public String getNota1() {
        return nota1;
    }

    public void setNota1(String nota1) {
        this.nota1 = nota1;
    }

    public String getNota2() {
        return nota2;
    }

    public void setNota2(String nota2) {
        this.nota2 = nota2;
    }

    public String getNota3() {
        return nota3;
    }

    public void setNota3(String nota3) {
        this.nota3 = nota3;
    }

    public String getNota4() {
        return nota4;
    }

    public void setNota4(String nota4) {
        this.nota4 = nota4;
    }

    public String getNota5() {
        return nota5;
    }

    public void setNota5(String nota5) {
        this.nota5 = nota5;
    }

    public String getNota6() {
        return nota6;
    }

    public void setNota6(String nota6) {
        this.nota6 = nota6;
    }
}
