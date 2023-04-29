package mx.unam.ciencias.edd.proyecto2.graphers;

public class GrapherSVG {

  public GrapherSVG() {}

  public String initSVG(int w, int h) {
    return String.format("<?xml version='1.0' encoding='UTF-8' ?>" + "\n" +
        "<svg xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='%d' height='%d'>\n\t<g>" + "\n", w, h);
  }

  public String closeSVG() {
    return "\t</g>\n</svg>";
  }

  public String drawLine(double x1, double y1, double x2, double y2, String color) {
    return String.format("\t\t<line x1='%,.4f' y1='%,.4f' x2='%,.4f' y2='%,.4f'" +
        " stroke='%s' stroke-width='3'/>" + "\n", x1, y1, x2, y2, color);
  }

  public String drawRect(int x, int y, int w, int h, String stroke, String fill) {
    return String.format("\t\t<rect x='%d' y='%d' width='%d' height='%d' stroke='%s' fill='%s'/>" + "\n",
        x, y, w, h, stroke, fill);
  }

  public String drawRectText(int x, int y, int w, int h, String stroke, String fill, int size, String color, String text) {
    return drawRect(x, y, w, h, stroke, fill) + drawText(w / 2 + x, h / 2 + y, size, color, text);
  }

  public String drawCircle(double x, double y, int r, String stroke, String fill) {
    return String.format("\t\t<circle cx='%,.4f' cy='%,.4f' r='%d' stroke='%s' stroke-width='3' fill='%s'/>" + "\n",
        x, y, r, stroke, fill);
  }

  public String drawCircleText(double x, double y, int r, String stroke, String fill, int size, String color, String text) {
    return drawCircle(x, y, r, stroke, fill) + drawText(x, y, size, color, text);
  }

  public String drawVertex(double x, double y, int r, String stroke, String fill, int size, String color, String text, String balance) {
    if (balance.split(" ")[0].equals("null"))
      return drawCircleText(x, y, r, stroke, fill, size,  color, text);
    return drawCircleText(x, y, r, stroke, fill, size,  color, text) +
            drawText(x + (balance.split(" ")[1].equals("R")  ? r + 10 : -r - 10), y - (r/4 + r/2),
                    size - 5, "black", balance.split(" ")[0]);
  }

  public String drawText(double x, double y, int size, String color, String text) {
    return String.format("\t\t<text x='%,.4f' y='%,.4f' text-anchor='middle'" +
        " font-family='sans-serif' font-size='%d' fill='%s'>%s</text>" + "\n", x, y + 5, size, color, text);
  }

  public String drawTriangle(int x, int y, int w, int h, String color) {
    return String.format("\t\t<polygon points='%d,%d %d,%d %d,%d' fill='%s'/>" + "\n",
        x, (h / 2) + y,
        x + w, y,
        x + w, y + h, color);
  }

  public String drawArrows(int x, int y) {
    return String.format("\t\t<use xlink:href='#arrows' x='%d' y='%d'/>" + "\n", x, y);
  }
  public String declareArrows() {
    return "\t\t<symbol id='arrows' width='40' height='50' viewBox='0 0'>" + "\n" +
      "\t" + drawTriangle(0, 25, 7, 15, "black") +
      "\t" + drawRect(7, 32, 7, 3, "", "black") +
      "\t" + drawTriangle(24, 21, -7, 15, "black") +
      "\t" + drawRect(10, 28, 7, 3, "", "black") +
      "\t\t</symbol>" + "\n";
  }

  public String drawArrow(int x, int y) {
    return String.format("\t\t<use xlink:href='#arrow' x='%d' y='%d'/>" + "\n", x, y);
  }

  public String declareArrow() {
    return "\t\t<symbol id='arrow' width='40' height='50' viewBox='0 0'>" + "\n" +
      "\t" + drawTriangle(24, 20, -12, 20, "black") +
      "\t" + drawRect(0, 27, 12, 6, "", "black") +
      "\t\t</symbol>" + "\n";
  }

  public String drawEmpty() {
    return drawCircle(30, 30, 20, "black", "white") +
      drawLine(10, 50, 50, 10, "black");
  }
}
