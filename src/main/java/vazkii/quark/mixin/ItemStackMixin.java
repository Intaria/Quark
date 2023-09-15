package vazkii.quark.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.quark.content.client.hax.PseudoAccessorItemStack;
import vazkii.quark.content.client.resources.AttributeSlot;
import vazkii.quark.content.management.module.ItemSharingModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin implements PseudoAccessorItemStack {

	@ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
	private Component getHoverName(Component prev) {
		return ItemSharingModule.createStackComponent((ItemStack) (Object) this, (MutableComponent) prev);
	}

	@ModifyExpressionValue(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hasTag()Z", ordinal = 0))
	private boolean hasTagIfBaked(boolean hasTag, @Share("removedEnchantments") LocalBooleanRef ref) {
		return hasTag || ref.get();
	}

	@Inject(method = "getTooltipLines", at = @At("RETURN"))
	private void removeTagIfBaked(Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir, @Share("removedEnchantments") LocalBooleanRef ref) {
		ItemStack self = (ItemStack) (Object) this;
		if (ref.get())
			self.setTag(null);
	}

	@Unique
	private Map<AttributeSlot, Multimap<Attribute, AttributeModifier>> capturedAttributes = new HashMap<>();

	@Override
	public Map<AttributeSlot, Multimap<Attribute, AttributeModifier>> quark$getCapturedAttributes() {
		return capturedAttributes;
	}

	@Override
	public void quark$capturePotionAttributes(List<Pair<Attribute, AttributeModifier>> attributes) {
		Multimap<Attribute, AttributeModifier> attributeContainer = LinkedHashMultimap.create();
		for (var pair : attributes) {
			attributeContainer.put(pair.getFirst(), pair.getSecond());
		}
		capturedAttributes.put(AttributeSlot.POTION, attributeContainer);
	}

	@Inject(method = "getTooltipLines", at = @At("HEAD"))
	private void clearCapturedTooltip(Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
		capturedAttributes = new HashMap<>();
	}

	@ModifyReceiver(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;isEmpty()Z", remap = false))
	private Multimap<Attribute, AttributeModifier> overrideAttributeTooltips(Multimap<Attribute, AttributeModifier> attributes, @Local EquipmentSlot slot) {
		return attributes;
	}
}
