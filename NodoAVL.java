
package proyectoarbolavl;
/**
 * @author adtre
 */
public class NodoAVL <T extends Comparable <T>>{
    
    private NodoAVL<T> papa;
    private NodoAVL<T> hijoIzq;
    private NodoAVL<T> hijoDer;
    private T dato;
    private int fe;
    
    public NodoAVL(){        
    }
    
    public NodoAVL(T dato) {
        this.papa = null;
        this.hijoIzq = null;
        this.hijoDer = null;
        this.dato = dato;
        this.fe = 0;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    public NodoAVL<T> getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(NodoAVL<T> hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public NodoAVL<T> getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(NodoAVL<T> hijoDer) {
        this.hijoDer = hijoDer;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public int getFe() {
        return fe;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }

    public void cuelga(NodoAVL<T> sust){
        if(sust == null)
            return;
        if(sust.getDato().compareTo(dato) <= 0)
            hijoIzq = sust;
        else
            hijoDer = sust;
        sust.setPapa(this);        
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Dato: "+dato+"\n");
        sb.append("FE: "+fe+"\n");
        return sb.toString();
    }
    
    
    
}
