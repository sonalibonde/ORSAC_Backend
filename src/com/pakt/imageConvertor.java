package com.pakt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class imageConvertor {
	public static String getImageString(String _licence_no){
//		System.out.println("x123");
		String path=null;
		if(_licence_no.equals("DL-011990004153")){
			path="D:\\NIKITA\\icons\\DL-011990004153.jpg";
		}
		if(_licence_no.equals("DL-052004021997")){
			path="D:\\NIKITA\\icons\\DL-052004021997.jpg";
		}
		if(_licence_no.equals("DL-420050075000")){
			path="D:\\NIKITA\\icons\\DL-420050075000.jpg";
		}
		if(_licence_no.equals("DL-012003000737")){
			path="D:\\NIKITA\\icons\\DL-012003000737.jpg";
		}
		if(_licence_no.equals("DL-520010088844")){
			path="D:\\NIKITA\\icons\\DL-520010088844.jpg";
		}
		if(_licence_no.equals("DL-03200300912")){
			path="D:\\NIKITA\\icons\\DL-03200300912.jpg";
		}
		if(_licence_no.equals("DL-071994003003")){
			path="D:\\NIKITA\\icons\\DL-071994003003.jpg";
		}
		if(_licence_no.equals("DL-052000003536")){
			path="D:\\NIKITA\\icons\\DL-052000003536.jpg";
		}
		String imageDataString ="";
		File imageFile = new File(path);
		 try {
	            // Reading a Image file from file system
	            FileInputStream imageInFile = new FileInputStream(imageFile);
	            byte imageData[] = new byte[(int) imageFile.length()];
	            imageInFile.read(imageData);
	             imageDataString = encodeImage(imageData);
		 } catch (FileNotFoundException e) {
	            System.out.println("Image not found" + e);
	            return "IMAGE_NOT_FOUND";
	     } catch (Exception ioe) {
	            System.out.println("Exception while reading the Image " + ioe);
	            return "IMAGE_NOT_FOUND";
	     }
		return imageDataString; 
	}
	
	 /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }
 
    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}
