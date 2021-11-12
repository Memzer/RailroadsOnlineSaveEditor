package com.rr.rrol.se.model.property.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rr.rrol.se.io.BinaryReader;

public class TextItem extends Item<String> {

	private String value;
	private int opt;
	private byte ff;
	private int i1;
	
	private Integer i2;
	private Byte term;
	private String hash;
	private String format;
	
	private List<TextRow> rows;
	
	public TextItem(BinaryReader reader, ItemType itemType, ItemName itemName) throws Exception {
		super(reader, itemType, itemName);
//		System.out.println(Integer.toHexString(reader.position()));
		
		opt = reader.readInt32();
		ff = reader.get();
		i1 = reader.readInt32();
		
		if(opt == 1) {
			i2 = reader.readInt32();
			term = reader.get();
			hash = reader.readString();
			format = reader.readString();
			int numRows = reader.readInt32();
			
			rows = new ArrayList<>();
			for(int i=0; i<numRows; i++) {
				rows.add(new TextRow(reader, i==0));
			}
//			System.out.println(getValue());
		} else if(opt == 2) {
			value = reader.readString();
//			System.out.println(value);
		}
	}

	@Override
	public String getValue() {
		if(value != null) {
			return value;
		}
		if(rows != null && rows.size() > 0) {
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
	
	public class TextRow {
		private String rowId;
		private byte b;
		private int opt;
		private byte ff;
		private Integer i;
		private String value;
		
		public TextRow(BinaryReader reader, boolean first) {
//			System.out.println(Integer.toHexString(reader.position()));
			rowId = reader.readString();
			b = reader.get();//Expect always == 4;
			
			opt = reader.readInt32();//Expect always == 2
			ff = reader.get();//Expect always == FF (-1)
			i = reader.readInt32();
			if(i == 0) {
				reader.seek(-4);
			}
			value = reader.readString();
//			System.out.print("    "+value);
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

	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	public byte getFf() {
		return ff;
	}

	public void setFf(byte ff) {
		this.ff = ff;
	}

	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public Integer getI2() {
		return i2;
	}

	public void setI2(Integer l2) {
		this.i2 = l2;
	}

	public Byte getTerm() {
		return term;
	}

	public void setTerm(Byte term) {
		this.term = term;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<TextRow> getRows() {
		return rows;
	}

	public void setRows(List<TextRow> rows) {
		this.rows = rows;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
