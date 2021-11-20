package com.infinity.entity;

import com.infinity.common.Main;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);
	
	public static final RegistryObject<EntityType<weirdcow>> INFCOW = ENTITY_TYPES.register("weird_cow", 
			() -> EntityType.Builder.<weirdcow>of(weirdcow::new, EntityClassification.CREATURE).sized(0.9F, 1.4F).build(new ResourceLocation(Main.MOD_ID, "weird_cow").toString()));
	
	public static final RegistryObject<EntityType<Cripper>> BOOM = ENTITY_TYPES.register("cripper",
			() -> EntityType.Builder.<Cripper>of(Cripper::new, EntityClassification.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8).build(new ResourceLocation(Main.MOD_ID, "cripper").toString()));
}