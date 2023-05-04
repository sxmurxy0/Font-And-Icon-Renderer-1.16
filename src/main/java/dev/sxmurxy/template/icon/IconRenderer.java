package dev.sxmurxy.template.icon;

import java.awt.Color;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.platform.GlStateManager;

import dev.sxmurxy.template.Wrapper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public final class IconRenderer implements Wrapper {

	public static float drawIcon(MatrixStack matrices, IconFont font, char c, double x, double y, Color color) {
		return renderIcon(matrices, font, c, x, y, color);
	}
	
	public static float drawCenteredXIcon(MatrixStack matrices, IconFont font, char c, double x, double y, Color color) {
		return renderIcon(matrices, font, c, x - font.getWidth(c) / 2.0f, y, color);
	}

	public static float drawCenteredYIcon(MatrixStack matrices, IconFont font, char c, double x, double y, Color color) {
		return renderIcon(matrices, font, c, x, y + font.getLifting() / 2.0f + 0.5f, color);
	}
	
	public static float drawCenteredXYIcon(MatrixStack matrices, IconFont font, char c, double x, double y, Color color) {
		return renderIcon(matrices, font, c, x - font.getWidth(c) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, color);
	}
	
	private static float renderIcon(MatrixStack matrices, IconFont font, char c, double x, double y, Color color) {
		y -= font.getLifting();

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
		
		float w = font.renderGlyph(matrix, c, (float) x * 2f, (float) y * 2f, red, green, blue, alpha);
		
		font.unbindTex();
		
		matrices.pop();
		GlStateManager.disableBlend();
		
		return w / 2.0f;
	}

}
