package dev.sxmurxy.template;

import java.awt.Color;

import dev.sxmurxy.template.font.CustomFont;
import dev.sxmurxy.template.font.CustomFont.Lang;
import dev.sxmurxy.template.font.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(TemplateMod.MOD_ID)
public class TemplateMod {
	
	public static final String MOD_ID = "template";
	private static CustomFont font = new CustomFont("Nunito-Medium.ttf", 35, Lang.RU_ENG, true, 0.0f, 2.0f, 0.5f);
	private static CustomFont font1 = new CustomFont("Montserrat Medium.ttf", 26, Lang.RU_ENG, true, 0.0f, 2.0f, 0.5f);
	private static CustomFont font2 = new CustomFont("Greycliff.ttf", 30, Lang.RU_ENG, true, 0.0f, 2.0f, 0.5f);
	private static CustomFont font3 = new CustomFont("Comfortaa.ttf", 35, Lang.RU_ENG, true, 0.0f, 2.0f, 0.5f);
	private static CustomFont font4 = new CustomFont("Circe Regular.ttf", 35, Lang.RU_ENG, true, 0.0f, 2.0f, 0.5f);

    public TemplateMod() {
    	 MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
    	if(event.getType() == ElementType.ALL) {
    		FontRenderer.drawString(event.getMatrixStack(), font1, "§a§labsc§nd§4egfdg§f§o§linbuu§6g§n§mfd43'543§b§moprb,4g[aa", 60, 120 - font.getFontHeight(), Color.WHITE);
    		FontRenderer.drawShadowedString(event.getMatrixStack(), font1, "§a§labsc§nd§4egfdg§f§o§linbuu§6g§n§mfd43'543§b§moprb,4g[aa", 60, 140 - font.getFontHeight(), Color.WHITE);
    		FontRenderer.drawString(event.getMatrixStack(), font1, "§lЭта строка является тестовой 123", 60, 160 - font.getFontHeight(), Color.GREEN);
    		FontRenderer.drawString(event.getMatrixStack(), font2, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 180 - font.getFontHeight(), Color.WHITE);
    		FontRenderer.drawString(event.getMatrixStack(), font3, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 200 - font.getFontHeight(), Color.YELLOW);
    		FontRenderer.drawShadowedString(event.getMatrixStack(), font4, "§lTesting CUSTOMFONT renderer font Minecraft 1.16", 60, 220 - font.getFontHeight(), Color.MAGENTA);
    		FontRenderer.drawString(event.getMatrixStack(), font, "Testing CUSTOMFONT renderer font Minecraft 1.16", 60, 240 - font.getFontHeight(), Color.WHITE);
    	}
    }

}
