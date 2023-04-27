package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto2.*;
import mx.unam.ciencias.edd.proyecto2.graphers.*;

public class Main {

  public GrapherStructure gs = new GrapherStructure();
  public Cola<Integer> elements = new Cola<>();

  public void start(String[] args) {
    Read read = new Read();
    GrapherStructure g = new GrapherStructure();

    if (args.length == 0) read.read(read.standardInput());
    else read.read(read.file(args[0]));
    makeStructure(read.getLista());
    String svg = gs.doGraph();

    System.out.println(svg);
  }

  private void makeStructure(Lista<String> lista) {
    setStructure(lista);
    setElements(lista);

    switch (gs.structure) {
      case TRN:
        gs.build = new ArbolRojinegro<>();
        break;

      case LISTA:
        gs.build = new Lista<>();
        break;

      case TAVL:
        gs.build = new ArbolAVL<>();
        break;

      case TBC:
        gs.build = new ArbolBinarioCompleto<>();
        break;

      case TBO:
        gs.build = new ArbolBinarioOrdenado<>();
        break;

      case GRAP:
        gs.build3 = new Grafica<>();
        break;

      case COLA:
        gs.build2 = new Cola<>();
        break;

      case PILA:
        gs.build2 = new Pila<>();
        break;
    }

    if (gs.structure == Structure.GRAP) {
      while (!elements.esVacia()) {
        Integer a = null;
        Integer b = null;
        try {
          a = elements.saca();
          b = elements.saca();
        } catch (Exception e) {
          System.err.println("Para crear una gráfica se requiere un número par de elementos.");
          System.exit(1);
        }
        if (a.equals(b)) gs.build3.agrega(a);
        else if (gs.build3.contiene(a) && !gs.build3.contiene(b)) {
          gs.build3.agrega(b);
          gs.build3.conecta(a,b);
        } else if (!gs.build3.contiene(a) && gs.build3.contiene(b)) {
          gs.build3.agrega(a);
          gs.build3.conecta(a,b);
        } else if (!gs.build3.contiene(a) && !gs.build3.contiene(b)) {
          gs.build3.agrega(a);
          gs.build3.agrega(b);
          gs.build3.conecta(a,b);
        } else if (gs.build3.contiene(a) && gs.build3.contiene(b) && !gs.build3.sonVecinos(a,b)) gs.build3.conecta(a,b);
      } 
    }

    while (!elements.esVacia()) {
      if (gs.build != null) gs.build.agrega(elements.saca());
      else gs.build2.mete(elements.saca());
    }
  }

  private void setStructure(Lista<String> lista) {
    String s = lista.getPrimero().toLowerCase().split(" ")[0];
    switch (s) {
      case "arbolrojinegro":
        gs.structure = Structure.TRN;
        break;
      case "cola":
        gs.structure = Structure.COLA;
        break;
      case "pila":
        gs.structure = Structure.PILA;
        break;
      case "lista":
        gs.structure = Structure.LISTA;
        break;
      case "arbolavl":
        gs.structure = Structure.TAVL;
        break;
      case "arbolbinariocompleto":
        gs.structure = Structure.TBC;
        break;
      case "arbolbinarioordenado":
        gs.structure = Structure.TBO;
        break;
      case "grafica":
        gs.structure = Structure.GRAP;
        break;
      default:
        System.err.println("La estructura de datos \"" + s + "\" no es válida.");
        System.exit(1);
    }
  }

  private void setElements(Lista<String> lista) {
    while (!lista.esVacia())
      for (String e : lista.eliminaPrimero().split("[^0-9]"))
        if (e.length() > 0 && Character.isDigit(e.charAt(0))) elements.mete(Integer.parseInt(e));  
  }

}
