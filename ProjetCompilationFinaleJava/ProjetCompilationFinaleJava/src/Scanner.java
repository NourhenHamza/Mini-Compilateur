import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;





public class Scanner {
    protected ArrayList<Character> fluxCaracteres;
    protected int indiceCourant;
    protected char caractereCourant;
    protected boolean eof;

    public Scanner() {
        this("");
    }

    public Scanner(String nomFich) {   /*ouvrir le fichier et mettre les char dans un arrayList*/
        BufferedReader f=null;
        int car=0;
        fluxCaracteres=new ArrayList<Character>();
        indiceCourant=0;
        eof=false;
        try {
            f=new BufferedReader(new FileReader(nomFich));

        }
        catch(IOException e) {
            System.out.println("taper votre texte ci-dessous (cmd+d pour finir)");
            f=new BufferedReader(new InputStreamReader(System.in));

        }

        try {
            while((car=f.read())!=-1) {

                fluxCaracteres.add((char) car);



            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("erreur");
        }
    }

    public void caractereSuivant() { /*donner char courant*/

        if(indiceCourant<fluxCaracteres.size()) {

            caractereCourant = fluxCaracteres.get(indiceCourant++);

        }
        else
            eof=true;

    }

    public void reculer() { /*reculer l'indice courant*/
        if(indiceCourant>0)
            indiceCourant--;
    }

    public UniteLexicale lexemeSuivant() { /*retourner l'uniteLexicale suivante*/
        caractereSuivant();

        while(eof || Character.isWhitespace(caractereCourant)) /*si eof retourner UniteLexicale(EOF,"") si charBlanc avancer*/
        {
            if (eof)
                return new UniteLexicale(Categorie.$, "$");
            caractereSuivant();
        }



        if(Character.isLetter(caractereCourant) )
            return getID();


        if(Character.isDigit(caractereCourant))
            return getNombre();

        if(caractereCourant=='+' || caractereCourant=='-' || caractereCourant=='*'|| caractereCourant=='/')
        {


            if (caractereCourant=='+')
                return new UniteLexicale(Categorie.OPAR,"ADD") ;

            else if (caractereCourant=='-')
                return new UniteLexicale(Categorie.OPAR,"SOUS") ;

            else if (caractereCourant=='/')
                return new UniteLexicale(Categorie.OPAR,"DIV") ;

            if (caractereCourant=='*')
                return new UniteLexicale(Categorie.OPAR,"MULT") ;
        }

        if(caractereCourant==';')
            return (new UniteLexicale(Categorie.PV, ';'));

        if (caractereCourant== ':' ) {
            caractereSuivant();
            if (caractereCourant == '=')
                return new UniteLexicale(Categorie.OPAFF, ":=");
            else {
                reculer();
                return new UniteLexicale(Categorie.DEUXPOINT, ":");

            }
        }

        if (caractereCourant== '"' ) {
            caractereSuivant();
            while(caractereCourant!='"')
            {

                caractereSuivant();
            }
            if (caractereCourant== '"' ) return new UniteLexicale(Categorie.LITTERAL,"LITTERAL") ;

        }






        if (caractereCourant == '[')
            return new UniteLexicale(Categorie.CROCHETG, "[") ;

        if (caractereCourant == ']')
            return  new UniteLexicale(Categorie.CROCHETD, "]") ;

        if (caractereCourant== '<' || caractereCourant=='>' || caractereCourant=='=')
            return getOPREL();
        if (caractereCourant == '(')
            return new UniteLexicale(Categorie.PARENTHG, "(") ;

        if (caractereCourant == ')')
            return  new UniteLexicale(Categorie.PARENTHD, ")") ;
        if (caractereCourant== '<' || caractereCourant=='>' || caractereCourant=='=')
            return getOPREL();




        return null;
    }



    public UniteLexicale getNombre() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            switch (etat) {
                case 0:
                    etat = 1;
                    sb.append(caractereCourant);
                    break;
                case 1:
                    caractereSuivant();
                    if (eof)
                        etat = 3;
                    else if (Character.isDigit(caractereCourant))
                        sb.append(caractereCourant);
                    else
                        etat = 2;
                    break;
                case 2:
                    reculer();
                    return new UniteLexicale(Categorie.NB, sb.toString());
                case 3:
                    return new UniteLexicale(Categorie.NB, sb.toString());
            }
        }
    }

    public UniteLexicale getID() {
        StringBuffer sb = new StringBuffer();

        while(Character.isLetterOrDigit(caractereCourant))
        {
            sb.append(caractereCourant);
            caractereSuivant();

        }
        reculer();

        if (sb.toString().equalsIgnoreCase("sinon"))
            return new UniteLexicale(Categorie.SINON, "SINON");
        if (sb.toString().equalsIgnoreCase("si"))
            return new UniteLexicale(Categorie.SI, "SI");
        if (sb.toString().equalsIgnoreCase("alors"))
            return new UniteLexicale(Categorie.ALORS, "ALORS");
        if (sb.toString().equalsIgnoreCase("tableau"))
            return new UniteLexicale(Categorie.TABLEAU, "TABLEAU");
        if (sb.toString().equalsIgnoreCase("ecrire"))
            return new UniteLexicale(Categorie.ECRIRE, "ecrire");
        if (sb.toString().equalsIgnoreCase("lire"))
            return new UniteLexicale(Categorie.LIRE, "lire");
        if (sb.toString().equalsIgnoreCase("de"))
            return new UniteLexicale(Categorie.DE, "DE");
        if (sb.toString().equalsIgnoreCase("tantque"))
            return new UniteLexicale(Categorie.TANTQUE, "TANTQUE");
        if (sb.toString().equalsIgnoreCase("entier"))
            return new UniteLexicale(Categorie.TYPE, "ENTIER");
        if (sb.toString().equalsIgnoreCase("reel"))
            return new UniteLexicale(Categorie.TYPE, "REEL");
        if (sb.toString().equalsIgnoreCase("char"))
            return new UniteLexicale(Categorie.TYPE, "CHAR");
        if (sb.toString().equalsIgnoreCase("booleen"))
            return new UniteLexicale(Categorie.TYPE, "BOOL");
        if (sb.toString().equalsIgnoreCase("chaine"))
            return new UniteLexicale(Categorie.TYPE, "CHAINE");
        if (sb.toString().equalsIgnoreCase("vrai"))
            return new UniteLexicale(Categorie.VRAI, "TRUE");
        if (sb.toString().equalsIgnoreCase("faux"))
            return new UniteLexicale(Categorie.FAUX, "faux");
        if (sb.toString().equalsIgnoreCase("faire"))
            return new UniteLexicale(Categorie.FAIRE, "faire");
        else
            return new UniteLexicale(Categorie.ID, sb.toString());



    }



    public UniteLexicale getOPREL() {

        if (caractereCourant == '<') {
            caractereSuivant();
            if (caractereCourant == '=')
                return new UniteLexicale(Categorie.OPREL, "PPE");
            else if (caractereCourant == '>')
                return new UniteLexicale(Categorie.OPREL, "DIFF");

            else {
                reculer();
                return new UniteLexicale(Categorie.OPREL, "PPQ");
            }
        }
        else if (caractereCourant == '>') {
            caractereSuivant();
            if (caractereCourant == '=')
                return new UniteLexicale(Categorie.OPREL, "PGE");

            else {
                reculer();
                return new UniteLexicale(Categorie.OPREL, "PGQ");
            }
        }
        else
            return  new UniteLexicale(Categorie.OPREL, "EGA");


    }


    public String toString() {
        // TODO Auto-generated method stub
        return fluxCaracteres.toString();
    }


}
