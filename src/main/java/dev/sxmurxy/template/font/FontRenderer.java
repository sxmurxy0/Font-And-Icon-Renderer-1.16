package dev.sxmurxy.template.font;

import java.awt.Color;
import java.util.Locale;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

public class FontRenderer {
	
	public static final Tessellator TESSELLATOR = Tessellator.getInstance();
	public static final BufferBuilder BUFFER_BUILDER = TESSELLATOR.getBuilder();
	public static final String styleCodes = "0123456789abcdefklmnor";
	public static final int[] colorCodes = new int[32];
	
	static {
		for (int i = 0; i < 32; ++i) {
			int j = (i >> 3 & 1) * 85;
			int k = (i >> 2 & 1) * 170 + j;
			int l = (i >> 1 & 1) * 170 + j;
			int i1 = (i & 1) * 170 + j;

			if (i == 6) {
				k += 85;
			}

			if (i >= 16) {
				k /= 4;
				l /= 4;
				i1 /= 4;
			}

			colorCodes[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
		}
	}
	
	public static void drawString(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color) {
		renderString(matrices, font, text, x, y, false, color);
	}
	
	public static void drawCenteredXString(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color) {
		renderString(matrices, font, text, x - font.getWidth(text) / 2, y, false, color);
	}

	public static void drawShadowedString(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color) {
		renderStringWithShadow(matrices, font, text, x, y, color, getShadowColor(color));
	}
	
	public static void drawShadowedCenteredXString(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color) {
		renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2, y, color, getShadowColor(color));
	}
	
	public static void drawShadowedString(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color, Color shadowColor) {
		renderStringWithShadow(matrices, font, text, x, y, color, shadowColor);
	}
	
	public static void drawShadowedCenteredXString(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color, Color shadowColor) {
		renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2, y, color, shadowColor);
	}
	
	private static void renderStringWithShadow(MatrixStack matrices, CustomFont font, String text, double x, double y, Color color, Color shadowColor) {
			y -= 1f;
			renderString(matrices, font, text, x + 1.0f, y + 1.0f, true, shadowColor);
			renderString(matrices, font, text, x, y, false, color);
	}

	private static void renderString(MatrixStack matrices, CustomFont font, String text, double x, double y, boolean shadow, Color color) {
		y -= font.getLifting();
		x -= 1;
		
		float posX = (float)x * 2.0f;
		float posY = (float)y * 2.0f;
		float red = color.getRed() / 255.0f;
		float green = color.getGreen() / 255.0f;
		float blue = color.getBlue() / 255.0f;
		float alpha = color.getAlpha() / 255.0f;
		boolean boldStyle = false;
		boolean italicStyle = false;
		boolean strikethroughStyle = false;
		boolean underlineStyle = false;
		
		GlStateManager._enableBlend();
		GlStateManager._blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		matrices.pushPose();
		matrices.scale(0.5f, 0.5f, 1f);
		Matrix4f matrix = matrices.last().pose();
		
		for(int i = 0; i < text.length(); i++) {
			char c0 = text.charAt(i);
			
			if (c0 == 167 && i + 1 < text.length() &&
					styleCodes.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1)) != -1) {
				int i1 = styleCodes.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

				if (i1 < 16) {
					boldStyle = false;
					strikethroughStyle = false;
					underlineStyle = false;
					italicStyle = false;

					if(shadow) {
						i1 += 16;
					}
					
					int j1 = colorCodes[i1];

					red = (float) (j1 >> 16 & 255) / 255.0F;
					green = (float) (j1 >> 8 & 255) / 255.0F;
					blue = (float) (j1 & 255) / 255.0F;
					alpha = 1f;
				} else if (i1 == 16) {
				} else if (i1 == 17) {
					boldStyle = true;
				} else if (i1 == 18) {
					strikethroughStyle = true;
				} else if (i1 == 19) {
					underlineStyle = true;
				} else if (i1 == 20) {
					italicStyle = true;
				} else if(i1 == 21) {
					boldStyle = false;
					strikethroughStyle = false;
					underlineStyle = false;
					italicStyle = false;
				}

				i++;
			} else {
				float f = font.renderChar(matrix, c0, posX, posY, boldStyle, italicStyle, red, green, blue, alpha);
				
				if(strikethroughStyle) {
					float h = font.getFontHeight() + 2;
					GlStateManager._disableTexture();
					BUFFER_BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR);
					BUFFER_BUILDER.vertex(matrix, posX, posY + h + 3f, 0).color(red, green, blue, alpha).endVertex();
					BUFFER_BUILDER.vertex(matrix, posX + f, posY + h + 3f, 0).color(red, green, blue, alpha).endVertex();
					BUFFER_BUILDER.vertex(matrix, posX + f, posY + h, 0).color(red, green, blue, alpha).endVertex();
					BUFFER_BUILDER.vertex(matrix, posX, posY + h, 0).color(red, green, blue, alpha).endVertex();
					TESSELLATOR.end();
					GlStateManager._enableTexture();
				}

				if(underlineStyle) {
					float y1 = posY + font.getFontHeight() * 2 + 2;
					GlStateManager._disableTexture();
					BUFFER_BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR);
					BUFFER_BUILDER.vertex(matrix, posX, y1 + 4, 0).color(red, green, blue, alpha).endVertex();
					BUFFER_BUILDER.vertex(matrix, posX + f, y1 + 4, 0).color(red, green, blue, alpha).endVertex();
					BUFFER_BUILDER.vertex(matrix, posX + f, y1 + 2, 0).color(red, green, blue, alpha).endVertex();
					BUFFER_BUILDER.vertex(matrix, posX, y1 + 2, 0).color(red, green, blue, alpha).endVertex();
					TESSELLATOR.end();
					GlStateManager._enableTexture();
				}
				posX += f;
			}
		}
		
		matrices.popPose();
		GlStateManager._disableBlend();
	}
	
	public static Color getShadowColor(Color color) {
		return new Color((color.getRGB() & 16579836) >> 2 | color.getRGB()  & -16777216);
	}
	
}
