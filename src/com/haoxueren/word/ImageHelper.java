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
 * createImage ����ͼƬ��<br>
 * compressImage ѹ��ͼƬ��<br>
 */
public class ImageHelper
{
	/** ����һ��PNGͼƬ�� */
	public static void createImage(File file)
	{
		try
		{
			// ȷ��ͼƬ�Ĵ�С��
			int width = 960, height = 540;
			// ����ͼƬ����
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// ��ȡGraphics2D����
			Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
			// ����ͼƬ������
			graphics2d.setBackground(Color.WHITE);
			graphics2d.clearRect(0, 0, width, height);
			// ��ͼƬ���浽���أ�
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}

	/** ѹ��ͼƬ(BufferedImage.ImageType)�� */
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

	/** ��ȡͼ���λ��ȣ� */
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
