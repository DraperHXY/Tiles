package com.mutesaid.service;

import com.mutesaid.pojo.Profession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProfessionService {

    Map<String, List> getProfesList();
}
