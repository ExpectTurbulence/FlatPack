package turbulence.flatpack.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class TestBlockOBJ extends Block {

    final public static String registryName = "testblockobj";

    public TestBlockOBJ() {
        super(Properties.create(Material.WOOD)
            .sound(SoundType.WOOD)
            .hardnessAndResistance(2.0f)
        );
        setRegistryName(registryName);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}