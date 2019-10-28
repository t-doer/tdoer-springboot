/*
 * Copyright 2017-2019 T-Doer (tdoer.com).
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

package com.tdoer.springboot.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.plugins.MapperAnnotationPlugin;

/**
 * @description MybatisGeneratorPlugin
 * @author fly_once(654126198@qq.com)
 * @create 2019-10-27
 */
public class MybatisGeneratorPlugin extends MapperAnnotationPlugin {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        sqlMap.setMergeable(false);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        addSelectAllStatement(document, introspectedTable);
        addSelectByExampleStatement(document, introspectedTable);
        addSaveOrUpdateStatement(document, introspectedTable);
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        addSuperInterface(interfaze, introspectedTable);
        addSelectAllMethod(interfaze, introspectedTable);
        addSaveOrUpdateMethod(interfaze, introspectedTable);
        addSelectByExampleMethod(interfaze, introspectedTable);
        super.clientGenerated(interfaze, topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return addServiceFiles(introspectedTable);
    }

    // add statement in generated xml file
    private void addSelectAllStatement(Document document, IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getSelectAllStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter.next()));

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        String orderByClause = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
        if (hasOrderBy) {
            sb.setLength(0);
            sb.append("order by "); //$NON-NLS-1$
            sb.append(orderByClause);
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapSelectAllElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void addSaveOrUpdateStatement(Document document, IntrospectedTable introspectedTable) {
    }

    private void addSelectByExampleStatement(Document document, IntrospectedTable introspectedTable) {
    }

    // add super interface and methods in generated java mapper file
    private void addSuperInterface(Interface interfaze, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> pkcs = introspectedTable.getPrimaryKeyColumns();
        boolean multi = pkcs.size() > 1;
        boolean hasPk = pkcs.size() > 0;
        if (hasPk) {
            FullyQualifiedJavaType pkType;
            if (multi) {
                pkType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            } else {
                pkType = pkcs.get(0).getFullyQualifiedJavaType();
            }
            FullyQualifiedJavaType superInterfaceType = new FullyQualifiedJavaType(
                    "com.tdoer.springboot.mapper.IBaseMapper");
            superInterfaceType.addTypeArgument(pkType);
            superInterfaceType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            interfaze.addImportedType(superInterfaceType);
            interfaze.addImportedType(pkType);
            interfaze.addSuperInterface(new FullyQualifiedJavaType(superInterfaceType.getShortName()));
        } else {
            // no pk do nothing.
        }
    }

    private void addSelectAllMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            Method method = new Method("selectAll");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            interfaze.addMethod(method);
        }
    }

    private void addSaveOrUpdateMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            Method method = new Method("saveOrUpdate");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.setReturnType(FullyQualifiedJavaType.getIntInstance());
            method.addParameter(
                    new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "entity"));
            interfaze.addMethod(method);
        }
    }

    private void addSelectByExampleMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime() == TargetRuntime.MYBATIS3) {
            Method method = new Method("selectByExample");
            method.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
            returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            method.setReturnType(returnType);
            method.addParameter(
                    new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "example"));
            interfaze.addMethod(method);
        }
    }

    // generate service files, exampel: XXXService, XXXServiceImpl
    private List<GeneratedJavaFile> addServiceFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> javaFiles = new ArrayList<>();
        return javaFiles;
    }
}