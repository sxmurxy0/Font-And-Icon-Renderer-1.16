package dev.sxmurxy.template.simplified;

import java.awt.Color;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.platform.GlStateManager;

import dev.sxmurxy.template.Wrapper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public final class SimplifiedFontRenderer implements Wrapper {

	public static float drawString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x, y, color);
	}
	
	public static float drawCenteredXString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x - font.getWidth(text) / 2.0f, y, color);
	}

	public static float drawCenteredYString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x, y + font.getLifting() / 2.0f + 0.5f, color);
	}
	
	public static float drawCenteredXYString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x - font.getWidth(text) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, color);
	}
	
	public static float drawShadowedString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x, y, color, getShadowColor(color));
	}
	
	public static float drawShadowedCenteredXString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y, color, getShadowColor(color));
	}
	
	public static float drawShadowedCenteredYString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x, y + font.getLifting() / 2.0f + 0.5f, color, getShadowColor(color));
	}
	
	public static float drawShadowedCenteredXYString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, color, getShadowColor(color));
	}
	
	public static float drawShadowedString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x, y, color, shadowColor);
	}
	
	public static float drawShadowedCenteredXString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y, color, shadowColor);
	}
	
	public static float drawShadowedCenteredYString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x, y + font.getLifting() / 2.0f + 0.5f, color, shadowColor);
	}
	
	public static float drawShadowedCenteredXYString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, color, shadowColor);
	}
	
	private static float renderStringWithShadow(MatrixStack matrices, TextFont font, String text, double x, double y, Color color, Color shadowColor) {
		renderString(matrices, font, text, x + 1.0F, y, shadowColor);
		return renderString(matrices, font, text, x, y -= 1.0f, color) + 1.0f;
	}

	// returns string width
	private static float renderString(MatrixStack matrices, TextFont font, String text, double x, double y, Color color) {
		y -= font.getLifting();

		float startPos = (float) x * 2f;
		float posX = startPos;
		float posY = (float) y * 2f;
		float red = color.getRed() / 255.0f;
		float green = color.getGreen() / 255.0f;
		float blue = color.getBlue() / 255.0f;
		float alpha = color.getAlpha() / 255.0f;
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		matrices.push();
		matrices.scale(0.5f, 0.5f, 1f);
		
		Matrix4f matrix = matrices.peek().getModel();
		
		font.bindTex();
		
		for (int i = 0; i < text.length(); i++) {
			posX += font.renderGlyph(matrix, text.charAt(i), posX, posY, red, green, blue, alpha);
		}
		
		font.unbindTex();
		
		matrices.pop();
		GlStateManager.disableBlend();
		
		return (posX - startPos) / 2.0f;
	}
	
	public static Color getShadowColor(Color color) {
		return new Color((color.getRGB() & 16579836) >> 2 | color.getRGB()  & -16777216);
	}

}
