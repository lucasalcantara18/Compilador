/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.util;

import compilador.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Eliene
 */
public class ReadFiles {

    public ArrayList<String> Lista = new ArrayList<>();

    public ArrayList<CaracList> leituraAlfabeto() {
        ArrayList<CaracList> alfabeto = new ArrayList<>();
        CaracList caractere;
        String linha = new String();

        String nomearquivo = "C:\\Users\\lucao\\OneDrive\\Documentos\\NetBeansProjects\\Compilador\\src\\compilador\\alfabeto.txt";
        File arquivo = new File(nomearquivo);

        String separado[];
        ArrayList<String> guardaLinhas = new ArrayList<>();

        int contlinhas = 0;

        if (arquivo.exists()) {
            try {
                FileReader leitorDeArquivo = new FileReader(nomearquivo);
                BufferedReader bufferDeArquivo = new BufferedReader(leitorDeArquivo);

                while (true) {
                    linha = bufferDeArquivo.readLine();

                    if (linha == null) {
                        break;
                    }

                    separado = linha.split(" ");
                    caractere = new CaracList();
                    caractere.setCaracter(separado[0].charAt(0));
                    caractere.setBytes(Integer.parseInt(separado[1]));
                    caractere.setIncid(0);

                    alfabeto.add(caractere);

                }
            } catch (Exception e) {
                System.out.println("FIM");
            }

        }

        return alfabeto;

    }

    public ArrayList<CaracList> leituraAlfabetoIlegal() {
        ArrayList<CaracList> alfabetoIlegal = new ArrayList<>();
        CaracList caractere;
        String linha = new String();

        String nomearquivo = "C:\\Users\\lucao\\OneDrive\\Documentos\\NetBeansProjects\\Compilador\\src\\compilador\\alfabetoIlegal.txt";
        File arquivo = new File(nomearquivo);

        String separado[];
        ArrayList<String> guardaLinhas = new ArrayList<>();

        int contlinhas = 0;

        if (arquivo.exists()) {
            try {
                FileReader leitorDeArquivo = new FileReader(nomearquivo);
                BufferedReader bufferDeArquivo = new BufferedReader(leitorDeArquivo);

                while (true) {
                    linha = bufferDeArquivo.readLine();

                    if (linha == null) {
                        break;
                    }

                    separado = linha.split(" ");
                    caractere = new CaracList();
                    caractere.setBytes(Integer.parseInt(separado[0]));
                    caractere.setIncid(0);

                    alfabetoIlegal.add(caractere);

                }
            } catch (Exception e) {
                System.out.println("FIM");
            }

        }
        return alfabetoIlegal;
    }

    public ArrayList<char[]> LerArquivo() {
        int pos1b = 0;
        boolean coment1 = false;
        char spc = 0;
        char spc2 = 32;
        boolean coment2 = false;

        String linha = new String();
        boolean bloccoment = false;
        boolean space = false;
        char charAt = ' ';
        boolean val = false;
        int barra = 0;
        int ast = 0;
        char aux[];
        char aux2[];
        boolean prifa = false;
        boolean priff = false;

        String nomearquivo = "C:\\Users\\lucao\\OneDrive\\Documentos\\NetBeansProjects\\Compilador\\src\\compilador\\arquivoo.txt";
        File arquivo = new File(nomearquivo);
        StringBuilder palavra = new StringBuilder();
        String separado;
        ArrayList<char[]> ListaLinhas = new ArrayList<>();

        int contlinhas = 0;

        if (arquivo.exists()) {
            try {
                FileReader leitorDeArquivo = new FileReader(nomearquivo);
                BufferedReader bufferDeArquivo = new BufferedReader(leitorDeArquivo);

                while (true) {
                    linha = bufferDeArquivo.readLine();
                    aux = linha.toCharArray();
                    coment1 = false;
                    palavra = new StringBuilder();
                    barra = 0;
                    if (linha == null) {
                        break;
                    }
                    if (linha.length() > 0) {

                        for (int i = 0; i < linha.length(); i++) {

                            if (coment2 != true) {

                                if (linha.charAt(i) == '/') {
                                    barra++;
                                    if (barra == 2) {
                                        palavra.deleteCharAt(pos1b);
                                        pos1b = 0;
                                        coment1 = true;
                                    } else if (barra == 1) {
                                        pos1b = palavra.length();
                                    }
                                } else if ((barra == 1) && (linha.charAt(i) == '*')) {
                                    palavra.deleteCharAt(pos1b);
                                    pos1b = 0;
                                    coment2 = true;
                                    barra = 0;
                                } else if ((linha.charAt(i) != '\t')) {

                                }

                                if (coment1 != true && coment2 != true) {
                                    if (linha.charAt(i) != '\t') {
                                        palavra.append(linha.charAt(i));
                                    } else if (linha.charAt(i) == '\t') {
                                        palavra.append(' ');
                                    }

                                }

                            } else {
                                if ((linha.charAt(i) == '*')) {
                                    ast++;
                                } else if (((linha.charAt(i) == '/') && (ast > 0))) {
                                    coment2 = false;
                                    ast--;
                                } else if (((linha.charAt(i) != '*') && (linha.charAt(i) != '/'))) {
                                    ast = 0;
                                }

                            }

                        }
                        if ((palavra.length() > 0)) {
                            linha = palavra.toString();
                            Lista.add(linha);
                            aux = linha.toCharArray();
                            ListaLinhas.add(aux);
                        }

                        /* 
                         aux2 = new char[aux.length];
                        
                         for (int i = 0; i < aux2.length; i++) {
                         aux2[i] = ' ';
                         }
                        
                        
                         for (int i = 0; i < aux.length; i++) {
                         if (aux[i] == coment1) {
                         if (aux[i + 1] == coment1) {
                         i = aux.length;

                         } else if (aux[i + 1] == coment2) {
                         bloccoment = true;
                         for (int j = (i + 2); j < aux.length; j++) {
                         if (aux[j] == coment2) {
                         if (aux[j + 1] == coment1) {
                         bloccoment = false;
                         aux2 = new char[aux.length];
                         i = aux.length;
                         break;
                         }
                         }
                         }

                         }else{
                         aux2[i] = aux[i];
                         }
                         } else if (aux[i] == coment2) {
                         if (aux[i + 1] == coment1) {
                         bloccoment = false;
                         aux2 = new char[aux.length];
                         break;

                         }else{
                         aux2[i] = aux[i];
                         }

                         } else {
                         aux2[i] = aux[i];

                         }

                         }*//*
                         if (bloccoment == false) {
                         for (int i = 0; i < aux2.length; i++) {
                         if ((aux2[i] == spc) || (Character.isWhitespace(aux2[i]))) {
                         space = true;
                         } else {
                         ListaLinhas.add(aux2);
                         break;

                         }
                         }

                         }*/

                    }
                    contlinhas++;
                }
            } catch (Exception e) {
                System.out.println("FIM");
            }

        }

        return ListaLinhas;
    }

    public ArrayList<String> Repasse() {

        return Lista;
    }

    public void ShowCode(ArrayList<char[]> code) {
        String aux;
        for (int i = 0; i < code.size(); i++) {
            aux = new String(code.get(i));
            System.out.println(i + " " + aux);

        }
    }
}
