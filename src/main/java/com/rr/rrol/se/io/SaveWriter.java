package com.rr.rrol.se.io;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.rr.rrol.game.objects.Player;
import com.rr.rrol.se.model.CustomFormatDataEntry;
import com.rr.rrol.se.model.Guid;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.PropertyName;
import com.rr.rrol.se.model.property.PropertyType;
import com.rr.rrol.se.model.property.item.ItemName;
import com.rr.rrol.se.model.property.item.ItemSubType;
import com.rr.rrol.se.model.property.item.ItemType;

public class SaveWriter {

	private SaveWriter() {}
	
	public static ByteArrayOutputStream write(Save save) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.writeBytes(Save.getHeader());
		BinaryWriter.int32(os, save.getSaveGameVersion());
		BinaryWriter.int32(os, save.getPackageVersion());
		BinaryWriter.int16(os, save.getMajor());
		BinaryWriter.int16(os, save.getMinor());
		BinaryWriter.int16(os, save.getPatch());
		BinaryWriter.int32(os, save.getBuild());
		BinaryWriter.string(os, save.getBuildId());
		BinaryWriter.int32(os, save.getCustomFormatVersion());
		BinaryWriter.int32(os, save.getCustomFormatCount());
		
		for(CustomFormatDataEntry entry : save.getCustomFormatData()) {
			BinaryWriter.guid(os, entry.getGuid());
			BinaryWriter.int32(os, entry.getValue());
		}
		
		BinaryWriter.string(os, save.getSaveGameType());
		
		writeSaveGameDate(os, save.getSaveGameDate());
		writePlayers(os, save.getPlayers());
		
		return os;
	}
	
	private static void writeSaveGameDate(ByteArrayOutputStream os, String date) {
		BinaryWriter.string(os, PropertyName.SaveGameDate.name());
		BinaryWriter.string(os, PropertyType.StrProperty.name());
		BinaryWriter.int64(os, 4+date.length()+1L);
		os.write(0);
		BinaryWriter.string(os, date);
	}
	
	private static void writePlayers(ByteArrayOutputStream os, List<Player> players) {
	//Player Name
		long length = 0L;
		length+=1+4;// 0x00 + int32(count)
		for(Player p : players) {
			length += 4+p.getName().length();
		}
		
		BinaryWriter.string(os, PropertyName.PlayerNameArray.name());
		BinaryWriter.string(os, PropertyType.ArrayProperty.name());
		BinaryWriter.int64(os, length);
		
		BinaryWriter.string(os, ItemType.StrProperty.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.int32(os, players.size());
		
		for(Player p : players) {
			BinaryWriter.string(os, p.getName());
		}
		
	//Player Location
		length = 0L;
		length+=ItemType.StructProperty.name().length();
		length+=1+4;//0x00 + int32(count)
		length+=PropertyName.PlayerLocationArray.name().length();
		length+=ItemType.StructProperty.name().length();
		length+=8;//int64(length)
		length+=ItemSubType.Vector.name().length();
		length+=1+16;//0x00 + guid
		length+=players.size()*12L;
		
		BinaryWriter.string(os, PropertyName.PlayerLocationArray.name());
		BinaryWriter.string(os, PropertyType.ArrayProperty.name());
		BinaryWriter.int64(os, length);
		
		BinaryWriter.string(os, ItemType.StructProperty.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.int32(os, players.size());
		
		BinaryWriter.string(os, PropertyName.PlayerLocationArray.name());
		BinaryWriter.string(os, ItemType.StructProperty.name());
		BinaryWriter.int64(os, players.size()*12L);
		BinaryWriter.string(os, ItemSubType.Vector.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.guid(os, Guid.empty());
		
		for(Player p : players) {
			BinaryWriter.float32(os, (float)p.getLocation().getX());
			BinaryWriter.float32(os, (float)p.getLocation().getY());
			BinaryWriter.float32(os, (float)p.getLocation().getZ());
		}
		
	//Player Rotation
		length = 0L;
		length+=0+4;//1+4;// 0x00 + int32(count)
		length+=players.size()*4L;
		
		BinaryWriter.string(os, PropertyName.PlayerRotationArray.name());
		BinaryWriter.string(os, PropertyType.ArrayProperty.name());
		BinaryWriter.int64(os, length);
		
		BinaryWriter.string(os, ItemType.FloatProperty.name());
		BinaryWriter.int8(os, (byte)0);
		BinaryWriter.int32(os, players.size());
		
		for(Player p : players) {
			BinaryWriter.float32(os, p.getR());
		}
	}
}
