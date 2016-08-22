package imc.cursoandroid.gdgcali.com.imccalculator.model.iagree;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joseberna on 16/08/16.
 */
public class ObligationsModel {
    @SerializedName("obligation_number")
    private Integer obligation_number;

    @SerializedName("overdue_balance")
    private Integer overdue_balance;

    @SerializedName("capital")
    private Integer capital;

    @SerializedName("capital_balance")
    private Integer capital_balance;

    @SerializedName("interest")
    private Integer interest;

    @SerializedName("campaign_number")
    private Integer campaign_number;

    @SerializedName("client")
    private Integer client;

    @SerializedName("fecha_final")
    private String fecha_final;

    public ObligationsModel(Integer obligation_number, Integer overdue_balance, Integer capital, Integer capital_balance, Integer interest, Integer campaign_number, Integer client, String fecha_final) {
        this.obligation_number = obligation_number;
        this.overdue_balance = overdue_balance;
        this.capital = capital;
        this.capital_balance = capital_balance;
        this.interest = interest;
        this.campaign_number = campaign_number;
        this.client = client;
        this.fecha_final = fecha_final;
    }

    public Integer getObligation_number() {
        return obligation_number;
    }

    public void setObligation_number(Integer obligation_number) {
        this.obligation_number = obligation_number;
    }

    public Integer getOverdue_balance() {
        return overdue_balance;
    }

    public void setOverdue_balance(Integer overdue_balance) {
        this.overdue_balance = overdue_balance;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public Integer getCapital_balance() {
        return capital_balance;
    }

    public void setCapital_balance(Integer capital_balance) {
        this.capital_balance = capital_balance;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getCampaign_number() {
        return campaign_number;
    }

    public void setCampaign_number(Integer campaign_number) {
        this.campaign_number = campaign_number;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }
}
