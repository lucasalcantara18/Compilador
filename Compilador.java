/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import compilador.util.Folha;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import sun.font.Script;

/**
 *
 * @author Eliene
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        

       AnalisadorLexico al = new AnalisadorLexico();
       al.Inicio();

    }

}
