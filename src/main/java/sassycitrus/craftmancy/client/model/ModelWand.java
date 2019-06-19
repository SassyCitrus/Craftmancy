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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;

@SideOnly(Side.CLIENT)
public final class ModelWand implements IModel
{
    public static final ModelWand MODEL = new ModelWand();

    public static final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(Craftmancy.modid, "wand"), "inventory");
    public static final ResourceLocation LOCATION_WAND = new ResourceLocation(Craftmancy.modid, "items/wand_1");

    // private final ResourceLocation LOCATION_CORE = new ResourceLocation(Craftmancy.modid, "items/wand/core");
    // private final ResourceLocation LOCATION_BINDING = new ResourceLocation(Craftmancy.modid, "items/wand/binding");
    // private final ResourceLocation LOCATION_GEM = new ResourceLocation(Craftmancy.modid, "items/wand/gem");
    
    @Override
    public Collection<ResourceLocation> getTextures()
    {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
        builder.add(LOCATION_WAND);
        return builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
            Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        ImmutableMap<TransformType, TRSRTransformation> transformMap = getTransformMap();

        // TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
        
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        IBakedModel model = (new ItemLayerModel(ImmutableList.of(LOCATION_WAND))).bake(state, format, bakedTextureGetter);
        builder.addAll(model.getQuads(null, null, 0));

        return new Baked(this, builder.build(), format, Maps.immutableEnumMap(transformMap), Maps.<String, IBakedModel> newHashMap());
    }

    @Override
    public IModel process(ImmutableMap<String, String> customData)
    {
        return IModel.super.process(customData);
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
            return super.handleItemState(originalModel, stack, world, entity);
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