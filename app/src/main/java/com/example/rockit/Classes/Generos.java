package com.example.rockit.Classes;

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

    public Generos(String pais,Integer alternative, Integer axe, Integer blues, Integer disco, Integer eletronica, Integer forro,
                 Integer funk, Integer funky_americano, Integer hard_rock,
                 Integer heavy_metal, Integer hip_hop,
                 Integer jazz, Integer mpb, Integer opera, Integer pagode,
                 Integer pop, Integer power_metal, Integer punk, Integer rap,
                   Integer reggae, Integer rock_classico, Integer samba, Integer sertanejo
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
    }

    public Generos(){
    }

    public Integer getGenreValue(int i){
        if(i==0){return alternative;}
        if(i==1){return axe;}
        if(i==2){return blues;}
        if(i==3){return disco;}
        if(i==4){return eletronica;}
        if(i==5){return forro;}
        if(i==6){return funk;}
        if(i==7){return funky_americano;}
        if(i==8){return hard_rock;}
        if(i==9){return heavy_metal;}
        if(i==10){return hip_hop;}
        if(i==11){return jazz;}
        if(i==12){return mpb;}
        if(i==13){return opera;}
        if(i==14){return pagode;}
        if(i==15){return pop;}
        if(i==16){return power_metal;}
        if(i==17){return punk;}
        if(i==18){return rap;}
        if(i==19){return reggae;}
        if(i==20){return rock_classico;}
        if(i==21){return samba;}
        if(i==22){return sertanejo;}
        //else
        return 0;
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

    public void setFunky_americano(Integer funky_americano) {
        this.funky_americano = funky_americano;
    }

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

    public Integer getMpb() {
        return mpb;
    }

    public void setMpb(Integer mpb) {
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
}
