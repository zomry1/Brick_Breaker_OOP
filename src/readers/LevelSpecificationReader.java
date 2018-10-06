package readers;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import game.mechanics.Block;
import game.mechanics.BlocksFromSymbolsFactory;
import game.mechanics.ColorsMap;
import game.mechanics.Sprite;
import game.mechanics.Velocity;
import game.mechanics.LevelInformation;

/**
 * Read from file of levels definitions and create list of levels information.
 */
public class LevelSpecificationReader {
    /**
     * Read from file of levels definitions and create list of levels information.
     * @param reader - the file we want to read
     * @return - list of all the level informations is the file
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        //List of all the text
        List<String> text = new ArrayList<String>();
        //List of the levels
        List<LevelInformation> levels = new ArrayList<LevelInformation>();

        BufferedReader lineReader = null;
        //Try read the text
        try {
            lineReader = new BufferedReader(reader);
            String line = lineReader.readLine();

            while (line != null) {
                text.add(line);
                line = lineReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file.");
        } catch (IOException e) {
            System.err.println("Failed reading file" + ", message:" + e.getMessage());
            e.printStackTrace(System.err);
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
        //Check for start and end levels, start and end blocks list
        int currLine = 0;

        int lineStartLevel = 0;
        int lineEndLevel = 0;
        boolean startFlag = false;
        boolean endFlag = false;

        int lineStartBlocks = 0;
        int lineEndBlocks = 0;
        boolean startBlockFlag = false;
        boolean endBlockFlag = false;

        for (String line: text) {
            if (line.equals("START_LEVEL")) {
                lineStartLevel = currLine;
                startFlag = true;
            } else if (line.equals("END_LEVEL")) {
                lineEndLevel = currLine;
                endFlag = true;
            } else if (line.equals("START_BLOCKS")) {
                lineStartBlocks = currLine;
                startBlockFlag = true;
            } else if (line.equals("END_BLOCKS")) {
                lineEndBlocks = currLine;
                endBlockFlag = true;
            }

            if ((startFlag && endFlag) && (startBlockFlag && endBlockFlag)) {
                    //Send the text to the function and get a level
                    LevelInformation level = this.levelReader(
                            text.subList(lineStartLevel, lineEndLevel),
                            text.subList(lineStartBlocks + 1, lineEndBlocks));
                    //If there is a level - add it to the list
                    if (level != null) {
                        levels.add(level);
                    } else {
                        //Print level load is
                        System.out.println("Loading level failed");
                    }
                    startFlag = false;
                    endFlag = false;
                    //
                    startBlockFlag = false;
                    endBlockFlag = false;
                    //
            }

            currLine++;
        }
        if (levels.isEmpty()) {
            //there is no levels in the list
            System.out.println("no valid levels");
        }
        return levels;

    }

    /**
     * Create list of the blocks.
     * @param blockMap - the text of the blocks
     * @param factory - the facotry for the blocks
     * @param x - the x start of the blocks
     * @param y - the y start of the blocks
     * @param row - the space between to rows
     * @return - List of all the blocks in the level
     */
    private List<Block> blocksMapReader(List<String> blockMap, BlocksFromSymbolsFactory factory,
                                        int x, int y, int row) {
        List<Block> blocks = new ArrayList<Block>();
        int currX = x;
        int currY = y;
        for (String line: blockMap) {
            currX = x;
            //Foreach symbol
            for (char symbol: line.toCharArray()) {
                //Check if there is a block with that symbol in the factory
                if (factory.isBlockSymbol(String.valueOf(symbol))) {
                    blocks.add(factory.getBlock(String.valueOf(symbol), currX, currY));
                    currX += factory.getBlock(String.valueOf(symbol), currX, currY).getCollistionRectangle().getWidth();
                    //Check if there is a spacer with that symbol
                } else if (factory.isSpaceSymbol(String.valueOf(symbol))) {
                    currX += factory.getSpaceWidth(String.valueOf(symbol));
                }
            }
            currY += row;
        }
        return blocks;
    }

    /**
     * Get text of level and return a LevelInformation.
     * @param values - the text of the values
     * @param blocks - the text of the blocks
     * @return - levelInformation from the file
     */
    private LevelInformation levelReader(List<String> values, List<String> blocks) {
        List<Velocity> velocities = new ArrayList<>();
        int paddleSpeed = -1;
        int paddleWidth = -1;
        int blockX = -1;
        int blockY = -1;
        int blockRow = -1;
        int blockNum = -1;
        String blocksText = "";
        Sprite background = null;
        String levelName = "";

        for (String line: values) {
            //For level name
            if (line.startsWith("level_name:")) {
                levelName = line.substring(line.indexOf(":") + 1);
            } else if (line.startsWith("ball_velocities:")) {
                //For Ball Velocities
                velocities = this.velocitiesText(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("paddle_speed:")) {
                //Paddle speed
                paddleSpeed = Integer.parseInt(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("paddle_width:")) {
                //Paddle width
                paddleWidth = Integer.parseInt(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("blocks_start_x:")) {
                //Blocks x start
                blockX = Integer.parseInt(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("blocks_start_y:")) {
                //Blocks y start
                blockY = Integer.parseInt(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("row_height:")) {
                //space between 2 rows
                blockRow = Integer.parseInt(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("num_blocks:")) {
                //Num of blocks to be removed
                blockNum = Integer.parseInt(line.substring(line.indexOf(":") + 1));
            } else if (line.startsWith("block_definitions:")) {
                //File path to the blocks definitions
                blocksText = line.substring(line.indexOf(":") + 1);
            } else if (line.startsWith("background:")) {
                //Background
                background = backgroundCreator(line.substring(line.indexOf(":") + 1));
            }
        }

        //Some value is missing - check before trying to create the list of blocks
        if (minimumForSix(paddleWidth, paddleSpeed, blockX, blockY, blockRow, blockNum) < 0
                || velocities == null
                || background == null
                || blocksText.isEmpty()
                || levelName.isEmpty()
                ||
                velocities.isEmpty()) {
            return null;
        }

        //Read the block definitions file
        Reader reader = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blocksText);
        reader = new InputStreamReader(is);
        //Get the factory from the blocks file
        BlocksFromSymbolsFactory factory = BlocksDefinitionReader.fromReader(reader);
        if (factory == null) {
            return null;
        }
        //Create list of blocks from the factory and level file
        List<Block> blocksList = blocksMapReader(blocks, factory, blockX, blockY, blockRow);
        //Finalize all
        String levelNameF = levelName;
        List<Velocity> velocitiesF = velocities;
        int paddleSpeedF = paddleSpeed;
        int paddleWidthF = paddleWidth;
        int blockNumF = blockNum;
        Sprite backgroundF = background;
        List<Block> blocksListF = blocksList;

        //Create the levelInformation
        return new LevelInformation() {
            public int paddleWidth() {
                return paddleWidthF;
            }
            public int paddleSpeed() {
                return paddleSpeedF;
            }
            public int numberOfBlocksToRemove() {
                return blockNumF;
            }
            public int numberOfBalls() {
                return velocitiesF.size();
            }
            public String levelName() {
                return levelNameF;
            }
            public List<Velocity> initialBallVelocities() {
                return velocitiesF;
            }
            public Sprite getBackground() {
                return backgroundF;
            }
            public List<Block> blocks() {
                return blocksListF;
            }
        };
    }

    /**
     * @param num1 - 1
     * @param num2 - 2
     * @param num3 - 3
     * @param num4 - 4
     * @param num5 - 5
     * @param num6 - 6
     * @return     - get the minimum of the number
     */
    private int minimumForSix(int num1, int num2, int num3, int num4, int num5, int num6) {
        return Math.min(Math.min(Math.min(Math.min(Math.min(num1, num2), num3), num4), num5), num6);
    }

    /**
     *  get the line of the background and return the sprite of the background.
     * @param line - the line value of the background
     * @return - the sprite of the background
     */
    private Sprite backgroundCreator(String line) {
        int topLeftCornerX = 0;
        int topLeftCornerY = 0;
        int width = 800;
        int height = 600;
        //If the background is color
        if (ColorsMap.toColor(line) != null) {
            return new Sprite() {
                public void drawOn(DrawSurface d) {
                    d.setColor(ColorsMap.toColor(line));
                    d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);
                }
                public void timePassed(double dt) {
                }
            };
        }  else if (line.startsWith("image(")) {
            //Background is color
            Image img = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(
                                line.substring(line.indexOf("(") + 1, line.length() - 1));
                img = ImageIO.read(is);
            } catch (IOException e) {
                System.out.println("Error load image of block");
            }
            final Image imgF = img;
            return new Sprite() {
                public void drawOn(DrawSurface d) {
                    d.drawImage(topLeftCornerX, topLeftCornerY, imgF);
                }
                public void timePassed(double dt) {
                    //nothing
                }
            };
        } else {
            //Background is nothing
            return null;
        }
    }

    /**
     * Return list of velocities form the values.
     * @param velocities - values of velocities
     * @return  list of velocities
     */
    private  List<Velocity> velocitiesText(String velocities) {
        List<Velocity> velocitiesReturn = new ArrayList<Velocity>();
        String[] vels = velocities.split(" ");
        //Foreach velocity pair
        for (String vel: vels) {
            String angle = vel.substring(0, vel.indexOf(","));
            String speed = vel.substring(vel.indexOf(",") + 1);
            //If there is no angle or speed
            if (angle == null || speed == null) {
                return null;
            }
            velocitiesReturn.add(Velocity.fromAngleAndSpeed(Double.parseDouble(angle), Double.parseDouble(speed)));
        }
        return velocitiesReturn;
    }
}
