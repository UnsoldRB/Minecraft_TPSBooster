package com.unsoldriceball.gundamagedecreaser;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid = TBMain.ID_MOD, acceptableRemoteVersions = "*")
public class TBMain
{
    public static final String ID_MOD = "tpsbooster";


    private static EntityPlayer player;
    private static long lastTickedTime = System.nanoTime();
    private static long lastTickDuration = 0;




    //ModがInitializeを呼び出す前に発生するイベント。
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //これでこのクラス内でForgeのイベントが動作するようになるらしい。
        MinecraftForge.EVENT_BUS.register(this);
    }



    //ログインしたときのイベント。
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.player.world.isRemote) return;

        player = event.player;
    }



    //tick毎に実行されるイベント。
    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event)
    {
        if (event.side == Side.CLIENT) return;

        final long _NOW = System.nanoTime();
        lastTickDuration = _NOW - lastTickedTime;
        lastTickedTime = _NOW;
    }



    //Entityがアップデートされたときのイベント。
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        //現在の処理がサーバー側かつ、playerでない、加えて、lastTickDurationがallowableLimit_TickDurationを超えているかどうか。
        if (event.getEntity().world.isRemote) return;
        if (player == null) return;
        if (event.getEntity().getUniqueID().equals(player.getUniqueID())) return;
        if (lastTickDuration < TBConfig.allowableLimit_TickDuration) return;

        if (event.getEntity().dimension != player.dimension)
        {
            event.setCanceled(true);
        }
        else if (event.getEntity().isNonBoss())
        {
            if (event.getEntity().getDistanceSq(player.getPosition()) > TBConfig.maxDistanceFromPlayer * TBConfig.maxDistanceFromPlayer)
            {
                event.setCanceled(true);
            }
        }
    }
}
