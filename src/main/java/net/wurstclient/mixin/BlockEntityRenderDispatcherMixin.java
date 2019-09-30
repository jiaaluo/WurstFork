/*
 * Copyright (C) 2014 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.wurstclient.WurstClient;
import net.wurstclient.events.RenderBlockEntityListener.RenderBlockEntityEvent;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin
{
	@Inject(at = {@At("HEAD")},
		method = {
			"render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/class_4587;Lnet/minecraft/class_4597;DDD)V"},
		cancellable = true)
	private <E extends BlockEntity> void onRender(E blockEntity,
		float partialTicks, class_4587 class_4587_1, class_4597 class_4597_1,
		double double_1, double double_2, double double_3, CallbackInfo ci)
	{
		RenderBlockEntityEvent event = new RenderBlockEntityEvent(blockEntity);
		WurstClient.INSTANCE.getEventManager().fire(event);
		
		if(event.isCancelled())
			ci.cancel();
	}
}
