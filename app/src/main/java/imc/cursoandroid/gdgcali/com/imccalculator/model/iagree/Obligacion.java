package imc.cursoandroid.gdgcali.com.imccalculator.model.iagree;

/**
 * Created by desarrollo04 on 19/08/2016.
 */
public class Obligacion {


    private String obligation_number;

    private double overdue_balance;

    private double capital;

    private double capital_balance;

    private double interest;

    private int campaign_number;

    private String client;

    private String fecha_final;

    public String getObligation_number() {
        return obligation_number;
    }

    public void setObligation_number(String obligation_number) {
        this.obligation_number = obligation_number;
    }

    public double getOverdue_balance() {
        return overdue_balance;
    }

    public void setOverdue_balance(int overdue_balance) {
        this.overdue_balance = overdue_balance;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public double getCapital_balance() {
        return capital_balance;
    }

    public void setCapital_balance(int capital_balance) {
        this.capital_balance = capital_balance;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getCampaign_number() {
        return campaign_number;
    }

    public void setCampaign_number(int campaign_number) {
        this.campaign_number = campaign_number;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }
}

