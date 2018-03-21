#ADELINA TREJO ESPINOZA
class NodoAVL:
    __variable = "dato", "izq", "der", "papa", "fe"

    def __init__(self, dato):
        self.__fe = 0
        self.__dato = dato
        self.__der = None
        self.__izq = None
        self.__papa = None

    def getFe(self):
        return self.__fe

    def setFe(self, fe):
        self.__fe = fe

    def getDer(self):
        return self.__der

    def setDer(self, newder):
        self.__der = newder

    def getIzq(self):
        return self.__izq

    def setIzq(self, newizq):
        self.__izq = newizq

    def getPapa(self):
        return self.__papa

    def setPapa(self, newp):
        self.__papa = newp

    def getElem(self):
        return self.__dato

    def setElem(self, newelem):
        self.__dato = newelem

    def cuelga(self, n):
        if n == None:
            return
        if n.getElem() < self.__dato:
            self.__izq = n
        else:
            self.__der = n
        n.setPapa(self)

#######################################################################################
#######################################################################################

class ArbolAVL:
    __variable = "raiz", "contador"

    def __init__(self):
        self.__contador = 0
        self.__raiiz = None

    def getRaiz(self):
        return self.__raiz

    def inserta(self, dato):
        if self.__raiiz == None:
            self.__raiiz = NodoAVL(dato)
        else:
            nodo = NodoAVL(dato)
            self.__inserta2(self.__raiiz, nodo)
        self.__contador == self.__contador + 1

    def __inserta2(self, actual, dato):
        if actual.getElem() > dato.getElem():
            if actual.getIzq() == None:
                actual.setIzq(dato)
                dato.setPapa(actual)
            else:
                self.__inserta2(actual.getIzq(), dato)
        else:
            if actual.getDer() == None:
                actual.setDer(dato)
                dato.setPapa(actual)
            else:
                self.__inserta2(actual.getDer(), dato)
        self.__actualiza_fe(actual)
        self.__balancea(actual)

    def __actualiza_fe(self, nodo):
        if not(nodo == None):
            ahd = self.__calcularAltura(nodo.getDer())
            ahi = self.__calcularAltura(nodo.getIzq())
            nodo.setFe(ahd - ahi)
            self.__actualiza_fe(nodo.getPapa())

    def __calcularAltura(self, actual):#error
        if not(actual == None):
            cont1 = 1
            cont2 = 1
            if not(actual.getDer() == None):
                cont1 = 1 + self.__calcularAltura(actual.getDer())
            if not(actual.getIzq() == None):
                cont2 = 1 + self.__calcularAltura(actual.getIzq())
            return max(cont1, cont2)
        else:
            return 0

    # METODOS DE LAS ROTACIONES NECESARIAS PARA BALANCEAR
    def __balancea(self, actual):
        if not(actual == None):
            temp = actual.getPapa()
            if not(temp == None):
                if (temp.getFe() == -1) or (temp.getFe() == 0) or (temp.getFe() == 1):
                    temp = temp.getPapa()
                    if not(temp == None):
                        if (temp.getFe() == -1) and (temp.getFe()  == 0) and (temp.getFe() == 1):
                            self.__balanceaAux(temp)
                        else:
                            return
                    else:
                        return
                else:
                    self.__balanceaAux(temp)
            else:
                return

    def __balanceaAux(self, nodo):
        if nodo.getFe() == 2:
            if nodo.getDer().getFe() >= 0:  # castear?
                self.__rotaIzq(nodo)
            else:
                if nodo.getDer().getFe() < 0:  # castear?
                    self.__rotaDerIzq(nodo)
                else:
                    return
        else:
            if nodo.getFe() == -2:
                if nodo.getIzq().getFe() <= 0:  # castear?
                    self.__rotaDer(nodo)
                else:
                    if nodo.getIzq().getFe() > 0:  # castear?
                        self.__rotaIzqDer(nodo)
                    else:
                        return
            else:
                return

    def __rotaIzq(self, nodo):
        papa = nodo.getPapa()
        hijDer = nodo.getDer()
        aux = hijDer.getIzq()
        hijDer.setPapa(papa)
        if not(papa == None):
            if (not(papa.getIzq() == None)) and (papa.getIzq().getElem().__eq__(nodo.getElem())):  #(actual.getElem().__eq__(elem)
                papa.setIzq(hijDer)
            else:
                papa.setDer(hijDer)
        else:
            self.__raiiz = hijDer
        hijDer.setIzq(nodo)
        nodo.setPapa(hijDer)
        nodo.setDer(aux)
        if not(aux == None):
            aux.setPapa(nodo)
        self.__actualiza_fe(nodo)
        return hijDer

    def __rotaDer(self, nodo):
        papa = nodo.getPapa()
        hijIzq = nodo.getIzq()
        aux = hijIzq.getDer()
        hijIzq.setPapa(papa)
        if papa is not None:
            if (not(papa.getIzq() == None)) and (papa.getIzq().getElem().__eq__(nodo.getElem())):  # jalsa?
                papa.setIzq(hijIzq)
            else:
                papa.setDer(hijIzq)
        else:
            self.__raiiz = hijIzq
        hijIzq.setDer(nodo)
        nodo.setPapa(hijIzq)
        nodo.setIzq(aux)
        if not(aux == None):
            aux.setPapa(nodo)
        self.__actualiza_fe(nodo)
        return hijIzq

    def __rotaIzqDer(self, nodo):
        temp = nodo.getIzq()
        nodo = self.__rotaIzq(temp)
        self.__rotaDer(nodo.getPapa())

    def __rotaDerIzq(self, nodo):
        temp = nodo.getDer()
        nodo = self.__rotaDer(temp)
        self.__rotaIzq(nodo.getPapa())

    # METODOS PARA ELIMINAR, DIVIDIDOS POR CASOS
    def eliminar(self, objetivo):
        if not(self.__raiiz == None):
            self.__buscar(self.__raiiz, objetivo)

    def __buscar(self, actual, objetivo):
        if not(actual == None):
            if actual.getElem().__eq__(objetivo):  # (actual.getElem().__eq__(elem)
                self.__eliminaAux(actual)
            else:
                if actual.getElem() > objetivo:
                    self.__buscar(actual.getIzq(), objetivo)
                else:
                    if actual.getElem() < objetivo:
                        self.__buscar(actual.getDer(), objetivo)

    def __eliminaAux(self, actual):
        papa = actual.getPapa()
        if (actual.getIzq() == None) and (actual.getDer() == None):
            self.__eliminaSinHijos(actual, papa)
        else:
            sustituto = None
            if (not(actual.getIzq() == None)) and (actual.getDer() == None):
                sustituto = actual.getIzq()
                self.__eliminaSoloConHijoIzq(actual, papa, sustituto)
            else:
                if (actual.getIzq() == None) and (not(actual.getDer() == None)):
                    sustituto = actual.getDer()
                    self.__eliminaSoloConHijoDer(actual, papa, sustituto)
                else:
                    if (not(actual.getIzq() == None)) and (not(actual.getDer() == None)):
                        self.__eliminaConDosHijos(actual)

    def __eliminaSinHijos(self, actual, papa):
        if not(papa == None):
            if (not(papa.getIzq() == None)) and (papa.getIzq().getElem().__eq__(actual.getElem())):  #(actual.getElem().__eq__(elem)
                papa.setIzq(None)
                actual.setPapa(None)
                self.__actualiza_fe(papa)
                self.__balancea(papa.getDer())
            else:
                if papa.getDer().getElem().__eq__(actual.getElem()):
                    papa.setDer(None)
                    actual.setPapa(None)
                    self.__actualiza_fe(papa)
                    self.__balancea(papa.getIzq())
        else:
            self.__raiiz = None
        self.__contador = self.__contador - 1

    def __eliminaSoloConHijoIzq(self, actual, papa, sustituto):
        if not(papa == None):
            actual.setPapa(None)
            actual.setIzq(None)
            papa.cuelga(sustituto)
            self.__actualiza_fe(papa)
            self.__balancea(papa.getIzq())
        else:
            actual.setIzq(None)
            sustituto.setPapa(None)
            self.__raiiz = sustituto
        self.__cont = self.__cont - 1

    def __eliminaSoloConHijoDer(self, actual: NodoAVL, papa: NodoAVL, sustituto: NodoAVL):
        if not(papa == None):
            actual.setPapa(None)
            actual.setDer(None)
            papa.cuelga(sustituto)
            self.__actualiza_fe(papa)
            self.__balancea(papa.getDer())
        else:
            actual.setDer(None)
            sustituto.setPapa(None)
            self.__raiiz = sustituto
        self.__contador = self.__contador - 1

    def __eliminaConDosHijos(self, actual):
        suc = self.__sucesor(actual)
        actual.setElem(suc.getElem())
        self.__eliminaAux(suc)

    def __sucesor(self, actual):
        r = actual.getDer()
        while not(r.getIzq() == None):
            r = r.getIzq()
        return r

    #IMPRIME POR NIVEL DE IZQUIERDA A DERECHA
    #IMRIME EL FE DEL NODO QUE VISITA
    def imprimir(self):
        self.__imprimirNivel(self.__raiiz)

    def __imprimirNivel(self, raiz):
        if not(raiz == None):
            cola = []
            cola.append(raiz)
            while not(cola == []):
                temp = cola.pop(0)
                if not(temp == None):
                    cola.append(temp.getIzq())
                if not(temp == None):
                    cola.append(temp.getDer())
                if not (temp == None):
                    print(str(temp.getElem()) +" --->Fe: "+str(temp.getFe()))

    #IMPRIME CON NIVEL, PAPA Y FE
    # def imprimirConNivel(self):
    #     self.__imprimirConNivel2(self.__raiiz, 0)
    #     print(" ")
    #
    # def __imprimirConNivel2(self, temp, nivel):
    #     if (not(temp == None)):
    #         self.__imprimirConNivel2(temp.getIzq(), nivel + 1)
    #         s1 = str(temp.getElem())
    #         s2 = "  Nivel: "+ (str(nivel))
    #         s3 = "  Fe: "+((str(temp.getFe())))
    #         if (temp.getPapa() == None):
    #             s4 = "  Papa: null"
    #         else:
    #             s4= "  Papa :"+str(temp.getPapa().getElem())
    #         print(s1 + s2 + s3 + s4)
    #
    #         self.__imprimirConNivel2(temp.getDer(), nivel + 1)

#######################################################################################
#######################################################################################

a = ArbolAVL()
a.inserta(100)
a.inserta(300)
a.inserta(400)
a.inserta(350)
a.inserta(375)
a.inserta(50)
a.inserta(200)
a.inserta(360)
a.eliminar(300)
a.eliminar(360)
a.eliminar(350)
a.imprimir()


