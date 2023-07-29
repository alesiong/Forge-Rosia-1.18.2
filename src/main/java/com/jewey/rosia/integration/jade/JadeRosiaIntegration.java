package com.jewey.rosia.integration.jade;

import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.dries007.tfc.compat.jade.common.BlockEntityTooltip;
import net.minecraft.world.level.block.Block;

@WailaPlugin
public class JadeRosiaIntegration implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registry)
    {
        RosiaBlockEntityTooltips.register((tooltip, aClass) -> register(registry, tooltip, aClass));
    }
    private void register(IWailaClientRegistration registry, BlockEntityTooltip blockEntityTooltip, Class<? extends Block> blockClass)
    {
        registry.registerComponentProvider((tooltip, access, config) -> blockEntityTooltip.display(access.getLevel(), access.getBlockState(), access.getPosition(), access.getBlockEntity(), tooltip::add), TooltipPosition.BODY, blockClass);
    }
}
