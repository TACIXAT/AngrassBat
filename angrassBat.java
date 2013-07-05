import java.awt.Robot;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import java.util.Scanner;

public class angrassBat {
	public static int[][] equivalenceList = new int[1000][1000];

	public static void main(String[] args) {
		System.out.println("Press return when your mouse is in position.");
		System.out.print("Place the mouse at the origin of the search rectangle: ");
		Point pos = getPositionAtMouse();
		int origX = (int) pos.getX();
		int origY = (int) pos.getY();
		
		System.out.print("Place the mouse at the right bound of the search rectangle: ");
		pos = getPositionAtMouse();
		int width = (int) pos.getX() - origX;
		
		System.out.print("Place the mouse at the lower bound of the search rectangle: ");
		pos = getPositionAtMouse();
		int height = (int) pos.getY() - origY;
		
		System.out.println("x: " + pos.getX() + "\ny: " + pos.getY());
		//dragMouse(200, 200, 300, 300, 500);
		
		int[][] imageMap = screenToArray(origX, origY, width, height);
		imageMap = connectedComponents(imageMap);
		printImageMap(imageMap);
		/*while(true) {
			Color pixelColor = getColorAtMouse();
			System.out.println(	"r: " + pixelColor.getRed() + 
								"g: " + pixelColor.getGreen() + 
								"b: " + pixelColor.getBlue() + 
								"rgb: " + pixelColor.getRGB());
		}//*/
	}
	
	public static Point getPositionAtMouse() {
		//wait for return
		Scanner read = new Scanner(System.in);
		read.nextLine();
		//get and return mouse position
		PointerInfo pinf = MouseInfo.getPointerInfo();
		Point pos = pinf.getLocation();
		return pos; 
	}
	
	public static Color getColorAtMouse() {
		Point pos = getPositionAtMouse();
		try {
			Robot kittens = new Robot();
			Color pixelColor = kittens.getPixelColor((int)pos.getX(), (int)pos.getY());
			return pixelColor;
		} catch(AWTException awte) {
			awte.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public static void dragMouse(int fromX, int fromY, int toX, int toY, int moveDelay) {
		try {
			Robot kittens = new Robot();
			kittens.mouseMove(fromX, fromY);
			kittens.delay(100);
			kittens.mousePress(InputEvent.BUTTON1_MASK);
			kittens.delay(100);
			kittens.mouseMove(toX, toY);
			kittens.delay(100);
			kittens.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch(AWTException awte) {
			awte.printStackTrace();
		}
	}
	
	//public static boolean searchRectForColor(Rectangle rect, redThresh, greenThresh, blueThresh)
	/*public static void printRectangleColors(int rectX, int rectY, int rectWidth, int rectHeight) {
		try {
			Rectangle rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
			Robot kittens = new Robot();
			System.out.println("Grabbing screen in 3 seconds. Minimize windows.");
			kittens.delay(3000);
			BufferedImage screenGrab = kittens.createScreenCapture(rect);
			Raster image = screenGrab.getData();
			int x, y, height, width;
			height = image.getHeight();
			width = image.getWidth();
			int[] pixel;
			System.out.println("Image got, sleeping 3 seconds then printing.\nSwitch to full screen now.");
			kittens.delay(3000);
			
			for(y=0; y<height; y++) {
				for(x=0; x<width; x++){
					pixel = image.getPixel(x, y, new int[3]);
					if(pixel[0] > 188 && pixel[1] > 188 && pixel[2] > 188) {
						System.out.print("X");
						//System.out.println("r: " + pixel[0] + " g: " + pixel[1] + " b: " + pixel[2]);
					} else {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
			
		} catch(AWTException awte) {
			awte.printStackTrace();
		}
	}*/
	
	public static int[][] screenToArray(int rectX, int rectY, int rectWidth, int rectHeight) {
		try {
			Rectangle rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
			Robot kittens = new Robot();
			System.out.println("Grabbing screen in 3 seconds. Minimize windows.");
			kittens.delay(3000);
			BufferedImage screenGrab = kittens.createScreenCapture(rect);
			Raster image = screenGrab.getData();
			int x, y, height, width;
			height = image.getHeight();
			width = image.getWidth();
			int[] pixel;
			System.out.println("Image got, sleeping 3 seconds then printing.\nSwitch to full screen now.");
			kittens.delay(3000);
			
			int set = 0x736574;
			int[][] imageMap = new int[height][width];
			for(y=0; y<height; y++) {
				for(x=0; x<width; x++){
					pixel = image.getPixel(x, y, new int[3]);
					if(pixel[0] > 188 && pixel[1] > 188 && pixel[2] > 188) {
						imageMap[y][x] = set;
					} else {
						imageMap[y][x] = 0;
					}
				}
			}
			
			return imageMap;
		} catch(AWTException awte) {
			awte.printStackTrace();
		}
		return null;
	}
	
	public static int[][] connectedComponents(int[][] imageMap) {
		int set = 0x736574;
		int newGroup = 1;
		int height = imageMap.length;
		int width = imageMap[0].length;
		int x, y;
		
		//first pass
		for(y=0; y<height; y++) {
			for(x=0; x<width; x++){
				if(imageMap[y][x] == set) {
					int groupNumber = getLowestNeighbor(x, y, imageMap);
					if(groupNumber == set) {
						groupNumber = newGroup;
						newGroup++;
						if(newGroup > 999)
							return null;
					}
					imageMap[y][x] = groupNumber;
				}
			}
		}
		
		//second pass
		for(y=0; y<height; y++) {
			for(x=0; x<width; x++){
				if(imageMap[y][x] != 0) {
					imageMap[y][x] = getLowestEqual(imageMap[y][x]);
				}
			}
		}
		
		return imageMap;
	}
	
	public static int getLowestEqual(int value) {
		int i, retVal = 1000;
		for(i=1; i<1000; i++) {
			if(i == value) 
				return i;
			if(equivalenceList[value][i] != 0) {
				retVal = getLowestEqual(i);
				break;
			}
		}
		
		equivalenceList[value][retVal] = 1;
		return retVal;
	}
	
	public static int getLowestNeighbor(int x, int y, int[][] imageMap) {
		int height = imageMap.length;
		int width = imageMap[0].length;
		int set = 0x736574;
		int i, j;
		
		int lowestNeighbor = set;
		int[][] neighbors = {{1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}};
		int[] nVals = new int[8];
		
		//get neighbors
		for(i=0; i<8; i++) {
			int xMod = x + neighbors[i][0];
			int yMod = y + neighbors[i][1];
			if(xMod < width && yMod < height) {
				int nVal = imageMap[yMod][xMod];
				if(nVal != 0 && nVal != set) {
					nVals[i] = nVal;
					if(nVal < lowestNeighbor) {
						lowestNeighbor = nVal;
					}
				}
			}
		}
		
		//build equivalence list
		for(i=0; i<8; i++) {
			if(nVals[i] != 0) {
				int value = nVals[i];	
				for(j=0; j<8; j++) {
					int eqVal = nVals[j];
					if(eqVal != 0)
						equivalenceList[value][eqVal] = 1;
				}
			}
		}
		
		return lowestNeighbor;
	}
	
	public static void printImageMap(int[][] imageMap) {
		int height = imageMap.length;
		int width = imageMap[0].length;
		int x, y;
		
		for(y=0; y<height; y++) {
			for(x=0; x<width; x++){
				if(imageMap[y][x] == 0) {
					System.out.print(" ");
				} else {
					System.out.print(imageMap[y][x]);
				}
			}
			System.out.println();
		}
	}
}




















