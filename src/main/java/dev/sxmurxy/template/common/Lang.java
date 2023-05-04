package dev.sxmurxy.template.common;

public enum Lang {
	
	ENG(new int[] {31, 127, 0, 0}),
	ENG_RU(new int[] {31, 127, 1024, 1106});
	
	private int[] charCodes;
	
	private Lang(int[] charCodes) {
		this.charCodes = charCodes;
	}
	
	public int[] getCharCodes() {
		return charCodes;
	}
	
}
