package binarykeys.com.attendance;

/**
 * Created by nitin on 27-06-2017.
 */

public class Card {
    private String sub;
    private String attbyTot;
    private String fac;
    private String per;

    public Card() {
    }

    public Card(String sub, String attbyTot, String fac, String per) {

        sub = sub;
        attbyTot = attbyTot;
        fac = fac;
        per = per;
    }


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAttbyTot() {
        return attbyTot;
    }

    public void setAttbyTot(String attbyTot) {
        this.attbyTot = attbyTot;
    }


    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }
}
