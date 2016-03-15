package yang;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class HugeImage {
	
	private BufferedImage subImage;
	private int x;
	private int y;
	private int w;
	private int h;
	
	public HugeImage(final Rectangle rect) {
		x = rect.x;
		y = rect.y;
		w = rect.width;
		h = rect.height;
	}
	
	public void execute(String file) {
		BufferedImage image = null;
		
		try {
			Rectangle subRegion = new Rectangle(x, y, w, h);
			File input = new File(file);
			ImageInputStream stream = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
			
			if(readers.hasNext()) {
				ImageReader reader = readers.next();
				reader.setInput(stream);
				
				ImageReadParam param = reader.getDefaultReadParam();
				param.setSourceRegion(subRegion);
				
				image = reader.read(0, param);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		subImage = image;
	}
	
	public BufferedImage getSubImage() {
		return subImage;
	}
	
	public static void main(String[] args) {
		int x = 16000;
		int y = 15000;
		int width = 1000;
		int height = 1000;
//		String sourcePath = "E:/Data/www.brain-map.com/278260569_";
//		String targetPath = "E:/Data/region from Brain/";
//		int min = 2;
//		int max = 10;
//		
//		HugeImage h = new HugeImage(new Rectangle(x, y, width, height));
//		for(int i = min; i <= max; i++) {
//			String path = sourcePath + i + ".jpg";
//			h.execute(path);
//			BufferedImage subImage = h.getSubImage();
//			try {
//				ImageIO.write(subImage, "jpg", 
//						new File(targetPath + i + ".jpg"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			Runtime.getRuntime().gc();
//		}
		
		
		String file = "E://test.jpg";
		HugeImage h = new HugeImage(new Rectangle(x, y, width, height));
		h.execute(file);
		BufferedImage subImage = h.getSubImage();
		
		try {
			ImageIO.write(subImage, "jpg", new File("E://subImage.jpg"));
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}
