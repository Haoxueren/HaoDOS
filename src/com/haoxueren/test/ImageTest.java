package com.haoxueren.test;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest
{
	public static void main(String[] args) throws Exception
	{
		// File dir = new File("C:/Users/Haosir/Desktop/”¢”Ôµ•¥ ÕºΩ‚");
		// File[] files = dir.listFiles();
		// for (File file : files)
		// {
		// compress(file, 9, file);
		// }

		for (int imageType = 1; imageType <= 13; imageType++)
		{
			File file = new File("d:/test.png");
//			compress(file, imageType);
		}
		System.out.println("Õº∆¨—πÀıÕÍ±œ£°");
	}





	public static void image() throws IOException
	{
		int width = 960;
		int height = 540;
		String text = "Haoxueren";

		File file = new File("d:/image.png");
		Font font = new Font("Œ¢»Ì—≈∫⁄", 0, 15);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
		graphics2d.setBackground(Color.WHITE);
		graphics2d.clearRect(0, 0, width, height);
		graphics2d.setPaint(Color.BLACK);
		graphics2d.setFont(font);

		FontRenderContext context = graphics2d.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;

		graphics2d.drawString(text, (int) x, (int) baseY);
		ImageIO.write(bufferedImage, "png", file);
		Desktop.getDesktop().open(file);
	}
}
