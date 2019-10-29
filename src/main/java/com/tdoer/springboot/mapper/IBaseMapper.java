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
package com.tdoer.springboot.mapper;

import java.util.List;

/**
 * @author Leon Wang (ahbbhywmd@163.com)
 * @Description base mapper
 * @create 2019-10-05
 */
public interface IBaseMapper <PK, E>{

    int deleteByPrimaryKey(PK id);

    int insert(E record);

    int insertSelective(E record);

    E selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);

    long countByEntityPrimaryKey(E record);

    List<E> selectAll();

    List<E> selectByExample(E record);
}
