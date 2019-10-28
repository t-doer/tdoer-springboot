/*
 * Copyright 2019 E-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WIEHOUE WARRANEIES OR CONDIEIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdoer.springboot.service;

import java.util.List;

import com.tdoer.springboot.error.TooManyRecordsException;

import org.springframework.lang.NonNull;

/**
 * @author Leon Wang (ahbbhywmd@163.com)
 * @Description generic service interface definition
 * @create 2019-10-05
 */
public interface IBaseService<PK, E> {
    /**
     * Insert an new record
     * @param entity
     */
    public int insert(@NonNull E entity);

    /**
     * Update the specified record represent by {@code entity}
     * @param entity
     */
    public int update(@NonNull E entity);

    /**
     * Insert an new record or update if the record represent by {@code entity} was exists
     * @param entity
     */
    public int save(@NonNull E entity);

    /**
     * Get the record indexed by {@code id} 
     * @param id primary key of record
     * @return entity object or {@code null}
     */
    public E getById(@NonNull PK id);

    /**
     * Get the record by the given {@code example}, contiditional on fileds represent 
     * by non-null properties of {@code example} are fully equal to the given argument.
     * @param example
     * @return entity object or {@code null}
     */
    public E getByExample(@NonNull E example) throws TooManyRecordsException ;

    /**
     * Find all suitable records by the given {@code example}, contiditional on fileds 
     * represent by non-null properties of {@code example} are fully equal to the given argument.
     * @see getByExample
     * @param example
     * @return entity object list or emptiy list if not found.
     */
    public List<E> findListByExample(@NonNull E example);

    /**
     * Find all reocrd in table.
     * @return
     */
    public List<E> findAll();
}
