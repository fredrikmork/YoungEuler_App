package no.hvl.dat109;

public class Question {
    // Objektvariabler
    private String spm;
    private String svar;
    private int niva;
    private int id;

    // Konstruktør
    public Question(){
        this("", "", 0, 0);
    }

    public Question(String spm, String svar, int niva, int id){
        this.spm = spm;
        this.svar = svar;
        this.niva = niva;
        this.id = id;
    }

    public String getSpm() {
        return spm;
    }

    public void setSpm(String spm) {
        this.spm = spm;
    }

    public String getSvar() {
        return svar;
    }

    public void setSvar(String svar) {
        this.svar = svar;
    }

    public int getNiva() {
        return niva;
    }

    public void setNiva(int niva) {
        this.niva = niva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
