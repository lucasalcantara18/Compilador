/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.util;

import compilador.Simbolo;
import java.util.ArrayList;

/**
 *
 * @author lucao
 */
public class Folha {

    public ArrayList<Folha> folha;
    public ArrayList<Simbolo> tabelaSimbolos;
    
    public String codigo;
    public boolean profundidade = false;
    public boolean expressao = false;

    public Folha() {
        //folha=new ArrayList<>();
    }

    public ArrayList<Folha> getFolha() {
        return folha;
    }

    public void setFolha(ArrayList<Folha> folha) {
        this.folha = folha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isProfundidade() {
        return profundidade;
    }

    public void setProfundidade(boolean profundidade) {
        this.profundidade = profundidade;
        tabelaSimbolos = new ArrayList<>();
        folha=new ArrayList<>();
    }

    public boolean isExpressao() {
        return expressao;
    }

    public void setExpressao(boolean expressao) {
        this.expressao = expressao;
    }
    
    
    
}
