/*
 * The class Easing implements easing functions used in various animations in the game
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public final class Easing {
    public static double easeInQuad(double t, double b, double c, double d){
        t /= d;
        return c*t*t + b;
    }

    public static double easeOutQuad(double t, double b, double c, double d){
        t /= d;
        return -c * t*(t-2) + b;
    }

    public static double easeInOutQuad(double t, double b, double c, double d){
        if ((t/=d/2) < 1) return c/2*t*t + b;
        return -c/2 * ((--t)*(t-2) - 1) + b;
    }

    public static double easeInCube(double t, double b, double c, double d){
        t /= d;
        return c*t*t*t + b;
    }

    public static double easeOutCube(double t, double b, double c, double d){
        t /= d;
        t--;
        return c*(t*t*t + 1) + b;
    }
}
