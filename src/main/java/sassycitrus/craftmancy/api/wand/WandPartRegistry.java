package sassycitrus.craftmancy.api.wand;

import java.util.Collection;
import java.util.HashMap;

import com.google.common.collect.ImmutableSet;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import sassycitrus.craftmancy.Craftmancy;

public class WandPartRegistry
{
    private static WandPartRegistry INSTANCE = new WandPartRegistry();

    private HashMap<String, WandPart> parts;

    private WandPartRegistry()
    {
        this.parts = new HashMap<String, WandPart>();
    }

    public static WandPartRegistry getInstance()
    {
        return INSTANCE;
    }

    public void registerWandPart(String name, PartType type, Item material)
    {
        WandPart part = new WandPart(name, type, material);
        this.parts.put(part.toString(), part);
    }

    public WandPart getPart(PartType type, String name)
    {
        return getPart(type.toString() + ":" + name);
    }

    public WandPart getPart(String name)
    {
        return this.parts.get(name);
    }

    public Collection<ResourceLocation> getTextures()
    {
        ImmutableSet.Builder<ResourceLocation> textures = ImmutableSet.builder();

        for (WandPart part : this.parts.values())
        {
            textures.add(part.getTexture());
        }

        return textures.build();
    }

    public class WandPart
    {
        private String name;
        private PartType type;
        private Item material;
        private ResourceLocation texture;

        public WandPart(String name, PartType type, Item material)
        {
            this.type = type;
            this.material = material;
            this.name = name;

            this.texture = new ResourceLocation(Craftmancy.modid, "items/wand/" + type.toString() + "_" + name);
        }

        public String getName()
        {
            return this.name;
        }

        public PartType getType()
        {
            return this.type;
        }

        public Item getMaterial()
        {
            return this.material;
        }

        public ResourceLocation getTexture()
        {
            return this.texture;
        }

        @Override
        public String toString()
        {
            return this.type.toString() + ":" + this.name;
        }
    }

    public enum PartType
    {
        CORE("core"),
        BINDING("binding"),
        GEM("gem");

        private String value;

        PartType(final String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }
}