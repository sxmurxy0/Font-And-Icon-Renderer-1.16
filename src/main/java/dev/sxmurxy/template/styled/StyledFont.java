package dev.sxmurxy.template.styled;

import java.awt.Font;
import java.util.Locale;

import dev.sxmurxy.template.common.AbstractFont;
import dev.sxmurxy.template.common.Lang;
import net.minecraft.util.math.vector.Matrix4f;

public final class StyledFont {
	
	private final GlyphPage regular, bold, italic, boldItalic;
	
	public StyledFont(String fileName, int size, float stretching, float spacing, float lifting, Lang lang) {
		int[] codes = lang.getCharCodes();
		char[] chars = new char[(codes[1] - codes[0] + codes[3] - codes[2])];
		
		int c = 0;
		for (int d = 0; d <= 2; d += 2) {
			for(int i = codes[d]; i <= codes[d + 1] - 1; i++) {
				chars[c] = (char) i;
				c++;
			}
		}
		
		this.regular = new GlyphPage(AbstractFont.getFont(fileName, Font.PLAIN, size), chars, stretching, spacing, lifting);
		this.bold = new GlyphPage(AbstractFont.getFont(fileName, Font.BOLD, size), chars, stretching, spacing, lifting);
		this.italic = new GlyphPage(AbstractFont.getFont(fileName, Font.ITALIC, size), chars, stretching, spacing, lifting);
		this.boldItalic = new GlyphPage(AbstractFont.getFont(fileName, Font.BOLD | Font.ITALIC, size), chars, stretching, spacing, lifting);
	}
	
	public float renderGlyph(Matrix4f matrix, char c, float x, float y, boolean bold, boolean italic, float red, float green, float blue, float alpha) {
		return getGlyphPage(bold, italic).renderGlyph(matrix, c, x, y, red, green, blue, alpha);
	}
	
	public float getWidth(String text) {
		boolean bold = false;
		boolean italic = false;
		float width = 0.0f;
		
		for(int i = 0; i < text.length(); i++) {
			char c0 = text.charAt(i);

			if (c0 == 167 && i + 1 < text.length() &&
					 StyledFontRenderer.STYLE_CODES.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1)) != -1) {
				int i1 = StyledFontRenderer.STYLE_CODES.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

				if(i1 < 16) {
					bold = false;
					italic = false;
				} else if(i1 == 17) {
					bold = true;
				} else if(i1 == 20) {
					italic = true;
				} else if(i1 == 21) {
					bold = false;
					italic = false;
				}

				i ++;
			} else {
				width += getGlyphPage(bold, italic).getWidth(c0) + regular.getSpacing();
			}
		}
		
		return (width - regular.getSpacing()) / 2.0f;
	}
	
	private GlyphPage getGlyphPage(boolean boldStyle, boolean italicStyle) {
		if(boldStyle && italicStyle)
			return boldItalic;
		else if(boldStyle)
			return bold;
		else if(italicStyle)
			return italic;
		else
			return regular;
	}
	
	public String getFontName() {
		return regular.getFontName();
	}
	
	public float getFontHeight() {
		return regular.getFontHeight();
	}
	
	public float getStretching() {
		return regular.getStretching();
	}
	
	public float getSpacing() {
		return regular.getSpacing();
	}
	
	public float getLifting() {
		return regular.getLifting();
	}
	
}
