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

public class ingressBot {
	public static void main(String[] args) {
		
		Point pos = getPositionAtMouse();
		System.out.println("x: " + pos.getX() + "\ny: " + pos.getY());
		//dragMouse(200, 200, 300, 300, 500);
		
		printRectangleColors(453, 314, 60, 40);
		
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
		System.out.print("Move your mouse then press enter to print its coordinates: ");
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
					if(pixel[0] > 180 && pixel[1] > 180 && pixel[2] > 180) {
						System.out.print("X");
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


