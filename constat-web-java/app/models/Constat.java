package models;

/**
 * Created by adelegue on 29/05/15.
 */
public class Constat {

    Conducteur conducteurA;
    Conducteur conducteurB;
    String description;

    public Conducteur getConducteurA() {
        return conducteurA;
    }

    public void setConducteurA(Conducteur conducteurA) {
        this.conducteurA = conducteurA;
    }

    public Conducteur getConducteurB() {
        return conducteurB;
    }

    public void setConducteurB(Conducteur conducteurB) {
        this.conducteurB = conducteurB;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Conducteur {
        String nom;
        String prenom;
        Immatriculation immatriculation;
        Circonstances circonstances;

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public Immatriculation getImmatriculation() {
            return immatriculation;
        }

        public void setImmatriculation(Immatriculation immatriculation) {
            this.immatriculation = immatriculation;
        }

        public Circonstances getCirconstances() {
            return circonstances;
        }

        public void setCirconstances(Circonstances circonstances) {
            this.circonstances = circonstances;
        }
    }
    public static class Immatriculation {
        String immatriculation;

        public String getImmatriculation() {
            return immatriculation;
        }

        public void setImmatriculation(String immatriculation) {
            this.immatriculation = immatriculation;
        }
    }
    public static class Circonstances {
        Boolean cases2;
        Boolean cases4;
        Boolean cases8;
        Boolean cases10;
        Boolean cases14;
        Boolean cases15;
        Boolean cases16;

        public Boolean isCases2() {
            return cases2;
        }

        public void setCases2(Boolean cases2) {
            this.cases2 = cases2;
        }

        public Boolean isCases4() {
            return cases4;
        }

        public void setCases4(Boolean cases4) {
            this.cases4 = cases4;
        }

        public Boolean isCases8() {
            return cases8;
        }

        public void setCases8(Boolean cases8) {
            this.cases8 = cases8;
        }

        public Boolean isCases10() {
            return cases10;
        }

        public void setCases10(Boolean cases10) {
            this.cases10 = cases10;
        }

        public Boolean isCases14() {
            return cases14;
        }

        public void setCases14(Boolean cases14) {
            this.cases14 = cases14;
        }

        public Boolean isCases15() {
            return cases15;
        }

        public void setCases15(Boolean cases15) {
            this.cases15 = cases15;
        }

        public Boolean isCases16() {
            return cases16;
        }

        public void setCases16(Boolean cases16) {
            this.cases16 = cases16;
        }
    }

}
