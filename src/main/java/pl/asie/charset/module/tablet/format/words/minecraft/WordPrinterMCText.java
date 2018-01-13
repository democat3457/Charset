package pl.asie.charset.module.tablet.format.words.minecraft;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import pl.asie.charset.ModCharset;
import pl.asie.charset.module.tablet.format.api.IPrintingContextMinecraft;
import pl.asie.charset.module.tablet.format.api.IStyle;
import pl.asie.charset.module.tablet.format.api.WordPrinterMinecraft;
import pl.asie.charset.module.tablet.format.words.StyleColor;
import pl.asie.charset.module.tablet.format.words.StyleFormat;
import pl.asie.charset.module.tablet.format.words.WordText;

public class WordPrinterMCText extends WordPrinterMinecraft<WordText> {
	@Override
	public int getWidth(IPrintingContextMinecraft context, WordText word) {
		StringBuilder s = new StringBuilder(word.getText());
		for (IStyle style : context.getStyleList()) {
			if (style instanceof StyleFormat) {
				s.insert(0, ((StyleFormat) style).getMcPrefix());
			}
		}
		return context.getFontRenderer().getStringWidth(s.toString());
	}

	@Override
	public int getHeight(IPrintingContextMinecraft context, WordText word) {
		return context.getFontRenderer().FONT_HEIGHT;
	}

	@Override
	public void draw(IPrintingContextMinecraft context, WordText word, int x, int y, boolean isHovering) {
		StringBuilder s = new StringBuilder(word.getText());
		int color = 0xFF000000;
		for (IStyle style : context.getStyleList()) {
			if (style instanceof StyleFormat) {
				s.insert(0, ((StyleFormat) style).getMcPrefix());
			} else if (style instanceof StyleColor) {
				color = 0xFF000000 | ((StyleColor) style).color;
			}
		}
		if (word.getScale() == 1.0f) {
			context.getFontRenderer().drawString(s.toString(), x, y, color);
		} else {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, 0);
			GlStateManager.scale(word.getScale(), word.getScale(), word.getScale());
			context.getFontRenderer().drawString(s.toString(), 0, 0, color);
			GlStateManager.popMatrix();
		}
	}
}
