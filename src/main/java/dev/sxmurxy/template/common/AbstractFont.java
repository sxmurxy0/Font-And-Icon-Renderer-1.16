package dev.sxmurxy.template.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.platform.GlStateManager;

import dev.sxmurxy.template.TemplateMod;
import dev.sxmurxy.template.TextureHelper;
import dev.sxmurxy.template.Wrapper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

public abstract class AbstractFont implements Wrapper {
	
	protected final Map<Character, Glyph> glyphs = new HashMap<>();
	protected int texId, imgWidth, imgHeight;
	protected float fontHeight;
	protected String fontName;
	
	public abstract float getStretching();
	
	public abstract float getSpacing();
	
	public abstract float getLifting();
	
	public float getFontHeight() {
		return fontHeight;
	}
	
	public final String getFontName() {
		return fontName;
	}
	
	protected final void setTexture(BufferedImage img) {
		texId = TextureHelper.loadTexture(img);
	}
	
	public final void bindTex() {
		GlStateManager._bindTexture(texId);
	}

	public final void unbindTex() {
		GlStateManager._bindTexture(0);
	}
	
	public static final Font getFont(String fileName, int style, int size) {
		String path = TemplateMod.FONT_DIR.concat(fileName);
		Font font = null;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, TemplateMod.class.getResourceAsStream(path))
				.deriveFont(style, size);
		} catch (FontFormatException | IOException e) {e.printStackTrace();}
		
		return font;
	}
	
	public final Graphics2D setupGraphics(BufferedImage img, Font font) {
		Graphics2D graphics = img.createGraphics();
		
		graphics.setFont(font);
		graphics.setColor(new Color(255, 255, 255, 0));
		graphics.fillRect(0, 0, imgWidth, imgHeight);
		graphics.setColor(Color.WHITE);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,	RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		return graphics;
	}
	
	// color components should be in range [0;1]
	public float renderGlyph(Matrix4f matrix, char c, float x, float y, float red, float green, float blue, float alpha) {
		Glyph glyph = glyphs.get(c);
		
		if (glyph == null) 
			return 0;
		
		float pageX = glyph.x / (float) imgWidth;
		float pageY = glyph.y / (float) imgHeight;
		float pageWidth = glyph.width / (float) imgWidth;
		float pageHeight = glyph.height / (float) imgHeight;
		float width = glyph.width + getStretching();
		float height = glyph.height;
		
		BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
		BUILDER.vertex(matrix, x, y + height, 0).color(red, green, blue, alpha)
				.uv(pageX, pageY + pageHeight).endVertex();
		BUILDER.vertex(matrix, x + width, y + height, 0).color(red, green, blue, alpha)
				.uv(pageX + pageWidth, pageY + pageHeight).endVertex();
		BUILDER.vertex(matrix, x + width, y, 0).color(red, green, blue, alpha)
				.uv(pageX + pageWidth, pageY).endVertex();
		BUILDER.vertex(matrix, x, y, 0).color(red, green, blue, alpha)
				.uv(pageX, pageY).endVertex();
		TESSELLATOR.end();
		
		return width + getSpacing();
	}
	
	public float getWidth(char ch) {
		return glyphs.get(ch).width + getStretching();
	}
	
	public float getWidth(String text) {
		float width = 0.0f;
		float sp = getSpacing();
		for (int i = 0; i < text.length(); i++) {
			width += getWidth(text.charAt(i)) + sp;
		}
		
		return (width - sp) / 2f;
	}
	
	public class Glyph {
		
		public int x;
		public int y;
		public int width;
		public int height;
		
	}

}
