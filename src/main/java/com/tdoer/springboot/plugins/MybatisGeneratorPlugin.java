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
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.IntrospectedTable.TargetRuntime;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.plugins.MapperAnnotationPlugin;

/**
 * @description MybatisGeneratorPlugin
 * @author fly_once(654126198@qq.com)
 * @create 2019-10-27
 */
public class MybatisGeneratorPlugin extends MapperAnnotationPlugin {

    private final static String S_SELECT_ALL = "selectAll";
    private final static String S_SELECT_BY_EXAMPLE = "selectByExample";
    private final static String S_COUNT_BY_ENTITY_PRIMARY_KEY = "countByEntityPrimaryKey";
    private final static String S_FILE_COMMENT = "" + 
    "/* \n"+
    "/* Copyright 2017-2019 T-Doer (tdoer.com). \n"+
    "/* \n"+
    "* Licensed under the Apache License, Version 2.0 (the \"License\"); \n"+
    "* you may not use this file except in compliance with the License. \n"+
    "* You may obtain a copy of the License at \n"+
    "* \n"+
    "*      http://www.apache.org/licenses/LICENSE-2.0 \n"+
    "* \n"+
    "* Unless required by applicable law or agreed to in writing, software \n"+
    "* distributed under the License is distributed on an \"AS IS\" BASIS, \n"+
    "* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. \n"+
    "* See the License for the specific language governing permissions and \n"+
    "* limitations under the License. \n"+
    "*/ \n";

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
        addCountByEntityPrimaryKeyStatement(document, introspectedTable);
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        interfaze.addFileCommentLine(S_FILE_COMMENT);
        addSuperInterface(interfaze, introspectedTable);
        interfaze.getMethods().clear();
        super.clientGenerated(interfaze, topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return new ArrayList<>();
        }
        return addServiceFiles(introspectedTable);
    }

    // add statement in generated xml file
    private void addSelectAllStatement(Document document, IntrospectedTable introspectedTable) {
        XmlElement parent = document.getRootElement();
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", S_SELECT_ALL));
        answer.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        parent.addElement(answer);
    }

    private void addCountByEntityPrimaryKeyStatement(Document document, IntrospectedTable introspectedTable) {
        XmlElement parent = document.getRootElement();
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", S_COUNT_BY_ENTITY_PRIMARY_KEY));
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        answer.addAttribute(new Attribute("resultType", "java.lang.Integer"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" where id=#{id,jdbcType=BIGINT}");

        answer.addElement(new TextElement(sb.toString()));
        parent.addElement(answer);
    }

    private void addSelectByExampleStatement(Document document, IntrospectedTable introspectedTable) {
        XmlElement parent = document.getRootElement();
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", S_SELECT_BY_EXAMPLE));
        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        answer.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement whereClouse = new XmlElement("where");
        for (IntrospectedColumn introspectedColumn : ListUtilities
                .removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            XmlElement isNotNullElement = new XmlElement("if");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append("and ");
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)); // $NON-NLS-1$

            isNotNullElement.addElement(new TextElement(sb.toString()));
            whereClouse.addElement(isNotNullElement);
        }
        answer.addElement(whereClouse);
        parent.addElement(answer);
    }

    // add super interface and methods in generated java mapper file
    private void addSuperInterface(Interface interfaze, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType pkType = getPKType(introspectedTable);
        if (pkType != null) {
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
            Method method = new Method(S_SELECT_ALL);
            method.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
            returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            method.setReturnType(new FullyQualifiedJavaType(returnType.getShortName()));
            interfaze.addMethod(method);
        }
    }

    private void addCountByEntityPrimaryKeyMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        Method method = new Method(S_COUNT_BY_ENTITY_PRIMARY_KEY);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "entity"));
        interfaze.addMethod(method);
    }

    private void addSelectByExampleMethod(Interface interfaze, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        interfaze.addImportedType(returnType);
        returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        Method method = new Method(S_SELECT_BY_EXAMPLE);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(returnType.getShortName()));
        method.addParameter(
                new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "example"));
        interfaze.addMethod(method);
    }

    // generate service files, exampel: XXXService, XXXServiceImpl
    private List<GeneratedJavaFile> addServiceFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> javaFiles = new ArrayList<>();
        FullyQualifiedJavaType pkType = getPKType(introspectedTable);
        if (pkType != null) {
            String targetProject = context.getJavaClientGeneratorConfiguration().getTargetProject();
            String mapperType = introspectedTable.getMyBatis3JavaMapperType();
            String interfacePackage = mapperType.replace(".mapper.", ".service.").substring(0,
                    mapperType.lastIndexOf(".")+1);
            String interfaceName = mapperType.substring(mapperType.lastIndexOf(".") + 1).replace("Mapper", "Service");
            String interfaceType = interfacePackage.concat(".").concat(interfaceName);
            FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            // add service interface
            Interface interfaze = new Interface(new FullyQualifiedJavaType(interfaceType));
            interfaze.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType superInterfazeType = new FullyQualifiedJavaType(
                    "com.tdoer.springboot.service.IBaseService");
            superInterfazeType.addTypeArgument(pkType);
            superInterfazeType.addTypeArgument(recordType);
            interfaze.addImportedType(recordType);
            interfaze.addImportedType(superInterfazeType);
            interfaze.addSuperInterface(new FullyQualifiedJavaType(superInterfazeType.getShortName()));
            interfaze.addFileCommentLine(S_FILE_COMMENT);
            GeneratedJavaFile serviceInterface = new GeneratedJavaFile(interfaze, targetProject,
                    context.getJavaFormatter());
            javaFiles.add(serviceInterface);
            // add service implement
            String clazzPackage = interfacePackage.concat(".impl");
            String clazzName = interfaceName.concat("Impl");
            String clazzType = clazzPackage.concat(".").concat(clazzName); 
            TopLevelClass clazz = new TopLevelClass(clazzType);
            clazz.setVisibility(JavaVisibility.PUBLIC);
            FullyQualifiedJavaType superClazzType = new FullyQualifiedJavaType("com.tdoer.springboot.service.BaseServiceImpl");
            superClazzType.addTypeArgument(pkType);
            superClazzType.addTypeArgument(recordType);
            superClazzType.addTypeArgument(new FullyQualifiedJavaType(mapperType));
            clazz.addImportedType(pkType);
            clazz.addImportedType(recordType);
            clazz.addImportedType(mapperType);
            clazz.addImportedType(superClazzType);
            clazz.addImportedType(interfaceType);
            clazz.addImportedType("org.springframework.stereotype.Service");
            clazz.setSuperClass(superClazzType.getShortName());
            clazz.addAnnotation("@Service");
            clazz.addSuperInterface(new FullyQualifiedJavaType(interfaceType));
            clazz.addFileCommentLine(S_FILE_COMMENT);
            GeneratedJavaFile serviceClazz = new GeneratedJavaFile(clazz, targetProject, context.getJavaFormatter());
            javaFiles.add(serviceClazz); 
        }
        return javaFiles;
    }

    private FullyQualifiedJavaType getPKType(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> pkcs = introspectedTable.getPrimaryKeyColumns();
        boolean multi = pkcs.size() > 1;
        boolean hasPk = pkcs.size() > 0;
        FullyQualifiedJavaType pkType = null;
        if (hasPk) {
            if (multi) {
                pkType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            } else {
                pkType = pkcs.get(0).getFullyQualifiedJavaType();
            }
        }
        return pkType;
    }
}