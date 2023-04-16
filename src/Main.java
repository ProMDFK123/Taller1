//Importación de librerias necesarias
import edu.princeton.cs.stdlib.StdDraw;
import java.awt.*;

/**
 * Taller 1: Salvapantallas
 *
 * @author Gabriel López - 21.583.391-7
 */
public class Main {
    public static void main(String[] args) {
        salvapantallas();
    }

    /**
     * Método que contiene el funcionamiento principal del programa,
     * incluyendo la definición de las diversas variables implicadas.
     */
    public static void salvapantallas(){
        //Rango de dibujado
        double min =-1.0;
        double max =1.0;
        double rango = (max-min)+1;

        //Escalado del lienzo
        StdDraw.setXscale(min,max);
        StdDraw.setXscale(min,max);
        //StdDraw.setCanvasSize(1300,675);

        StdDraw.enableDoubleBuffering(); //Evite de parpadeo

        //Lista de posibles colores
        Color[] colores = {Color.BLACK,Color.BLUE,Color.DARK_GRAY,Color.MAGENTA,Color.RED,Color.GREEN};

        //Generación coordenadas iniciales, finales y velocidad de la primera linea
        double x0 = (Math.random()*rango)+min;
        double y0 = (Math.random()*rango)+min;
        double x = (Math.random()*rango)+min;
        double y = (Math.random()*rango)+min;
        double vx = puntoMedio(x0,x);
        double vy = puntoMedio(y0,y);

        int cantidadLineas = 0; //Contador de lineas dibujadas

        Color color = colores[(int) (Math.random()* colores.length)]; //Selecciona el color de la primera linea generada

        //Ciclo infinito
        while(true){
            //Revisión de colisiones con bordes
            x0 = colisionX0(x0,x,vx,y0,y,max);
            y0 = colisionY0(x0,x,vy,y0,y,max);
            x = colisionX(x0, x, vx, y0, y, max, min, rango);
            y = colisionY(x0, x, vx, y0, y, max, min, rango);

            dibujarlinea(x0,y0,x,y,vx,vy,color,min,max,rango);
            cantidadLineas++; //Actualiza la cantidad de lineas dibujadas
            //Define las nuevas posiciones iniciales, finales y el color de la siguiente linea
            x0=x;
            y0=y;
            x= (Math.random()*rango)+min;
            y =(Math.random()*rango)+min;
            color = colores[(int) (Math.random()* colores.length)];

            if(cantidadLineas%6==0){ //Verifica si ya hay 6 lineas dibujadas o no
                StdDraw.clear(); //Limpia la pantalla
            }
        }
    }

    /**
     * Metodo que dibuja una linea en el lienzo.
     * @param x0 - Coordenada X inicial.
     * @param y0 - Coordenada Y inicial.
     * @param x - Coordenada X final.
     * @param y - Coordenada Y final.
     * @param color - Color de la linea.
     */
    public static void dibujarlinea(double x0,double y0,double x,double y, double vx, double vy,Color color,double min, double max, double rango){
        StdDraw.setPenColor(color); //Define el color de la linea

        //Dibujado de la linea
        StdDraw.line(x0,y0,x,y);
        StdDraw.show();
        StdDraw.pause(200);
    }

    /**
     * Método que revisa si la coordenada inicial en X esta fuera del rango.
     * @param x0 - Coordenada X inicial.
     * @param x - Coordenada X final.
     * @param vx - Punto medio en X.
     * @param y0 - Coordenada Y inicial.
     * @param y - Coordenada Y final.
     * @param max - Valor máximo del rango.
     * @return retorna el valor final para x0.
     */
    public static double colisionX0(double x0,double x, double vx, double y0, double y,double max){
        double largo = pitagoras(x0,x,y0,y);
        if(Math.abs(x0+vx)>max-largo){return x0=-vx;}
        return x0;
    }

    /**
     * Método que revisa si la coordenada final en X esta fuera del rango.
     * @param x0 - Coordenada X inicial.
     * @param x - Coordenada X final.
     * @param vx - Punto medio en X.
     * @param y0 - Coordenada Y inicial.
     * @param y - Coordenada Y final.
     * @param max - Valor máximo del rango.
     * @param min - Valor minimo del rango.
     * @param rango - Valor del rango.
     * @return retorna el valor final para X.
     */
    public static double colisionX(double x0,double x, double vx, double y0, double y,double max,double min, double rango){
        double largo = pitagoras(x0,x,y0,y);
        if(Math.abs(x+vx)>max-largo){return x=Math.random()*rango+min;}
        return x;
    }

    /**
     * Método que revisa si la coordenada inicial en Y esta fuera del rango.
     * @param x0 - Coordenada X inicial.
     * @param x - Coordenada X final.
     * @param vy - Punto medio en Y.
     * @param y0 - Coordenada Y inicial.
     * @param y - Coordenada Y final.
     * @param max - Valor máximo del rango.
     * @return retorna el valor final para y0.
     */
    public static double colisionY0(double x0,double x, double vy, double y0, double y,double max){
        double largo = pitagoras(x0,x,y0,y);
        if(Math.abs(x0+vy)>max-largo){return y0=-vy;}
        return y0;
    }

    /**
     * Método que revisa si la coordenada final en Y esta fuera del rango.
     * @param x0 - Coordenada X inicial.
     * @param x - Coordenada X final.
     * @param vy - Punto medio en Y.
     * @param y0 - Coordenada Y inicial.
     * @param y - Coordenada Y final.
     * @param max - Valor máximo del rango.
     * @param min - Valor minimo del rango.
     * @param rango - Valor del rango.
     * @return retorna el valor final para Y.
     */
    public static double colisionY(double x0,double x, double vy, double y0, double y,double max,double min, double rango){
        double largo = pitagoras(x0,x,y0,y);
        if(Math.abs(y+vy)>max-largo){return y=Math.random()*rango+min;}
        return y;
    }

    /**
     * Método que calcula el largo de una linea, usando el teorema de pitagoras.
     * @param x0 - Coordenada X inicial.
     * @param x - Coordenada X final.
     * @param y0 - Coordenada Y inicial.
     * @param y - Coordenada Y final.
     * @return retorna el valor final de la hipotenusa (largo de la linea).
     */
    public static double pitagoras(double x0, double x, double y0, double y){
        double ejeX = Math.abs(x0-x);
        double ejeY = Math.abs(y0-y);
        double cateto1 = Math.pow(ejeX,2);
        double cateto2 = Math.pow(ejeY,2);
        double hipotenusa = cateto1+cateto2;
        return (Math.sqrt(hipotenusa));
    }

    /**
     * Método que busca el punto medio en un eje dado.
     * @param puntoInicial
     * @param puntoFinal
     * @return retorna el valor medio entre ambos puntos.
     */
    public static double puntoMedio(double puntoInicial,double puntoFinal){return ((puntoFinal+puntoInicial)/2);}
}