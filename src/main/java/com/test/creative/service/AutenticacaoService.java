package com.test.creative.service;

import com.test.creative.dto.RequestAutenticacaoDto;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface AutenticacaoService {
    public String autenticar(@Valid RequestAutenticacaoDto requestAutenticacaoDto);
}
