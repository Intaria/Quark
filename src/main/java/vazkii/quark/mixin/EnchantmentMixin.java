package vazkii.quark.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.quark.content.experimental.module.EnchantmentsBegoneModule;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

	@ModifyReturnValue(method = "canEnchant", at = @At("RETURN"))
	private boolean canApply(boolean prev, ItemStack stack) {
		Enchantment self = (Enchantment) (Object) this;
		return !EnchantmentsBegoneModule.shouldBegone(self);
	}

	@ModifyReturnValue(method = "canApplyAtEnchantingTable", at = @At("RETURN"), remap = false)
	private boolean canApplyAtEnchantingTable(boolean prev, ItemStack stack) {
		Enchantment self = (Enchantment) (Object) this;
		return prev && !EnchantmentsBegoneModule.shouldBegone(self);
	}

	@ModifyReturnValue(method = "isDiscoverable", at = @At("RETURN"))
	private boolean isDiscoverable(boolean prev) {
		Enchantment self = (Enchantment) (Object) this;
		return prev && !EnchantmentsBegoneModule.shouldBegone(self);
	}
}
