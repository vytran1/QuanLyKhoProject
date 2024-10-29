package com.quanlykho.inventory_order;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quanlykho.common.inventory_order.InventoryOrder;

public interface FilterableInventoryOrderRepostiroy {
    public Page<InventoryOrder> listWithFilter(Pageable pageable,Map<String,Object> filterFields);
}
