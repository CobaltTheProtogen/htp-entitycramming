package com.htp.entitycramming;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EntityCramming.MOD_ID)
public class EntityCramming
{
    public static final Logger LOGGER = LogManager.getLogger(EntityCramming.class);
    public static final String MOD_ID = "entitycramming";
    public static final String NAME = "HTP Entitycramming";
    public static final String VERSION = "1.0.1";

    public EntityCramming() {
    }
}
