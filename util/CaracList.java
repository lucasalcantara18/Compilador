/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.util;

/**
 *
 * @author Eliene
 */
public class CaracList {

    public char caracter;
    public int bytes;
    public int incid;

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public int getIncid() {
        return incid;
    }

    public void setIncid(int incid) {
        this.incid = incid;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }
    
    public void incidencia(){
        incid++;
    }
    
}
