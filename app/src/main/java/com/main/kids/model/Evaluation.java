package com.main.kids.model;

public class Evaluation {
    private int nombre_1;
    private int nombre_2;
    private int numeroQuestion = 1;
    private double reponse;
    private String question;
    private int score = 0;

    public Evaluation(){
        randomOperation();
    }

    public String getQuestion(){
        return question;
    }

    public int getScore(){
        return this.score;
    }
    public double getReponse(){
        return this.reponse;
    }
    public int getNumeroQuestion(){
        return numeroQuestion;
    }

    public void setVars(){
        this.nombre_1=(int)(Math.random() * 101);
        this.nombre_2=(int)(Math.random() * 101);
    }

    public boolean estBonneReponse(double r){
        if(r==reponse){
            this.score++;
            return true;
        }
        return false;
    }

    public void randomOperation(){
        this.setVars();
        int ope=(int)(Math.random() * 3);
        String o="";
        switch(ope){
            case 0:
                reponse= nombre_1 + nombre_2;
                o=" + ";
                break;
            case 1:
                reponse=nombre_1 * nombre_2;
                o=" * ";
                break;
            case 2:
                reponse=nombre_1 - nombre_2;
                o=" - ";
                break;
        }
        this.question= new String(this.nombre_1+o+this.nombre_2+" ?");
    }
    public String getMention(int maxQuestions){
        String mention="tresbien";
        double pourcentage=score * Math.pow(maxQuestions, -1) * 100;
        if(pourcentage>=90){
            mention="tresbien";
        }
        if(pourcentage>=70 && pourcentage<90){
            mention="bien";
        }
        if(pourcentage >= 50 && pourcentage < 70){
            mention="moyen";
        }
        if(pourcentage <50 ){
            mention="mauvais";
        }
        return mention;
    }

    public void nextQuestion(){
        this.numeroQuestion++;
        this.randomOperation();
    }
}
