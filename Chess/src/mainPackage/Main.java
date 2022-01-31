package mainPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Gameplay gameplay = new Gameplay();
		obj.setBounds(500, 130, 855, 878);
		obj.setTitle("Chess");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
	}
	
	
	// Code to make slightly transparent pixels on images fully transparent, and the rest either white or black
	public static void main1(String[] args) {
		try {
			String fileName = "BlackPawn.png";
			BufferedImage img = ImageIO.read(new File("Pieces\\" + fileName));
			
			for(int i = 0; i < img.getWidth(); i++) {
				for(int j = 0; j < img.getHeight(); j++) {
					String argb = Integer.toHexString(img.getRGB(i, j));
					while(argb.length() < 8) {
						argb = "0" + argb;
					}
					System.out.println("argb: " + argb);
					
					int transparency = Integer.parseInt(argb.substring(0, 2), 16);
					
					int r = Integer.parseInt(argb.substring(2, 4), 16);
					int g = Integer.parseInt(argb.substring(4, 6), 16);
					int b = Integer.parseInt(argb.substring(6, 8), 16);
					
					System.out.println("transp: " + transparency + ", r: " + r + ", g: " + g + ", b: " + b);
					if(transparency < 240) {
						System.out.println("Transparent");
						img.setRGB(i, j, 16777215); // Make the pixel transparent
					} else if(r+g+b < 255*1.5) {
						System.out.println("Black");
						img.setRGB(i, j, 0xff000000); //Make the pixel black
					} else {
						System.out.println("White");
						img.setRGB(i, j, 0xffffffff); // Make the pixel white
					}
					
				}
			}
			
			File outputfile = new File("Pieces\\" + fileName);
			ImageIO.write(img, "png", outputfile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}