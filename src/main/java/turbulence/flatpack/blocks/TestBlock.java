package turbulence.flatpack.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class TestBlock extends Block {

    final public static String registryName = "testblock";

    public TestBlock() {
        super(Properties.create(Material.WOOD)
            .sound(SoundType.WOOD)
            .hardnessAndResistance(2.0f)
        );
        setRegistryName(registryName);
    }
}