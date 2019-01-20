package at.htl.bank.model;

public abstract class BankKonto {


    private String name;
    protected double kontoStand;

    BankKonto(String name){
        this.setName(name);
        this.kontoStand = 0;
    }

    BankKonto(String name, double anfangsBestand){
        this.name = name;
        this.kontoStand = anfangsBestand;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKontoStand() {
        return kontoStand;
    }



    public void einzahlen(double betrag){
        this.kontoStand += betrag;
    }

    public void auszahlen(double betrag){
        this.kontoStand -= betrag;
    }

}
