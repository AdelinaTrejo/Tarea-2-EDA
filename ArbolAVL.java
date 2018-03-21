
package proyectoarbolavl;

public class ArbolAVL <T extends Comparable <T>> {
    
    private NodoAVL<T> raiz;
    private int cont;

    public ArbolAVL() {
        this.raiz = null;
        this.cont = 0;
    }
    
    public void imprimirConNivel(){
        imprimirConNivel(raiz,0);
        System.out.println();
    }
    
    private void imprimirConNivel(NodoAVL<T> tmp,int nivel){
        if(tmp!=null){
            imprimirConNivel(tmp.getHijoIzq(),nivel+1);
            System.out.print(tmp.getDato().toString()+"("+nivel+")"+"/ Fe: "+tmp.getFe());
            if(tmp.getPapa()==null)
                System.out.println(" / "+ "null");
            else
                System.out.println(" / "+ tmp.getPapa().getDato());
            imprimirConNivel(tmp.getHijoDer(),nivel+1);
        }
    }
    
    public void agrega(T dato){
        if(raiz==null)
            raiz = new NodoAVL(dato);        
        else{
            NodoAVL<T> nodo = new NodoAVL(dato);
            agrega(raiz, nodo);
        }
        cont++;
    }
    
    private void agrega(NodoAVL<T> actual,NodoAVL<T> dato){
        if(actual.getDato().compareTo(dato.getDato())>0){
            if(actual.getHijoIzq()==null){
               actual.setHijoIzq(dato);
               dato.setPapa(actual);
            }
            else            
               agrega(actual.getHijoIzq(),dato);
        }
        else{
            if(actual.getHijoDer()==null){
               actual.setHijoDer(dato);
               dato.setPapa(actual);
            }
            else            
               agrega(actual.getHijoDer(),dato);   
        }
        actualizaFe(actual); 
        balancea(actual);
    }
    
    private void actualizaFe(NodoAVL<T> nodo){
        if(nodo!=null){
           int ahD = calcularAltura(nodo.getHijoDer());
           int ahI = calcularAltura(nodo.getHijoIzq());
           nodo.setFe(ahD - ahI);
           actualizaFe(nodo.getPapa());
        }  
    }
    
    public int calcularAltura(NodoAVL<T> actual){
        if(actual != null){
            int cont1=1, cont2=1;
            if(actual.getHijoDer()!=null)
               cont1 = 1 + calcularAltura(actual.getHijoDer());
            if(actual.getHijoIzq()!=null)
               cont2 = 1+ calcularAltura(actual.getHijoIzq());
            return Math.max(cont1, cont2);
        }
        else
            return 0;
    }
    
    private void balancea(NodoAVL<T> actual){
     if(actual!=null){
        NodoAVL temp= actual.getPapa();
        if(temp!=null){            
            if(temp.getFe()==-1 || temp.getFe()==0 || temp.getFe()==1){
               temp= temp.getPapa();
               if(temp!=null){               
                  if(temp.getFe()!=-1 && temp.getFe()!=0 && temp.getFe()!=1)
                      balanceaAux(temp);
                  else
                      return;
               }
               else
                   return;
            }
            else
              balanceaAux(temp);
        }
        else
            return;
      }             
    }
    
    private void balanceaAux(NodoAVL<T> nodo){
        if(nodo.getFe()==2){
            if(nodo.getHijoDer().getFe()>=0)
                rotarSimpleIzquierda(nodo);
            else{
                if(nodo.getHijoDer().getFe()<0)
                   rotarDerIzq(nodo);
                else
                    return;
            }
        }
        else
            if(nodo.getFe()==-2){
                if(nodo.getHijoIzq().getFe()<=0)
                   rotarSimpleDerecha(nodo);
                else{
                    if(nodo.getHijoIzq().getFe()>0)
                       rotarIzqDer(nodo); 
                    else
                        return;
                }
            }
            else
                return;
    }
  
    private NodoAVL<T> rotarSimpleIzquierda(NodoAVL<T> nodo){
        NodoAVL papa = nodo.getPapa();
        NodoAVL hijDer = nodo.getHijoDer();
        NodoAVL aux =  hijDer.getHijoIzq();
        hijDer.setPapa(papa);
        if (papa!=null){
            if(papa.getHijoIzq()!=null && papa.getHijoIzq().equals(nodo))
               papa.setHijoIzq(hijDer);
            else
               papa.setHijoDer(hijDer);
        }
        else
            raiz = hijDer;
        hijDer.setHijoIzq(nodo);
        nodo.setPapa(hijDer);
        nodo.setHijoDer(aux);
        if(aux!=null)
           aux.setPapa(nodo);        
        actualizaFe(nodo);
        return hijDer;
    }
    
    private void rotarIzqDer(NodoAVL<T> nodo){
        NodoAVL<T> temp = nodo.getHijoIzq();
        nodo = rotarSimpleIzquierda(temp);
        rotarSimpleDerecha(nodo.getPapa());    
    }
    
    private void rotarDerIzq(NodoAVL<T> nodo){
        NodoAVL<T> temp = nodo.getHijoDer();
        nodo = rotarSimpleDerecha(temp);
        rotarSimpleIzquierda(nodo.getPapa());    
    }
    
    private NodoAVL<T> rotarSimpleDerecha(NodoAVL<T> nodo){
        NodoAVL papa = nodo.getPapa();
        NodoAVL hijIzq = nodo.getHijoIzq();
        NodoAVL aux =  hijIzq.getHijoDer();
        hijIzq.setPapa(papa);
        if (papa!=null){
            if(papa.getHijoIzq()!=null && papa.getHijoIzq().equals(nodo))
               papa.setHijoIzq(hijIzq);
            else
                if(papa.getHijoDer()!=null && papa.getHijoDer().equals(nodo))
                   papa.setHijoDer(hijIzq);            
        }
        else
            raiz = hijIzq;
        hijIzq.setHijoDer(nodo);
        nodo.setPapa(hijIzq);
        nodo.setHijoIzq(aux);
        if(aux!=null)
           aux.setPapa(nodo);        
        actualizaFe(nodo);
        return hijIzq;
    }
         
   public NodoAVL<T> getRaiz() {
        return raiz;
    } 
   
   public void eliminar(T objetivo){
       if(raiz!=null)
           buscar(raiz,objetivo);    
   }
   
   private void buscar(NodoAVL<T> actual,T objetivo){
       if(actual!=null){
           if(actual.getDato().equals(objetivo))
               eliminarAux(actual);
           else
               if(actual.getDato().compareTo(objetivo)>0)
                   buscar(actual.getHijoIzq(), objetivo);
               else
                   if(actual.getDato().compareTo(objetivo)<0)
                      buscar(actual.getHijoDer(), objetivo);
       }
   }
   
   private void eliminarAux(NodoAVL<T> actual){
       //CASO1: Nodo sin hijos
       NodoAVL<T> papa= actual.getPapa();
       if(actual.getHijoIzq()==null && actual.getHijoDer()==null)
           eliminaSinHijos(actual,papa);       
       else{//CASO2: Nodo con un solo hijo (izq)
           NodoAVL<T> sustituto;
            if(actual.getHijoIzq()!=null && actual.getHijoDer()==null){
                sustituto= actual.getHijoIzq();
                eliminaSoloConHijoIzq(actual,papa,sustituto); 
            }
           else//CASO3: Nodo con un solo hijo (der)
               if(actual.getHijoIzq()==null && actual.getHijoDer()!=null){
                   sustituto= actual.getHijoDer();
                   eliminaSoloConHijoDer(actual,papa, sustituto);
               }
               else //CASO4: Nodo con dos hijos
                   if(actual.getHijoIzq()!=null && actual.getHijoDer()!=null)
                       eliminaConDosHijos(actual);
            }
   
   }
   
   private void eliminaSinHijos(NodoAVL<T> actual,NodoAVL<T> papa){
        if(papa!=null){          
            if(papa.getHijoIzq()!=null && papa.getHijoIzq().equals(actual)){
                papa.setHijoIzq(null);
                actual.setPapa(null);
                actualizaFe(papa);
                balancea(papa.getHijoDer());
            }
            else
                if(papa.getHijoDer().equals(actual)){
                    papa.setHijoDer(null);
                    actual.setPapa(null);
                    actualizaFe(papa);
                    balancea(papa.getHijoIzq());
                }          
            }
            else
                raiz=null; 
        cont--;
   }
   
   private void eliminaSoloConHijoIzq(NodoAVL<T> actual,NodoAVL<T> papa, NodoAVL<T> sustituto){
        if(papa!=null){  
            actual.setPapa(null);
            actual.setHijoIzq(null);
            papa.cuelga(sustituto);
            this.actualizaFe(papa);
            balancea(papa.getHijoIzq());
        }
        else{//si el actual es la raiz
            actual.setHijoIzq(null);
            sustituto.setPapa(null);
            raiz = sustituto;
           } 
        cont--;
   }
   
   private void eliminaSoloConHijoDer(NodoAVL<T> actual,NodoAVL<T> papa, NodoAVL<T> sustituto){
        if(papa!=null){  
            actual.setPapa(null);
            actual.setHijoDer(null);
            papa.cuelga(sustituto);
            this.actualizaFe(papa);
            balancea(papa.getHijoDer());
        }
        else{//si el actual es la raiz
            actual.setHijoDer(null);
            sustituto.setPapa(null);
            raiz = sustituto;
           } 
        cont--;
   } 
   
    private void eliminaConDosHijos(NodoAVL<T> actual){
        NodoAVL<T> suc = sucesor(actual);
        actual.setDato(suc.getDato());                
        eliminarAux(suc);        
   }
    
    private NodoAVL<T> sucesor(NodoAVL<T> actual){
         NodoAVL<T> r = actual.getHijoDer();
         while(r.getHijoIzq()!=null)
                r = r.getHijoIzq();            
         return r;      
    }
    
    String[] niveles;
    public void imprimirNivel() {
        niveles = new String[calcularAltura(raiz) + 1];

        imprimirNivel(raiz, 0);
        for (int i = 0; i < niveles.length; i++) {
            System.out.println(niveles[i] + " En nivel: " + i);
        }
    }
    
    private void imprimirNivel(NodoAVL<T> pivote, int nivel2) {
        if (pivote != null) {
            niveles[nivel2] = pivote.getDato() + ", " + ((niveles[nivel2] != null) ? niveles[nivel2] : "");
            imprimirNivel(pivote.getHijoDer(), nivel2 + 1);
            imprimirNivel(pivote.getHijoIzq(), nivel2 + 1);
        }
    }
    
}
    
    
    
    
        
        
    
    
    
    
    
    

