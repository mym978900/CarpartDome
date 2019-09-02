package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.ItemsMapper;
import com.xiupeilian.carpart.mapper.PartsMapper;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private PartsMapper partsMapper;

    @Override
    public List<Items> findItemsByQueryVo(Items items) {
        return itemsMapper.findItemsByQueryVo( items);
    }

    @Override
    public List<Items> findItemsByUserId(Integer id) {
        return itemsMapper.findItemsByUserId( id);
    }

    @Override
    public List<Parts> findParts() {
        return partsMapper.findPartsAll();
    }

    @Override
    public void updateDeleteStatusById(int parseInt) {
        Items items=new Items();
        items.setDeleteStatus(1);
        items.setId(parseInt);
        itemsMapper.updateByPrimaryKeySelective(items);
    }
}
