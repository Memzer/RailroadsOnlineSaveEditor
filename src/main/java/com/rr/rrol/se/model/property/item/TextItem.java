package com.rr.rrol.se.model.property.item;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rr.rrol.se.reader.BinaryReader;
import com.rr.rrol.se.reader.BinaryWriter;

public class TextItem extends Item<String> {

	private String value;
	private int opt;
	private byte ff;
	private int i1;
	
	private Integer l2;
	private Byte term;
	private String hash;
	private String format;
	
	private List<TextRow> rows;
	
	public TextItem(BinaryReader reader, ItemType itemType, ItemName itemName) throws Exception {
		super(reader, itemType, itemName);
		System.out.print(Integer.toHexString(reader.position())+"    ");
		
		opt = reader.readInt32();
		ff = reader.get();
		i1 = reader.readInt32();
		
		if(opt == 1) {
			l2 = reader.readInt32();
			term = reader.get();
			hash = reader.readString();
			format = reader.readString();
			int numRows = reader.readInt32();
			
			rows = new ArrayList<>();
			for(int i=0; i<numRows; i++) {
				rows.add(new TextRow(reader, i==0));
			}
			
			System.out.println();
			return;
		} else if(opt == 2) {
			value = reader.readString();
			System.out.println(value);
		} else {
			System.out.println();
			return;
		}
	}

	@Override
	public void toByteArrayOutputStream(ByteArrayOutputStream os) {
		BinaryWriter.int32(os, opt);
		BinaryWriter.int8(os, ff);
		BinaryWriter.int32(os, i1);
		if(opt == 2 && value != null) {
			BinaryWriter.string(os, value);
		}
		if(opt == 1) {
			BinaryWriter.int32(os, l2);
			BinaryWriter.int8(os, term);
			BinaryWriter.string(os, hash);
			BinaryWriter.string(os, format);
			BinaryWriter.int32(os, rows.size());
			for(TextRow row : rows) {
				row.toByteArrayOutputStream(os);
			}
		}
	}

	@Override
	public String getValue() {
		if(value != null) {
			return value;
		}
		if(rows.size() > 0) {
			StringBuilder sb = new StringBuilder();
			Iterator<TextRow> i = rows.iterator();
			while(i.hasNext()) {
				TextRow row = i.next();
				sb.append(row.getValue());
				if(i.hasNext()) {
					sb.append(format);
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	class TextRow {
		private String rowId;
		private byte b;
		private int opt;
		private byte ff;
		private Integer i;
		private String value;
		
		public TextRow(BinaryReader reader, boolean first) {
			rowId = reader.readString();
			b = reader.get();//Expect always == 4;
			
			opt = reader.readInt32();//Expect always == 2
			ff = reader.get();//Expect always == FF (-1)
			if(first) {
				i = reader.readInt32();
			}
			value = reader.readString();
			System.out.print("    "+value);
		}
		
		public void toByteArrayOutputStream(ByteArrayOutputStream os) {
			System.out.println(Integer.toHexString(os.size()));
			BinaryWriter.string(os, rowId);
			BinaryWriter.int8(os, b);
			BinaryWriter.int32(os, opt);
			BinaryWriter.int8(os, ff);
			if(i != null) {
				BinaryWriter.int32(os, i);
			}
			BinaryWriter.string(os, value);
		}

		public String getRowId() {
			return rowId;
		}

		public byte getB() {
			return b;
		}

		public int getOpt() {
			return opt;
		}

		public byte getFf() {
			return ff;
		}

		public Integer getI() {
			return i;
		}

		public String getValue() {
			return value;
		}
	}
}
