package com.ravvoid.client.render;

	import com.ravvoid.client.models.ModelVoidBeast;
import com.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
	import net.minecraft.util.ResourceLocation;
	import net.minecraftforge.fml.relauncher.Side;
	import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVoidBeast extends RenderLiving {
	
    private static final ResourceLocation VOIDBEASTTEXTURE = new ResourceLocation("rv:textures/models/voidbeast.png");

    public RenderVoidBeast(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelVoidBeast(), 0.6F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return VOIDBEASTTEXTURE;
    }
}