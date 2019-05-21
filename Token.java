/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Eliene
 */
public class Token {
    
    public String data;
    public int linha;
    public String Tipo;
    public String Escopo;
    public boolean Tokenzao = false;

    public Token(String cData, int nLinha){
        this.data = cData;
        this.linha = nLinha;
    }
    
    public Token(){
        
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void imprime(){
    
        System.out.println("Linha: "+linha+" - "+ "token: "+data);
       
    }

    public String getTipo() {
        return Tipo;
    }

    public boolean isTokenzao() {
        return Tokenzao;
    }

    public String getEscopo() {
        return Escopo;
    }

    public void setEscopo(String Escopo) {
        this.Escopo = Escopo;
    }
    
    

    public void setTokenzao(boolean Tokenzao) {
        this.Tokenzao = Tokenzao;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    
}
