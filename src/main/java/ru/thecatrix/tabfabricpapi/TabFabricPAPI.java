package ru.thecatrix.tabfabricpapi;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.event.plugin.TabLoadEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabFabricPAPI implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("tabfabricpapi");

	@Override
	public void onInitialize() {
		LOGGER.info("TabFabricPAPI initialized");

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			registerPlaceholders(server);

			TabAPI.getInstance().getEventBus().register(TabLoadEvent.class, event -> {
				LOGGER.info("[TabFabricPAPI] TabLoadEvent triggered");
				registerPlaceholders(server);
			});
		});
	}

	private void registerPlaceholders(MinecraftServer server) {
		TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%vanish_online%", 1000, () -> Placeholders.parseText(Text.of("%vanish:online%"), PlaceholderContext.of(server)).getString());
		LOGGER.info("[TabFabricPAPI] Placeholder %vanish_online% registered");

		TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%spark_tps%", 1000, () -> Placeholders.parseText(Text.of("%spark:tps 10s%"), PlaceholderContext.of(server)).getString().replace("*", ""));
		LOGGER.info("[TabFabricPAPI] Placeholder %spark_tps% registered");

		TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%spark_cpu%", 1000, () -> Placeholders.parseText(Text.of("%spark:cpu_process 10s%"), PlaceholderContext.of(server)).getString().replace("%", ""));
		LOGGER.info("[TabFabricPAPI] Placeholder %spark_cpu% registered");
	}
}