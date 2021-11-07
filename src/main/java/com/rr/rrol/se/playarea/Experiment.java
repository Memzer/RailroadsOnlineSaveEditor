package com.rr.rrol.se.playarea;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.rr.rrol.game.render.MapRenderer;
import com.rr.rrol.se.io.SaveReader;
import com.rr.rrol.se.io.SaveWriter;
import com.rr.rrol.se.model.Save;

public class Experiment {

	public static void main(String[] args) throws Exception {
//		String filename ="C:\\Users\\robert\\Downloads\\slot1.sav";
//		String filename ="C:\\Users\\robert\\Downloads\\slot7_lottie.sav";
		String filename ="C:\\Users\\robert\\Downloads\\slot1_avarus.sav";
		InputStream fis = new FileInputStream(filename);
		
		Save save = SaveReader.read(fis);		

//		String edited ="C:\\Users\\robert\\Downloads\\edited.sav";
//		testWriting(edited, filename, save);

		MapRenderer map = new MapRenderer();
		BufferedImage image = map.getImage(save);
		map.writeImageToDisk(image, new File("C:\\Users\\robert\\Downloads\\map.gif"));
		
		System.out.println();
	}

	public static void testWriting(String edited, String filename, Save save) throws IOException, FileNotFoundException {
		byte[] fileContent = Files.readAllBytes(Paths.get(filename));
		
//		System.out.println(Player.getPlayerNames(save));
		
//		Player p = Player.getInstance(save, "Mr Bonkers");
//		p.setMoney(15000);
		
		ByteArrayOutputStream os = SaveWriter.write(save);
		os.flush();
		
		OutputStream fos = new FileOutputStream(edited);
		os.writeTo(fos);
		
	    byte[] target = os.toByteArray();
	    
	    for(int i=0; i<target.length; i++) {
	    	if(fileContent[i] != target[i]) {
	    		System.out.print("\u001b[41m");
	    	}
	    	if(i % 16 == 0) {
	    		System.out.print(padToLength(8, Integer.toHexString(i))+"  ");
	    	}
	    	System.out.print(padToLength(2,Integer.toHexString(target[i]))+" ");
	    	if(fileContent[i] != target[i]) {
	    		System.out.print("\u001b[m");
	    	}
	    	if((i+1) % 16 == 0 || i == target.length-1) {
	    		System.out.println();
	    	}
	    }
	}
	
	private static String padToLength(int length, String hex) {
		while(hex.length() < length) {
			hex = "0"+hex;
		}
		while(hex.length() > length) {
			hex = hex.substring(1);
		}
		return hex;
	}
	
}
