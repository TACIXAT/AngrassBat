import java.awt.Robot;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Scanner;

public class ingressBot {
	public static void main(String[] args) {
		Point pos = getPosition();
		System.out.println("x: " + pos.getX() + "\ny: " + pos.getY());
		//Robot kittens = new Robot();
		//kittens.delay(1000);
	}
	
	public static Point getPosition() {
		//wait for return
		Scanner read = new Scanner(System.in);
		System.out.print("Move your mouse then press enter to print its coordinates: ");
		read.nextLine();
		//get and return mouse position
		PointerInfo pinf = MouseInfo.getPointerInfo();
		Point pos = pinf.getLocation();
		return pos;
	}
	
}


