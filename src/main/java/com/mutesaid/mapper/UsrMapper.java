package com.mutesaid.mapper;

import com.mutesaid.pojo.Usr;

public interface UsrMapper {
    void insert(Usr v);

    Usr getByName(String name);
}
