/*
 * Copyright (c) 2015, 2016, 2017 Adrian Siekierka
 *
 * This file is part of Charset.
 *
 * Charset is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Charset is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Charset.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.asie.charset.module.storage.barrels;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.lwjgl.opengl.GL11;
import pl.asie.charset.lib.block.BlockBase;

import java.util.Random;

public class RenderMinecartDayBarrel extends RenderMinecart<EntityMinecartDayBarrel> {
    private static final TileEntityDayBarrelRenderer tesr = new TileEntityDayBarrelRenderer();

    static {
        tesr.setRendererDispatcher(TileEntityRendererDispatcher.instance);
    }

    public RenderMinecartDayBarrel(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    protected void renderCartContents(EntityMinecartDayBarrel minecart, float partialTicks, IBlockState state) {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.rotate(180, 0, 1, 0);
        GlStateManager.translate(-1, 0, 0);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        RenderHelper.disableStandardItemLighting();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        buffer.setTranslation(-minecart.getPosition().getX(), -minecart.getPosition().getY(), -minecart.getPosition().getZ());

        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer()
                .renderModelFlat(minecart.getEntityWorld(), BarrelModel.INSTANCE, state, minecart.getPosition(), buffer, false, 0L);

        tessellator.draw();
        buffer.setTranslation(0, 0, 0);

        GlStateManager.disableBlend();
        tesr.render(minecart.barrel, 0, 0, 0, partialTicks, 0, 1.0f);
        GlStateManager.popMatrix();
    }
}
