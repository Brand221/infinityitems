package com.infinity.common.events;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class tegg 
{
    public static class Common 
    {
        public ConfigValue<Boolean> disable_aether_portal = null;
        
        public Common(ForgeConfigSpec.Builder builder) 
        {
        	 builder.push("Modpack");
             disable_aether_portal = builder
                     .comment("Prevents the Weird Portal from being created normally in the mod")
                     .define("Disables Weird Portal creation", false);
             builder.pop();
        }
    }
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
    }
}
