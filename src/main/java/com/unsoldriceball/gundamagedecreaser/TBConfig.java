package com.unsoldriceball.gundamagedecreaser;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.unsoldriceball.gundamagedecreaser.TBMain.ID_MOD;




@Config(modid = ID_MOD)
public class TBConfig
{
    public static boolean debugMode = false;
    @Config.RangeInt(min = 0)
    public static int maxDistanceFromPlayer = 48;
    @Config.RangeInt(min = 50000000)
    @Config.Comment("nanoTime.")
    public static long allowableLimit_TickDuration = 60000000;



    @Mod.EventBusSubscriber(modid = ID_MOD)
    private static class EventHandler
    {
        //Configが変更されたときに呼び出される。変更を適用する関数。
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(ID_MOD))
            {
                ConfigManager.sync(ID_MOD, Config.Type.INSTANCE);
            }
        }
    }
}