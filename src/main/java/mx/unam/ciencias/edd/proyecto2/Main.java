package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto2.*;
import mx.unam.ciencias.edd.proyecto2.graphers.*;

public class Main {

  public Structure structure;
  public Cola<Integer> elements = new Cola<>();
  public Coleccion<Integer> build;
  public MeteSaca<Integer> build2;
  public Grafica<Integer> build3; 
  
  public void start(String[] args) {
    Read read = new Read();
    GrapherSVG g = new GrapherSVG();
    String svg = g.initSVG(250, 60) + g.declareArrows() +
    g.drawArrows(0,0) + g.drawArrows(50, 0) + g.drawArrows(100, 0) + g.closeSVG();
    if (args.length == 0) read.read(read.standardInput());
    else read.read(read.file(args[0]));
    System.out.println(read.getLista());
    makeStructure(read.getLista());
    System.out.println(read.getLista());
    System.out.println(structure);
    System.out.println(elements);
    System.out.println(build);
    System.out.println(build2);
    System.out.println(build3);
    System.out.println(svg);
  }

  private void makeStructure(Lista<String> lista) {
    setStructure(lista);
    setElements(lista);

    switch (structure) {
      case TRN:
        build = new ArbolRojinegro<>();
        break;

      case LISTA:
        build = new Lista<>();
        break;

      case TAVL:
        build = new ArbolAVL<>();
        break;

      case TBC:
        build = new ArbolBinarioCompleto<>();
        break;

      case TBO:
        build = new ArbolBinarioOrdenado<>();
        break;

      case GRAP:
        build3 = new Grafica<>();
        break;

      case COLA:
        build2 = new Cola<>();
        break;

      case PILA:
        build2 = new Pila<>();
        break;
    }

    if (build3 != null) {
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
        if (a.equals(b)) build3.agrega(a);
        else if (build3.contiene(a) && !build3.contiene(b)) {
          build3.agrega(b);
          build3.conecta(a,b);
        } else if (!build3.contiene(a) && build3.contiene(b)) {
          build3.agrega(a);
          build3.conecta(a,b);
        } else if (!build3.contiene(a) && !build3.contiene(b)) {
          build3.agrega(a);
          build3.agrega(b);
          build3.conecta(a,b);
        } else if (build3.contiene(a) && build3.contiene(b) && !build3.sonVecinos(a,b)) build3.conecta(a,b);
      } 
      
    }
    while (!elements.esVacia()) {
      if (build != null) build.agrega(elements.saca());
      else build2.mete(elements.saca());
    }
  }

  private void setStructure(Lista<String> lista) {
    String s = lista.getPrimero().toLowerCase().split(" ")[0];
    switch (s) {
      case "arbolrojinegro":
        structure = Structure.TRN;
        break;
      case "cola":
        structure = Structure.COLA;
        break;
      case "pila":
        structure = Structure.PILA;
        break;
      case "lista":
        structure = Structure.LISTA;
        break;
      case "arbolavl":
        structure = Structure.TAVL;
        break;
      case "arbolbinariocompleto":
        structure = Structure.TBC;
        break;
      case "arbolbinarioordenado":
        structure = Structure.TBO;
        break;
      case "grafica":
        structure = Structure.GRAP;
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
