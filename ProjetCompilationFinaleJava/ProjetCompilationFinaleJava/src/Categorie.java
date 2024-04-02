public enum Categorie  {
    EOF,
    $,
    NUL,
    PV,
    OPAFF,
    OPREL,
    OPAR,
    NB,
    ID,
    SI,
    CHAINE,
    ECRIRE,
    LIRE,
    SINON,
    ALORS,
    TABLEAU,
    DE,
    CROCHETD,
    CROCHETG,
    PARENTHD,
    PARENTHG,
    DEUXPOINT,
    TANTQUE,
    TYPE,
    LITTERAL,
    VRAI,
    FAUX,
    FAIRE,

    ;




/*La méthode java string toLowerCase () renvoie la chaîne en minuscules. En d'autres termes,
il convertit tous les caractères de la chaîne en minuscules. */




    public String toString() {
        return this.name().toLowerCase();
    }

    public static Categorie toCategorie(String s) {
        for(Categorie c: Categorie.values())
            if(c.toString().equalsIgnoreCase(s))
                return c;
        return null;
    }



}

