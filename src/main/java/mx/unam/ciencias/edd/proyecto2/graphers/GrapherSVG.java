package mx.unam.ciencias.edd.Proyecto2.graphers;

public class GrapherSVG {
  
  public GrapherSVG() {}

  public String initSVG(int w, int h) {
    return String.format("<?xml version='1.0' encoding='UTF-8' ?>" + "\n" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width='%d' height='%d'> <g>" + "\n", w, h);  
  }

  public String closeSVG() {
    return "</g> </svg>";
  }

  public String drawLine(int x1, int y1, int x2, int y2, String color) {
    return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d'" +
            " stroke='%s' stroke-width='3' />" + "\n", x1, y1, x2, y2, color);
  }

  public String drawRect(int x, int y, int w, int h, String stroke, String fill) {
    return String.format("<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' fill='%s' />" + "\n",
            x, y, w, h, stroke, fill);
  }

  public String drawRectText(int x, int y, int w, int h, String stroke, String fill, int size, String color, String text) {
    return drawRect(x, y, w, h, stroke, fill) + drawText(w / 2 + x, h / 2 + y, size, color, text);
  }

  public String drawCircle(int x, int y, int r, String stroke, String fill) {
    return String.format("<circle cx='%d' cy='%d' r='%d' stroke='%s' stroke-width='3' fill='%s' />" + "\n",
            x, y, r, stroke, fill);
  }

  public String drawCircleText(int x, int y, int r, String stroke, String fill, int size, String color, String text) {
    return drawCircle(x, y, r, stroke, fill) + drawText(x, y, size, color, text);
  }

  public String drawText(int x, int y, int size, String color, String text) {
    return String.format("<text x='%d' y='%d' text-anchor='middle'" +
            " font-family='sans-serif' font-size='%d' fill='%s'>%s</text>" + "\n", x, y + 5, size, color, text);
  }

  public String drawArrows() {
    return "";
  }

  public String declareArrows() {
    return "";
  }

  public String drawArrow() {
    return "";
  }

  public String declareArrow() {
    return "";
  }

}
