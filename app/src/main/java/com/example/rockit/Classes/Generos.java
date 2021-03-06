package com.example.rockit.Classes;

import android.util.Log;

public class Generos {

    public String pais;
    public Integer alternative;
    public Integer axe;
    public Integer blues;
    public Integer disco;
    public Integer eletronica;
    public Integer forro;
    public Integer funk;
    public Integer funky_americano;
    public Integer hard_rock;
    public Integer heavy_metal;
    public Integer hip_hop;
    public Integer jazz;
    public Integer mpb;
    public Integer opera;
    public Integer pagode;
    public Integer pop;
    public Integer power_metal;
    public Integer punk;
    public Integer rap;
    public Integer reggae;
    public Integer rock_classico;
    public Integer samba;
    public Integer sertanejo;
    public Integer soul;
    public Integer classica;
    public Integer rb;

    public Generos(String pais,Integer alternative, Integer axe, Integer blues, Integer disco, Integer eletronica, Integer forro,
                 Integer funk, Integer funky_americano, Integer hard_rock,
                 Integer heavy_metal, Integer hip_hop,
                 Integer jazz, Integer mpb, Integer opera, Integer pagode,
                 Integer pop, Integer power_metal, Integer punk, Integer rap,
                   Integer reggae, Integer rock_classico, Integer samba, Integer sertanejo,
                   Integer soul, Integer classica, Integer rb
                   ) {

        this.pais = pais;
        this.alternative = alternative;
        this.axe = axe;
        this.blues = blues;
        this.disco = disco;
        this.eletronica = eletronica;
        this.forro = forro;
        this.funk = funk;
        this.funky_americano = funky_americano;
        this.hard_rock = hard_rock;
        this.heavy_metal = heavy_metal;
        this.hip_hop = hip_hop;
        this.jazz = jazz;
        this.mpb = mpb;
        this.opera = opera;
        this.pagode = pagode;
        this.pop = pop;
        this.power_metal = power_metal;
        this.punk = punk;
        this.rap = rap;
        this.reggae = reggae;
        this.rock_classico = rock_classico;
        this.samba = samba;
        this.sertanejo = sertanejo;
        this.soul = soul;
        this.classica = classica;
        this.rb = rb;
    }

    public Generos(){
    }

    public Integer getNumberGenres(){
        return 26;
}

    public Integer getGenreValue(int i){
        //Precisa estar na ordem alfabetica do firebase
        if(i==0){if(alternative==null){return 0;}return alternative;}
        if(i==1){if(axe==null){return 0;}return axe;}
        if(i==2){if(blues==null){return 0;}return blues;}
        if(i==3){if(classica==null){return 0;}return classica;}
        if(i==4){if(disco==null){return 0;}return disco;}
        if(i==5){if(eletronica==null){return 0;}return eletronica;}
        if(i==6){if(forro==null){return 0;}return forro;}
        if(i==7){if(funk==null){return 0;}return funk;}
        if(i==8){if(funky_americano==null){return 0;}return funky_americano;}
        if(i==9){if(hard_rock==null){return 0;}return hard_rock;}
        if(i==10){if(heavy_metal==null){return 0;}return heavy_metal;}
        if(i==11){if(hip_hop==null){return 0;}return hip_hop;}
        if(i==12){if(jazz==null){return 0;}return jazz;}
        if(i==13){if(mpb==null){return 0;}return mpb;}
        if(i==14){if(opera==null){return 0;}return opera;}
        if(i==15){if(pagode==null){return 0;}return pagode;}
        if(i==16){if(pop==null){return 0;}return pop;}
        if(i==17){if(power_metal==null){return 0;}return power_metal;}
        if(i==18){if(punk==null){return 0;}return punk;}
        if(i==19){if(rap==null){return 0;}return rap;}
        if(i==20){if(rb==null){return 0;}return rb;}
        if(i==21){if(reggae==null){return 0;}return reggae;}
        if(i==22){if(rock_classico==null){return 0;}return rock_classico;}
        if(i==23){if(samba==null){return 0;}return samba;}
        if(i==24){if(sertanejo==null){return 0;}return sertanejo;}
        if(i==25){if(soul==null){return 0;}return soul;}
        //else
        return 0;
    }
    public String getGenreName(int i){
        if(i==0){return "alternative";}
        if(i==1){return "axe";}
        if(i==2){return "blues";}
        if(i==3){return "classica";}
        if(i==4){return "disco";}
        if(i==5){return "eletronica";}
        if(i==6){return "forro";}
        if(i==7){return "funk";}
        if(i==8){return "funky_americano";}
        if(i==9){return "hard_rock";}
        if(i==10){return "heavy_metal";}
        if(i==11){return "hip_hop";}
        if(i==12){return "jazz";}
        if(i==13){return "mpb";}
        if(i==14){return "opera";}
        if(i==15){return "pagode";}
        if(i==16){return "pop";}
        if(i==17){return "power_metal";}
        if(i==18){return "punk";}
        if(i==19){return "rap";}
        if(i==20){return "rb";}
        if(i==21){return "reggae";}
        if(i==22){return "rock_classico";}
        if(i==23){return "samba";}
        if(i==24){return "sertanejo";}
        if(i==25){return "soul";}
        //else
        return "0";
    }

    public Integer getAlternative() {
        return alternative;
    }

    public void setAlternative(Integer alternative) {
        this.alternative = alternative;
    }

    public Integer getAxe() {
        return axe;
    }

    public void setAxe(Integer axe) {
        this.axe = axe;
    }

    public Integer getBlues() {
        return blues;
    }

    public void setBlues(Integer blues) {
        this.blues = blues;
    }

    public Integer getDisco() {
        return disco;
    }

    public void setDisco(Integer disco) {
        this.disco = disco;
    }

    public Integer getEletronica() {
        return eletronica;
    }

    public void setEletronica(Integer eletronica) {
        this.eletronica = eletronica;
    }

    public Integer getForro() {
        return forro;
    }

    public void setForro(Integer forro) {
        this.forro = forro;
    }

    public Integer getFunk() {
        return funk;
    }

    public void setFunk(Integer funk) {
        this.funk = funk;
    }

    public Integer getFunky_americano() {
        return funky_americano;
    }

    public void setFunky_americano(Integer funky_americano) {        this.funky_americano = funky_americano;    }

    public Integer getHard_rock() {
        return hard_rock;
    }

    public void setHard_rock(Integer hard_rock) {
        this.hard_rock = hard_rock;
    }

    public Integer getHeavy_metal() {
        return heavy_metal;
    }

    public void setHeavy_metal(Integer heavy_metal) {
        this.heavy_metal = heavy_metal;
    }

    public Integer getHip_hop() {
        return hip_hop;
    }

    public void setHip_hop(Integer hip_hop) {
        this.hip_hop = hip_hop;
    }

    public Integer getJazz() {
        return jazz;
    }

    public void setJazz(Integer jazz) {
        this.jazz = jazz;
    }

    public Integer gempb() {
        return mpb;
    }

    public void setmpb(Integer mpb) {
        this.mpb = mpb;
    }

    public Integer getOpera() {
        return opera;
    }

    public void setOpera(Integer opera) {
        this.opera = opera;
    }

    public Integer getPagode() {
        return pagode;
    }

    public void setPagode(Integer pagode) {
        this.pagode = pagode;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getPower_metal() {
        return power_metal;
    }

    public void setPower_metal(Integer power_metal) {
        this.power_metal = power_metal;
    }

    public Integer getPunk() {
        return punk;
    }

    public void setPunk(Integer punk) {
        this.punk = punk;
    }

    public Integer getRap() {
        return rap;
    }

    public void setRap(Integer rap) {
        this.rap = rap;
    }

    public Integer getReggae() {
        return reggae;
    }

    public void setReggae(Integer reggae) {
        this.reggae = reggae;
    }

    public Integer getRock_classico() {
        return rock_classico;
    }

    public void setRock_classico(Integer rock_classico) {
        this.rock_classico = rock_classico;
    }

    public Integer getSamba() {
        return samba;
    }

    public void setSamba(Integer samba) {
        this.samba = samba;
    }

    public Integer getSertanejo() {
        return sertanejo;
    }

    public void setSertanejo(Integer sertanejo) {
        this.sertanejo = sertanejo;
    }

    public Integer getSoul() {        return soul;    }

    public void setSoul(Integer soul) {        this.soul = soul;    }

    public Integer getClassica() {        return classica;    }

    public void setClassica(Integer classica) {        this.classica = classica;    }

    public Integer getRB() {        return rb;    }

    public void setRB(Integer rb) {        rb = rb;    }
}
