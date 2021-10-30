package com.rr.rrol.game.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.ArrayProperty;
import com.rr.rrol.se.model.property.Property;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.item.FloatItem;
import com.rr.rrol.se.model.property.item.IntItem;
import com.rr.rrol.se.model.property.item.StrItem;
import com.rr.rrol.se.model.property.item.StructItem;

public class Parser {

	private Save save;
	
	public Parser(Save save) {
		this.save = save;
	}
	public List<Player> getPlayers() {
		ArrayProperty names = (ArrayProperty)save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.PlayerNameArray)).findFirst().get();
		ArrayProperty locations = (ArrayProperty)save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.PlayerLocationArray)).findFirst().get();
		ArrayProperty money = (ArrayProperty)save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.PlayerMoneyArray)).findFirst().get();
		ArrayProperty rotation = (ArrayProperty)save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.PlayerRotationArray)).findFirst().get();
		ArrayProperty xps = (ArrayProperty)save.getProperties().stream().filter(p -> p.getPropertyName().equals(PropertyName.PlayerXPArray)).findFirst().get();
		
		List<Player> players = new ArrayList<>();
		
		for(int i=0; i<names.getItems().size(); i++) {
			String name = ((StrItem)names.getItems().get(i)).getValue();
			float x = ((StructItem)locations.getItems().get(i)).getX();
			float y = ((StructItem)locations.getItems().get(i)).getY();
			float z = ((StructItem)locations.getItems().get(i)).getZ();
			float m = ((FloatItem)money.getItems().get(i)).getValue();
			float r = ((FloatItem)rotation.getItems().get(i)).getValue();
			int xp = ((IntItem)xps.getItems().get(i)).getValue();
			Player p = new Player(name, m, xp, x, y, z, r);
			players.add(p);
		}
		
		return players;
	}
	
}
