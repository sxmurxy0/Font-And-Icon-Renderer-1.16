package dev.sxmurxy.template;

import java.awt.Color;

import com.mojang.blaze3d.matrix.MatrixStack;

import dev.sxmurxy.template.common.Lang;
import dev.sxmurxy.template.icon.IconFont;
import dev.sxmurxy.template.icon.IconRenderer;
import dev.sxmurxy.template.simplified.SimplifiedFontRenderer;
import dev.sxmurxy.template.simplified.TextFont;
import dev.sxmurxy.template.styled.StyledFont;
import dev.sxmurxy.template.styled.StyledFontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(TemplateMod.MOD_ID)
public class TemplateMod {

	public static final String MOD_ID = "template";
	public static final String FONT_DIR = "/assets/" + TemplateMod.MOD_ID + "/font/";
	private static StyledFont font = new StyledFont("Nunito-Medium.ttf", 35, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static StyledFont font1 = new StyledFont("Montserrat Medium.ttf", 26, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static TextFont font2 = new TextFont("Greycliff.ttf", 30, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static TextFont font3 = new TextFont("Comfortaa.ttf", 35, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static IconFont font4 = new IconFont("Icons.ttf", 60, 'a', 'b', 'c');

	public TemplateMod() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if(event.getType() == ElementType.ALL) {
			MatrixStack matrices = event.getMatrixStack();
			
			StyledFontRenderer.drawString(matrices, font, "§a§labsc§nd§4egfdg§f§o§linbuu§6g§n§mfd43'543§b§moprb,4g[aa", 60, 120 - font.getFontHeight(), Color.WHITE);
			StyledFontRenderer.drawShadowedString(matrices, font1, "§a§labsc§nd§4перрарк§f§o§linbuu§6g§n§mfd43'543§b§mпепбуааb,4g[aa", 60, 140 - font.getFontHeight(), Color.WHITE);
		
			SimplifiedFontRenderer.drawString(matrices, font2, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 180 - font.getFontHeight(), Color.WHITE);
			SimplifiedFontRenderer.drawString(matrices, font3, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 200 - font.getFontHeight(), Color.YELLOW);
			SimplifiedFontRenderer.drawString(matrices, font3, "абвгдеёжзиклмнопрстфухцшщчаюяАБВГДЕЁЖЗИКЛМНОПРСТШЩУФХЦЧЭЮЯ", 60, 270 - font.getFontHeight(), Color.YELLOW);
			SimplifiedFontRenderer.drawString(matrices, font3, "-=)()((*?::%;##", 60, 300 - font.getFontHeight(), Color.BLUE);
			
			IconRenderer.drawIcon(matrices, font4, 'a', 60, 240 - font.getFontHeight(), Color.WHITE);
			IconRenderer.drawIcon(matrices, font4, 'b', 60 + 30, 240 - font.getFontHeight(), Color.DARK_GRAY);
			IconRenderer.drawIcon(matrices, font4, 'c', 60 + 60, 240 - font.getFontHeight(), Color.WHITE);
		}
	}

}
