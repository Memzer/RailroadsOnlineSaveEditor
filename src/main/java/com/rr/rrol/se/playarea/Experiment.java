package com.rr.rrol.se.playarea;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.rr.rrol.game.objects.Parser;
import com.rr.rrol.game.objects.Player;
import com.rr.rrol.game.render.Map;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.reader.BinaryReader;

public class Experiment {

	public static void main(String[] args) throws Exception {
		String edited ="C:\\Users\\robert\\Downloads\\edited.sav";
		String filename ="C:\\Users\\robert\\Downloads\\slot1.sav";
//		String filename ="C:\\Users\\robert\\Downloads\\slot7_lottie.sav";
//		String filename ="C:\\Users\\robert\\Downloads\\slot1_avarus.sav";
		InputStream fis = new FileInputStream(filename);		
		BinaryReader reader = new BinaryReader(fis);
		Save save = new Save(reader);
		
		byte[] fileContent = Files.readAllBytes(Paths.get(filename));
		
		System.out.println(Player.getPlayerNames(save));
		
		Player p = Player.getInstance(save, "Mr Bonkers");
		p.setMoney(15000);
		
		ByteArrayOutputStream os = save.toByteArrayOutputStream();
		os.flush();
		
		OutputStream fos = new FileOutputStream(edited);
		os.writeTo(fos);
		
	    byte[] target = os.toByteArray();
	    
	    for(int i=0; i<target.length; i++) {
	    	if(fileContent[i] != target[i]) {
	    		System.out.print("\u001b[1;31m");
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
		
//		List<Property> trees = save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.RemovedVegetationAssetsArray)).collect(Collectors.toList());
		
//		Parser p = new Parser(save);
//		p.getPlayers();
//		
//		p.getStrProperty(PropertyName.SaveGameDate).getValue();
//		
//		Map map = new Map();
//		BufferedImage image = map.getImage(save);
//		map.writeImageToDisk(image, new File("C:\\Users\\robert\\Downloads\\map.gif"));
//		
//		System.out.println();
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
