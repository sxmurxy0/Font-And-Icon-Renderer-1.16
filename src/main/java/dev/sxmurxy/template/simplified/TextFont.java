package dev.sxmurxy.template.simplified;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.sxmurxy.template.common.AbstractFont;
import dev.sxmurxy.template.common.Lang;

public final class TextFont extends AbstractFont {
	
	private final float stretching, spacing, lifting;

	public TextFont(String fileName, int size, float stretching, float spacing, float lifting, Lang lang) {
		Font font = getFont(fileName, Font.PLAIN, size);
		FontRenderContext fontRenderContext = new FontRenderContext(font.getTransform(), true, true);
		
		double maxWidth = 0;
		double maxHeight = 0;

		int[] codes = lang.getCharCodes();
		char[] chars = new char[(codes[1] - codes[0] + codes[3] - codes[2])];

		int n = 0;
		for (int d = 0; d <= 2; d += 2) {
			for(int i = codes[d]; i <= codes[d + 1] - 1; i++) {
				chars[n] = (char) i;
				Rectangle2D bound = font.getStringBounds(Character.toString(chars[n]), fontRenderContext);
				maxWidth = Math.max(maxWidth, bound.getWidth());
				maxHeight = Math.max(maxHeight, bound.getHeight());
				n++;
			}
		}

		int d = (int)Math.ceil(Math.sqrt((maxHeight + 2) * (maxWidth + 2) * chars.length));
		
		this.stretching = stretching;
		this.spacing = spacing;
		this.lifting = lifting;
		this.fontName = font.getFontName(Locale.ENGLISH);
		this.fontHeight = (float)(maxHeight / 2);
		this.imgHeight = d;
		this.imgWidth = d;
		
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = setupGraphics(image, font);
		
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int posX = 1;
		int posY = 2;
		
		for (char c : chars) {
			Glyph glyph = new Glyph();
			Rectangle2D bounds = fontMetrics.getStringBounds(Character.toString(c), graphics);
			glyph.width = (int)bounds.getWidth() + 1;
			glyph.height = (int)bounds.getHeight() + 2;

			if (posX + glyph.width >= imgWidth) {
				posX = 1;
				posY += maxHeight + fontMetrics.getDescent() + 2;
			}

			glyph.x = posX;
			glyph.y = posY;

			graphics.drawString(Character.toString(c), posX, posY + fontMetrics.getAscent());

			posX += glyph.width + 4;
			glyphs.put(c, glyph);
		}
		
		RenderSystem.recordRenderCall(() -> setTexture(image));
	}
	
	public float getStretching() {
		return stretching;
	}
	
	public float getSpacing() {
		return spacing;
	}
	
	public float getLifting() {
		return fontHeight + lifting;
	}
	
}

