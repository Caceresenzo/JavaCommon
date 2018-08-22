package caceresenzo.libs.io;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	
	private static HashMap<String, BufferedImage> cache = new HashMap<>();
	
	private static boolean useCache = false;
	
	public static BufferedImage loadImage(String path) throws IOException {
		if (useCache && cache.containsKey(path)) {
			return cache.get(path);
		}
		
		return ImageIO.read(BufferedImageLoader.class.getResource(path));
	}
	
	public static void setUseCache(boolean useCache) {
		BufferedImageLoader.useCache = useCache;
	}
	
}