package mx.unam.ciencias.edd.proyecto2.graphers;

public class GrapherSVG {
  
  public GrapherSVG() {}

  public String initSVG(int w, int h) {
    return String.format("<?xml version='1.0' encoding='UTF-8' ?>" + "\n" +
            "<svg xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='%d' height='%d'>\t<g>" + "\n", w, h);
  }

  public String closeSVG() {
    return "\t</g>\n</svg>";
  }

  public String drawLine(int x1, int y1, int x2, int y2, String color) {
    return String.format("\t\t<line x1='%d' y1='%d' x2='%d' y2='%d'" +
            " stroke='%s' stroke-width='3' />" + "\n", x1, y1, x2, y2, color);
  }

  public String drawRect(int x, int y, int w, int h, String stroke, String fill) {
    return String.format("\t\t<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' fill='%s' />" + "\n",
            x, y, w, h, stroke, fill);
  }

  public String drawRectText(int x, int y, int w, int h, String stroke, String fill, int size, String color, String text) {
    return drawRect(x, y, w, h, stroke, fill) + drawText(w / 2 + x, h / 2 + y, size, color, text);
  }

  public String drawCircle(int x, int y, int r, String stroke, String fill) {
    return String.format("\t\t<circle cx='%d' cy='%d' r='%d' stroke='%s' stroke-width='3' fill='%s' />" + "\n",
            x, y, r, stroke, fill);
  }

  public String drawCircleText(int x, int y, int r, String stroke, String fill, int size, String color, String text) {
    return drawCircle(x, y, r, stroke, fill) + drawText(x, y, size, color, text);
  }

  public String drawText(int x, int y, int size, String color, String text) {
    return String.format("\t\t<text x='%d' y='%d' text-anchor='middle'" +
            " font-family='sans-serif' font-size='%d' fill='%s'>%s</text>" + "\n", x, y + 5, size, color, text);
  }

  public String drawTriangle(int x, int y, int w, int h, String color) {
    return String.format("\t\t<polygon points='%d,%d %d,%d %d,%d' fill='%s' />" + "\n",
            x, (h / 2) + y,
            x + w, y,
            x + w, y + h, color);
  }

  public String drawArrows(int x, int y) {
    return String.format("\t\t<use xlink:href='#arrows' x='%d' y='%d'/>" + "\n", x, y);
  }
  public String declareArrows() {
    return "\t\t<symbol id='arrows' width='40' height='50' viewBox='0 0'>" + "\n" +
            "\t" + drawTriangle(0, 25, 12, 20, "black") +
            "\t" + drawRect(12, 32, 12, 6, "", "black") +
            "\t" + drawTriangle(40, 15, -12, 20, "black") +
            "\t" + drawRect(16, 23, 12, 6, "", "black") +
            "\t\t</symbol>" + "\n";
  }

  public String drawArrow(int x, int y) {
    return String.format("\t\t<use xlink:href='#arrow' x='%d' y='%d'/>" + "\n", x, y);
  }

  public String declareArrow() {
    return "<symbol id='arrow' width='40' height='50' viewBox='0 0'>" + "\n" +
            "\t" + drawTriangle(24, 25, -12, 20, "black") +
            "\t" + drawRect(0, 32, 12, 6, "", "black") +
            "</symbol>" + "\n";
  }

  public String drawEmpty() {
    return drawCircle(30, 30, 50, "black", "white") +
            drawLine(10, 50, 50, 10, "black");
  }
}
