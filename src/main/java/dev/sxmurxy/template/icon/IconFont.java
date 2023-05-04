package dev.sxmurxy.template.icon;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.sxmurxy.template.common.AbstractFont;

public final class IconFont extends AbstractFont {

	public IconFont(String fileName, int size, char... chars) {
		Font font = AbstractFont.getFont(fileName, Font.PLAIN, size);

		FontRenderContext fontRenderContext = new FontRenderContext(font.getTransform(), true, true);
		double maxWidth = 0;
		double maxHeight = 0;

		for (char c : chars) {
			Rectangle2D bound = font.getStringBounds(Character.toString(c), fontRenderContext);
			maxWidth = Math.max(maxWidth, bound.getWidth());
			maxHeight = Math.max(maxHeight, bound.getHeight());
		}

		this.fontName = font.getFontName(Locale.ENGLISH);
		this.fontHeight = (float)(maxHeight / 2.0f);
		this.imgHeight = (int)maxHeight + 4;
		this.imgWidth = ((int)maxWidth + 4) * chars.length;
		
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = setupGraphics(image, font);

		FontMetrics fontMetrics = graphics.getFontMetrics();
		int posX = 2;
		int posY = 2;

		for (char c : chars) {
			Glyph glyph = new Glyph();
			Rectangle2D bounds = fontMetrics.getStringBounds(Character.toString(c), graphics);
			glyph.width = (int)bounds.getWidth() + 1;
			glyph.height = (int)bounds.getHeight() + 2;

			glyph.x = posX;
			glyph.y = posY;

			graphics.drawString(Character.toString(c), posX, posY + fontMetrics.getAscent());

			glyphs.put(c, glyph);
			
			posX += glyph.width + 2;
		}
		
		RenderSystem.recordRenderCall(() -> setTexture(image));
	}
	
	public float getStretching() {
		return 0.0f;
	}
	
	public float getSpacing() {
		return 0.0f;
	}
	
	public float getLifting() {
		return fontHeight;
	}
	
}
