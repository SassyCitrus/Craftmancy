package sassycitrus.craftmancy.client.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.api.wand.WandPartRegistry;
import sassycitrus.craftmancy.api.wand.WandPartRegistry.WandPart;

@SideOnly(Side.CLIENT)
public final class ModelWand implements IModel
{
    public static final ModelWand MODEL = new ModelWand();

    public static final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(Craftmancy.modid, "wand"), "inventory");

    private ResourceLocation core;
    private ResourceLocation binding;
    private ResourceLocation gem;

    public ModelWand()
    {
        this.core = null;
        this.binding = null;
        this.gem = null;
    }

    public ModelWand(ResourceLocation core, ResourceLocation binding, ResourceLocation gem)
    {
        this.core = core;
        this.binding = binding;
        this.gem = gem;
    }
    
    @Override
    public Collection<ResourceLocation> getTextures()
    {
        return WandPartRegistry.getInstance().getTextures();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
            Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        ImmutableMap<TransformType, TRSRTransformation> transformMap = getTransformMap();

        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        if (this.core != null && this.binding != null && this.gem != null)
        {
            IBakedModel modelCore = (new ItemLayerModel(ImmutableList.of(this.core))).bake(state, format, bakedTextureGetter);
            builder.addAll(modelCore.getQuads(null, null, 0));

            IBakedModel modelBinding = (new ItemLayerModel(ImmutableList.of(this.binding))).bake(state, format, bakedTextureGetter);
            builder.addAll(modelBinding.getQuads(null, null, 0));

            IBakedModel modelGem = (new ItemLayerModel(ImmutableList.of(this.gem))).bake(state, format, bakedTextureGetter);
            builder.addAll(modelGem.getQuads(null, null, 0));
        }

        return new Baked(this, builder.build(), format, Maps.immutableEnumMap(transformMap), Maps.<String, IBakedModel> newHashMap());
    }

    @Override
    public IModel process(ImmutableMap<String, String> customData)
    {
        ResourceLocation core = new ResourceLocation(customData.get("core"));
        ResourceLocation binding = new ResourceLocation(customData.get("binding"));
        ResourceLocation gem = new ResourceLocation(customData.get("gem"));

        return new ModelWand(core, binding, gem);
    }

    private ImmutableMap<TransformType, TRSRTransformation> getTransformMap()
    {
        ImmutableMap.Builder<TransformType, TRSRTransformation> builder = ImmutableMap.builder();

        for (TransformType transform : TransformType.values())
        {
            builder.put(transform, getTransformation(transform));
        }

        return builder.build();
    }

    private TRSRTransformation getTransformation(TransformType type)
    {
        Vector3f scale;
        Vector3f translation;
        Quat4f rotation;

        switch(type)
        {
            case FIRST_PERSON_RIGHT_HAND:
                scale = new Vector3f(0.68F, 0.68F, 0.68F);
                translation = new Vector3f(1.13F, 3.2F, 1.13F);
                rotation = TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, -90, 25));
                break;
            case FIRST_PERSON_LEFT_HAND:
                scale = new Vector3f(0.68F, 0.68F, 0.68F);
                translation = new Vector3f(1.13F, 3.2F, 1.13F);
                rotation = TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 90, -25));
                break;
            case THIRD_PERSON_RIGHT_HAND:
                scale = new Vector3f(0.85F, 0.85F, 0.85F);
                translation = new Vector3f(1.13F, 3.2F, 1.13F);
                rotation = TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, -90, 55));
                break;
            case THIRD_PERSON_LEFT_HAND:
                scale = new Vector3f(0.85F, 0.85F, 0.85F);
                translation = new Vector3f(1.13F, 3.2F, 1.13F);
                rotation = TRSRTransformation.quatFromXYZDegrees(new Vector3f(0, 90, -55));
                break;
            case GROUND:
                scale = new Vector3f(0.5F, 0.5F, 0.5F);
                translation = new Vector3f(0, 2F, 0);
                rotation = null;
                break;
            default:
                scale = new Vector3f(1F, 1F, 1F);
                translation = new Vector3f(0, 0, 0);
                rotation = null;
                break;
        } 
        
        translation.scale(0.0625F);
        return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(translation, rotation, scale, null));
    }

    public enum CustomModelLoader implements ICustomModelLoader
    {
        INSTANCE;

        @Override
        public boolean accepts(ResourceLocation modelLocation)
        {
            return modelLocation.getResourceDomain().equals(Craftmancy.modid) && modelLocation.getResourcePath().contains("wand");
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception
        {
            return MODEL;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager){}
    }

    private static final class BakedOverrideHandler extends ItemOverrideList
    {
        public static final BakedOverrideHandler INSTANCE = new BakedOverrideHandler();

        private BakedOverrideHandler()
        {
            super(ImmutableList.<ItemOverride> of());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world,
                EntityLivingBase entity)
        {
            Baked model = (Baked) originalModel;

            WandPartRegistry registry = WandPartRegistry.getInstance();

            if (stack.hasTagCompound())
            {
                WandPart core = registry.getPart(stack.getTagCompound().getString("core"));
                WandPart binding = registry.getPart(stack.getTagCompound().getString("binding"));
                WandPart gem = registry.getPart(stack.getTagCompound().getString("gem"));

                String key = core.toString() + "/" + binding.toString() + "/" + gem.toString();

                if (!model.cache.containsKey(key))
                {
                    IModel parent = model.parent.process(ImmutableMap.of("core", core.getTexture().toString(), "binding", binding.getTexture().toString(), "gem", gem.getTexture().toString()));

                    Function<ResourceLocation, TextureAtlasSprite> textureGetter;
                    textureGetter = new Function<ResourceLocation, TextureAtlasSprite>()
                    {
                        @Override
                        public TextureAtlasSprite apply(ResourceLocation location)
                        {
                            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
                        }
                    };

                    IBakedModel baked = parent.bake(new SimpleModelState(model.transforms), model.format, textureGetter);
                    model.cache.put(key, baked);
                    return baked;
                }
                
                return model.cache.get(key);
            }

            return model;
        }
    }

    private static final class Baked implements IBakedModel
    {
        private final ModelWand parent;
        private final Map<String, IBakedModel> cache;
        private final ImmutableMap<TransformType, TRSRTransformation> transforms;
        private final ImmutableList<BakedQuad> quads;
        private final VertexFormat format;

        public Baked(ModelWand parent, ImmutableList<BakedQuad> quads, VertexFormat format,
                ImmutableMap<ItemCameraTransforms.TransformType,TRSRTransformation> transforms, Map<String, IBakedModel> cache)
        {
            this.parent = parent;
            this.quads = quads;
            this.format = format;
            this.transforms = transforms;
            this.cache = cache;
        }

        @Override
        public ItemOverrideList getOverrides()
        {
            return BakedOverrideHandler.INSTANCE;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType)
        {
            return PerspectiveMapWrapper.handlePerspective(this, this.transforms, cameraTransformType);
        }

        @Override
        public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand)
        {
            if (side == null)
            {
                return quads;
            }
            return ImmutableList.of();
        }

        @Override
        public boolean isAmbientOcclusion()
        {
            return true;
        }

        @Override
        public boolean isGui3d()
        {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer()
        {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture()
        {
            return null;
        }
    }
}