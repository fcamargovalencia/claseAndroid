package imc.cursoandroid.gdgcali.com.imccalculator.model;

/**
 * Created by joseberna on 29/07/16.
 */
public class ResultModel {
    private int cont = 0;
    private String id;
    private double peso;
    private double altura;
    private double imc;

    public ResultModel() {
    }

    public ResultModel(double peso, double altura, double imc) {
        this.id = getIdmodel();
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    private String getIdmodel() {

        return (int) (Math.random() * 100) + "";
    }
}
