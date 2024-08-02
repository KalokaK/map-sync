package gjum.minecraft.mapsync.forge;

import gjum.minecraft.mapsync.common.MapSyncMod;
import gjum.minecraft.mapsync.common.ModGui;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod("mapsync")
public class ForgeMapSyncMod extends MapSyncMod {
	public ForgeMapSyncMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getVersion() {
		return VERSION + "+forge";
	}

	@Override
	public boolean isDevMode() {
		return !FMLLoader.isProduction();
	}

	public void clientSetup(FMLClientSetupEvent event) {
		init();

		// Register hook for the mod list
		ModLoadingContext.get().registerExtensionPoint(
				ConfigScreenHandler.ConfigScreenFactory.class,
				() -> new ConfigScreenHandler.ConfigScreenFactory(
						(minecraft, previousScreen) -> new ModGui(previousScreen)
				)
		);
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		try {
			if (event.phase == TickEvent.Phase.START) {
				handleTick();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerKeyBinding(KeyMapping mapping) {
		return;
		// ClientRegistry.registerKeyBinding(mapping);
	}

	@SubscribeEvent
	 public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
		event.register(ForgeMapSyncMod.openGuiKey);
	}
}
