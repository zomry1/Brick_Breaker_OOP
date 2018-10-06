package game.mechanics;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Color map get string of color and return it as Color.
 * @author Omry Zur
 *
 */
public class ColorsMap {

    /**
     * get string color-name of color and return the Color.
     * @param color - the string color-name
     * @return  the match Color
     */
    private static Color getColor(String color) {
        //Create map of string of colors and Colors
        Map<String, Color> colors = new HashMap<String, Color>();
        colors.put("black", Color.BLACK);
        colors.put("blue", Color.BLUE);
        colors.put("cyan", Color.CYAN);
        colors.put("darkgray", Color.DARK_GRAY);
        colors.put("gray", Color.GRAY);
        colors.put("green", Color.GREEN);
        colors.put("lightgray", Color.LIGHT_GRAY);
        colors.put("magenta", Color.MAGENTA);
        colors.put("orange", Color.ORANGE);
        colors.put("pink", Color.PINK);
        colors.put("red", Color.RED);
        colors.put("white", Color.WHITE);
        colors.put("yellow", Color.YELLOW);

        return colors.get(color.toLowerCase());
        }

    /**
     * Static - return Color from string.
     * @param color - the string of color
     * @return - the match Color
     */
    public static Color toColor(String color) {
        //Check if the color string build from 3 numbers
        if (color.startsWith("color(RGB")) {
            String onlyValues = color.substring(color.indexOf("B(") + 2, color.indexOf(")"));
            String[] values = onlyValues.split(",");
            int red = Integer.parseInt(values[0]);
            int green = Integer.parseInt(values[1]);
            int blue = Integer.parseInt(values[2]);
            //If the numbers not valid for RGB
            try {
                return new Color(red, green, blue);
            } catch (Exception e) {
                return null;
            }

        } else if (color.startsWith("color(")) {
            //the color string is color name
            return ColorsMap.getColor(color.substring(color.indexOf("(") + 1, color.indexOf(")")));
        }
        return null;

    }
}
