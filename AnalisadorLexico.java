/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.util.CaracList;
import compilador.util.ReadFiles;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Eliene
 */
public class AnalisadorLexico {

    public ArrayList<CaracList> alfabeto = new ArrayList<>();
    public ArrayList<CaracList> alfabetoIlegal = new ArrayList<>();
    public ArrayList<char[]> CodigoFonte = new ArrayList<>();
    public ArrayList<String> Codigo = new ArrayList<>();

    public ReadFiles leitura = new ReadFiles();
    public ArrayList<Token> TokenList = new ArrayList<>();
    public ArrayList<Character> delimitadores = new ArrayList<>();
    public boolean lexico = false;

    public HashMap<String, Integer> PalavrasReservadas = new HashMap<>();
    public HashMap<String, Integer> PalavrasReservadasInvalidas = new HashMap<>();
    public HashMap<String, Integer> PalavrasInvalidas = new HashMap<>();
    public HashMap<String, Integer> OperadoresLogicos = new HashMap<>();
    public HashMap<String, Integer> OperadoresAritmeticos = new HashMap<>();
    public HashMap<String, Integer> OperadoresAtribuicao = new HashMap<>();
    public HashMap<String, Integer> SimboloEspecial = new HashMap<>();
    public HashMap<String, Integer> Delimitador = new HashMap<>();
    public HashMap<String, Integer> Tipo = new HashMap<>();

    public void Inicio() {

        OperadoresAtribuicao.put("=", 1);
        
        
        OperadoresLogicos.put(">", 1);
        OperadoresLogicos.put("<", 2);
        OperadoresLogicos.put("==", 3);
        OperadoresLogicos.put("!=", 4);
        OperadoresLogicos.put(">=", 5);
        OperadoresLogicos.put("<=", 6);
        OperadoresLogicos.put("||", 7);
        OperadoresLogicos.put("&&", 6);

        PalavrasReservadas.put("else", 1);
        PalavrasReservadas.put("main", 2);
        PalavrasReservadas.put("for", 3);
        PalavrasReservadas.put("if", 4);
        PalavrasReservadas.put("return", 5);
        PalavrasReservadas.put("while", 6);
        PalavrasReservadas.put("printf", 7);
        PalavrasReservadas.put("scanf", 8);
        PalavrasReservadas.put(";", 9);

        Tipo.put("int", 5);
        Tipo.put("char", 9);
        Tipo.put("float", 2);
        Tipo.put("void", 7);

        OperadoresAritmeticos.put("+", 1);
        OperadoresAritmeticos.put("++", 2);
        OperadoresAritmeticos.put("-", 3);
        OperadoresAritmeticos.put("--", 4);
        OperadoresAritmeticos.put("*", 5);
        OperadoresAritmeticos.put("/", 6);

        PalavrasInvalidas.put(":", 1);
        PalavrasInvalidas.put(".", 2);

        SimboloEspecial.put("(", 1);
        SimboloEspecial.put(")", 2);
        SimboloEspecial.put("{", 3);
        SimboloEspecial.put("}", 4);
        SimboloEspecial.put("[", 5);
        SimboloEspecial.put("]", 6);

        Delimitador.put(",", 1);

        PalavrasReservadasInvalidas.put("asm", 1);
        PalavrasReservadasInvalidas.put("auto", 2);
        PalavrasReservadasInvalidas.put("break", 3);
        PalavrasReservadasInvalidas.put("case", 4);
        PalavrasReservadasInvalidas.put("catch", 5);
        PalavrasReservadasInvalidas.put("class", 7);
        PalavrasReservadasInvalidas.put("const", 8);
        PalavrasReservadasInvalidas.put("continue", 9);
        PalavrasReservadasInvalidas.put("default", 10);
        PalavrasReservadasInvalidas.put("delete", 11);
        PalavrasReservadasInvalidas.put("do", 12);
        PalavrasReservadasInvalidas.put("double", 13);
        PalavrasReservadasInvalidas.put("short", 32);
        PalavrasReservadasInvalidas.put("signed", 33);
        PalavrasReservadasInvalidas.put("sizeof", 34);
        PalavrasReservadasInvalidas.put("static", 35);
        PalavrasReservadasInvalidas.put("struct", 36);
        PalavrasReservadasInvalidas.put("switch", 37);
        PalavrasReservadasInvalidas.put("template", 38);
        PalavrasReservadasInvalidas.put("this", 39);
        PalavrasReservadasInvalidas.put("throw", 40);
        PalavrasReservadasInvalidas.put("try", 41);
        PalavrasReservadasInvalidas.put("typedef", 42);
        PalavrasReservadasInvalidas.put("union", 43);
        PalavrasReservadasInvalidas.put("unsigned", 44);
        PalavrasReservadasInvalidas.put("virtual", 45);
        PalavrasReservadasInvalidas.put("enum", 15);
        PalavrasReservadasInvalidas.put("extern", 16);
        PalavrasReservadasInvalidas.put("long", 24);
        PalavrasReservadasInvalidas.put("new", 25);
        PalavrasReservadasInvalidas.put("operator", 26);
        PalavrasReservadasInvalidas.put("private", 27);
        PalavrasReservadasInvalidas.put("protected", 28);
        PalavrasReservadasInvalidas.put("public", 29);
        PalavrasReservadasInvalidas.put("register", 30);
        PalavrasReservadasInvalidas.put("friend", 19);
        PalavrasReservadasInvalidas.put("goto", 20);
        PalavrasReservadasInvalidas.put("volatile", 47);
        PalavrasReservadasInvalidas.put("inline", 22);

        delimitadores.add(' ');
        delimitadores.add(',');
        delimitadores.add(';');
        delimitadores.add(':');
        delimitadores.add('(');
        delimitadores.add(')');
        delimitadores.add('\n');
        delimitadores.add('{');
        delimitadores.add('}');
        //delimitadores.add('+');
        //delimitadores.add('-');
        delimitadores.add('*');
        delimitadores.add('[');
        delimitadores.add(']');
        delimitadores.add('=');
        //delimitadores.add('.');

        delimitadores.add('>');
        delimitadores.add('<');

        /*delimitadores.add("==");
         delimitadores.add("!=");
         delimitadores.add(">=");
         delimitadores.add("<=");
            
         delimitadores.add("=");
         delimitadores.add("+=");
         delimitadores.add("-=");
         delimitadores.add("*=");
         delimitadores.add("/=");
         delimitadores.add("%=");
         delimitadores.add("++");
         delimitadores.add("--");*/
        char[] aux;

        alfabeto = leitura.leituraAlfabeto();
        alfabetoIlegal = leitura.leituraAlfabetoIlegal();
        CodigoFonte = leitura.LerArquivo();
        Codigo = leitura.Repasse();
        System.out.println("===========Analisador Lexico===========");
        System.out.println("=======================================");
        System.out.println("====== Codigo fonte para Analise ======");
        leitura.ShowCode(CodigoFonte);
        System.out.println("=======================================");

        for (int i = 0; i < CodigoFonte.size(); i++) {
            aux = CodigoFonte.get(i);
            Tokenizar(aux, i);
        }
        System.out.println("Lista de Tokens");
        for (int i = 0; i < TokenList.size(); i++) {
            TokenList.get(i).imprime();
        }
        ClassificaTokens(TokenList);
        System.out.println("=======================================");
        AnalisadorSintatico as = new AnalisadorSintatico();
        as.inicializa(TokenList, Codigo);

    }

    public void Tokenizar(char[] code, int nlinha) {
        String pfor = "for";
        String pwhile = "while";
        String pif = "if";
        String pf = "printf";
        String psf = "scanf";
        
        
        int tam = 0;
        Token token;
        StringBuilder stoken = new StringBuilder();
        StringBuilder stokend = new StringBuilder();
        String linha;
        int contk = 0;
        boolean tokezao = false;
        String aux[];
        linha = new String(code);
        
        if(linha.contains(pfor)){
            tokezao = true;
        }else if(linha.contains(pwhile)){
            tokezao = true;
        }else if(linha.contains(pif)){
            tokezao = true;
        }else if(linha.contains(pf)){
            tokezao = true;
        }else if(linha.contains(psf)){
            tokezao = true;
        }
        
        aux = linha.split(" ");
        
        
        for (int i = 0; i < aux.length; i++) {
            stoken = new StringBuilder();

            for (int j = 0; j < aux[i].length(); j++) {
                tam = aux[i].length();
                lexema(aux[i].charAt(j));
                if (lexico == true) {
                    System.out.println("Erro - código fonte incompilavel " + aux[i].charAt(j) + " na linha: " + (nlinha));
                    lexico = false;
                }

                if (delimitadores.contains(aux[i].charAt(j))) {

                    if ((aux[i].charAt(j) == '(')&&(tokezao == true)) {//aqui
                        if ((stoken.length() > 0)) {
                            token = new Token(stoken.toString(), nlinha);
                            TokenList.add(token);
                            stoken = new StringBuilder();
                        }
                        //--------------------------------------------/
                        stokend.append(aux[i].charAt(j));
                        token = new Token(stokend.toString(), nlinha);
                        TokenList.add(token);
                        stokend = new StringBuilder();
                        //--------------------------------------------/
                        contk++;
                        j++;
                        lexema(aux[i].charAt(j));
                        if (lexico == true) {
                            System.out.println("Erro - código fonte incompilavel " + aux[i].charAt(j) + " na linha: " + (nlinha));
                            lexico = false;
                        }

                        while ((contk != 0)) {
                            if (i != aux.length) {

                                if (aux[i].length() == 0) {
                                    j = 0;
                                    i++;
                                } else {

                                    if (aux[i].charAt(j) == '(') {
                                        contk++;
                                    } else if (aux[i].charAt(j) == ')') {
                                        contk--;
                                        if (contk == 0) {
                                            if (stoken.length() > 0) {
                                                token = new Token(stoken.toString(), nlinha);
                                                token.setTokenzao(true);
                                                TokenList.add(token);
                                                stoken = new StringBuilder();
                                            }
                                            //--------------------------------------------/
                                            stokend.append(aux[i].charAt(j));
                                            token = new Token(stokend.toString(), nlinha);
                                            TokenList.add(token);
                                            stokend = new StringBuilder();

                                            //--------------------------------------------/
                                            break;
                                        }
                                    }
                                    stoken.append(aux[i].charAt(j));

                                    if (j != (aux[i].length() - 1)) {
                                        lexema(aux[i].charAt(j));
                                        if (lexico == true) {
                                            System.out.println("Erro - código fonte incompilavel " + aux[i].charAt(j) + " na linha: " + (nlinha));
                                            lexico = false;
                                        }
                                        j++;
                                    } else {
                                        j = 0;
                                        i++;
                                        stoken.append(' ');
                                    }
                                }
                            } else if (i == aux.length) {
                                break;
                            }
                        }
                        if (i == aux.length) {
                            break;
                        }
                    } else {

                        if ((stoken.length() > 0)) {
                            token = new Token(stoken.toString(), nlinha);
                            TokenList.add(token);
                            stoken = new StringBuilder();
                        }

                        stokend.append(aux[i].charAt(j));
                        token = new Token(stokend.toString(), nlinha);
                        TokenList.add(token);
                        stokend = new StringBuilder();
                    }

                } else {
                    stoken.append(aux[i].charAt(j));

                }

            }

            if ((stoken.length() > 0)) {
                token = new Token(stoken.toString(), nlinha);
                TokenList.add(token);
                stoken = new StringBuilder();
            }

        }
        

    }

    public ArrayList<Token> Tokenzao(char[] code, int nlinha, boolean interpretador) {
        
         String pfor = "for";
        String pwhile = "while";
        String pif = "if";
        String pf = "printf";
        String psf = "scanf";
        
        
        OperadoresAtribuicao.put("=", 1);
        
        
        OperadoresLogicos.put(">", 1);
        OperadoresLogicos.put("<", 2);
        OperadoresLogicos.put("==", 3);
        OperadoresLogicos.put("!=", 4);
        OperadoresLogicos.put(">=", 5);
        OperadoresLogicos.put("<=", 6);
        OperadoresLogicos.put("||", 7);
        OperadoresLogicos.put("&&", 6);

        PalavrasReservadas.put("else", 1);
        PalavrasReservadas.put("main", 2);
        PalavrasReservadas.put("for", 3);
        PalavrasReservadas.put("if", 4);
        PalavrasReservadas.put("return", 5);
        PalavrasReservadas.put("while", 6);
        PalavrasReservadas.put("printf", 7);
        PalavrasReservadas.put("scanf", 8);
        
        Tipo.put("int", 5);
        Tipo.put("char", 9);
        Tipo.put("float", 2);
        Tipo.put("void", 7);

        OperadoresAritmeticos.put("+", 1);
        OperadoresAritmeticos.put("++", 2);
        OperadoresAritmeticos.put("-", 3);
        OperadoresAritmeticos.put("--", 4);
        OperadoresAritmeticos.put("*", 5);
        OperadoresAritmeticos.put("/", 6);

        PalavrasInvalidas.put(":", 1);
        PalavrasInvalidas.put(".", 2);

        SimboloEspecial.put("(", 1);
        SimboloEspecial.put(")", 2);
        SimboloEspecial.put("{", 3);
        SimboloEspecial.put("}", 4);
        SimboloEspecial.put("[", 5);
        SimboloEspecial.put("]", 6);

        Delimitador.put(",", 1);
        Delimitador.put(";", 2);

        PalavrasReservadasInvalidas.put("asm", 1);
        PalavrasReservadasInvalidas.put("auto", 2);
        PalavrasReservadasInvalidas.put("break", 3);
        PalavrasReservadasInvalidas.put("case", 4);
        PalavrasReservadasInvalidas.put("catch", 5);
        PalavrasReservadasInvalidas.put("class", 7);
        PalavrasReservadasInvalidas.put("const", 8);
        PalavrasReservadasInvalidas.put("continue", 9);
        PalavrasReservadasInvalidas.put("default", 10);
        PalavrasReservadasInvalidas.put("delete", 11);
        PalavrasReservadasInvalidas.put("do", 12);
        PalavrasReservadasInvalidas.put("double", 13);
        PalavrasReservadasInvalidas.put("short", 32);
        PalavrasReservadasInvalidas.put("signed", 33);
        PalavrasReservadasInvalidas.put("sizeof", 34);
        PalavrasReservadasInvalidas.put("static", 35);
        PalavrasReservadasInvalidas.put("struct", 36);
        PalavrasReservadasInvalidas.put("switch", 37);
        PalavrasReservadasInvalidas.put("template", 38);
        PalavrasReservadasInvalidas.put("this", 39);
        PalavrasReservadasInvalidas.put("throw", 40);
        PalavrasReservadasInvalidas.put("try", 41);
        PalavrasReservadasInvalidas.put("typedef", 42);
        PalavrasReservadasInvalidas.put("union", 43);
        PalavrasReservadasInvalidas.put("unsigned", 44);
        PalavrasReservadasInvalidas.put("virtual", 45);
        PalavrasReservadasInvalidas.put("enum", 15);
        PalavrasReservadasInvalidas.put("extern", 16);
        PalavrasReservadasInvalidas.put("long", 24);
        PalavrasReservadasInvalidas.put("new", 25);
        PalavrasReservadasInvalidas.put("operator", 26);
        PalavrasReservadasInvalidas.put("private", 27);
        PalavrasReservadasInvalidas.put("protected", 28);
        PalavrasReservadasInvalidas.put("public", 29);
        PalavrasReservadasInvalidas.put("register", 30);
        PalavrasReservadasInvalidas.put("friend", 19);
        PalavrasReservadasInvalidas.put("goto", 20);
        PalavrasReservadasInvalidas.put("volatile", 47);
        PalavrasReservadasInvalidas.put("inline", 22);
        
        delimitadores.add(' ');
        delimitadores.add(',');
        delimitadores.add(';');
        delimitadores.add(':');
        delimitadores.add('(');
        delimitadores.add(')');
        delimitadores.add('\n');
        delimitadores.add('{');
        delimitadores.add('}');
        //delimitadores.add('+');
        //delimitadores.add('-');
        delimitadores.add('*');
        delimitadores.add('[');
        delimitadores.add(']');
        //delimitadores.add('=');
        //delimitadores.add('.');

        //delimitadores.add('>');
        //delimitadores.add('<');
        alfabeto = leitura.leituraAlfabeto();
        char sppp = ' ';
        
        alfabetoIlegal = leitura.leituraAlfabetoIlegal();

        ArrayList<Token> tokens = new ArrayList<>();
        int tam = 0;
        Token token;
        StringBuilder stoken = new StringBuilder();
        StringBuilder stokend = new StringBuilder();
        String linha;
        int contk = 0;
        boolean tokezao = false;
        String aux[];
        linha = new String(code);
        
         if(linha.contains(pfor)){
            tokezao = true;
        }else if(linha.contains(pwhile)){
            tokezao = true;
        }else if(linha.contains(pif)){
            tokezao = true;
        }else if(linha.contains(pf)){
            tokezao = true;
        }else if(linha.contains(psf)){
            tokezao = true;
        }
        
        
        aux = linha.split(" ");

        if(interpretador == true){
            aux = new String[1];
            aux[0] = linha;
        }
        
        for (int i = 0; i < aux.length; i++) {
            stoken = new StringBuilder();

            for (int j = 0; j < aux[i].length(); j++) {
                tam = aux[i].length();
                //lexema(aux[i].charAt(j));
               
                if ((lexico == true)&&(interpretador == false)) {
                    System.out.println("Erro - código fonte incompilavel " + aux[i].charAt(j) + " na linha: " + (nlinha));
                    lexico = false;
                }
                
                if (delimitadores.contains(aux[i].charAt(j))) {

                    if ((aux[i].charAt(j) == '(')&&(tokezao == true)) {
                        if ((stoken.length() > 0)) {
                            token = new Token(stoken.toString(), nlinha);
                            tokens.add(token);
                            stoken = new StringBuilder();
                        }
                        //--------------------------------------------/
                        stokend.append(aux[i].charAt(j));
                        token = new Token(stokend.toString(), nlinha);
                        tokens.add(token);
                        stokend = new StringBuilder();
                        //--------------------------------------------/
                        contk++;
                        j++;
                        //lexema(aux[i].charAt(j));
                        
                        if ((lexico == true)&&(interpretador == false)) {
                            System.out.println("Erro - código fonte incompilavel " + aux[i].charAt(j) + " na linha: " + (nlinha));
                            lexico = false;
                        }

                        while ((contk != 0)) {
                            if (i != aux.length) {

                                if (aux[i].length() == 0) {
                                    j = 0;
                                    i++;
                                } else {

                                    if (aux[i].charAt(j) == '(') {
                                        contk++;
                                    } else if (aux[i].charAt(j) == ')') {
                                        contk--;
                                        if (contk == 0) {
                                            
                                            token = new Token(stoken.toString(), nlinha);
                                            tokens.add(token);
                                            stoken = new StringBuilder();
                                            //--------------------------------------------/
                                            stokend.append(aux[i].charAt(j));
                                            token = new Token(stokend.toString(), nlinha);
                                            tokens.add(token);
                                            stokend = new StringBuilder();

                                            //--------------------------------------------/
                                            break;
                                        }
                                    }
                                    stoken.append(aux[i].charAt(j));

                                    if (j != (aux[i].length() - 1)) {
                                        //lexema(aux[i].charAt(j));
                                        
                                        if ((lexico == true)&&(interpretador == false)) {
                                            System.out.println("Erro - código fonte incompilavel " + aux[i].charAt(j) + " na linha: " + (nlinha));
                                            lexico = false;
                                        }
                                        j++;
                                    } else {
                                        j = 0;
                                        i++;
                                        stoken.append(' ');
                                    }
                                }
                            } else if (i == aux.length) {
                                break;
                            }
                        }
                        if (i == aux.length) {
                            break;
                        }
                    } else {

                        if ((stoken.length() > 0)) {
                            token = new Token(stoken.toString(), nlinha);
                            tokens.add(token);
                            stoken = new StringBuilder();
                        }

                        stokend.append(aux[i].charAt(j));
                        token = new Token(stokend.toString(), nlinha);
                        tokens.add(token);
                        stokend = new StringBuilder();
                    }

                } else {
                    stoken.append(aux[i].charAt(j));

                }

            }

            if ((stoken.length() > 0)) {
                token = new Token(stoken.toString(), nlinha);
                tokens.add(token);
                stoken = new StringBuilder();
            }

        }
        ClassificaTokens(tokens);
        return tokens;
    }

    public void lexema(char caractere) {

        boolean find = false;
        for (int i = 0; i < alfabeto.size(); i++) {
            if (caractere == alfabeto.get(i).caracter) {
                alfabeto.get(i).incidencia();
                find = true;
                break;
            }
        }
        if (find == false) {
            lexico = true;
        }

    }

    public void ClassificaTokens(ArrayList<Token> TokenList) {
       // System.out.println("=======================================");
        //System.out.println("Classificação dos Tokens");
       // System.out.println("");
        double valor;
        boolean ehNumero;
     //   System.out.println("----------------------------------------------------------");
        for (int i = 0; i < TokenList.size(); i++) {

            if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("Keywords");
            } else if (PalavrasReservadasInvalidas.containsKey(TokenList.get(i).data)) {
                System.out.println("Error");
                System.exit(0);
            } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {
                System.out.println("Error");
                System.exit(0);
            } else if (OperadoresLogicos.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("LogicalOperator");
            } else if (OperadoresAritmeticos.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("ArithmeticOperator");
            } else if (SimboloEspecial.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("SpecialSymbols");
            } else if (Delimitador.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("Delimiter");
            } else if (Tipo.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("TypeSpecifier");
            } else if (OperadoresAtribuicao.containsKey(TokenList.get(i).data)) {
                TokenList.get(i).setTipo("AssignmentOperator");
            }else {
                try {
                    valor = (Double.parseDouble(TokenList.get(i).data));
                    ehNumero = true;
                } catch (NumberFormatException e) {
                    ehNumero = false;
                }
                if (TokenList.get(i).data.length() > 0) {
                    if (ehNumero == true) {//constant
                        TokenList.get(i).setTipo("Constant");
                    }else if (TokenList.get(i).isTokenzao() == true) {
                        TokenList.get(i).setTipo("Tokenzao");
                    } else if (ehNumero == false && TokenList.get(i).data.charAt(0) == 34) {
                        TokenList.get(i).setTipo("String");
                    } else if (ehNumero == false && TokenList.get(i).data.charAt(0) == 39) {
                        TokenList.get(i).setTipo("String");
                    } else if (ehNumero == false && TokenList.get(i).data.length() > 0) {
                        TokenList.get(i).setTipo("Identfier");
                    } 
                }
            }
           // System.out.println(TokenList.get(i).data + " - " + TokenList.get(i).Tipo);
           // System.out.println("----------------------------------------------------------");
        }

    }
    
}
