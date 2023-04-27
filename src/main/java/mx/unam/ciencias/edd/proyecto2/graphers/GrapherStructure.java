package mx.unam.ciencias.edd.proyecto2.graphers;

import java.util.Iterator;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto2.*;

public class GrapherStructure extends GrapherSVG {

    public Structure structure;
    public Coleccion<Integer> build;
    public MeteSaca<Integer> build2;
    public Grafica<Integer> build3;
    public String svg = "";

    private int WIDTH;
    private int HEIGHT;
    private int ELEMENT_WIDTH;
    private final int MIN_ELEMENT_WIDTH;
    private int ELEMENT_HEIGHT;
    private int RADIUS;
    private int MARGIN;
    private int PADDING;
    private int FONT_SIZE;

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

    public String graphLineal(boolean doubleArrows) {
        String conectorDeclaration = doubleArrows ? declareArrows() : declareArrow();
        String s = "";
        String value;
        String[] elements = doubleArrows ? build.toString().substring(1, build.toString().length() - 1).split(", ")
                                         : build2.toString().split(",");
        if (elements[0] != null && elements[0] != "") {
            value = elements[0];
            ELEMENT_WIDTH = calcElementWidth(value);
            WIDTH += 2 * MARGIN + ELEMENT_WIDTH;
            HEIGHT = 2 * MARGIN + ELEMENT_HEIGHT;
            s += drawRectText(MARGIN, MARGIN, ELEMENT_WIDTH, ELEMENT_HEIGHT, "Black", "White", FONT_SIZE, "Black", value);
        } else {
            s += drawEmpty();
            WIDTH = 2 * MARGIN + 40;
            HEIGHT = 2 * MARGIN + 40;
        }
        if (elements[1] != null && elements[1] != "") s += conectorDeclaration;
        for (int i = 1; i < elements.length; i++) {
            value = String.valueOf(elements[i]);
            ELEMENT_WIDTH = calcElementWidth(value);
            WIDTH += PADDING;
            s += doubleArrows ? drawArrows(WIDTH - MARGIN, 0) : drawArrow(WIDTH - MARGIN, 0);
            WIDTH += PADDING + 24;
            s += drawRectText(WIDTH - MARGIN, MARGIN, ELEMENT_WIDTH, ELEMENT_HEIGHT, "Black", "White", FONT_SIZE, "Black", value);
            WIDTH += ELEMENT_WIDTH;
        }
        return s;
    }

    public String graphLista() {
        return graphLineal(true);
    }

    public String graphPila() {
        String s = "";
        return s;
    }

    public String graphCola() {
        return graphLineal(false);
    }

    public String graphArbol() {
        String s = "";
        return s;
    }

    public String graphGraph() {
        String s = "";
        return s;
    }

    private int calcElementWidth(String value) {
        return (value.length() * 14) + (2 * PADDING) > 40 ? (value.length() * 14) + (2 * PADDING) : MIN_ELEMENT_WIDTH;
    }
}
