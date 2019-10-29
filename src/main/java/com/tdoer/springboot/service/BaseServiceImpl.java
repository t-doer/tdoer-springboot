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

import com.tdoer.springboot.error.TooManyRecordsException;
import com.tdoer.springboot.mapper.IBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Leon Wang (ahbbhywmd@163.com)
 * @Description base service implement
 * @create 2019-10-05
 */
public class BaseServiceImpl<PK, E, M extends IBaseMapper<PK, E>> implements IBaseService<PK, E> {
    @Autowired
    public M mapper;

    @Override
    public int insert(E entity) {
        return mapper.insert(entity);
    }

    @Override
    public int save(E entity) {
        if (mapper.countByEntityPrimaryKey(entity) > 0) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    @Override
    public int update(E entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public E getById(PK id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public E getByExample(E example) {
        List<E> list = mapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new TooManyRecordsException();
        }
        return list.get(0);
    }

    @Override
    public List<E> findListByExample(E example) {
        return mapper.selectByExample(example);
    }

    @Override
    public List<E> findAll() {
        return mapper.selectAll();
    }
}
