package dev.sxmurxy.template.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

public class GlyphPage {
	
	private Map<Character, Glyph> glyphs = new HashMap<>();
	private int italicSpacing, texId, imgSize;

	public GlyphPage(Font font, char[] chars, boolean fractionalMetrics) {
		FontRenderContext fontRenderContext = new FontRenderContext(font.getTransform(), true, fractionalMetrics);
		double maxWidth = 0;
		double maxHeight = 0;
		
		for(char c : chars) {
			Rectangle2D bound = font.getStringBounds(Character.toString(c), fontRenderContext);
			maxWidth = Math.max(maxWidth, bound.getWidth());
			maxHeight = Math.max(maxHeight, bound.getHeight());
		}
		
		italicSpacing = font.isItalic() ? 5 : 0;
		imgSize = (int)Math.ceil(Math.sqrt((maxHeight + 2) * (maxWidth + italicSpacing) * chars.length));
		
		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setFont(font);
		graphics.setColor(new Color(255, 255, 255, 0));
		graphics.fillRect(0, 0, imgSize, imgSize);
		graphics.setColor(Color.WHITE);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, 
				fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,	RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int posX = 1;
		int posY = 2;
		
		for(char c : chars) {
			Glyph glyph = new Glyph();
			Rectangle2D bounds = fontMetrics.getStringBounds(Character.toString(c), graphics);
			glyph.width = (int)bounds.getWidth() + italicSpacing;
			glyph.height = (int)bounds.getHeight() + 2;

			if(posX + glyph.width >= imgSize) {
				posX = 1;
				posY += maxHeight + fontMetrics.getDescent() + 1;
			}

			glyph.x = posX;
			glyph.y = posY;
			
			graphics.drawString(Character.toString(c), posX, posY + fontMetrics.getAscent());
			
			posX += glyph.width + 6;
			glyphs.put(c, glyph);
		}
		
		try {
			texId = TextureHelper.loadTexture(bufferedImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float renderChar(Matrix4f matrix, char c, float x, float y, float red, float green, float blue, float alpha, float f) {
		Glyph glyph = glyphs.get(c);
		
		if(glyph == null) 
			return 0;
		
		GlStateManager._bindTexture(texId);
		float pageX = glyph.x / (float)imgSize;
		float pageY = glyph.y / (float)imgSize;
		float pageWidth = glyph.width / (float)imgSize;
		float pageHeight = glyph.height / (float)imgSize;
		float width = glyph.width + f;
		float height = glyph.height;
		
		FontRenderer.BUFFER_BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
		FontRenderer.BUFFER_BUILDER.vertex(matrix, x, y + height, 0).color(red, green, blue, alpha)
				.uv(pageX, pageY + pageHeight).endVertex();
		FontRenderer.BUFFER_BUILDER.vertex(matrix, x + width, y + height, 0).color(red, green, blue, alpha)
				.uv(pageX + pageWidth, pageY + pageHeight).endVertex();
		FontRenderer.BUFFER_BUILDER.vertex(matrix, x + width, y, 0).color(red, green, blue, alpha)
				.uv(pageX + pageWidth, pageY).endVertex();
		FontRenderer.BUFFER_BUILDER.vertex(matrix, x, y, 0).color(red, green, blue, alpha)
				.uv(pageX, pageY).endVertex();
		FontRenderer.TESSELLATOR.end();
		
		GlStateManager._bindTexture(0);
		
		return width - italicSpacing;
	}
	
	public float getWidth(char c) {
		Glyph glyph = glyphs.get(c);
		return glyph == null ? 0 : glyph.width - italicSpacing;
	}
	
	private static class Glyph {
		private int x;
		private int y;
		private int width;
		private int height;
	}
	
}
	
