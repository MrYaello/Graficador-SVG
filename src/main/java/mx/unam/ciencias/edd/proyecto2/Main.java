package mx.unam.ciencias.edd.Proyecto2;

import mx.unam.ciencias.edd.*;

/**
  * Clase que realiza la actividad principal del proyecto. Ordenar la lista de cadenas,
  * normalizar las cadenas y formatear la lista.
  */
public class Main {

  public Structure structure;
  public Cola<Integer> elements = new Cola<>();
  public Coleccion<Integer> build;
  public MeteSaca<Integer> build2;
  public Grafica<Integer> build3; 
  
  public void start(String[] args) {
    Read read = new Read();
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
  }

  private void makeStructure(Lista<String> lista) {
    setStructure(lista);
    setElements(lista);

    switch (structure) {
      case TRN:
        build = new ArbolRojinegro<Integer>(); 
        break;

      case LISTA:
        build = new Lista<Integer>(); 
        break;

      case TAVL:
        build = new ArbolAVL<Integer>(); 
        break;

      case TBC:
        build = new ArbolBinarioCompleto<Integer>(); 
        break;

      case TBO:
        build = new ArbolBinarioOrdenado<Integer>();
        break;

      case GRAP:
        build3 = new Grafica<Integer>();
        break;

      case COLA:
        build2 = new Cola<Integer>();
        break;

      case PILA:
        build2 = new Pila<Integer>();
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
        if (a == b) build3.agrega(a);
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
    if (s.equals("arbolrojinegro"))
      structure = Structure.TRN;
    else if (s.equals("cola"))
      structure = Structure.COLA;
    else if (s.equals("pila"))
      structure = Structure.PILA;
    else if (s.equals("lista"))
      structure = Structure.LISTA;
    else if (s.equals("arbolavl"))
      structure = Structure.TAVL;
    else if (s.equals("arbolbinariocompleto"))
      structure = Structure.TBC;
    else if (s.equals("arbolbinarioordenado"))
      structure = Structure.TBO;
    else if (s.equals("grafica"))
      structure = Structure.GRAP;
    else { 
      System.err.println("La estructura de datos \"" + s + "\" no es válida.");
      System.exit(1);
    }
  }

  private void setElements(Lista<String> lista) {
    while (!lista.esVacia()) {
      String s = lista.eliminaPrimero().replaceAll("[^0-9]", "_");
      for (String e : s.split("_"))
        if (e.length() > 0 && Character.isDigit(e.charAt(0))) elements.mete(Integer.parseInt(e));  
    }
  }
  
}
