package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;

import java.util.List;

public interface ItemsService {
    List<Items> findItemsByQueryVo(Items items);

    List<Items> findItemsByUserId(Integer id);

    List<Parts> findParts();

    void updateDeleteStatusById(int parseInt);
}
