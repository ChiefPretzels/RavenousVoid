package com.ravvoid.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelVoidBeast extends ModelBase {

	ModelRenderer Head;
    ModelRenderer Chest;
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Spike_1;
    ModelRenderer Spike_2;
    ModelRenderer Spike_3;
    ModelRenderer Spike_4;
    ModelRenderer Spike_5;
  
  public ModelVoidBeast()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    Head = new ModelRenderer(this, 0, 0);
    Head.addBox(-2.5F, -2.5F, -6F, 5, 5, 6);
    Head.setRotationPoint(0F, 10.5F, -6.5F);
    Head.setTextureSize(64, 32);
    Head.mirror = true;
    setRotation(Head, 0F, 0F, 0F);
    Chest = new ModelRenderer(this, 22, 0);
    Chest.addBox(-6F, -3.5F, -3F, 12, 7, 9);
    Chest.setRotationPoint(0F, 12F, -3F);
    Chest.setTextureSize(64, 32);
    Chest.mirror = true;
    setRotation(Chest, 1.570796F, 0F, 0F);
    Body = new ModelRenderer(this, 36, 16);
    Body.addBox(-4F, 0F, -3F, 8, 10, 6);
    Body.setRotationPoint(0F, 11F, 0.4666667F);
    Body.setTextureSize(64, 32);
    Body.mirror = true;
    setRotation(Body, 1.570796F, 0F, 0F);
    Leg1 = new ModelRenderer(this, 22, 16);
    Leg1.addBox(-1.533333F, 0F, -1F, 3, 13, 3);
    Leg1.setRotationPoint(5.5F, 11F, -4F);
    Leg1.setTextureSize(64, 32);
    Leg1.mirror = true;
    setRotation(Leg1, 0F, 0F, 0F);
    Leg2 = new ModelRenderer(this, 22, 16);
    Leg2.addBox(-1.533333F, 0F, -1F, 3, 13, 3);
    Leg2.setRotationPoint(-5.466667F, 11F, -4F);
    Leg2.setTextureSize(64, 32);
    Leg2.mirror = true;
    setRotation(Leg2, 0F, 0F, 0F);
    Leg3 = new ModelRenderer(this, 0, 16);
    Leg3.addBox(-1F, 0F, -1F, 2, 13, 3);
    Leg3.setRotationPoint(3.966667F, 11.06667F, 7F);
    Leg3.setTextureSize(64, 32);
    Leg3.mirror = true;
    setRotation(Leg3, 0.1047198F, 0F, -0.0371786F);
    Leg4 = new ModelRenderer(this, 0, 16);
    Leg4.addBox(-1F, 0F, -1F, 2, 13, 3);
    Leg4.setRotationPoint(-4F, 11.13333F, 7F);
    Leg4.setTextureSize(64, 32);
    Leg4.mirror = true;
    setRotation(Leg4, 0.1047198F, 0F, 0.0371755F);
    Spike_1 = new ModelRenderer(this, 18, 11);
    Spike_1.addBox(-0.5F, -4F, 0.5F, 1, 4, 1);
    Spike_1.setRotationPoint(4F, 6.466667F, -4F);
    Spike_1.setTextureSize(64, 32);
    Spike_1.mirror = true;
    setRotation(Spike_1, -0.1115358F, -0.0371786F, 0.1487144F);
    Spike_2 = new ModelRenderer(this, 18, 11);
    Spike_2.addBox(-0.5F, -4F, -0.5F, 1, 4, 1);
    Spike_2.setRotationPoint(-4F, 6.5F, -3F);
    Spike_2.setTextureSize(64, 32);
    Spike_2.mirror = true;
    setRotation(Spike_2, -0.111544F, 0.0371755F, -0.1487195F);
    Spike_3 = new ModelRenderer(this, 18, 11);
    Spike_3.addBox(-0.5F, -5F, -0.5F, 1, 5, 1);
    Spike_3.setRotationPoint(0F, 8.5F, 3F);
    Spike_3.setTextureSize(64, 32);
    Spike_3.mirror = true;
    setRotation(Spike_3, -0.1487144F, 0F, 0F);
    Spike_4 = new ModelRenderer(this, 18, 11);
    Spike_4.addBox(-0.5F, -4F, -0.5F, 1, 4, 1);
    Spike_4.setRotationPoint(0F, 8.5F, 7F);
    Spike_4.setTextureSize(64, 32);
    Spike_4.mirror = true;
    setRotation(Spike_4, -0.1487144F, 0F, 0F);
    Spike_5 = new ModelRenderer(this, 18, 11);
    Spike_5.addBox(-0.5F, -4F, -0.5F, 1, 4, 1);
    Spike_5.setRotationPoint(0F, 6.5F, -5F);
    Spike_5.setTextureSize(64, 32);
    Spike_5.mirror = true;
    setRotation(Spike_5, -0.1487144F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Head.render(f5);
    Chest.render(f5);
    Body.render(f5);
    Leg1.render(f5);
    Leg2.render(f5);
    Leg3.render(f5);
    Leg4.render(f5);
    Spike_1.render(f5);
    Spike_2.render(f5);
    Spike_3.render(f5);
    Spike_4.render(f5);
    Spike_5.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
  {
	  this.Head.rotateAngleX = headPitch * 0.017453292F;
      this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
      this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * 1.4F * limbSwingAmount;
      this.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.4F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.4F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * 1.4F * limbSwingAmount;
  }

}