package com.gmail.roastnewt.WaterProofBlocks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WaterProofBlocks extends JavaPlugin implements Listener
{
	
	private boolean _BlockWater = true;
	private boolean _BlockLava = true;
	private ArrayList<String> _MaterialList = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable()
	{
		
		// Write default config
		if (!new File(this.getDataFolder()+"/config.yml").exists())
		{
			this.saveDefaultConfig();
		}
		
		FileConfiguration config = this.getConfig();
		
		// If someone has borked up the default config, write some values.
		if ( !config.contains( "BlockWater" ) ) config.set( "BlockWater", "true" );
		if ( !config.contains( "BlockLava" ) ) config.set( "BlockLava", "true" );
		if ( !config.contains( "MaterialList" ) ) config.set( "MaterialList", new ArrayList<String>(Arrays.asList("REDSTONE_WIRE")) );
		this.saveConfig();
		
		_BlockWater = config.getBoolean("BlockWater");
		_BlockLava = config.getBoolean("BlockLava");
		_MaterialList = (ArrayList<String>) config.getList("MaterialList");
		
		// Listen for events
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		// do nothing
	}
	
	@EventHandler
	public void onBlockChange(BlockFromToEvent e)
	{

		if (e.getBlock().getType() == Material.WATER && !_BlockWater)
		{
			System.out.println("Would have blocked water, but BlockWater == False");
			return;
		}
		if (e.getBlock().getType() == Material.STATIONARY_WATER && !_BlockWater)
		{
			System.out.println("Would have blocked water, but BlockWater == False");
			return;
		}
		if (e.getBlock().getType() == Material.LAVA && !_BlockLava)
		{
			System.out.println("Would have blocked lava, but BlockLava == False");
			return;
		}
		if (e.getBlock().getType() == Material.STATIONARY_LAVA && !_BlockLava)
		{
			System.out.println("Would have blocked lava, but BlockLava == False");
			return;
		}
		
		Material blockMaterial = e.getToBlock().getType();
		
		for (String materialString : _MaterialList)
		{
			if (blockMaterial == Material.matchMaterial(materialString))
			{
				e.setCancelled(true);
				System.out.println("Cancelled Event!");
			}
		}
		
	}
	
}
