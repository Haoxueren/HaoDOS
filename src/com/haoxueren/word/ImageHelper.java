package com.haoxueren.word;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * createImage 创建图片；<br>
 * compressImage 压缩图片；<br>
 */
public class ImageHelper
{
	/** 创建一个PNG图片； */
	public static void createImage(File file)
	{
		try
		{
			// 确定图片的大小；
			int width = 960, height = 540;
			// 创建图片对象；
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 获取Graphics2D对象；
			Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
			// 设置图片背景；
			graphics2d.setBackground(Color.WHITE);
			graphics2d.clearRect(0, 0, width, height);
			// 将图片保存到本地；
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}

	/** 压缩图片(BufferedImage.ImageType)； */
	public static void compressImage(File file, int imageType) throws IOException
	{
		Image sourceImage = ImageIO.read(file);
		int width = sourceImage.getWidth(null);
		int height = sourceImage.getHeight(null);
		BufferedImage compressedImage = new BufferedImage(width, height, imageType);
		Graphics2D graphics2d = (Graphics2D) compressedImage.getGraphics();
		graphics2d.drawImage(sourceImage, 0, 0, null);
		ImageIO.write(compressedImage, "png", file);
	}

	/** 获取图像的位深度； */
	public static int getPixelSize(File imageFile) throws IOException
	{
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
		GraphicsConfiguration configuration = graphics2d.getDeviceConfiguration();
		ColorModel colorModel = configuration.getColorModel();
		int pixelSize = colorModel.getPixelSize();
		return pixelSize;
	}
}
