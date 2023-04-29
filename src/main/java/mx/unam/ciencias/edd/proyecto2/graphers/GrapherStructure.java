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
    if (build4.esVacia()) {
      s += drawEmpty();
      WIDTH = 2 * MARGIN + 40;
      HEIGHT = 2 * MARGIN + 40;
      return s;
    }
    int depth = build4.altura() == 0 ? 1 : build4.altura();
    RADIUS = calcElementWidth(max(toStringArr(build4.iterator(), build4.getElementos()))) / 2;
    HEIGHT = 2 * MARGIN + 2 * (depth + 3) * RADIUS;
    WIDTH = 2 * MARGIN + (breadth(build4.raiz()) + 2) * RADIUS * 2;
    s += drawLines(build4.raiz(), (WIDTH / 2) / 2, WIDTH / 2, RADIUS + 10);
    s += drawVertexes(build4.raiz(), (WIDTH / 2) / 2, WIDTH / 2, RADIUS + 10, RADIUS, true);
    WIDTH += 2 * MARGIN;
    return s;
  }

  private String drawVertexes(VerticeArbolBinario v, int dec, int x, int y, int r, boolean isRight) {
    String s = "";
    if (v != null) {
      s += drawVertex(x, y, r, "black", getColor(v.toString()),
              FONT_SIZE, getColor(v.toString()).equals("White") ? "Black" : "White",
              v.get().toString(), getAVL(v.toString()) + (isRight ? " R" : " L"));
      if (v.hayIzquierdo() && v.hayDerecho())
        s += drawVertexes(v.izquierdo(), dec / 2, x - dec, y + 2 * r + 10, r, false) +
                drawVertexes(v.derecho(), dec / 2, x + dec, y + 2 * r + 10, r, true);
      if (v.hayIzquierdo() && !v.hayDerecho())
        s += drawVertexes(v.izquierdo(), dec / 2, x - dec, y + 2 * r + 10, r, false);
      if (!v.hayIzquierdo() && v.hayDerecho())
        s += drawVertexes(v.derecho(), dec / 2, x + dec, y + 2 * r + 10, r, true);
    }
    return s;
  }

  private String drawLines(VerticeArbolBinario v, int dec, int x, int y) {
    String s = "";
    if (v.hayIzquierdo() && v.hayDerecho())
      s += drawLine(x, y, x - dec, y + 2 * RADIUS + 10, "Black") +
              drawLine(x, y, x + dec, y + 2 * RADIUS + 10, "Black") +
              drawLines(v.izquierdo(), dec / 2, x - dec, y + 2 * RADIUS + 10) +
              drawLines(v.derecho(), dec / 2, x +  dec, y + 2 * RADIUS + 10);
    if (v.hayIzquierdo() && !v.hayDerecho())
      s += drawLine(x, y, x - dec, y + 2 * RADIUS + 10, "Black") +
              drawLines(v.izquierdo(), dec / 2, x - dec, y + 2 * RADIUS + 10);
    if (!v.hayIzquierdo() && v.hayDerecho())
      s += drawLine(x, y, x + dec, y + 2 * RADIUS + 10, "Black") +
              drawLines(v.derecho(), dec / 2, x +  dec, y + 2 * RADIUS + 10);
    return s;
  }

  private String getAVL(String s) {
    String balance = null;
    if (structure == Structure.TAVL) balance = s.split(" ")[1];
    return balance;
  }

  private String getColor(String s) {
    String color = "White";
    if (structure == Structure.TRN) color = s.charAt(0) == 'N' ? "Black" : "Red";
    return color;
  }

  public String graphGraph() {
    String v = "", l = "";
    RADIUS = calcElementWidth(max(toStringArr(build3.iterator(), build3.getElementos()))) / 2;
    WIDTH = HEIGHT = build3.getElementos() * (RADIUS + 10) * 2;
    Lista<String> coords = new Lista<>();
    int i = 1;
    for (Integer e : build3) {
      coords.agrega(getCoords(build3.getElementos(), i, e.toString()));
      double x = Double.parseDouble(coords.getUltimo().split(" ")[1].split(",")[0]);
      double y = Double.parseDouble(coords.getUltimo().split(" ")[1].split(",")[1]);
      v += drawCircleText((int) x, (int) y, RADIUS, "Black", "White", 20, "Black", e.toString());
      i++;
    }

    String a = build3.toString().substring(build3.toString().indexOf(" {") + 2, build3.toString().lastIndexOf(", }"));
    String[] aristas = a.replace("(", "_").replace(")", "_").split("_, _");
    for (String e : aristas) {
      double x1 = -1;
      double x2 = -1;
      double y1 = -1;
      double y2 = -1;

      e = e.replace("_", "");
      for (String c : coords) {
        if (e.split(", ")[0].equals(c.split(" ")[0])) {
          x1 = Double.parseDouble(c.split(" ")[1].split(",")[0]);
          y1 = Double.parseDouble(c.split(" ")[1].split(",")[1]);
        }
        if (e.split(", ")[1].equals(c.split(" ")[0])) {
          x2 = Double.parseDouble(c.split(" ")[1].split(",")[0]);
          y2 = Double.parseDouble(c.split(" ")[1].split(",")[1]);
        }
      }
      if (x1 != -1 && x2 != -1 && y1 != -1 && y2 != -1)
        l += drawLine((int) x1, (int) y1, (int) x2, (int) y2, "Black");
    }
    return l + v;
  }

  private String getCoords(int n, int i, String e) {
    double a = n * (RADIUS + 10);
    double b = n * (RADIUS);
    double k = (double) i/n;
    double x = a + b * Math.sin(k * 2 * Math.PI);
    double y = a + b * Math.cos(k * 2 * Math.PI);
    return e + " " + x + "," + y;
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

  private int breadth(VerticeArbolBinario<Integer> v) {
    int n = 1;
    if (v.hayDerecho()) {
      n += breadth(v.derecho());
    }
    if (v.hayIzquierdo()) {
      n += breadth(v.izquierdo());
    }
    return n;
  }
}
