package imc.cursoandroid.gdgcali.com.imccalculator.model;

/**
 * Created by joseberna on 29/07/16.
 */
public class ResultModel {
    private double id;
    private double peso;
    private double altura;
    private double imc;

    public ResultModel() {
    }

    public ResultModel(double peso, double altura, double imc) {
        this.id = id;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }
}
