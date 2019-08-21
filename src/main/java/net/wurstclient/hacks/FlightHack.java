/*
 * Copyright (C) 2014 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.events.UpdateListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.SliderSetting;
import net.wurstclient.settings.SliderSetting.ValueDisplay;

@SearchTags({"FlyHack", "fly hack", "flying"})
public final class FlightHack extends Hack implements UpdateListener
{
	public final SliderSetting speed =
		new SliderSetting("Speed", 1, 0.05, 5, 0.05, ValueDisplay.DECIMAL);
	
	public FlightHack()
	{
		super("Flight",
			"Allows you to you fly.\n\n" + ChatFormatting.RED
				+ ChatFormatting.BOLD + "WARNING:" + ChatFormatting.RESET
				+ " You will take fall damage if you don't use NoFall.");
		setCategory(Category.MOVEMENT);
		addSetting(speed);
	}
	
	@Override
	public void onEnable()
	{
		WURST.getEventManager().add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		WURST.getEventManager().remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		ClientPlayerEntity player = MC.player;
		
		player.abilities.flying = false;
		player.field_6281 = speed.getValueF();
		
		player.setVelocity(0, 0, 0);
		Vec3d velcity = player.getVelocity();
		
		if(MC.options.keyJump.isPressed())
			player.setVelocity(velcity.add(0, speed.getValue(), 0));
		
		if(MC.options.keySneak.isPressed())
			player.setVelocity(velcity.subtract(0, speed.getValue(), 0));
	}
}