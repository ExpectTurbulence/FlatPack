package turbulence.flatpack;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FlatPack.modid)
public class FlatPack {
    final public static String modid = "flatpack";

    public FlatPack() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        OBJLoader.INSTANCE.addDomain(FlatPack.modid);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            Registration.registerBlocks(event);
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            Registration.registerItems(event);
        }

        /*@SubscribeEvent
        public static void onModelBakeEvent(ModelBakeEvent event) {
            try {
                // Try to load an OBJ model (placed in src/main/resources/assets/examplemod/models/)
                IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("flatpack:test.obj"));

                if (model instanceof OBJModel) {
                    // If loading OBJ model succeeds, bake the model and replace stick's model with the baked model
                    IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.ITEM);
                    event.getModelRegistry().put(new ModelResourceLocation("stick", "inventory"), bakedModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
