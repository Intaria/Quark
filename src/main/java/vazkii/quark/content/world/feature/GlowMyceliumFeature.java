package vazkii.quark.content.world.feature;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import vazkii.quark.content.world.module.GlimmeringWealdModule;

public class GlowMyceliumFeature extends Feature<NoneFeatureConfiguration> {

	public GlowMyceliumFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	public static PlacedFeature placed(ConfiguredFeature<NoneFeatureConfiguration, ?> f) {
		return f.placed(CountPlacement.of(125), 
				InSquarePlacement.spread(), 
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, 
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), 
				RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> config) {
		WorldGenLevel worldgenlevel = config.level();
		BlockPos blockpos = config.origin();
		Random rng = config.random();

		MutableBlockPos setPos = new MutableBlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
		for(int i = -6; i < 7; i++)
			for(int j = -6; j < 7; j++) 
				for(int k = -2; k < 3; k++) {
					setPos.set(blockpos.getX() + i, blockpos.getY() + k, blockpos.getZ() + j);
					double dist = blockpos.distSqr(setPos);
					if(dist > 10) {
						double chance = 1F - ((dist - 10) / 10);
						if(chance < 0 || rng.nextDouble() < chance)
							continue;
					}
					
					if(worldgenlevel.isStateAtPosition(setPos, s -> s.getBlock() == Blocks.DEEPSLATE) && worldgenlevel.isStateAtPosition(setPos.above(), BlockState::isAir)) {
						worldgenlevel.setBlock(setPos, GlimmeringWealdModule.glow_mycelium.defaultBlockState(), 2);
						
						if(rng.nextDouble() < 0.05)
							worldgenlevel.setBlock(setPos.above(), GlimmeringWealdModule.glow_shroom.defaultBlockState(), 2);
					}
				}

		return true;
	}

}