package com.mutesaid.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProfessionService {

    Map<String, List> getProfesList();
}
