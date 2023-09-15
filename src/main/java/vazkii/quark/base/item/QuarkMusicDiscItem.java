package vazkii.quark.base.item;

import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.module.QuarkModule;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class QuarkMusicDiscItem extends RecordItem implements IQuarkItem {

	private final QuarkModule module;
	private final boolean isAmbient;
	private final Supplier<SoundEvent> soundSupplier;

	private BooleanSupplier enabledSupplier = () -> true;

	public QuarkMusicDiscItem(int comparatorValue, Supplier<SoundEvent> sound, String name, QuarkModule module, int lengthInTicks) {
		super(comparatorValue, sound, (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE), lengthInTicks);

		RegistryHelper.registerItem(this, "music_disc_" + name);
		this.module = module;
		this.isAmbient = lengthInTicks == Integer.MAX_VALUE;
		this.soundSupplier = sound;
	}

	@Override
	public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
		if(isEnabled() || group == CreativeModeTab.TAB_SEARCH)
			super.fillItemCategory(group, items);
	}

	@Override
	public QuarkMusicDiscItem setCondition(BooleanSupplier enabledSupplier) {
		this.enabledSupplier = enabledSupplier;
		return this;
	}

	@Override
	public QuarkModule getModule() {
		return module;
	}

	@Override
	public boolean doesConditionApply() {
		return enabledSupplier.getAsBoolean();
	}
}
