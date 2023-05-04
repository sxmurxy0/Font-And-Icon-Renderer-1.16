package dev.sxmurxy.template;

import java.awt.Color;

import dev.sxmurxy.template.common.Lang;
import dev.sxmurxy.template.icon.IconFont;
import dev.sxmurxy.template.icon.IconRenderer;
import dev.sxmurxy.template.simplified.SimplifiedFontRenderer;
import dev.sxmurxy.template.simplified.TextFont;
import dev.sxmurxy.template.styled.StyledFont;
import dev.sxmurxy.template.styled.StyledFontRenderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.util.math.MatrixStack;

public class TemplateMod implements ModInitializer {
	
	public static final String MOD_ID = "template";
	public static final String FONT_DIR = "/assets/" + TemplateMod.MOD_ID + "/font/";
	private static StyledFont font = new StyledFont("Nunito-Medium.ttf", 35, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static StyledFont font1 = new StyledFont("Montserrat Medium.ttf", 26, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static TextFont font2 = new TextFont("Greycliff.ttf", 30, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static TextFont font3 = new TextFont("Comfortaa.ttf", 35, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static IconFont font4 = new IconFont("Icons.ttf", 60, 'a', 'b', 'c');
	
	public void onInitialize() {
		HudRenderCallback.EVENT.register(this::onHudRender);
	}
	
	private void onHudRender(MatrixStack matrixStack, float tickDelta) {
		StyledFontRenderer.drawString(matrixStack, font, "§a§labsc§nd§4egfdg§f§o§linbuu§6g§n§mfd43'543§b§moprb,4g[aa", 60, 120 - font.getFontHeight(), Color.WHITE);
		StyledFontRenderer.drawShadowedString(matrixStack, font1, "§a§labsc§nd§4перрарк§f§o§linbuu§6g§n§mfd43'543§b§mпепбуааb,4g[aa", 60, 140 - font.getFontHeight(), Color.WHITE);
	
		SimplifiedFontRenderer.drawString(matrixStack, font2, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 180 - font.getFontHeight(), Color.WHITE);
		SimplifiedFontRenderer.drawString(matrixStack, font3, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 200 - font3.getFontHeight(), Color.YELLOW);
		SimplifiedFontRenderer.drawString(matrixStack, font3, "абвгдеёжзиклмнопрстфухцшщчаюяАБВГДЕЁЖЗИКЛМНОПРСТШЩУФХЦЧЭЮЯ", 60, 270 - font.getFontHeight(), Color.YELLOW);
		SimplifiedFontRenderer.drawString(matrixStack, font3, "-=)()((*?::%;##", 60, 300 - font.getFontHeight(), Color.BLUE);
		
		IconRenderer.drawIcon(matrixStack, font4, 'a', 60, 240 - font4.getFontHeight(), Color.WHITE);
		IconRenderer.drawIcon(matrixStack, font4, 'b', 60 + 30, 240 - font.getFontHeight(), Color.DARK_GRAY);
		IconRenderer.drawIcon(matrixStack, font4, 'c', 60 + 60, 240 - font.getFontHeight(), Color.WHITE);
	}
	
}
