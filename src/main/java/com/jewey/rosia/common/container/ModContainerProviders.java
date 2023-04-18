package com.jewey.rosia.common.container;

import net.dries007.tfc.common.container.ItemStackContainerProvider;

public class ModContainerProviders {
    public static final ItemStackContainerProvider LEATHER_SATCHEL = new ItemStackContainerProvider(LeatherSatchelContainer::create);
}
