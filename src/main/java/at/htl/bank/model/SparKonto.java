package at.htl.bank.model;

public class SparKonto extends BankKonto {

    private double zinsSatz;

    public SparKonto(String name, double zinsSatz){
        super(name);
        this.zinsSatz = zinsSatz;
    }

    public SparKonto(String name, double anfangsBestand, double zinsSatz){
        super(name, anfangsBestand);
        this.zinsSatz = zinsSatz;
    }

    public void zinsenAnrechnen(){
        kontoStand += kontoStand * zinsSatz / 100;
    }

}
