package turbulence.flatpack;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;
import turbulence.flatpack.blocks.TestBlock;

public class Registration {

    @ObjectHolder(FlatPack.modid + ":" + TestBlock.registryName)
    public static TestBlock TESTBLOCK;

    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new TestBlock());
    }
    
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        registerBlockItem(TESTBLOCK, event);
    }

    public static void registerBlockItem(Block block, final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(block, new Item.Properties()).setRegistryName(block.getRegistryName()));
    }
}