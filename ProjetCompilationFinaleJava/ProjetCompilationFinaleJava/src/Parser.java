import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Parser {
    public HashMap<String,String>  tableSym = new HashMap<String,String> ();


    public String[] LRGS = {
            "D'->D",
            "D->id deuxpoint T pv D",
            "D->I",
            "I->si E alors I",
            "I->sinon I",
            "I->tantque E faire I",
            "I->id opaff J pv I",
            "I->ecrire parenthg E parenthd pv I",
            "I->lire parenthg E parenthd pv I",
            "I-> ",
            "T->type",
            "T->tableau crochetg nb crochetd de T",
            "J->E oprel J",
            "J->E opar J",
            "J->E",
            "E->id",
            "E->nb",
            "E->litteral",
            "E->vrai",
            "E->faux",
            "E->E crochetg nb crochetg"
    } ;   // table des regles de prod
    // '' remplace epsilon

    public String[][] tableSLR ={
            {"etat","id","deuxpoint","pv","si","alors","sinon","tantque","faire","opaff","ecrire","parenthg","parenthd","lire","type","tableau","crochetg","nb","crochetd","de","oprel","opar","litteral","vrai","faux","$","D","I","T","J","E" },
            {"0","s2","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","1","3","err","err","err" },
            {"1","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","acc","err","err","err","err","err" },
            {"2","err","s9","err","err","err","err","err","err","s10","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err" },
            {"3","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r2","err","err","err","err","err" },
            {"4","s12",	"err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","err","11"},
            {"5","s18","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","err","17","err","err","err"},
            {"6","s12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","err","19"},
            {"7","err","err","err","err","err","err","err","err","err","err","s20","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"8","err","err","err","err","err","err","err","err","err","err","s21","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"9","err","err","err","err","err","err","err","err","err","err","err","err","err","s23","s24","err","err","err","err","err","err","err","err","err","err","err","err","22","err","err"},
            {"10","s12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","25","26"},
            {"11","err","err","err","err","s27","err","err","err","err","err","err","err","err","err","err","s28","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"12","err","err","r15","err","r15","err","err","r15","err","err","err","r15","err","err","err","r15","err","err","err","r15","r15","err","err","err","err","err","err","err","err","err"},
            {"13","err","err","r16","err","r16","err","err","r16","err","err","err","r16","err","err","err","r16","err","err","err","r16","r16","err","err","err","err","err","err","err","err","err"},
            {"14","err","err","r17","err","r17","err","err","r17","err","err","err","r17","err","err","err","r17","err","err","err","r17","r17","err","err","err","err","err","err","err","err","err"},
            {"15","err","err","r18","err","r18","err","err","r18","err","err","err","r18","err","err","err","r18","err","err","err","r18","r18","err","err","err","err","err","err","err","err","err"},
            {"16","err","err","r19","err","r19","err","err","r19","err","err","err","r19","err","err","err","r19","err","err","err","r19","r19","err","err","err","err","err","err","err","err","err"},
            {"17","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r4","err","err","err","err","err" },
            {"18","err","err","err","err","err","err","err","err","s10","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"19","err","err","err","err","err","err","err","s29","err","err","err","err","err","err","err","s28","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"20","s12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","err","30"},
            {"21","s12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","err","31"},
            {"22","err","err","s32","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"23","err","err","r10","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"24","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s33","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"25","err","err","s34","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"26","err","err","r14","err","err","err","err","err","err","err","err","err","err","err","err","s28","err","err","err","s35","s36","err","err","err","err","err","err","err","err","err"},
            {"27","s18","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","err","37","err","err","err"},
            {"28","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s38","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"29","s18","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","err","39","err","err","err"},
            {"30","err","err","err","err","err","err","err","err","err","err","err","s40","err","err","err","s28","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"31","err","err","err","err","err","err","err","err","err","err","err","s41","err","err","err","s28","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"32","s2","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","42","3","err","err","err"},
            {"33","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s43","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"34","s18","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","err","44","err","err","err"},
            {"35","s12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","45","26"},
            {"36","s12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s13","err","err","err","err","s14","s15","s16","err","err","err","err","46","26"},
            {"37","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r3","err","err","err","err","err"},
            {"38","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s47","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"39","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r5","err","err","err","err","err"},
            {"40","err","err","s48","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"41","err","err","s49","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"42","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r1","err","err","err","err","err"},
            {"43","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s50","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"44","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r6","err","err","err","err","err"},
            {"45","err","err","r12","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"46","err","err","r13","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"},
            {"47","err","err","r20","err","r20","err","err","r20","err","err","err","r20","err","err","err","r20","err","err","err","r20","r20","err","err","err","err","err","err","err","err","err"},
            {"48","s18","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","err","51","err","err","err"},
            {"49","s18","err","err","s4","err","s5","s6","err","err","s7","err","err","s8","err","err","err","err","err","err","err","err","err","err","err","r9","err","52","err","err","err"},
            {"50","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","s53","err","err","err","err","err","err","err","err","err","err","err"},
            {"51","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r7","err","err","err","err","err"},
            {"52","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","r8","err","err","err","err","err"},
            {"53","err","err","err","err","err","err","err","err","err","err","err","err","err","s23","s24","err","err","err","err","err","err","err","err","err","err","err","err","54","err","err"},
            {"54","err","err","r11","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err","err"}
    } ;







    public Stack<String> analyse = new Stack<>();
    public Stack<String> analyseSem = new Stack<>();
    public Stack<String > pileId= new Stack<>();
    public  String ch[]= {"id","deuxpoint","pv","si","alors","sinon","tantque","faire","opaff","ecrire","parnethg","parnethd","lire","type","tableau","crochetg","nb","crochetd","de","oprel","opar","litteral","vrai","faux","$"};
    public String strInput ;
    public String action = "";
    public int index = 0 ;
    public int indexId = 0 ;
    public String analSemResult ="" ;





    public void analyzeSLnew(ArrayList<String> tt,ArrayList<UniteLexicale> tt1) {

        action = "";
        index = 0;
        String typeTab ="" ;
        String eType="" ;
        String jType="" ;
        
        analyse.push("0");


        System.out.println("********pile     	    Entrée            Action***********");
   
        while(index<tt.size()) {

            String s = analyse.peek();
            String act = action(s, tt.get(index));



            if (action(s, tt.get(index)).charAt(0) == 's') { // décalage

                action = "shift " + act;

                afficherSLRnew(tt);
                //System.out.println(tt.get(index));
                analyse.push(tt.get(index));
                // analyse sémantique
                /*if ("id".equals(tt.get(index)) && ("deuxpoint".equals(tt.get(index + 1)) || "opaff".equals(tt.get(index + 1)) ))
                {  pileId.push(tt1.get(index).getLexemee().toString());
                   }*/
             // Inside the analyzeSLnew method
                if ("id".equals(tt.get(index)) && ("deuxpoint".equals(tt.get(index + 1)) || "opaff".equals(tt.get(index + 1)))) {
                    String lexeme = tt1.get(index).getLexemee().toString();
                    String nextToken = tt.get(index + 1);
                    // Assuming the next token determines the type or the operation, adjust logic accordingly
                    if ("deuxpoint".equals(nextToken)) {
                        // Peek ahead to find the type after the colon
                        String typeToken = tt.get(index + 2);
                        if ("type".equals(typeToken)) {
                            // Assuming `getLexemee()` returns the type of the next token, adjust as necessary
                            String type = tt1.get(index + 2).getLexemee().toString();
                            // Save the identifier with its type in the symbol table
                            tableSym.put(lexeme, type);
                        }
                    }
                    pileId.push(lexeme);
                }

                if ("alors".equals(tt.get(index))) {
                   if (!analyseSem.pop().equals("BOOL"))
                   {
                   analSemResult="condition fausse : n'est pas de type bool "+"\n"+analSemResult ;
                   } }
                if ("tantque".equals(tt.get(index))) {
                    if (!analyseSem.pop().equals("BOOL"))
                    {
                        analSemResult="condition fausse : n'est pas de type bool "+"\n"+analSemResult ;
                    } }
                if("parenthd".equals(tt.get(index)))
                { analyseSem.pop();
                }



            // fin analyse sémantique


            //System.out.println(action(s, tt.get(index)).substring(1));
            analyse.push(action(s, tt.get(index)).substring(1));


            index++;

        }

            else if (action(s,tt.get(index)).charAt(0) == 'r') { // Réduction

                String str = LRGS[Integer.parseInt(action(s, tt.get(index)).substring(1))];
                action = "reduce:" + str;
                afficherSLRnew(tt);


                int pos = str.indexOf('>');

                String tabparties[] = str.split("->");


                String Partiegauche = tabparties[0];
                //System.out.println("Partiegauche"+Partiegauche);

                String Partiedroite = tabparties[1];
                //System.out.println("Partiedroite"+Partiedroite);

                String tabtoken[] = Partiedroite.split(" ");

                String identifier="";
                int taillepile = tabtoken.length * 2;
                String typeE = "";
                String typeJ = "";
                //analyse sémantique
               /* if (str.equals("T->type")) {
                    tableSym.put(pileId.pop(), tt1.get(index - 1).getLexemee().toString());
                    indexId++ ;
                }*/
             // Specific semantic actions for handling boolean types
                if ("T->type".equals(str) && ("vrai".equals(tt.get(index - 1)) || "faux".equals(tt.get(index - 1)))) {
                    // This is a simplified example. Adapt it based on how you manage types in your grammar
                    String id = pileId.pop();
                    tableSym.put(id, "BOOL");
                }
             // Inside the reduction section where you handle type declarations
              
                if (str.equals("T->tableau crochetg nb crochetd de T")) {
                    identifier =pileId.pop() ;
                    typeTab = tableSym.get(identifier);
                    tableSym.remove(identifier);
                    tableSym.put(identifier, "tableau(" + typeTab + ")");
                    indexId++ ;


                }
                if (Partiegauche.equals("E")) {
                    if (Partiedroite.equals("id")) {
                        if (tableSym.containsKey(tt1.get(index - 1).getLexemee().toString())) {
                            eType = tableSym.get(tt1.get(index - 1).getLexemee().toString());
                            analyseSem.push(eType);
                        } else {
                            System.out.println((tt1.get(index - 1).getLexemee().toString()) + "  varible non déclarée");
                            analSemResult=(tt1.get(index - 1).getLexemee().toString()) + "  varible non déclarée\n"+analSemResult ;
                            analyseSem.push(eType);

                        }


                    }

                    if (Partiedroite.equals("nb"))
                        analyseSem.push("ENTIER");
                    else if (Partiedroite.equals("litteral"))
                        analyseSem.push("CHAINE");
                    else if (Partiedroite.equals("vrai") || Partiedroite.equals("faux"))
                        analyseSem.push("BOOL");





                }

                if (Partiegauche.equals("J")) {


                    if (Partiedroite.equals("E opar J")) {
                        typeE = analyseSem.pop();
                        typeJ = analyseSem.pop();
                        //System.out.println(typeE+" "+typeJ);
                        if (typeJ.equalsIgnoreCase(typeE)) {
                            analyseSem.push(typeE);
                        } else {
                            analSemResult=analSemResult+"operation arithmétique sur des types incompatibles " + typeJ + " avec " + typeE+"\n" ;
                            analyseSem.push(typeE);


                        }
                        //System.out.println(analyseSem.peek());

                    }
                    if (Partiedroite.equals("E oprel J")) {
                        typeE = analyseSem.pop();
                        typeJ = analyseSem.pop();
                       // System.out.println(typeE+" "+typeJ);
                        if (typeJ.equalsIgnoreCase(typeE)) {
                            analyseSem.push("BOOL");
                        } else {
                            analSemResult=analSemResult+"operation relationnelle sur des types incompatibles " + typeJ + " avec " + typeE+"\n" ;
                            analyseSem.push("BOOL");

                        }

                    }
                }

                if (Partiegauche.equals("I")&&Partiedroite.equals("id opaff J pv I")) {
                   //System.out.println(analyseSem.peek()+"  "+pileId.peek()+" "+tableSym.get(pileId.peek()));
                    identifier= pileId.pop();
                    if (!tableSym.containsKey(identifier))
                    {  System.out.println("varible non déclarée " + identifier);
                        analSemResult="varible non déclarée " + identifier+"\n"+analSemResult ;
                        analyseSem.pop();
                    }
                    else if (! (tableSym.get(identifier).equalsIgnoreCase(analyseSem.peek()))) {
                            System.out.println("affectation impossible deux types incompatibles : " + "varible "+identifier+" de type: " + tableSym.get(identifier) + " expression de type: " + analyseSem.peek());
                            analSemResult="affectation impossible deux types incompatibles : " + "varible "+identifier+" de type: " + tableSym.get(identifier) + " expression de type: " + analyseSem.peek()+"\n"+analSemResult ;
                            indexId++ ;
                            analyseSem.pop();
                        }
                    else analyseSem.pop();
                    }









                 //fin analyse sémantique



                for (int i = 0; i < taillepile; i++) {



                    analyse.pop();

                }
                String sommetpile = analyse.peek();
                //System.out.println(sommetpile);
                analyse.push(Partiegauche);
                //String tetesucc = analyse.peek();
                // System.out.println(analyse.peek());

                analyse.push(action(sommetpile, Partiegauche));
                //System.out.println(action(sommetpile,Partiegauche));



            }

            else if (action(s,tt.get(index)) == "acc") //acceptation
            {   action="accept";
                afficherSLRnew(tt);
                System.out.println("analyze SLR successfully syntaxe correcte");
                break;}

            else //erreur

            {

                System.out.println("analyze SLR failled: "+tt1.get(index)+" mal inséré "+action(s,tt.get(index)) );//informer l'utilisateur d'une erreur
                afficherSLRnew(tt);
                break;


            }

        }

    }




    public String action(String s, String a) {
        for (int i = 1; i < 56; i++)
            if (tableSLR[i][0].equals(s))
                for (int j = 1; j < 31; j++)
                    if (tableSLR[0][j].equals(a))
                        return tableSLR[i][j];

        // Add logic for handling oprel
        if (a.equals("oprel")) {
            // Assuming you have a method to get the type of the last expression on the stack
            String type = getTypeOfLastExpression();
            if (type != null) {
                if (type.equals("BOOL")) {
                    return "s23";  // Adjust with the appropriate state transition for oprel on BOOL type
                } else {
                    System.out.println("Error: Operation 'oprel' on non-BOOL type.");
                    return "err";
                }
            }
        }

        return "err";
    }

    // Add a method to get the type of the last expression on the stack
    private String getTypeOfLastExpression() {
        // Implement logic to get the type of the last expression on the stack
        // For example, peek the stack and check the type from the symbol table
        if (!analyseSem.isEmpty()) {
            String lastExpression = analyseSem.peek();
            // Assuming you have a method to get the type from the symbol table
            return getTypeFromSymbolTable(lastExpression);
        }
        return null;
    }

    // Add a method to get the type from the symbol table
    private String getTypeFromSymbolTable(String identifier) {
        // Implement logic to get the type from the symbol table
        // For example, use the identifier to look up the type in the symbol table
        if (tableSym.containsKey(identifier)) {
            return tableSym.get(identifier);
        }
        return null;
    }







    public void  afficherSLRnew (ArrayList<String> tt) {

        strInput="";
        for(int i=index; i<tt.size();i++)
            strInput= strInput+" "+tt.get(i);

        System.out.printf("%s", analyse+"---------------------" );
        System.out.printf("%s", strInput+"---------------------");
        System.out.printf("%s", action);
        System.out.println();
    }

    public void ouput() {


        System.out.println("**********Tableau SLR¨********");

        for (int i = 0; i < 56 ; i++) {
            for (int j = 0; j <30; j++) {
                System.out.printf("%10s", tableSLR[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("**********Fin tableau SLR********"); 

    }
    public void getAnalSem (){
        System.out.println("**************************Analyse Sémantique¨***********************");
        System.out.println(analSemResult);
        System.out.println("**************************Fin Analyse Sémantique¨***********************");
        System.out.println();

    }
    public void getTableSym () {
        System.out.println("*****************table de symboles********************");
        System.out.println("lexeme       type");
        for (String i : tableSym.keySet()) {
            System.out.printf( i + "        " + tableSym.get(i) + " ");
            System.out.println();
        }

    }



}