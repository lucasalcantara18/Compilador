/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.util.Folha;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author lucao
 */
public class Interpretador {

    AnalisadorLexico al = new AnalisadorLexico();
    ArrayList<Token> tokenswhile = new ArrayList<>();
    ArrayList<Token> tokensaux = new ArrayList<>();
    ArrayList<Token> tokens = new ArrayList<>();
    double valor;
    int valor2;
    boolean ehNumero;
    boolean condwhile = false;

    //Em construção
    public void Inicio(ArrayList<Folha> arvore, ArrayList<Simbolo> Memoria) {
        System.out.println("-----------------------Interpretador----------------------");
        interpretar(arvore.get(0).folha, Memoria);

    }

    public void interpretar(ArrayList<Folha> folha, ArrayList<Simbolo> Memoria) {
        int n = 0;
        int tam = folha.size();

        ArrayList<Token> tokenscond = tokenswhile;
        boolean isWile = condwhile;
        if (isWile == true) {
            loopWhile(folha, Memoria, tokenscond, isWile);
            n = folha.size();
        }
        while (n != tam) {

            if (folha.get(n).profundidade == true) {
                StringBuilder cond = new StringBuilder();
                if (folha.get(n).codigo.contains("while")) {
                    tokensaux = al.Tokenzao(folha.get(n).codigo.toCharArray(), 0, true);
                    tokenswhile = al.Tokenzao(tokensaux.get(2).data.toCharArray(), n, false);
                    condwhile = true;

                    //solveExpressaoLogica(tokens, Memoria);
                }
                interpretar(folha.get(n).folha, Memoria);

                n++;
            } else {

                if (folha.get(n).codigo.contains("printf")) {
                    tokensaux = al.Tokenzao(folha.get(n).codigo.toCharArray(), 0, true);
                    tokens = al.Tokenzao(tokensaux.get(2).data.toCharArray(), n, false);
                    printf(tokens, Memoria);
                    n++;
                } else if (folha.get(n).codigo.contains("scanf")) {
                    tokensaux = al.Tokenzao(folha.get(n).codigo.toCharArray(), 0, true);
                    tokens = al.Tokenzao(tokensaux.get(2).data.toCharArray(), n, false);
                    scanf(tokens, Memoria);
                    n++;
                } else if (folha.get(n).expressao == true) {

                    tokensaux = al.Tokenzao(folha.get(n).codigo.toCharArray(), 0, true);
                    solveExpressao(tokensaux, Memoria);
                    n++;
                } else {
                    if (folha.get(n).codigo.contains("return")) {
                        System.out.println("Programa Finalizado");
                    }
                    n++;
                }

            }

        }

    }

    public void printf(ArrayList<Token> tokens, ArrayList<Simbolo> Memoria) {

        for (int i = 0; i < tokens.size(); i++) {

            
            if (tokens.get(i).Tipo.equals("String")) {
                
                if(tokens.get(i).data.contains("\n")){
                    System.out.println(tokens.get(i).getData());
                }else{
                     
                System.out.println(tokens.get(i).getData());
               
                }
                
            } else if (tokens.get(i).Tipo.equals("Constant")) {
                System.out.print(tokens.get(i).getData());
            } else if (tokens.get(i).Tipo.equals("Identfier")) {

                for (int j = 0; j < Memoria.size(); j++) {
                    if (Memoria.get(j).getToken().equals(tokens.get(i).data)) {
                        System.out.print(Memoria.get(j).getMemoria());
                    }
                }
            }

        }

    }

    public void scanf(ArrayList<Token> tokens, ArrayList<Simbolo> Memoria) {
        Scanner sc1 = new Scanner(System.in);
        String aux = " ";
        for (int i = 0; i < tokens.size(); i++) {

            if (tokens.get(i).Tipo.equals("Identfier")) {

                for (int j = 0; j < Memoria.size(); j++) {
                    if (Memoria.get(j).getToken().equals(tokens.get(i).data)) {
                        aux = sc1.nextLine();

                        if (Memoria.get(j).getIdentificador().equals("int")) {
                            try {
                                valor2 = (Integer.parseInt(aux));
                                ehNumero = true;
                            } catch (NumberFormatException e) {
                                ehNumero = false;
                            }
                            if (ehNumero == true) {
                                Memoria.get(j).setMemoria(aux);
                                ehNumero = false;
                            } else {
                                System.out.println("Código fonte não compilavel - tipos incompatíveis");
                                System.exit(0);
                            }

                        } else if (Memoria.get(j).getIdentificador().equals("float")) {
                            try {
                                valor = (Double.parseDouble(aux));
                                ehNumero = true;
                            } catch (NumberFormatException e) {
                                ehNumero = false;
                            }
                            if (ehNumero == true) {
                                Memoria.get(j).setMemoria(aux);
                                ehNumero = false;
                            } else {
                                System.out.println("Código fonte não compilavel - tipos incompatíveis");
                                System.exit(0);
                            }
                        } else if (Memoria.get(j).getIdentificador().equals("char")) {
                            try {
                                valor = (Double.parseDouble(aux));
                                ehNumero = true;
                            } catch (NumberFormatException e) {
                                ehNumero = false;
                            }
                            if (ehNumero == false) {
                                Memoria.get(j).setMemoria(aux);
                                ehNumero = false;
                            } else {
                                System.out.println("Código fonte não compilavel - tipos incompatíveis");
                                System.exit(0);
                            }
                        }

                    }
                }

            }

        }

    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        x += parseTerm(); // addition
                    } else if (eat('-')) {
                        x -= parseTerm(); // subtraction
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) {
                        x *= parseFactor(); // multiplication
                    } else if (eat('/')) {
                        x /= parseFactor(); // division
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    return parseFactor(); // unary plus
                }
                if (eat('-')) {
                    return -parseFactor(); // unary minus
                }
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') {
                        nextChar();
                    }
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) {
                        x = Math.sqrt(x);
                    } else if (func.equals("sin")) {
                        x = Math.sin(Math.toRadians(x));
                    } else if (func.equals("cos")) {
                        x = Math.cos(Math.toRadians(x));
                    } else if (func.equals("tan")) {
                        x = Math.tan(Math.toRadians(x));
                    } else {
                        throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) {
                    x = Math.pow(x, parseFactor()); // exponentiation
                }
                return x;
            }
        }.parse();
    }

    public void solveExpressao(ArrayList<Token> tokens, ArrayList<Simbolo> Memoria) {
        Token op = new Token();
        boolean opreduzido = false;
        boolean maismais = false;
        boolean menosmenos = false;

        String nome = " ";
        Simbolo saux = new Simbolo();
        double result = 0;
        StringBuilder eaux = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).data.equals(" ")) {
                tokens.remove(i);
            }
        }
        op = tokens.get(0);
        nome = op.data;
        if (nome.contains("--")) {
            nome = nome.substring(0, (nome.length() - 2));
            opreduzido = true;
            menosmenos = true;
        } else if (nome.contains("++")) {
            nome = nome.substring(0, (nome.length() - 2));
            opreduzido = true;
            maismais = true;
        }

        if (opreduzido != true) {

            for (int i = 0; i < Memoria.size(); i++) {
                if (Memoria.get(i).getToken().equals(op.data)) {
                    saux = Memoria.get(i);
                    break;
                }
            }

            for (int i = 2; i < tokens.size(); i++) {
                if (tokens.get(i).Tipo.equals("Identfier")) {
                    for (int j = 0; j < Memoria.size(); j++) {
                        if (Memoria.get(j).getToken().equals(tokens.get(i).data)) {
                            eaux.append(Memoria.get(j).getMemoria());
                            eaux.append(" ");
                            break;
                        }
                    }
                } else {
                    eaux.append(tokens.get(i).data);
                    eaux.append(" ");
                }

            }
            result = eval(eaux.toString());
            if(result > Double.MAX_VALUE){
                System.out.println("ERROR - Exceção Aritmética: divisão por 0");
                System.exit(0);
            }
            eaux = new StringBuilder();
            eaux.append(result);
            if (saux.getIdentificador().equals("int")) {
                if ((int) result == result) {
                    for (int i = 0; i < Memoria.size(); i++) {
                        if (Memoria.get(i).getToken().equals(op.data)) {
                            Memoria.get(i).setMemoria(eaux.toString());
                            break;
                        }
                    }
                } else {
                    System.out.println("Código fonte não compilavel - tipos incompatíveis");
                }
            } else if (op.Tipo.equals("float")) {
                for (int i = 0; i < Memoria.size(); i++) {
                    if (Memoria.get(i).getToken().equals(op.data)) {
                        Memoria.get(i).setMemoria(eaux.toString());
                    }
                }
            }
        } else {

            for (int i = 0; i < Memoria.size(); i++) {
                if (Memoria.get(i).getToken().equals(nome)) {
                    saux = Memoria.get(i);
                    break;
                }
            }
            int memI = 0;
            for (int j = 0; j < Memoria.size(); j++) {
                if (Memoria.get(j).getToken().equals(nome)) {
                    memI = j;
                    break;
                }
            }
            if (maismais == true) {
                eaux.append(Memoria.get(memI).getMemoria());
                eaux.append(" ");
                eaux.append("+");
                eaux.append(" ");
                eaux.append("1");
            } else if (menosmenos == true) {
                eaux.append(Memoria.get(memI).getMemoria());
                eaux.append(" ");
                eaux.append("-");
                eaux.append(" ");
                eaux.append("1");
            }

            result = eval(eaux.toString());
            if(result > Double.MAX_VALUE){
                System.out.println("ERROR - Exceção Aritmética: divisão por 0");
                System.exit(0);
            }
            eaux = new StringBuilder();
            eaux.append(result);
            if (saux.getIdentificador().equals("int")) {
                if ((int) result == result) {

                    Memoria.get(memI).setMemoria(eaux.toString());

                } else {
                    System.out.println("Código fonte não compilavel - tipos incompatíveis");
                }
            } else if (op.Tipo.equals("float")) {

                Memoria.get(memI).setMemoria(eaux.toString());

            }

        }
    }

    public static boolean solveExpressaoLogica(ArrayList<Token> tokens, ArrayList<Simbolo> Memoria) {
        String expressao = " ";
        boolean resultado = false;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).Tipo.equals("LogicalOperator")) {
                expressao = tokens.get(i).data;
            }
        }

        String[] expArray = null;
        double n1 = 0;
        double n2 = 0;
        int contop = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).Tipo.equals("Identfier")) {
                for (int j = 0; j < Memoria.size(); j++) {
                    if (Memoria.get(j).getToken().equals(tokens.get(i).data)) {
                        if (contop == 0) {
                            n1 = Double.parseDouble(Memoria.get(j).getMemoria());
                            contop++;
                        } else if (contop == 1) {
                            n2 = Double.parseDouble(Memoria.get(j).getMemoria());
                            contop++;
                        }
                    }
                }

            } else if ((tokens.get(i).Tipo.equals("Constant"))) {

                if (contop == 0) {
                    n1 = Double.parseDouble(tokens.get(i).data);
                    contop++;
                } else if (contop == 1) {
                    n2 = Double.parseDouble(tokens.get(i).data);
                    contop++;
                }
            }
        }

        if (expressao.contains("==")) {
            if (n1 == n2) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else if (expressao.contains("!=")) {
            if (n1 != n2) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else if (expressao.contains(">=")) {
            if (n1 >= n2) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else if (expressao.contains("<=")) {
            if (n1 <= n2) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else if (expressao.contains(">")) {
            if (n1 > n2) {
                resultado = true;
            } else {
                resultado = false;
            }
        } else if (expressao.contains("<")) {
            if (n1 < n2) {
                resultado = true;
            } else {
                resultado = false;
            }
        }

        return resultado;

    }

    public void loopWhile(ArrayList<Folha> folha, ArrayList<Simbolo> Memoria, ArrayList<Token> tokenscond, boolean isWhile) {
        Token t = tokenscond.get(0);
        double ti = 0;
        int indice = 0;
        ArrayList<Token> tokenscicao = tokenscond;
        String tnum;
        boolean condicao = isWhile;
        for (int i = 0; i < Memoria.size(); i++) {
            if (Memoria.get(i).getToken().equals(t.data)) {
                indice = (int) Double.parseDouble(Memoria.get(i).getMemoria());
                break;
            }
        }
        while (solveExpressaoLogica(tokenscicao, Memoria) != false) {

           

            if (indice == folha.size()) {
                indice = 0;
            }
            if (folha.get(indice).profundidade == true) {
                StringBuilder cond = new StringBuilder();
                if (folha.get(indice).codigo.contains("while")) {
                    tokensaux = al.Tokenzao(folha.get(indice).codigo.toCharArray(), 0, true);
                    tokenswhile = al.Tokenzao(tokensaux.get(2).data.toCharArray(), indice, false);
                    condwhile = true;

                    //solveExpressaoLogica(tokens, Memoria);
                }
                interpretar(folha.get(indice).folha, Memoria);

                indice++;
            } else {

                if (folha.get(indice).codigo.contains("printf")) {
                    tokensaux = al.Tokenzao(folha.get(indice).codigo.toCharArray(), 0, true);
                    tokens = al.Tokenzao(tokensaux.get(2).data.toCharArray(), indice, false);
                    printf(tokens, Memoria);
                    indice++;
                } else if (folha.get(indice).codigo.contains("scanf")) {
                    tokensaux = al.Tokenzao(folha.get(indice).codigo.toCharArray(), 0, true);
                    tokens = al.Tokenzao(tokensaux.get(2).data.toCharArray(), indice, false);
                    scanf(tokens, Memoria);
                    indice++;
                } else if (folha.get(indice).expressao == true) {

                    tokensaux = al.Tokenzao(folha.get(indice).codigo.toCharArray(), 0, true);
                    solveExpressao(tokensaux, Memoria);
                    indice++;
                } else {
                    
                    indice++;
                }

            }

        }

    }

}
