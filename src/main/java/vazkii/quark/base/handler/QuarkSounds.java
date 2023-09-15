package vazkii.quark.base.handler;

import com.google.common.collect.Lists;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.GameData;
import vazkii.arl.util.RegistryHelper;

import java.util.List;

/**
 * @author WireSegal
 * Created at 12:40 PM on 9/9/19.
 */
public class QuarkSounds {
	private static final List<SoundEvent> REGISTRY_DEFERENCE = Lists.newArrayList();

	public static final SoundEvent ENTITY_CRAB_DIE = register("entity.crab.die");
	public static final SoundEvent ENTITY_CRAB_HURT = register("entity.crab.hurt");
	public static final SoundEvent ENTITY_CRAB_IDLE = register("entity.crab.idle");

	public static final SoundEvent ENTITY_TORETOISE_DIE = register("entity.toretoise.die");
	public static final SoundEvent ENTITY_TORETOISE_HURT = register("entity.toretoise.hurt");
	public static final SoundEvent ENTITY_TORETOISE_IDLE = register("entity.toretoise.idle");
	public static final SoundEvent ENTITY_TORETOISE_ANGRY = register("entity.toretoise.angry");
	public static final SoundEvent ENTITY_TORETOISE_HARVEST = register("entity.toretoise.harvest");
	public static final SoundEvent ENTITY_TORETOISE_EAT_SATIATED = register("entity.toretoise.eat_satiated");
	public static final SoundEvent ENTITY_TORETOISE_EAT = register("entity.toretoise.eat");
	public static final SoundEvent ENTITY_TORETOISE_REGROW = register("entity.toretoise.regrow");

	public static final SoundEvent ENTITY_PARROT_EGG = register("entity.parrot.egg");

	public static final SoundEvent ENTITY_SOUL_BEAD_IDLE = register("entity.soul_bead.idle");

	public static final SoundEvent BLOCK_MONSTER_BOX_GROWL = register("block.monster_box.growl");

	public static final SoundEvent BLOCK_PIPE_SHOOT = register("block.pipe.shoot");
	public static final SoundEvent BLOCK_PIPE_PICKUP = register("block.pipe.pickup");
	public static final SoundEvent BLOCK_PIPE_SHOOT_LENNY = register("block.pipe.shoot.lenny");
	public static final SoundEvent BLOCK_PIPE_PICKUP_LENNY = register("block.pipe.pickup.lenny");

	public static final SoundEvent BLOCK_POTATO_DO_IT = register("block.potato.do_it");
	public static final SoundEvent BLOCK_POTATO_SODA = register("block.potato.soda");
	public static final SoundEvent BLOCK_POTATO_KINGBDOGZ = register("block.potato.kingbdogz");
	public static final SoundEvent BLOCK_POTATO_YUNG = register("block.potato.yung");
	public static final SoundEvent BLOCK_POTATO_HURT = register("block.potato.hurt");

	public static final SoundEvent ITEM_CAMERA_SHUTTER = register("item.camera.shutter");
	public static final SoundEvent ITEM_SOUL_POWDER_SPAWN = register("item.soul_powder.spawn");

	public static final SoundEvent BUCKET_FILL_CRAB = register("item.bucket.fill_crab");
	public static final SoundEvent BUCKET_EMPTY_CRAB = register("item.bucket.empty_crab");

	public static final SoundEvent PET_DEVICE = register("pet.device");
	public static final SoundEvent PET_NEKO = register("pet.neko");
	public static final SoundEvent PET_SLIME = register("pet.slime");
	public static final SoundEvent PET_WIRE = register("pet.wire");

	public static void start() {
		for (SoundEvent event : REGISTRY_DEFERENCE)
			RegistryHelper.register(event, Registry.SOUND_EVENT_REGISTRY);
		REGISTRY_DEFERENCE.clear();
	}

	public static SoundEvent register(String name) {
		ResourceLocation loc = GameData.checkPrefix(name, false);
		SoundEvent event = new SoundEvent(loc);
		RegistryHelper.setInternalName(event, loc);
		REGISTRY_DEFERENCE.add(event);
		return event;
	}
}
