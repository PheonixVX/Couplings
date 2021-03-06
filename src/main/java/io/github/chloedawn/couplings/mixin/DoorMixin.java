/*
 * Copyright (C) 2019 Chloe Dawn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.chloedawn.couplings.mixin;

import io.github.chloedawn.couplings.Couplings;
import io.github.chloedawn.couplings.Doors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DoorBlock.class)
abstract class DoorMixin {
  @Inject(method = "onUse", at = @At(value = "RETURN", ordinal = 1), allow = 1)
  private void used(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player, final Hand hand, final BlockHitResult hit, final CallbackInfoReturnable<ActionResult> cir) {
      Doors.used(state, world, pos, player, hand, hit, cir.getReturnValue());
  }

  @Inject(method = "setOpen", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/DoorBlock;playOpenCloseSound(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Z)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
  private void toggled(final World world, final BlockState state, final BlockPos pos, final boolean open, final CallbackInfo ci) {
      Doors.toggled(state, world, pos, open);
  }

  @Inject(method = "neighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
  private void neighborUpdated(final BlockState state, final World world, final BlockPos pos, final Block block, final BlockPos neighborPos, final boolean moved, final CallbackInfo ci, final boolean powered) {
      Doors.neighborUpdated(state, world, pos, powered);
  }
}
