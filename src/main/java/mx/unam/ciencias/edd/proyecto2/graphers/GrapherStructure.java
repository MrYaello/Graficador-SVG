package mx.unam.ciencias.edd.proyecto2.graphers;

import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto2.*;
import java.util.Iterator;

public class GrapherStructure extends GrapherSVG {

  public Structure structure;
  public Coleccion<Integer> build;
  public MeteSaca<Integer> build2;
  public Grafica<Integer> build3;
  public ArbolBinario<Integer> build4;
  public String svg = "";

  private int WIDTH;
  private int HEIGHT;
  private int ELEMENT_WIDTH;
  private int ELEMENT_HEIGHT;
  private int RADIUS;
  private final int MIN_ELEMENT_WIDTH;
  private final int MARGIN;
  private final int PADDING;
  private final int FONT_SIZE;

  public GrapherStructure() {
    MIN_ELEMENT_WIDTH = 40;
    WIDTH = 0;
    HEIGHT = 0;
    RADIUS = 0;
    ELEMENT_HEIGHT = 40;
    MARGIN = 10;
    PADDING = 3;
    FONT_SIZE = 20;
  }

  public String doGraph() {
    String graph = "";
    switch (structure) {
      case TRN:
      case TAVL:
      case TBC:
      case TBO:
        graph = graphArbol();
        break;

      case LISTA:
        graph = graphLista();
        break;

      case GRAP:
        graph = graphGraph();
        break;

      case COLA:
        graph = graphCola();
        break;

      case PILA:
        graph = graphPila();
        break;
    }
    svg += initSVG(WIDTH, HEIGHT) + graph + closeSVG();
    return svg;
  }

  public String graphLineal() {
    boolean doubleArrows = structure == Structure.LISTA;
    String conectorDeclaration = doubleArrows ? declareArrows() : declareArrow();
    String s = "";
    String[] elements = doubleArrows ? build.toString().substring(1, build.toString().length() - 1).split(", ")
      : build2.toString().split(",");
    if (elements[0] != null && !elements[0].equals("")) {
      ELEMENT_WIDTH = calcElementWidth(elements[0]);
      WIDTH += 2 * MARGIN + ELEMENT_WIDTH;
      HEIGHT = 2 * MARGIN + ELEMENT_HEIGHT;
      s += drawRectText(MARGIN, MARGIN, ELEMENT_WIDTH, ELEMENT_HEIGHT, "Black", "White", FONT_SIZE, "Black", elements[0]);
    } else {
      s += drawEmpty();
      WIDTH = 2 * MARGIN + 40;
      HEIGHT = 2 * MARGIN + 40;
      return s;
    }
    s += conectorDeclaration;
    for (int i = 1; i < elements.length; i++) {
      ELEMENT_WIDTH = calcElementWidth(elements[i]);
      WIDTH += PADDING;
      s += doubleArrows ? drawArrows(WIDTH - MARGIN, 0) : drawArrow(WIDTH - MARGIN, 0);
      WIDTH += PADDING + 24;
      s += drawRectText(WIDTH - MARGIN, MARGIN, ELEMENT_WIDTH, ELEMENT_HEIGHT, "Black", "White", FONT_SIZE, "Black", elements[i]);
      WIDTH += ELEMENT_WIDTH;
    }
    return s;
  }

  public String graphLista() {
    return graphLineal();
  }

  public String graphPila() {
    String s = "";
    String[] elements = build2.toString().split("\n");
    if (elements[0] != null && !elements[0].equals("")) {
      ELEMENT_WIDTH = calcElementWidth(max(elements));
      WIDTH += 2 * MARGIN + ELEMENT_WIDTH;
      HEIGHT = 2 * MARGIN + ELEMENT_HEIGHT;
      s += drawRectText(MARGIN, MARGIN, ELEMENT_WIDTH, ELEMENT_HEIGHT, "Black", "White", FONT_SIZE, "Black", elements[0]);
    } else {
      s += drawEmpty();
      WIDTH = 2 * MARGIN + 40;
      HEIGHT = 2 * MARGIN + 40;
      return s;
    }
    for (int i = 1; i < elements.length; i++) {
      HEIGHT += PADDING;
      s += drawRectText(MARGIN, HEIGHT - MARGIN, ELEMENT_WIDTH, ELEMENT_HEIGHT, "Black", "White", FONT_SIZE, "Black", elements[i]);
      HEIGHT += ELEMENT_HEIGHT;
    }
    return s;
  }

  public String graphCola() {
    return graphLineal();
  }

  public String graphArbol() { 
    String s = "";
    if (build4.esVacio()) {
      s += drawEmpty();
      WIDTH = 2 * MARGIN + 40;
      HEIGHT = 2 * MARGIN + 40;
      return s;
    }
    int depth = build4.altura() == 0 ? 1 : build4.altura();
    RADIUS = calcElementWidth(max(toStringArr(build4.iterator(), build4.getElementos()))) / 2;
    HEIGHT = MARGIN + depth * 100; 
    WIDTH = 2 * MARGIN + breadth(build4.raiz(), 1) * 2 * RADIUS;
    s += drawLines(build4.raiz(), (WIDTH / 2) / 2, WIDTH / 2, 50);
    s += drawVertexs(build4.raiz(), (WIDTH / 2) / 2, WIDTH / 2, 50, RADIUS);
    return s;
  }

  private String drawVertexs(VerticeArbolBinario v, double dec, double coord, y, double rad) {
    String s = "";
    if (v != null) {
      String balance = getAVL(v.toString());

    }
  }

  private String drawVertex(int x, int y, int rad, String text, String color, boolean isAvl, String balance) {

  }

  private String getAVL(String s) {
    String balance = null;
    if (structure == Structure.TAVL) balance = s.split(" ")[1];
    return balance;
  }

  public String graphGraph() {
    String s = build3.toString();
    return s;
  }

  private int calcElementWidth(String value) {
    return (value.length() * 14) + (2 * PADDING) > 40 ? (value.length() * 14) + (2 * PADDING) : MIN_ELEMENT_WIDTH;
  }

  private String[] toStringArr(Iterator<Integer> iterator, int el) {
    String[] arr = new String[el];
    int i = 0;
    while (iterator.hasNext()) {
      arr[i++] = iterator.next().toString();
    }
    return arr;
  }

  private String max(String[] arr) {
    int max = Integer.MIN_VALUE;
    int pos = -1;
    for (int i = 0; i < arr.length; i++) {
      if (max < Integer.parseInt(arr[i])) {
        max = Integer.parseInt(arr[i]);
        pos = i;
      }
    }
    return arr[pos];
  }

  private int breadth(VerticeArbolBinario<Integer> v, int n) {
    if (!v.hayDerecho()) return n;
    return breadth(v.derecho(), n + 1);
  }
}
