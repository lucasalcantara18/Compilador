/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.util.Folha;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author lucao
 */
public class AnalisadorSintatico {

    public Interpretador interpretador = new Interpretador();
    public HashMap<String, Integer> TabelaSimbolos = new HashMap<>();
    public HashMap<String, Integer> PalavrasReservadas = new HashMap<>();
    public HashMap<String, Integer> PalavrasInvalidas = new HashMap<>();
    public HashMap<String, Integer> OperadoresLogicos = new HashMap<>();
    //public HashMap<String, Integer> PalavrasInvalidas = new HashMap<>();
    public ArrayList<Folha> arvore = new ArrayList<>();
    public ArrayList<Simbolo> Tabela = new ArrayList<>();
    public ArrayList<Simbolo> Memoria = new ArrayList<>();
    
    ArrayList<Integer> indices = new ArrayList<>();
    ArrayList<String> Codigo;
    String Escopo;
    //Stack ControleEscopos = new Stack();
    boolean ftwg = false;
    StringBuilder incrementog = new StringBuilder();
    int valEstrutura = 0;
    int efor = 0, ewhile = 0, eif = 0, eelse = 0;//controla escopo de cada bloco

    public void inicializa(ArrayList<Token> TokenList, ArrayList<String> Codigo) {

        PalavrasInvalidas.put(";", 2);
        PalavrasInvalidas.put(":", 3);
        PalavrasInvalidas.put("(", 4);
        PalavrasInvalidas.put(")", 5);
        PalavrasInvalidas.put("{", 6);
        PalavrasInvalidas.put("}", 7);
        PalavrasInvalidas.put("+", 8);
        PalavrasInvalidas.put("-", 9);
        PalavrasInvalidas.put("*", 10);
        PalavrasInvalidas.put("[", 11);
        PalavrasInvalidas.put("]", 12);
        PalavrasInvalidas.put(".", 14);
        PalavrasInvalidas.put(">", 15);
        PalavrasInvalidas.put("<", 16);
        PalavrasInvalidas.put("==", 17);
        PalavrasInvalidas.put("!=", 18);
        PalavrasInvalidas.put(">=", 19);
        PalavrasInvalidas.put("<=", 20);
        //PalavrasInvalidas.put("=", 21);
        PalavrasInvalidas.put("+=", 22);
        PalavrasInvalidas.put("-=", 23);
        PalavrasInvalidas.put("*=", 24);
        PalavrasInvalidas.put("/=", 25);
        PalavrasInvalidas.put("%=", 26);
        PalavrasInvalidas.put("++", 27);
        PalavrasInvalidas.put("--", 28);

        OperadoresLogicos.put(">", 15);
        OperadoresLogicos.put("<", 16);
        OperadoresLogicos.put("==", 17);
        OperadoresLogicos.put("!=", 18);
        OperadoresLogicos.put(">=", 19);
        OperadoresLogicos.put("<=", 20);

        PalavrasReservadas.put("asm", 1);
        PalavrasReservadas.put("auto", 2);
        PalavrasReservadas.put("break", 3);
        PalavrasReservadas.put("case", 4);
        PalavrasReservadas.put("catch", 5);
        PalavrasReservadas.put("char", 6);
        PalavrasReservadas.put("class", 7);
        PalavrasReservadas.put("const", 8);
        PalavrasReservadas.put("continue", 9);
        PalavrasReservadas.put("default", 10);
        PalavrasReservadas.put("delete", 11);
        PalavrasReservadas.put("do", 12);
        PalavrasReservadas.put("double", 13);
        PalavrasReservadas.put("else", 14);
        PalavrasReservadas.put("enum", 15);
        PalavrasReservadas.put("extern", 16);
        PalavrasReservadas.put("float", 17);
        PalavrasReservadas.put("for", 18);
        PalavrasReservadas.put("friend", 19);
        PalavrasReservadas.put("goto", 20);
        PalavrasReservadas.put("if", 21);
        PalavrasReservadas.put("inline", 22);
        PalavrasReservadas.put("int", 23);
        PalavrasReservadas.put("long", 24);
        PalavrasReservadas.put("new", 25);
        PalavrasReservadas.put("operator", 26);
        PalavrasReservadas.put("private", 27);
        PalavrasReservadas.put("protected", 28);
        PalavrasReservadas.put("public", 29);
        PalavrasReservadas.put("register", 30);
        PalavrasReservadas.put("return", 31);
        PalavrasReservadas.put("short", 32);
        PalavrasReservadas.put("signed", 33);
        PalavrasReservadas.put("sizeof", 34);
        PalavrasReservadas.put("static", 35);
        PalavrasReservadas.put("struct", 36);
        PalavrasReservadas.put("switch", 37);
        PalavrasReservadas.put("template", 38);
        PalavrasReservadas.put("this", 39);
        PalavrasReservadas.put("throw", 40);
        PalavrasReservadas.put("try", 41);
        PalavrasReservadas.put("typedef", 42);
        PalavrasReservadas.put("union", 43);
        PalavrasReservadas.put("unsigned", 44);
        PalavrasReservadas.put("virtual", 45);
        PalavrasReservadas.put("void", 46);
        PalavrasReservadas.put("volatile", 47);
        PalavrasReservadas.put("while", 48);
        this.Codigo = Codigo;
        CriaArvore(TokenList);

    }

    public boolean func = false;
    boolean control = false;
    double valor;
    boolean ehNumero;
    boolean a = false;
    boolean PontoVirgula = false;
    int i = 0;
    int k = 0;

    public void CriaTabelaSinbolos(ArrayList<Token> TokenList, ArrayList<Simbolo> Tabela, int controleTokenList, String k, int fimLinha) {
        boolean var = false;
        int controleLinha = 0;
        Simbolo simbolo;
        String type = " ";
        for (i = controleTokenList; i < fimLinha; i++) {
            PontoVirgula = false;
            func = false;
            if (control == true) {
                break;
            }
            if (TokenList.get(i).data.equals("int")) {
                type = "int";
                VerificaFuncao(TokenList, i);
                i++;
                if (func != true) {
                    if (TokenList.get(i).data.equals(";")) {//int; ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                        break;
                    } else if (TokenList.get(i).data.equals("=")) {//int = ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                        break;
                    } else if (TokenList.get(i - 1).linha != TokenList.get(i).linha) {//int ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + (TokenList.get(i).linha - 1));
                        break;
                    } else if (TokenList.get(i).data.equals(",")) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + (TokenList.get(i).linha - 1));
                        break;
                    } else {//int numLiteral
                        try {
                            valor = (Double.parseDouble(TokenList.get(i).data));
                            ehNumero = true;
                        } catch (NumberFormatException e) {
                            ehNumero = false;
                        }
                        if (ehNumero == true) {//int 0 ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {//int palavraReservada ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {//int delimitador ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (ehNumero == false) {//Entrou - int aux |
                            i++;
                            if (TokenList.get(i).data.equals(";")) {
                                TokenList.get(i - 1).setEscopo(k);
                                simbolo = new Simbolo(TokenList.get(i - 1).data, "int");
                                simbolo.setEscopo(k);
                                Memoria.add(simbolo);
                                Tabela.add(simbolo);
                                TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                            } else if (TokenList.get(i - 1).linha != TokenList.get(i).linha) {
                                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                break;
                            } else {
                                try {
                                    valor = (Double.parseDouble(TokenList.get(i).data));
                                    ehNumero = true;
                                } catch (NumberFormatException e) {
                                    ehNumero = false;
                                }
                                if (ehNumero == true) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (TokenList.get(i).data.equals(",") || TokenList.get(i).data.equals("=")) {

                                    if (TokenList.get(i).data.equals(",")) {
                                        TokenList.get(i - 1).setEscopo(k);
                                        simbolo = new Simbolo(TokenList.get(i - 1).data, "int");
                                        simbolo.setEscopo(k);
                                         Memoria.add(simbolo);
                                        Tabela.add(simbolo);
                                        TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                                        i++;
                                        virgula(TokenList, i, Tabela, k, type);

                                    } else if (TokenList.get(i).data.equals("=")) {
                                        TokenList.get(i - 1).setEscopo(k);
                                        simbolo = new Simbolo(TokenList.get(i - 1).data, "int");
                                        simbolo.setEscopo(k);
                                         Memoria.add(simbolo);
                                        Tabela.add(simbolo);
                                        //TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                                        i++;
                                        recebe(TokenList, i, Tabela, k, type);
                                    }

                                } else if (ehNumero == false) {

                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;

                                }

                            }

                        }

                    }
                }
            } else if (TokenList.get(i).data.equals("float")) {
                type = "float";
                VerificaFuncao(TokenList, i);
                i++;
                if (func != true) {
                    if (TokenList.get(i).data.equals(";")) {//int; ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                        break;
                    } else if (TokenList.get(i).data.equals("=")) {//int = ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                        break;
                    } else if (TokenList.get(i - 1).linha != TokenList.get(i).linha) {//int ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + (TokenList.get(i).linha - 1));
                        break;
                    } else if (TokenList.get(i).data.equals(",")) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + (TokenList.get(i).linha - 1));
                        break;
                    } else {//int numLiteral
                        try {
                            valor = (Double.parseDouble(TokenList.get(i).data));
                            ehNumero = true;
                        } catch (NumberFormatException e) {
                            ehNumero = false;
                        }
                        if (ehNumero == true) {//int 0 ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {//int palavraReservada ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {//int delimitador ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (ehNumero == false) {//Entrou - int aux |
                            i++;
                            if (TokenList.get(i).data.equals(";")) {
                                TokenList.get(i - 1).setEscopo(k);
                                simbolo = new Simbolo(TokenList.get(i - 1).data, "float");
                                simbolo.setEscopo(k);
                                 Memoria.add(simbolo);
                                Tabela.add(simbolo);
                                TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                            } else if (TokenList.get(i - 1).linha != TokenList.get(i).linha) {
                                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                break;
                            } else {
                                try {
                                    valor = (Double.parseDouble(TokenList.get(i).data));
                                    ehNumero = true;
                                } catch (NumberFormatException e) {
                                    ehNumero = false;
                                }
                                if (ehNumero == true) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (TokenList.get(i).data.equals(",") || TokenList.get(i).data.equals("=")) {

                                    if (TokenList.get(i).data.equals(",")) {
                                        TokenList.get(i - 1).setEscopo(k);
                                        simbolo = new Simbolo(TokenList.get(i - 1).data, "float");
                                        simbolo.setEscopo(k);
                                         Memoria.add(simbolo);
                                        Tabela.add(simbolo);
                                        TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                                        i++;
                                        virgula(TokenList, i, Tabela, k, type);

                                    } else if (TokenList.get(i).data.equals("=")) {
                                        TokenList.get(i - 1).setEscopo(k);
                                        simbolo = new Simbolo(TokenList.get(i - 1).data, "float");
                                        simbolo.setEscopo(k);
                                         Memoria.add(simbolo);
                                        Tabela.add(simbolo);
                                        TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                                        i++;
                                        recebe(TokenList, i, Tabela, k, type);
                                    }

                                } else if (ehNumero == false) {

                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;

                                }

                            }

                        }

                    }
                }
            } else if (TokenList.get(i).data.equals("void")) {
                VerificaFuncao(TokenList, i);
                if (TokenList.get(i).data.equals(";")) {
                    System.out.println("not a statement in line " + TokenList.get(i).linha);
                }
            } else if (TokenList.get(i).data.equals("char")) {
                VerificaFuncao(TokenList, i);
                i++;
                if (func != true) {
                    if (TokenList.get(i).data.equals(";")) {//int; ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                        break;
                    } else if (TokenList.get(i).data.equals("=")) {//int = ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                        break;
                    } else if (TokenList.get(i - 1).linha != TokenList.get(i).linha) {//int ERROR
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + (TokenList.get(i).linha - 1));
                        break;
                    } else if (TokenList.get(i).data.equals(",")) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + (TokenList.get(i).linha - 1));
                        break;
                    } else {//int numLiteral
                        try {
                            valor = (Double.parseDouble(TokenList.get(i).data));
                            ehNumero = true;
                        } catch (NumberFormatException e) {
                            ehNumero = false;
                        }
                        if (ehNumero == true) {//int 0 ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {//int palavraReservada ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {//int delimitador ERROR
                            System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                            break;
                        } else if (ehNumero == false) {//Entrou - int aux |
                            i++;
                            if (TokenList.get(i).data.equals(";")) {
                                TokenList.get(i - 1).setEscopo(k);
                                simbolo = new Simbolo(TokenList.get(i - 1).data, "char");
                                simbolo.setEscopo(k);
                                 Memoria.add(simbolo);
                                Tabela.add(simbolo);
                                TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                            } else if (TokenList.get(i - 1).linha != TokenList.get(i).linha) {
                                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                break;
                            } else {
                                try {
                                    valor = (Double.parseDouble(TokenList.get(i).data));
                                    ehNumero = true;
                                } catch (NumberFormatException e) {
                                    ehNumero = false;
                                }
                                if (ehNumero == true) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (PalavrasReservadas.containsKey(TokenList.get(i).data)) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (PalavrasInvalidas.containsKey(TokenList.get(i).data)) {//
                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;
                                } else if (TokenList.get(i).data.equals(",") || TokenList.get(i).data.equals("=")) {

                                    if (TokenList.get(i).data.equals(",")) {
                                        TokenList.get(i - 1).setEscopo(k);
                                        simbolo = new Simbolo(TokenList.get(i - 1).data, "char");
                                        simbolo.setEscopo(k);
                                         Memoria.add(simbolo);
                                        Tabela.add(simbolo);
                                        TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                                        i++;
                                        virgulaC(TokenList, i, Tabela, k);

                                    } else if (TokenList.get(i).data.equals("=")) {
                                        TokenList.get(i - 1).setEscopo(k);
                                        simbolo = new Simbolo(TokenList.get(i - 1).data, "char");
                                        simbolo.setEscopo(k);
                                         Memoria.add(simbolo);
                                        Tabela.add(simbolo);
                                        TabelaSimbolos.put(TokenList.get(i - 1).data, 0);
                                        i++;
                                        recebeC(TokenList, i, Tabela, k);
                                    }

                                } else if (ehNumero == false) {

                                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(i).linha);
                                    break;

                                }

                            }

                        }

                    }
                }

            }
        }
    }

    public void virgula(ArrayList<Token> TokenList, int pos, ArrayList<Simbolo> Tabela, String k, String tipo) {
        Simbolo simbolo;
        while (PontoVirgula != true) {

            if (TokenList.get(pos).data.equals(",")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).linha != TokenList.get(pos - 1).linha) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals(";")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals("=")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else {
                try {
                    valor = (Double.parseDouble(TokenList.get(pos).data));
                    ehNumero = true;
                } catch (NumberFormatException e) {
                    ehNumero = false;
                }
                if (ehNumero == true) {
                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                    control = true;
                    break;
                } else if ((ehNumero == false) && (TokenList.get(pos).data.charAt(0) == 39)) {
                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                    control = true;
                    break;
                } else if ((ehNumero == false) && (TokenList.get(pos).data.charAt(0) != 39)) {
                    TokenList.get(pos).setEscopo(k);
                    simbolo = new Simbolo(TokenList.get(pos).data, tipo);
                    simbolo.setEscopo(k);
                     Memoria.add(simbolo);
                    Tabela.add(simbolo);
                    TabelaSimbolos.put(TokenList.get(pos).data, 0);
                    if (TokenList.get(pos + 1).data.equals(";")) {
                        PontoVirgula = true;
                        break;
                    } else if (TokenList.get(pos + 1).data.equals(",")) {
                        pos = pos + 2;
                        virgula(TokenList, pos, Tabela, k, tipo);
                    } else if (TokenList.get(pos).linha != TokenList.get(pos + 1).linha) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                        control = true;
                        break;
                    } else if (TokenList.get(pos + 1).data.equals("=")) {
                        pos = pos + 2;
                        recebe(TokenList, pos, Tabela, k, tipo);
                    }
                }

            }

        }
        i = pos;

    }

    public void virgulaC(ArrayList<Token> TokenList, int pos, ArrayList<Simbolo> Tabela, String k) {
        Simbolo simbolo;
        while (PontoVirgula != true) {
            if (control == true) {
                break;
            }

            if (TokenList.get(pos).data.equals(",")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).linha != TokenList.get(pos - 1).linha) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals(";")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals("=")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else {
                try {
                    valor = (Double.parseDouble(TokenList.get(pos).data));
                    ehNumero = true;
                } catch (NumberFormatException e) {
                    ehNumero = false;
                }
                if (ehNumero == true) {
                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                    control = true;
                    break;
                } else if ((ehNumero == false) && (TokenList.get(pos).data.charAt(0) == 39)) {
                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                    control = true;
                    break;
                } else if ((ehNumero == false) && (TokenList.get(pos).data.charAt(0) != 39)) {
                    TokenList.get(pos).setEscopo(k);
                    simbolo = new Simbolo(TokenList.get(pos).data, "int");
                    simbolo.setEscopo(k);
                     Memoria.add(simbolo);
                    Tabela.add(simbolo);
                    TabelaSimbolos.put(TokenList.get(pos).data, 0);
                    if (TokenList.get(pos + 1).data.equals(";")) {
                        PontoVirgula = true;
                        break;
                    } else if (TokenList.get(pos + 1).data.equals(",")) {
                        pos = pos + 2;
                        virgulaC(TokenList, pos, Tabela, k);
                    } else if (TokenList.get(pos).linha != TokenList.get(pos + 1).linha) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                        control = true;
                        break;
                    } else if (TokenList.get(pos + 1).data.equals("=")) {
                        pos = pos + 2;
                        recebeC(TokenList, pos, Tabela, k);
                    }
                }

            }

        }
        i = pos;

    }

    public void recebeC(ArrayList<Token> TokenList, int pos, ArrayList<Simbolo> Tabela, String k) {
        Simbolo simbolo;
        while (PontoVirgula != true) {
            if (control == true) {
                break;
            }

            if (TokenList.get(pos).data.equals(",")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).linha != TokenList.get(pos - 1).linha) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals(";")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals("=")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else {
                try {
                    valor = (Double.parseDouble(TokenList.get(pos).data));
                    ehNumero = true;
                } catch (NumberFormatException e) {
                    ehNumero = false;
                }
                if (ehNumero == true) {//
                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                    control = true;
                    break;

                } else if (ehNumero == false) {//

                    //simbolo = new Simbolo(TokenList.get(pos-2).data, "int");
                    //TabelaSimbolos.put(TokenList.get(pos-2).data, simbolo);
                    if (TokenList.get(pos + 1).data.equals(";")) {
                        PontoVirgula = true;
                        break;
                    } else if (TokenList.get(pos + 1).data.equals(",")) {
                        pos = pos + 2;

                        virgulaC(TokenList, pos, Tabela, k);
                    } else if (TokenList.get(pos).linha != TokenList.get(pos + 1).linha) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                        control = true;
                        break;
                    }
                }
            }

        }
        i = pos;
    }

    public void recebe(ArrayList<Token> TokenList, int pos, ArrayList<Simbolo> Tabela, String k, String tipo) {
        Simbolo simbolo;
        while (PontoVirgula != true) {

            if (TokenList.get(pos).data.equals(",")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).linha != TokenList.get(pos - 1).linha) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals(";")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else if (TokenList.get(pos).data.equals("=")) {
                System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                control = true;
                break;
            } else {
                try {
                    valor = (Double.parseDouble(TokenList.get(pos).data));
                    ehNumero = true;
                } catch (NumberFormatException e) {
                    ehNumero = false;
                }
                if (ehNumero == true) {//
                    //simbolo = new Simbolo(TokenList.get(pos-2).data, "int");
                    //TabelaSimbolos.put(TokenList.get(pos-2).data, simbolo);
                    if (TokenList.get(pos + 1).data.equals(";")) {
                        PontoVirgula = true;
                        break;
                    } else if (TokenList.get(pos + 1).data.equals(",")) {
                        pos = pos + 2;

                        virgula(TokenList, pos, Tabela, k, tipo);
                    } else if (TokenList.get(pos).linha != TokenList.get(pos + 1).linha) {
                        System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                        control = true;
                        break;
                    }
                } else if (ehNumero == false) {//
                    System.out.println("Código fonte não compilável - não é uma declaração, na linha " + TokenList.get(pos).linha);
                    control = true;
                    break;
                }
            }

        }
        i = pos;
    }

    public void VerificaFuncao(ArrayList<Token> TokenList, int pos) {
        Simbolo simbolo;
        StringBuilder funcao = new StringBuilder();
        for (int j = pos; j < TokenList.size(); j++) {
            if (TokenList.get(j).data.equals("int")) {
                j++;
                if (TokenList.get(j).data.equals("main")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    func = true;
                } else if (TokenList.get(j + 1).data.equals("(")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    simbolo = new Simbolo(funcao.toString(), "funcao");
                    TabelaSimbolos.put(funcao.toString(), 0);
                    func = true;
                    break;
                } else {
                    break;
                }
            } else if (TokenList.get(j).data.equals("float")) {
                j++;
                if (TokenList.get(j).data.equals("main")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    func = true;
                } else if (TokenList.get(j + 1).data.equals("(")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    simbolo = new Simbolo(funcao.toString(), "funcao");
                    TabelaSimbolos.put(funcao.toString(), 0);
                    func = true;
                    break;
                } else {
                    break;
                }
            } else if (TokenList.get(j).data.equals("char")) {
                j++;
                if (TokenList.get(j).data.equals("main")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    func = true;
                } else if (TokenList.get(j + 1).data.equals("(")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    simbolo = new Simbolo(funcao.toString(), "funcao");
                    TabelaSimbolos.put(funcao.toString(), 0);
                    func = true;
                    break;
                } else {
                    break;
                }
            } else if (TokenList.get(j).data.equals("void")) {
                j++;
                if (TokenList.get(j).data.equals("main")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    func = true;
                } else if (TokenList.get(j + 1).data.equals("(")) {
                    while (!TokenList.get(j).data.equals(")")) {
                        funcao.append(TokenList.get(j).data);
                        j++;
                    }
                    funcao.append(TokenList.get(j).data);
                    simbolo = new Simbolo(funcao.toString(), "funcao");
                    TabelaSimbolos.put(funcao.toString(), 0);
                    func = true;
                    break;
                } else {
                    break;
                }
            }
        }
    }
    public int contkeys = 0;

    public void CriaArvore(ArrayList<Token> TokenList) {
        String ControleEscopo = new String();
        StringBuilder str = new StringBuilder();
        String srt = new String();
        Folha f = new Folha();
        Simbolo s = new Simbolo();
        int controlArvore = 0;
        float aux = 0;

        for (i = 0; i < TokenList.size(); i++) {

            if (TokenList.get(i).data.equals("int") || TokenList.get(i).data.equals("char") || TokenList.get(i).data.equals("float") || TokenList.get(i).data.equals("void")) {
                str.append(TokenList.get(i).data);
                str.append(" ");
                i++;
                if (TokenList.get(i).data.equals("main")) {
                    while (!TokenList.get(i).data.equals("{")) {
                        str.append(TokenList.get(i).data);
                        str.append(" ");
                        i++;
                    }
                    i++;
                    contkeys++;
                    f.setCodigo(str.toString());
                    str = new StringBuilder();
                    f.setProfundidade(true);
                    //s.setEscopo(controlArvore + 1);
                    s.setToken("raiz");
                    s.setIdentificador("raiz");
                    arvore.add(f);
                    Tabela.add(s);
                    indices.add(controlArvore);
                    //ControleEscopo = controlArvore + 1;
                    //ControleEscopo = (float) (ControleEscopo + 0.1);
                    ControleEscopo = "main";
                    Arvore(TokenList, ControleEscopo, arvore.get(controlArvore).folha, arvore.get(controlArvore).tabelaSimbolos);
                    //ControleEscopo++;
                    controlArvore++;

                } else {

                     while (!TokenList.get(i).data.equals("{")) {
                        str.append(TokenList.get(i).data);
                        str.append(" ");
                        i++;
                    }
                    i++;
                    contkeys++;
                    f.setCodigo(str.toString());
                    str = new StringBuilder();
                    f.setProfundidade(true);
                    //s.setEscopo(controlArvore + 1);
                    s.setToken("raiz");
                    s.setIdentificador("raiz");
                    arvore.add(f);
                    Tabela.add(s);
                    indices.add(controlArvore);
                    //ControleEscopo = controlArvore + 1;
                    //ControleEscopo = (float) (ControleEscopo + 0.1);
                    ControleEscopo = "main";
                    Arvore(TokenList, ControleEscopo, arvore.get(controlArvore).folha, arvore.get(controlArvore).tabelaSimbolos);
                    //ControleEscopo++;
                    controlArvore++;
                    
                    
                }

            }

        }
        
        interpretador.Inicio(arvore,Memoria);

    }

    public void VerificaSintaxe(ArrayList<Token> TokenList, int indice, int numlinha, ArrayList<Simbolo> tabela) {

        //----------adicionar chaves para estruturas de uma instrução-------------//    
        if((!TokenList.get(indice).data.equals("printf"))&&(!TokenList.get(indice).data.equals("scanf"))&&(!TokenList.get(indice).data.equals("return"))){
            int auxind = indice;
            int numlinharef = TokenList.get(indice).linha;
            boolean fk = true;
            int posin = 0;
            int numlpos = 0;
            int posf = 0;

            for (int j = auxind; j < TokenList.size(); j++) {

                if (TokenList.get(j).linha == numlinharef) {
                    if (TokenList.get(j).data.equals("{")) {
                        fk = true;
                        break;
                    }
                } else {
                    fk = false;
                    posin = j;
                    numlpos = numlinharef;
                    numlinharef = TokenList.get(j).linha;
                    for (int l = j; l < TokenList.size(); l++) {
                        if (TokenList.get(l).linha != numlinharef) {
                            posf = l + 1;
                            break;
                        }
                    }
                    break;
                }
            }
            if (fk == false) {
                String chaveo = "{";
                String chavec = "}";
                Token t = new Token(chaveo, numlpos);
                TokenList.add(posin, t);
                t = new Token(chavec, -1);
                TokenList.add(posf, t);
            }
        }
        //--------------------------------------------------------------------//

        int contParenteses = 0;

        AnalisadorLexico al = new AnalisadorLexico();

        ArrayList<Token> tokenzao = new ArrayList<>();
        if (TokenList.get(indice).data.equals("if")) {

            indice++;
            if (TokenList.get(indice).data.equals("(")) {
                contParenteses++;
                indice++;
            } else {
                System.out.println("Erro - '(' esperado na linha: " + TokenList.get(indice).linha);
            }
            tokenzao = al.Tokenzao(TokenList.get(indice).data.toCharArray(), TokenList.get(indice).linha, false);
            valEstrutura = 0;
            VerificaSintaxeParameter(tokenzao, numlinha, tabela);

        } else if (TokenList.get(indice).data.equals("else")) {

            indice++;
            if (!TokenList.get(indice).data.equals("{")) {
                System.out.println("Erro - '{' esperado na linha: " + TokenList.get(indice).linha);
            }
            indice++;
            if (TokenList.get(indice).data.equals("if")) {
                indice++;
                tokenzao = al.Tokenzao(TokenList.get(indice).data.toCharArray(), TokenList.get(indice).linha,false);
                valEstrutura = 0;
                VerificaSintaxeParameter(tokenzao, numlinha, tabela);
            }

        } else if (TokenList.get(indice).data.equals("while")) {

            indice++;
            if (TokenList.get(indice).data.equals("(")) {
                contParenteses++;
                indice++;
            } else {
                System.out.println("Erro - '(' esperado na linha: " + TokenList.get(indice).linha);
            }
            tokenzao = al.Tokenzao(TokenList.get(indice).data.toCharArray(), TokenList.get(indice).linha,false);
            valEstrutura = 0;
            VerificaSintaxeParameter(tokenzao, numlinha, tabela);

        } else if (TokenList.get(indice).data.equals("for")) {
            indice++;
            if (TokenList.get(indice).data.equals("(")) {
                contParenteses++;
                indice++;
            } else {
                System.out.println("Erro - '(' esperado na linha: " + TokenList.get(indice).linha);
            }
            tokenzao = al.Tokenzao(TokenList.get(indice).data.toCharArray(), TokenList.get(indice).linha,false);
            valEstrutura = 0;
            VerificaSintaxeForParameter(tokenzao, numlinha, tabela);
        } else if ((TokenList.get(indice).data.equals("printf")) || (TokenList.get(indice).data.equals("scanf"))) {

            if (TokenList.get(indice).data.equals("printf")) {
                indice++;
                if (TokenList.get(indice).data.equals("(")) {
                    contParenteses++;
                    indice++;
                } else {
                    System.out.println("Erro - '(' esperado na linha: " + TokenList.get(indice).linha);
                }
                tokenzao = al.Tokenzao(TokenList.get(indice).data.toCharArray(), TokenList.get(indice).linha,false);
                valEstrutura = 0;
                verificaPrintf(tokenzao, numlinha, tabela);

            } else if (TokenList.get(indice).data.equals("scanf")) {
                indice++;
                if (TokenList.get(indice).data.equals("(")) {
                    contParenteses++;
                    indice++;
                } else {
                    System.out.println("Erro - '(' esperado na linha: " + TokenList.get(indice).linha);
                }
                tokenzao = al.Tokenzao(TokenList.get(indice).data.toCharArray(), TokenList.get(indice).linha,false);
                valEstrutura = 0;
                verificaScanf(tokenzao, numlinha, tabela);
            }

        }

    }

    int ind = 0;
    ArrayList<Simbolo> nextEscopo = new ArrayList<>();

    public void Arvore(ArrayList<Token> TokenList, String escopo, ArrayList<Folha> folha, ArrayList<Simbolo> tabela) {

        if (nextEscopo.size() > 0) {
            for (int j = 0; j < nextEscopo.size(); j++) {
                tabela.add(nextEscopo.get(j));
            }
        }

        //variavel escopo ???
        int n = -1;
        //float k = escopo;
        String k = escopo;
        boolean ftw = ftwg;
        String incremento = incrementog.toString();
        StringBuilder str = new StringBuilder();
        Folha f = new Folha();
        while (!TokenList.get(i).data.equals("}")) {

            if (TokenList.get(i).data.equals("if")) {
                VerificaSintaxe(TokenList, i, TokenList.get(i).linha, tabela);

                while (!TokenList.get(i).data.equals("{")) {
                    str.append(TokenList.get(i).data);
                    //str.append(" ");
                    i++;
                }
                i++;
                contkeys++;
                f.setCodigo(str.toString());
                str = new StringBuilder();
                f.setProfundidade(true);
                folha.add(f);
                n++;
                f = new Folha();
                //escopo = (float) (escopo + (0.1));
                eif++;
                k = "if" + eif;
                nextEscopo = tabela;
                Arvore(TokenList, k, folha.get(n).folha, folha.get(n).tabelaSimbolos);

            } else if (TokenList.get(i).data.equals("else")) {
                VerificaSintaxe(TokenList, i, TokenList.get(i).linha, tabela);

                while (!TokenList.get(i).data.equals("{")) {
                    str.append(TokenList.get(i).data);
                    str.append(" ");
                    i++;
                }
                i++;
                contkeys++;
                f.setCodigo(str.toString());
                str = new StringBuilder();
                f.setProfundidade(true);
                folha.add(f);
                n++;
                f = new Folha();
                //escopo = (float) (escopo + (0.1));
                eelse++;
                k = "else" + eelse;
                nextEscopo = tabela;
                Arvore(TokenList, k, folha.get(n).folha, folha.get(n).tabelaSimbolos);

            } else if (TokenList.get(i).data.equals("while")) {
                VerificaSintaxe(TokenList, i, TokenList.get(i).linha, tabela);

                while (!TokenList.get(i).data.equals("{")) {
                    str.append(TokenList.get(i).data);
                    str.append(" ");
                    i++;
                }
                i++;
                contkeys++;
                f.setCodigo(str.toString());
                str = new StringBuilder();
                f.setProfundidade(true);
                folha.add(f);
                n++;
                System.out.println("fhtu");
                f = new Folha();
                //escopo = (float) (escopo + (0.1));
                ewhile++;
                k = "while" + ewhile;
                nextEscopo = tabela;
                Arvore(TokenList, k, folha.get(n).folha, folha.get(n).tabelaSimbolos);

            } else if (TokenList.get(i).data.equals("for")) {
                VerificaSintaxe(TokenList, i, TokenList.get(i).linha, tabela);
                
                //Este bloco representa o otimizador - transformar o for em while----//
                StringBuilder inicializacao = new StringBuilder();
                StringBuilder condicao = new StringBuilder();
                incrementog = new StringBuilder();
                String aux = TokenList.get(i + 2).data;
                int inicio = 0;
                int fim = 0;
                for (int j = 0; j < aux.length(); j++) {
                    if (aux.charAt(j) != ';') {
                        inicializacao.append(aux.charAt(j));
                    } else {
                        inicio = j + 1;
                        break;
                    }
                }
                for (int j = inicio; j < aux.length(); j++) {
                    if (aux.charAt(j) != ';') {
                        condicao.append(aux.charAt(j));
                    } else {
                        fim = j + 1;
                        break;
                    }
                }
                for (int j = fim; j < aux.length(); j++) {
                    if (aux.charAt(j) != ';') {
                        incrementog.append(aux.charAt(j));
                    } else {
                        break;
                    }
                }
                f.setCodigo(inicializacao.toString());
                f.setExpressao(true);
                f.setProfundidade(false);
                folha.add(f);
                n++;
                f = new Folha();
                while (!TokenList.get(i).data.equals("{")) {
                    if (TokenList.get(i).data.equals("for")) {
                        str.append("while");
                        i++;
                    } else if (TokenList.get(i - 1).data.equals("(")) {
                        str.append(condicao);
                        i++;
                    } else {
                        str.append(TokenList.get(i).data);
                        //str.append(" ");
                        i++;
                    }
                }
                //------------------------------------------------------------------------//
                i++;
                contkeys++;
                f.setCodigo(str.toString());
                str = new StringBuilder();
                f.setProfundidade(true);
                folha.add(f);
                n++;
                ftwg = true;
                f = new Folha();
                //escopo = (float) (escopo + (0.1));
                efor++;
                k = "for" + efor;
                nextEscopo = tabela;
                Arvore(TokenList, k, folha.get(n).folha, folha.get(n).tabelaSimbolos);

            } else {
                //-------------------------------------------//
                
                
                if(TokenList.get(i).Tipo.equals("Identfier")) {
                    
                    if (TokenList.get(i).Tipo.equals("Identfier")) {
                        if (TokenList.get(i + 1).Tipo.equals("AssignmentOperator")) {
                            verificaEscopoVariavel(TokenList.get(i), tabela);
                            int nlinha = i + 2;
                            int linharef = TokenList.get(i).linha;
                            ind = nlinha;
                            VerificaExpressao(TokenList, nlinha, linharef, tabela);
                        } else if ((TokenList.get(i).data.contains("--")) || (TokenList.get(i).data.contains("++"))) {

                        } else {
                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + TokenList.get(i).linha);
                            System.exit(0);
                        }

                    }

                    while (!TokenList.get(i).data.equals(";")) {
                        str.append(TokenList.get(i).data);
                        str.append(" ");
                        i++;
                    }
                    i++;
                    f.setCodigo(str.toString());
                    f.setExpressao(true);
                    str = new StringBuilder();
                    f.setProfundidade(false);
                    folha.add(f);
                    f = new Folha();
                    n++;

                } else if (TokenList.get(i).Tipo.equals("TypeSpecifier")) {

                    int linhaA = TokenList.get(i).linha;
                    int fimlinha = 0;
                    for (int j = i; j < TokenList.size(); j++) {
                        if (TokenList.get(j).linha != linhaA) {
                            fimlinha = (j - 1);
                            break;
                        }
                    }
                    CriaTabelaSinbolos(TokenList, tabela, i, k, fimlinha);//111
                    i = fimlinha;
                    i++;
                } else if (TokenList.get(i).Tipo.equals("Keywords")) {
                    VerificaSintaxe(TokenList, i, TokenList.get(i).linha, tabela);
                    while (!TokenList.get(i).data.equals(";")) {
                        str.append(TokenList.get(i).data);
                        i++;
                    }
                    i++;
                    f.setCodigo(str.toString());
                    str = new StringBuilder();
                    f.setProfundidade(false);
                    folha.add(f);
                    f = new Folha();
                    n++;

                }

                //str.append(TokenList.get(i).data);
                // str.append(" ");
                // i++;
                //i++;
                // f.setCodigo(str.toString());
                //str = new StringBuilder();
                //f.setProfundidade(false);
                //folha.add(f);
                //f = new Folha();
                //---------------------------------------------// 
            }

        }
        if (ftw == true) {
            ftw = false;
            f.setCodigo(incremento);
            incrementog = new StringBuilder();
            f.setExpressao(true);
            f.setProfundidade(false);
            folha.add(f);
            f = new Folha();
        }
        contkeys--;
        i++;

    }

    //Por enquanto nada a mexer no bloco abaixo
    public void VerificaSintaxeParameter(ArrayList<Token> TokenzaoList, int linha, ArrayList<Simbolo> tabela) {

        try {
            while (valEstrutura != TokenzaoList.size()) {

                if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                    verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                    valEstrutura++;
                    if (TokenzaoList.get(valEstrutura).Tipo.equals("LogicalOperator")) {
                        valEstrutura++;
                        if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                            verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                            valEstrutura++;
                            try {
                                if (TokenzaoList.get(valEstrutura).data.equals("&&")) {
                                    valEstrutura++;
                                    VerificaSintaxeParameter(TokenzaoList, linha, tabela);
                                } else if (TokenzaoList.get(valEstrutura).data.equals("||")) {
                                    valEstrutura++;
                                    VerificaSintaxeParameter(TokenzaoList, linha, tabela);
                                }
                            } catch (IndexOutOfBoundsException e) {

                            }
                        } else if (TokenzaoList.get(valEstrutura).Tipo.equals("Constant")) {
                            valEstrutura++;
                            if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                                verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                                VerificaSintaxeParameter(TokenzaoList, linha, tabela);
                            }
                        } else {

                        }
                    } else {
                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                        System.exit(0);
                    }

                } else {
                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                    System.exit(0);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
            System.exit(0);
        }

    }

    //Por enquanto nada a mexer no bloco abaixo
    public void VerificaSintaxeForParameter(ArrayList<Token> TokenzaoList, int linha, ArrayList<Simbolo> tabela) {

        try {
            while (valEstrutura != TokenzaoList.size()) {

                if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                    verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                    valEstrutura++;
                    if (TokenzaoList.get(valEstrutura).Tipo.equals("AssignmentOperator")) {
                        valEstrutura++;
                        if (TokenzaoList.get(valEstrutura).Tipo.equals("Constant") || TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                            if(TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")){
                                verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                            }
                            valEstrutura++;
                            if (TokenzaoList.get(valEstrutura).Tipo.equals("Delimiter")) {
                                valEstrutura++;
                                if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                                    valEstrutura++;
                                    if (TokenzaoList.get(valEstrutura).Tipo.equals("LogicalOperator")) {
                                        valEstrutura++;
                                        if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier") || TokenzaoList.get(valEstrutura).Tipo.equals("Constant")) {
                                             if(TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")){
                                                verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                                             }
                                            valEstrutura++;
                                            if (TokenzaoList.get(valEstrutura).Tipo.equals("Delimiter")) {
                                                valEstrutura++;
                                                if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                                                    valEstrutura++;
                                                    if (valEstrutura == TokenzaoList.size()) {
                                                        break;
                                                    } else {
                                                        if (TokenzaoList.get(valEstrutura).Tipo.equals("AssignmentOperator")) {
                                                            valEstrutura++;
                                                            if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                                                                valEstrutura++;
                                                                if (TokenzaoList.get(valEstrutura).Tipo.equals("ArithmeticOperator")) {
                                                                    valEstrutura++;
                                                                    if ((TokenzaoList.get(valEstrutura).Tipo.equals("Identfier") || TokenzaoList.get(valEstrutura).Tipo.equals("Constant"))) {
                                                                        valEstrutura++;
                                                                        if (valEstrutura == TokenzaoList.size()) {
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                                                        System.exit(0);
                                                                    }
                                                                } else {
                                                                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                                                    System.exit(0);
                                                                }
                                                            } else {
                                                                System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                                                System.exit(0);
                                                            }
                                                        } else {
                                                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                                            System.exit(0);
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                                    System.exit(0);
                                                }
                                            } else {
                                                System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                                System.exit(0);
                                            }
                                        } else {
                                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                            System.exit(0);
                                        }
                                    } else {
                                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                        System.exit(0);
                                    }
                                } else {
                                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                    System.exit(0);
                                }
                            } else {
                                System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                                System.exit(0);
                            }
                        } else {
                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                        System.exit(0);
                    }

                } else {
                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                    System.exit(0);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
            System.exit(0);
        }

    }

    int contparenteses = 0;

    public void VerificaExpressao(ArrayList<Token> TokenList, int indice, int linha, ArrayList<Simbolo> tabela) {

        while (!TokenList.get(ind).data.equals(";")) {

            if (TokenList.get(ind).Tipo.equals("Identfier")) {
                verificaEscopoVariavel(TokenList.get(ind), tabela);
                ind++;
                //-----------------------------------------------------//
                if (TokenList.get(ind).Tipo.equals("ArithmeticOperator")) {
                    ind++;
                    VerificaExpressao(TokenList, indice, linha, tabela);
                } else if (TokenList.get(ind).data.equals(";")) {
                    break;
                } else if (TokenList.get(ind).data.equals(")")) {
                    contparenteses--;
                    ind++;
                    VerificaExpressao(TokenList, indice, linha, tabela);
                } else {
                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                    System.exit(0);
                }

                //-----------------------------------------------------//
            } else if (TokenList.get(ind).Tipo.equals("Constant")) {
                ind++;
                //-----------------------------------------------------//
                if (TokenList.get(ind).Tipo.equals("ArithmeticOperator")) {
                    ind++;
                    VerificaExpressao(TokenList, indice, linha, tabela);
                } else if (TokenList.get(ind).data.equals(";")) {
                    break;
                } else if (TokenList.get(ind).data.equals(")")) {
                    contparenteses--;
                    ind++;
                    VerificaExpressao(TokenList, indice, linha, tabela);
                } else {
                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                    System.exit(0);
                }
                //-----------------------------------------------------//
            } else if (TokenList.get(ind).linha != linha) {
                System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                System.exit(0);
            } else if (TokenList.get(ind).data.equals("(")) {
                contparenteses++;
                ind++;
                VerificaExpressao(TokenList, indice, linha, tabela);
            } else if (TokenList.get(ind).data.equals(")")) {
                contparenteses--;
                ind++;
                VerificaExpressao(TokenList, indice, linha, tabela);
            } else if (TokenList.get(ind).Tipo.equals("ArithmeticOperator")) {
                ind++;
                VerificaExpressao(TokenList, indice, linha, tabela);
            }

        }
        if (contparenteses != 0) {
            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
            System.exit(0);
        }

    }

    public void verificaPrintf(ArrayList<Token> TokenzaoList, int linha, ArrayList<Simbolo> tabela) {

        try {
            while (valEstrutura != TokenzaoList.size()) {

                if (TokenzaoList.get(valEstrutura).Tipo.equals("String")) {
                    valEstrutura++;
                    if (TokenzaoList.get(valEstrutura).Tipo.equals("Delimiter")) {
                        valEstrutura++;
                        //verificaPrintf(TokenzaoList, linha);
                    } else {
                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                        System.exit(0);
                    }

                    if (valEstrutura == (TokenzaoList.size())) {
                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                            System.exit(0);
                    }

                } else if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                    verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                    valEstrutura++;
                    if (TokenzaoList.get(valEstrutura).Tipo.equals("Delimiter")) {
                        valEstrutura++;
                        //verificaPrintf(TokenzaoList, linha);
                    } else {
                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                        System.exit(0);
                    }

                    if (valEstrutura == (TokenzaoList.size())) {                   
                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                            System.exit(0);                        
                    }

                } else {
                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                    System.exit(0);
                }

            }
        } catch (IndexOutOfBoundsException e) {
            //System.out.println("Uncompilable source code - illegal start of expression in line: " + linha);
            //System.exit(0);
        }
    }

    public void verificaScanf(ArrayList<Token> TokenzaoList, int linha, ArrayList<Simbolo> tabela) {
        
        
           try {
            while (valEstrutura != TokenzaoList.size()) {

                if (TokenzaoList.get(valEstrutura).Tipo.equals("Identfier")) {
                    verificaEscopoVariavel(TokenzaoList.get(valEstrutura), tabela);
                    valEstrutura++;
                    if (TokenzaoList.get(valEstrutura).Tipo.equals("Delimiter")) {
                        valEstrutura++;
                        //verificaPrintf(TokenzaoList, linha);
                    } else {
                        System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                        System.exit(0);
                    }

                    if (valEstrutura == (TokenzaoList.size())) {                   
                            System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                            System.exit(0);                        
                    }

                } else {
                    System.out.println("Código fonte incompilável - início ilegal de expressão na linha: " + linha);
                    System.exit(0);
                }

            }
        } catch (IndexOutOfBoundsException e) {
            //System.out.println("Uncompilable source code - illegal start of expression in line: " + linha);
            //System.exit(0);
        }

    }

    public void verificaEscopoVariavel(Token token, ArrayList<Simbolo> tabela){
        boolean encontrou = false;
        for (int j = 0; j < tabela.size(); j++) {
            
            if(tabela.get(j).getToken().equals(token.data)){
                encontrou = true;
                break;
            }else{
                encontrou = false;
            }
            
        }
        
        if(encontrou == false){
              System.out.println("Código fonte não compilavel - não foi encontrado o símbolo: " +token.data+ " ");
              System.exit(0);
        }
        
    }
    
  
}
