package cn.solarmoon.immersive_delight.util;

import com.google.common.base.Functions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Credit: MoonLight - <a href="https://github.com/MehVahdJukaar/Moonlight">...</a>
 */
public class FluidRenderAnotherUtil {

    public static void renderFluid(float percentageFill, int color, int luminosity, ResourceLocation texture, PoseStack poseStack, MultiBufferSource bufferIn, int light, int combinedOverlayIn) {
        poseStack.pushPose();
        if (luminosity != 0) light = light & 15728640 | luminosity << 4;
        VertexConsumer builder = getBlockMaterial(texture).buffer(bufferIn, RenderType::entityTranslucentCull);
        Vector3f dimensions = LAST_KNOWN_DIMENSIONS;
        poseStack.translate(0.5, dimensions.z(), 0.5);
        addCube(builder, poseStack,
                dimensions.x(),
                percentageFill * dimensions.y(),
                light, color);
        poseStack.popPose();
    }

    private static final Vector3f LAST_KNOWN_DIMENSIONS = new Vector3f(8/16f, 12/16f, 1/16f);

    private static final Cache<ResourceLocation, Material> CACHED_MATERIALS = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.MINUTES)
            .build();

    public static Material getBlockMaterial(ResourceLocation bockTexture) {
        try {
            return CACHED_MATERIALS.get(bockTexture, () -> new Material(TextureAtlas.LOCATION_BLOCKS, bockTexture));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCube(VertexConsumer builder, PoseStack poseStack,
                               float width, float height, int light, int color) {
        addCube(builder, poseStack, 0, 0, width, height, light, color);
    }

    public static void addCube(VertexConsumer builder, PoseStack poseStack,
                               float uOff, float vOff,
                               float width, float height, int light, int color) {
        addCube(builder, poseStack, uOff, vOff,
                width, height, light, color, 1, true, true, false);
    }

    public static void addCube(VertexConsumer builder, PoseStack poseStack,
                               float uOff, float vOff,
                               float w, float h, int combinedLightIn,
                               int color, float alpha,
                               boolean up, boolean down, boolean wrap) {
        addCube(builder, poseStack, uOff, 1 - (vOff + h), uOff + w, 1 - vOff, w, h, combinedLightIn, color, alpha, up, down, wrap);
    }

    public static final Quaternionf XN90 = Axis.XP.rotationDegrees(-90);

    private static final Map<Direction, Quaternionf> DIR2ROT = Maps.newEnumMap(Arrays.stream(Direction.values())
            .collect(Collectors.toMap(Functions.identity(), d -> d.getOpposite().getRotation().mul(XN90))));

    public static void addCube(VertexConsumer builder, PoseStack poseStack,
                               float minU, float minV,
                               float maxU, float maxV,
                               float w, float h,
                               int combinedLightIn,
                               int color,
                               float alpha,
                               boolean up, boolean down, boolean wrap) {

        int lu = combinedLightIn & '\uffff';
        int lv = combinedLightIn >> 16 & '\uffff';
        float minV2 = maxV - w;

        int r = FastColor.ARGB32.red(color);
        int g = FastColor.ARGB32.green(color);
        int b = FastColor.ARGB32.blue(color);
        int a = (int) (255 * alpha);

        float hw = w / 2f;
        float hh = h / 2f;

        float inc = 0;

        poseStack.pushPose();
        poseStack.translate(0, hh, 0);
        for (var d : Direction.values()) {
            float v0 = minV;
            float t = hw;
            float y0 = -hh;
            float y1 = hh;
            float i = inc;
            if (d.getAxis() == Direction.Axis.Y) {
                if ((!up && d == Direction.UP) || !down) continue;
                t = hh;
                y0 = -hw;
                y1 = hw;
                v0 = minV2;
            } else if (wrap) {
                inc += w;
            }
            poseStack.pushPose();
            poseStack.mulPose(DIR2ROT.get(d));
            poseStack.translate(0, 0, -t);
            addQuad(builder, poseStack, -hw, y0, hw, y1, minU + i, v0, maxU + i, maxV, r, g, b, a, lu, lv);
            poseStack.popPose();

        }
        poseStack.popPose();
    }

    public static void addQuad(VertexConsumer builder, PoseStack poseStack,
                               float x0, float y0,
                               float x1, float y1,
                               float u0, float v0,
                               float u1, float v1,
                               int r, int g, int b, int a,
                               int lu, int lv) {
        PoseStack.Pose last = poseStack.last();
        Vector3f vector3f = last.normal().transform(new Vector3f(0, 0, -1));
        float nx = vector3f.x;
        float ny = vector3f.y;
        float nz = vector3f.z;
        vertF(builder, poseStack, x0, y1, 0, u0, v0, r, g, b, a, lu, lv, nx, ny, nz);
        vertF(builder, poseStack, x1, y1, 0, u1, v0, r, g, b, a, lu, lv, nx, ny, nz);
        vertF(builder, poseStack, x1, y0, 0, u1, v1, r, g, b, a, lu, lv, nx, ny, nz);
        vertF(builder, poseStack, x0, y0, 0, u0, v1, r, g, b, a, lu, lv, nx, ny, nz);
    }

    private static void vertF(VertexConsumer builder, PoseStack poseStack, float x, float y, float z,
                              float u, float v,
                              int r, int g, int b, int a,
                              int lu, int lv, float nx, float ny, float nz) {
        builder.vertex(poseStack.last().pose(), x, y, z);
        builder.color(r, g, b, a);
        builder.uv(u, v);
        builder.overlayCoords(0, 10);
        builder.uv2(lu, lv);
        builder.normal(nx, ny, nz);
        builder.endVertex();
    }

}
