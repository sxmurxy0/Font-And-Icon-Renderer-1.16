package dev.sxmurxy.template.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Locale;

import dev.sxmurxy.template.TemplateMod;
import net.minecraft.util.math.vector.Matrix4f;

public class CustomFont {
	
	private GlyphPage regular, bold, italic, boldItalic;
	private float fontHeight, stretching, spacing, lifting;
	private String fontName;
	
	public enum Lang {
		ENG(new int[] {31, 126, 0, 0}),
		RU(new int[] {1024, 1105, 0, 0}),
		BOTH(new int[] {31, 126, 1024, 1105});
		
		private int[] charCodes;
		
		private Lang(int[] charCodes) {
			this.charCodes = charCodes;
		}
		
		public int[] getCharCodes() {
			return charCodes;
		}
	}
	
	public CustomFont(String fileName, int size, Lang lang, boolean fractionalMetrics, float stretching, float spacing, float lifting) {
		String path = "/assets/template/font/".concat(fileName);
		Font regularFont = null;
		Font boldFont = null;
		Font italicFont = null;
		Font boldItalicFont = null;
		
		try {
			regularFont = Font.createFont(Font.TRUETYPE_FONT, TemplateMod.class.getResourceAsStream(path))
				.deriveFont(Font.PLAIN, size);
			boldFont = Font.createFont(Font.TRUETYPE_FONT, TemplateMod.class.getResourceAsStream(path))
					.deriveFont(Font.BOLD, size);
			italicFont = Font.createFont(Font.TRUETYPE_FONT, TemplateMod.class.getResourceAsStream(path))
					.deriveFont(Font.ITALIC, size);
			boldItalicFont = Font.createFont(Font.TRUETYPE_FONT, TemplateMod.class.getResourceAsStream(path))
					.deriveFont(Font.BOLD | Font.ITALIC, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		this.stretching = stretching;
		this.spacing = spacing;
		this.lifting = lifting;
		fontHeight = regularFont.getSize2D() / 2;
		fontName = regularFont.getFontName(Locale.ENGLISH);
		
		int[] codes = lang.getCharCodes();
		char[] chars = new char[(codes[1] - codes[0] + codes[3] - codes[2])];
		
		int c = 0;
		for(int i = codes[0] + 1; i <= codes[1]; i++) {
			chars[c] = (char)i;
			c++;
		}
		for(int i = codes[2] + 1; i <= codes[3]; i++) {
			chars[c] = (char)i;
			c++;
		}
		
		regular = new GlyphPage(regularFont, chars, fractionalMetrics);
		bold = new GlyphPage(boldFont, chars, fractionalMetrics);
		italic = new GlyphPage(italicFont, chars, fractionalMetrics);
		boldItalic = new GlyphPage(boldItalicFont, chars, fractionalMetrics);
	}
	
	public float renderChar(Matrix4f matrix, char c, float x, float y, boolean boldStyle, boolean italicStyle, float red, float green, float blue, float alpha) {
		GlyphPage current = getCurrentGP(boldStyle, italicStyle);
		float w = current.renderChar(matrix, c, x, y, red, green, blue, alpha, stretching) + spacing;
		return w;
	}
	
	public float getWidth(String text) {
		boolean boldStyle = false;
		boolean italicStyle = false;
		float width = 0.0f;
		
		for(int i = 0; i < text.length(); i++) {
			char c0 = text.charAt(i);

			if (c0 == 167 && i + 1 < text.length() &&
					 FontRenderer.styleCodes.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1)) != -1) {
				int i1 = FontRenderer.styleCodes.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

				if(i1 < 16) {
					boldStyle = false;
					italicStyle = false;
				} else if(i1 == 17) {
					boldStyle = true;
				} else if(i1 == 20) {
					italicStyle = true;
				} else if(i1 == 21) {
					boldStyle = false;
					italicStyle = false;
				}

				i ++;
			} else {
				GlyphPage current = getCurrentGP(boldStyle, italicStyle);
				width += current.getWidth(c0) + spacing + stretching;
			}
		}
		return (width - spacing) / 2.0f;
	}
	
	public GlyphPage getCurrentGP(boolean boldStyle, boolean italicStyle) {
		GlyphPage current;
		if(boldStyle && italicStyle)
			current = boldItalic;
		else if(boldStyle)
			current = bold;
		else if(italicStyle)
			current = italic;
		else
			current = regular;
		return current;
	}
	
	public float getLifting() {
		return lifting;
	}
	
	public float getSpacing() {
		return spacing;
	}
	
	public String getFontName() {
		return fontName;
	}
	
	public float getFontHeight() {
		return fontHeight;
	}
	
}
