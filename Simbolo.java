/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;


import java.util.ArrayList;

/**
 *
 * @author lucao
 */
public class Simbolo {
    public ArrayList<Simbolo> TabelaSimbolos;
    private String Token;
    private String Identificador;
    private String Escopo;
    private String memoria = "0";
  

    public Simbolo(String Token, String Identificador) {
        this.Token = Token;
        this.Identificador = Identificador;
    }

    public Simbolo() {
      TabelaSimbolos = new ArrayList<>();
    }
    
    
    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String Identificador) {
        this.Identificador = Identificador;
    }

    public String getEscopo() {
        return Escopo;
    }

    public void setEscopo(String Escopo) {
        this.Escopo = Escopo;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }
    
    
    
    public void imprime(){
        System.out.println("Token: "+ this.Token + " - Identificador: "+ this.Identificador);
    }
    
}
