package com.hzqing.admin.service.config.impl;

import com.hzqing.admin.common.constants.ConstantConfig;
import com.hzqing.admin.domain.config.Config;
import com.hzqing.admin.domain.system.UserInfo;
import com.hzqing.admin.dto.config.ConfigDto;
import com.hzqing.admin.mapper.config.ConfigMapper;
import com.hzqing.admin.service.config.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzqing
 * @date 2019-06-11 14:56
 */
@Service
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public int update(ConfigDto config) {
        return configMapper.updateBatch(toList(config));
    }


    @Override
    public ConfigDto getConfigDto() {
        List<Config> configs = configMapper.selectList();
        return toConfigDto(configs);
    }

    /**
     * 将list转换成Dto
     * @param configs
     * @return
     */
    private ConfigDto toConfigDto(List<Config> configs){
        ConfigDto dto = new ConfigDto();
        configs.forEach(item ->{
            if (item.getKey().equals(ConstantConfig.CONFIG_TITLE)){
                dto.setTitle(item.getValue());
            }else if (item.getKey().equals(ConstantConfig.CONFIG_RECORD)){
                dto.setKeepToRecord(item.getValue());
            }
        });
        return dto;
    }

    /**
     * 将DTO转换成list
     * @param configDto
     * @return
     */
    private List<Config> toList(ConfigDto configDto){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Config> configs = new ArrayList<>();
        Config config = new Config();
        config.setKey(ConstantConfig.CONFIG_TITLE);
        config.setValue(configDto.getTitle());
        config.setUpdateBy(userInfo.getId());
        config.setUpdateTime(LocalDateTime.now());
        configs.add(config);

        Config record = new Config();
        record.setKey(ConstantConfig.CONFIG_RECORD);
        record.setValue(configDto.getKeepToRecord());
        record.setUpdateBy(userInfo.getId());
        record.setUpdateTime(LocalDateTime.now());
        configs.add(record);
        return configs;
    }
}
