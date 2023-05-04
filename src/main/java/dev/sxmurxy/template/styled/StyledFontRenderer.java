package dev.sxmurxy.template.styled;

import java.awt.Color;
import java.util.Locale;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;

import dev.sxmurxy.template.Wrapper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;

public final class StyledFontRenderer implements Wrapper {
	
	public static final String STYLE_CODES = "0123456789abcdefklmnor";
	public static final int[] COLOR_CODES = new int[32];
	
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

			COLOR_CODES[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
		}
	}
	
	public static float drawString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x, y, false, color);
	}
	
	public static float drawCenteredXString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x - font.getWidth(text) / 2.0f, y, false, color);
	}
	
	public static float drawCenteredYString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x, y + font.getLifting() / 2.0f + 0.5f, false, color);
	}
	
	public static float drawCenteredXYString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderString(matrices, font, text, x - font.getWidth(text) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, false, color);
	}

	public static float drawShadowedString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x, y, color, getShadowColor(color));
	}
	
	public static float drawShadowedCenteredXString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y, color, getShadowColor(color));
	}
	
	public static float drawShadowedCenteredYString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x, y + font.getLifting() / 2.0f + 0.5f, color, getShadowColor(color));
	}
	
	public static float drawShadowedCenteredXYString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, color, getShadowColor(color));
	}
	
	public static float drawShadowedString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x, y, color, shadowColor);
	}
	
	public static float drawShadowedCenteredXString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y, color, shadowColor);
	}
	
	public static float drawShadowedCenteredYString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x, y + font.getLifting() / 2.0f + 0.5f, color, shadowColor);
	}

	public static float drawShadowedCenteredXYString(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color, Color shadowColor) {
		return renderStringWithShadow(matrices, font, text, x - font.getWidth(text) / 2.0f, y + font.getLifting() / 2.0f + 0.5f, color, shadowColor);
	}

	private static float renderStringWithShadow(MatrixStack matrices, StyledFont font, String text, double x, double y, Color color, Color shadowColor) {
		renderString(matrices, font, text, x + 1.0f, y, true, shadowColor);
		return renderString(matrices, font, text, x, y - 1.0f, false, color) + 1.0f;
	}

	// returns string width
	private static float renderString(MatrixStack matrices, StyledFont font, String text, double x, double y, boolean shadow, Color color) {
		y -= font.getLifting();

		float startPos = (float) x * 2.0f;
		float posX = startPos;
		float posY = (float) y * 2.0f;
		float red = color.getRed() / 255.0f;
		float green = color.getGreen() / 255.0f;
		float blue = color.getBlue() / 255.0f;
		float alpha = color.getAlpha() / 255.0f;
		boolean bold = false;
		boolean italic = false;
		boolean strikethrough = false;
		boolean underline = false;
		
		GlStateManager._enableBlend();
		GlStateManager._blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		matrices.pushPose();
		matrices.scale(0.5f, 0.5f, 1f);
		
		Matrix4f matrix = matrices.last().pose();
		
		for(int i = 0; i < text.length(); i++) {
			char c0 = text.charAt(i);
			
			if (c0 == 167 && i + 1 < text.length() &&
					STYLE_CODES.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1)) != -1) {
				int i1 = STYLE_CODES.indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));

				if (i1 < 16) {
					bold = false;
					strikethrough = false;
					underline = false;
					italic = false;

					if(shadow) {
						i1 += 16;
					}
					
					int j1 = COLOR_CODES[i1];

					red = (float) (j1 >> 16 & 255) / 255.0F;
					green = (float) (j1 >> 8 & 255) / 255.0F;
					blue = (float) (j1 & 255) / 255.0F;
					alpha = 1f;
				} else if (i1 == 16) {
				} else if (i1 == 17) {
					bold = true;
				} else if (i1 == 18) {
					strikethrough = true;
				} else if (i1 == 19) {
					underline = true;
				} else if (i1 == 20) {
					italic = true;
				} else if(i1 == 21) {
					bold = false;
					strikethrough = false;
					underline = false;
					italic = false;
				}

				i++;
			} else {
				float f = font.renderGlyph(matrix, c0, posX, posY, bold, italic, red, green, blue, alpha);
				
				if(strikethrough) {
					float h = font.getLifting() + 2;
					GlStateManager._disableTexture();
					
					BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR);
					BUILDER.vertex(matrix, posX, posY + h + 3, 0).color(red, green, blue, alpha).endVertex();
					BUILDER.vertex(matrix, posX + f, posY + h + 3, 0).color(red, green, blue, alpha).endVertex();
					BUILDER.vertex(matrix, posX + f, posY + h, 0).color(red, green, blue, alpha).endVertex();
					BUILDER.vertex(matrix, posX, posY + h, 0).color(red, green, blue, alpha).endVertex();
					TESSELLATOR.end();
					
					GlStateManager._enableTexture();
				}

				if(underline) {
					float y1 = posY + font.getLifting() * 2.0f - 4;
					GlStateManager._disableTexture();
					
					BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR);
					BUILDER.vertex(matrix, posX, y1 + 4, 0).color(red, green, blue, alpha).endVertex();
					BUILDER.vertex(matrix, posX + f, y1 + 4, 0).color(red, green, blue, alpha).endVertex();
					BUILDER.vertex(matrix, posX + f, y1 + 2, 0).color(red, green, blue, alpha).endVertex();
					BUILDER.vertex(matrix, posX, y1 + 2, 0).color(red, green, blue, alpha).endVertex();
					TESSELLATOR.end();
					
					GlStateManager._enableTexture();
				}
				posX += f;
			}
		}

		matrices.popPose();
		GlStateManager._disableBlend();
		
		return (posX - startPos) / 2.0f;
	}

	public static Color getShadowColor(Color color) {
		return new Color((color.getRGB() & 16579836) >> 2 | color.getRGB()  & -16777216);
	}

}
