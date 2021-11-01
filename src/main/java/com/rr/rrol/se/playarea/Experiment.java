package com.rr.rrol.se.playarea;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.rr.rrol.game.objects.Parser;
import com.rr.rrol.game.render.Map;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.reader.BinaryReader;

public class Experiment {

	public static void main(String[] args) throws Exception {
		String filename ="C:\\Users\\robert\\AppData\\Local\\arr\\Saved\\SaveGames\\slot1.sav";
//		String filename ="C:\\Users\\robert\\Downloads\\slot7_lottie.sav";
//		String filename ="C:\\Users\\robert\\Downloads\\slot1_avarus.sav";
		InputStream fis = new FileInputStream(filename);		
		BinaryReader reader = new BinaryReader(fis);
		Save save = new Save(reader);
		
//		List<Property> trees = save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.RemovedVegetationAssetsArray)).collect(Collectors.toList());
		
		Parser p = new Parser(save);
		p.getPlayers();
		
		p.getStrProperty(PropertyName.SaveGameDate).getValue();
		
		Map map = new Map();
		BufferedImage image = map.getImage(save);
		map.writeImageToDisk(image, new File("C:\\Users\\robert\\Downloads\\map.gif"));
		
		System.out.println();
	}
	
}
