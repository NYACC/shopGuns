package com.stylefeng.guns.core.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;  
import javax.imageio.ImageIO;  
import javax.imageio.ImageTypeSpecifier;  
import javax.imageio.ImageWriter;  
import javax.imageio.metadata.IIOMetadata;  
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;  
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Element;  

public class CompressUtil {
	/**
	 * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
	 * @param imgsrc 源图片地址
	 * @param imgdist 目标图片地址 
	 * @param widthdist 压缩后图片宽度（当rate==null时，必传）
	 * @param heightdist 压缩后图片高度（当rate==null时，必传）
	 * @param rate 压缩比例 
	 */
	public static void reduceImg(String imgsrc, String imgdist, int widthdist,
			int heightdist, Float rate) {
		try {
//			 System.out.println("----------"+imgdist+"--------"+imgsrc);
			File srcfile = new File(imgsrc);
			// 检查文件是否存在
			if (!srcfile.exists()) {
				return;
			}
			// 如果rate不为空说明是按比例压缩
			if (rate != null && rate > 0) {
				// 获取文件高度和宽度
				int[] results = getImgWidth(srcfile);
				if (results == null || results[0] == 0 || results[1] == 0) {
					return;
				} else {
					widthdist = (int) (results[0] * rate);
					heightdist = (int) (results[1] * rate);
				}
			}
			// 开始读取文件并进行压缩
			Image src = javax.imageio.ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage((int) widthdist,
					(int) heightdist, BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(
					src.getScaledInstance(widthdist, heightdist,
							Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream out = new FileOutputStream(imgdist);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(tag);
			
			String filePrefix = imgsrc.substring(imgsrc.lastIndexOf(".") + 1); 

			ImageIO.write(tag, filePrefix, out);
			
			out.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return 宽度
	 */
	public static int[] getImgWidth(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int result[] = { 0, 0 };
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			result[0] = src.getWidth(null); // 得到源图宽
			result[1] = src.getHeight(null); // 得到源图高
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
    
    /** 
       * 以JPEG编码保存图片 
       * @param dpi  分辨率 
       * @param image_to_save  要处理的图像图片 
       * @param JPEGcompression  压缩比 
       * @param fos 文件输出流 
       * @throws IOException 
       */  
      public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {  
              
          //useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html  
          //useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data  
          
          //old jpeg class  
          //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder  =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);  
          //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam  =  jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);  
          
          // Image writer  
//        JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();  
          ImageWriter imageWriter  =   ImageIO.getImageWritersBySuffix("jpg").next();  
          ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);  
          imageWriter.setOutput(ios);  
          //and metadata  
          IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);  
             
             
          if(dpi !=  null && !dpi.equals("")){  
                 
               //old metadata  
              //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);  
              //jpegEncodeParam.setXDensity(dpi);  
              //jpegEncodeParam.setYDensity(dpi);  
          
              //new metadata  
              Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");  
              Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);  
              jfif.setAttribute("Xdensity", Integer.toString(dpi) );  
              jfif.setAttribute("Ydensity", Integer.toString(dpi));  
                 
          }  
          
          
          if(JPEGcompression >= 0 && JPEGcompression <= 1f){  
          
              //old compression  
              //jpegEncodeParam.setQuality(JPEGcompression,false);  
          
              // new Compression  
              JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();  
              jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);  
              jpegParams.setCompressionQuality(JPEGcompression);  
          
          }  
          
          //old write and clean  
          //jpegEncoder.encode(image_to_save, jpegEncodeParam);  
          
          //new Write and clean up  
          imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);  
          ios.close();  
          imageWriter.dispose();  
          
      }  
      
	
	 public static void simpleCompress(File images) throws Exception {
		 System.out.println("----------"+images.getPath());
		 String separator=File.separator;
		 int i=images.getPath().lastIndexOf(separator);
		 String a = images.getPath().substring(0, i+1);
		 String b = images.getPath().substring(i+1, images.getPath().length());
		 String[] arr = {"50"+separator,"100"+separator,"150"+separator};
		 String path50=a+"50"+separator;
		 String path100=a+"100"+separator;
		 String path150=a+"150"+separator;
			
		File file_50=new File(path50);
		if (!file_50.exists()) {
			file_50.mkdirs();
		}
		File file_100=new File(path100);
		if (!file_100.exists()) {
			file_100.mkdirs();
		}
		File file_150=new File(path150);
		if (!file_150.exists()) {
			file_150.mkdirs();
		}		 
		 
	     reduceImg(images.getPath(), path50+b, 50,50,null);
	     reduceImg(images.getPath(),path100+b, 100, 100,null);
	     reduceImg(images.getPath(),path150+b, 150, 150,null);

	  }
}
