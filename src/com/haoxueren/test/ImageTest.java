package com.haoxueren.test;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest
{
	public static void main(String[] args) throws Exception
	{
		for (int imageType = 1; imageType <= 13; imageType++)
		{
			tinyPNG("d:/test.png", "d:/test" + imageType + ".png", imageType);
		}
		System.out.println("Í¼Æ¬Ñ¹ËõÍê±Ï£¡");
	}

	public static void image() throws IOException
	{
		int width = 960;
		int height = 540;
		String text = "Haoxueren";

		File file = new File("d:/image.png");
		Font font = new Font("Î¢ÈíÑÅºÚ", 0, 15);
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

	public static void tinyPNG(String fromFile, String toFile, int imageType)
	{
		try
		{
			File file = new File(fromFile);
			BufferedImage bufferedImage1 = ImageIO.read(file);
			int width = bufferedImage1.getWidth();
			int height = bufferedImage1.getHeight();

			BufferedImage bufferedImage2 = new BufferedImage(width, height, imageType);
			Graphics2D graphics2d = bufferedImage2.createGraphics();

			GraphicsConfiguration configuration = graphics2d.getDeviceConfiguration();
			bufferedImage2 = configuration.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			graphics2d.dispose();

			graphics2d = bufferedImage2.createGraphics();
//			Image from = bufferedImage1.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			graphics2d.drawImage(bufferedImage1, 0, 0, null);
			graphics2d.dispose();

			ImageIO.write(bufferedImage2, "png", new File(toFile));
		} catch (IOException e)
		{

			e.printStackTrace();

		}

	}

}
