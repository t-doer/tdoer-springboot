/*
 * Copyright 2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdoer.springboot.service;

import com.tdoer.springboot.mapper.IBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Leon Wang (ahbbhywmd@163.com)
 * @Description base service implement
 * @create 2019-10-05
 */
public class BaseServiceImpl<PK, E, M extends IBaseMapper<PK, E>> implements IBaseService<E> {
    @Autowired
    public M mapper;

    @Override
    public void save(E entity) {
        mapper.insertSelective(entity);
    }

    @Override
    public void update(E entity) {
        mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public E getByExample(E example) {
        List<E> list = mapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new RuntimeException("select result too many records");
        }
        return list.get(0);
    }

    @Override
    public List<E> findListByExample(E example) {
        return mapper.selectByExample(example);
    }

    @Override
    public List<E> findAll() {
        return findListByExample(null);
    }
}
