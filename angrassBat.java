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
		
		printRectangleColors(origX, origY, width, height);
		
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
	public static void printRectangleColors(int rectX, int rectY, int rectWidth, int rectHeight) {
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
	}
	   
}


