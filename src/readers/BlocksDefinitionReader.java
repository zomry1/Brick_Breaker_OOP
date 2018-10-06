package readers;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import game.mechanics.Block;
import game.mechanics.BlockCreator;
import game.mechanics.BlocksFromSymbolsFactory;
import game.mechanics.ColorsMap;
import geometry.Point;
import geometry.Rectangle;

/**
 * Read file of block definitions and create match factory.
 * of spacers and block creators
 */
public class BlocksDefinitionReader {
    /**
     * Read file of block definitions and create match factory.
     * of spacers and block creators
     * @param reader - the file with the block definitions
     * @return factory for blocks and spacers
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        //List of all the text
        List<String> text = new ArrayList<String>();
        BufferedReader lineReader = null;
        try {
            lineReader = new BufferedReader(reader);
            String line = lineReader.readLine();

            while (line != null) {
                text.add(line);

                line = lineReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file.");
            return null;
        } catch (IOException e) {
            System.err.println("Failed reading file" + ", message:" + e.getMessage());
            e.printStackTrace(System.err);
            return null;
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    System.err.println("Failed close the file" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        //Map of symbols and blockCreators
        Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();
        //Map of symbols and spacers
        Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
        String defaults = "";
        for (String line: text) {
            if (line.startsWith("#") || line.equals("")) {
                //Must to write something in the statment
                System.out.print("");
            } else if (line.startsWith("default")) {
                defaults = defaults.concat(line.substring(line.indexOf(" ") + 1) + " ");
            } else if (line.startsWith("bdef")) {
                String symbol = findSymbol(line);
                BlockCreator bc = blockReader(defaults.concat(line.substring(5)));
                //not found symbol or invalid blockCreator
                if (symbol == null || bc == null) {
                    return null;
                }
                blockCreators.put(symbol, bc);
            } else if (line.startsWith("sdef")) {
                String symbol = findSymbol(line);
                int spacer = spaceReader(line);
                //not found symbol or invalid spacer
                if (symbol == null || spacer == -1) {
                    return null;
                }
                spacerWidths.put(symbol, spacer);
            } else {
                //not valid line
                return null;
            }
        }
        //Return the match factory
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }
    /**
     * Find the symbol in the String.
     * @param line - the String
     * @return the symbol
     */
    private static String findSymbol(String line) {
        String symbol = "";
        int index = line.indexOf("symbol:");
        //Symbol is 1 char
        symbol = line.substring(index + 7, line.indexOf(" ", index + 8));
        return symbol;
    }

    /**
     * Create blockCreator from a String.
     * @param line - the String
     * @return blockCreator - null if there is something wrong in the input
     */
    private static BlockCreator blockReader(String line) {
        int width = -1;
        int height = -1;
        int hit = -1;
        String fill = "";
        String stroke = "";
        Map<Integer, String> fillsMap = new HashMap<Integer, String>();
        String[] keyNValue;
        //Split for values
        String[] values = line.split(" ");
        for (String oneMap: values) {
            //Split for key and value
            keyNValue = oneMap.split(":");
            if (keyNValue[0].equals("symbol")) {
                //Must to write something in the statment
                System.out.print("");
            } else if (keyNValue[0].equals("width")) {
                //width is number
                try {
                    width = Integer.parseInt(keyNValue[1]);
                } catch (Exception e) {
                    return null;
                }
            } else if (keyNValue[0].equals("height")) {
                //height is number
                try {
                    height = Integer.parseInt(keyNValue[1]);
                } catch (Exception e) {
                    return null;
                }
            } else if (keyNValue[0].equals("hit_points")) {
                //hit is number
                try {
                    hit = Integer.parseInt(keyNValue[1]);
                } catch (Exception e) {
                    return null;
                }
            } else if (keyNValue[0].equals("fill")) {
                fill = keyNValue[1];
            } else if (keyNValue[0].startsWith("fill-")) {
                int hypenIndex = keyNValue[0].indexOf("-");
                //The hit level of the fill
                int fillLevel = Integer.parseInt(keyNValue[0].substring(hypenIndex + 1));
                String filled = keyNValue[1];
                fillsMap.put(fillLevel, filled);
            } else if (keyNValue[0].equals("stroke")) {
                stroke = keyNValue[1];
            } else {
                return null;
            }
        }

        //Check values
        if (width == -1 || height == -1 || hit == -1) {
            return null;
        }
        //Check validation of fill and stroke
        if (!validStroke(stroke)) {
            return null;
        }
        //Check if there is fill or fill map for all hit points
        if (!validFill(fill)) {
            for (int i = hit; i > 0; i--) {
                if (!validFill(fillsMap.get(i))) {
                    return null;
                }
            }
        }
        //Check validation of the fillsMap
        for (Map.Entry<Integer, String> currFill: fillsMap.entrySet()) {
            if (!validFill(currFill.getValue())) {
                return null;
            }
        }

        final int widthF = width;
        final int heightF = height;
        final int hitF = hit;
        final String fillF = fill;
        final String strokeF = stroke;
        final Map<Integer, String> fillsMapF = fillsMap;

        //Return new blockCreator
        return new BlockCreator() {
            public Block create(int xpos, int ypos) {
                return new Block(new Rectangle(new Point(xpos, ypos), widthF, heightF),
                                                hitF, fillF, strokeF, fillsMapF);
            }
        };
    }

    /**
     * Check if the stroke String is valid input.
     * @param line - the stroke String
     * @return true - valid input, false - invalid input
     */
    private static boolean validStroke(String line) {
        //Its a color
        if (line.startsWith("color")) {
            if (ColorsMap.toColor(line) == null) {
                return false;
            }
            return true;
        } else if (line.equals("")) {
            //No stroke
            return true;
        }
        return false;
    }

    /**
     * Check if the fill String is valid input.
     * @param line - the fill String
     * @return true - valid input, false - invalid input
     */
    private static boolean validFill(String line) {
        //There is no fill
        if (line == null) {
            return false;
        } else if (line.startsWith("color")) {
            //Fill is color
            if (ColorsMap.toColor(line) == null) {
                //Cant convert to color
                return false;
            }
            return true;
        } else if (line.startsWith("image")) {
            //Load image
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
                        line.substring(line.indexOf("(") + 1, line.length() - 1));
            try {
                ImageIO.read(is);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Create spacer from a String.
     * @param line - the String
     * @return int - is the width of the spacer, -1 if there is something wrong in the input
     */
    private static int spaceReader(String line) {
        String[] keyNValue;
        String[] values = line.split(" ");

        for (String oneMap: values) {
            keyNValue = oneMap.split(":");
            if (keyNValue[0].equals("width")) {
                //If width is number
                try {
                    return Integer.parseInt(keyNValue[1]);
                } catch (Exception e) {
                    return -1;
                }
            }
        }
        return -1;
    }
}
