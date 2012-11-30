/*
 * Copyright 2008-2009 the original author or authors.
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
package org.more.hypha.xml.tags.beans;
import java.util.HashMap;
import java.util.Map;
import org.more.core.xml.XmlStackDecorator;
import org.more.core.xml.stream.StartElementEvent;
import org.more.hypha.define.AbstractPropertyDefine;
import org.more.hypha.define.ValueMetaData;
import org.more.hypha.xml.XmlDefineResource;
import org.more.hypha.xml.tags.beans.qpp.QPP;
import org.more.util.attribute.AttBase;
/**
 * beans�����ռ�����Ա�ǩ�������ࡣ���಻�ᴦ������ֵԪ��Ϣ�Ľ����ⲿ����Ϣ�Ľ���������ר�б�ǩ������������{@link QPP}�ӿڸ�������
 * @version 2010-9-19
 * @author ������ (zyc@byshell.org)
 */
public abstract class TagBeans_AbstractPropertyDefine<T extends AbstractPropertyDefine> extends TagBeans_AbstractDefine<T> {
    /**����Ԫ��Ϣ.*/
    public static final String PropertyDefine = "$more_Beans_PropertyDefine";
    /**����{@link TagBeans_AbstractPropertyDefine}����*/
    public TagBeans_AbstractPropertyDefine(XmlDefineResource configuration) {
        super(configuration);
    }
    /**���ԵĶ�������*/
    protected String getAttributeName() {
        return PropertyDefine;
    }
    /**�����������Ͷ���*/
    protected abstract T createDefine(XmlStackDecorator<Object> context);
    /**����ͨ�õ����ԡ�*/
    public enum PropertyKey {
        value, boolLazyInit, classType, description
    };
    /**����������xml�����Զ�Ӧ��ϵ��*/
    protected Map<Enum<?>, String> getPropertyMappings() {
        HashMap<Enum<?>, String> propertys = new HashMap<Enum<?>, String>();
        propertys.put(PropertyKey.classType, "type");
        propertys.put(PropertyKey.description, "description");
        return propertys;
    }
    /**��ʼ�������Ա�ǩ��*/
    public void beginElement(XmlStackDecorator<Object> context, String xpath, StartElementEvent event) {
        //1.��������
        super.beginElement(context, xpath, event);
        //2.������������classType��
        AbstractPropertyDefine pdefine = this.getDefine(context);
        //3.��Ԫ�ض�����������Զ����ӵ�att�С�
        AttBase<String> att = new AttBase<String>();
        for (int i = 0; i < event.getAttributeCount(); i++)
            att.put(event.getAttributeName(i).getLocalPart(), event.getAttributeValue(i));
        //4.�����������ֵԪ��Ϣ��������value��date��bigText��ref��file��directory��uri��el�ȱ�ǩ�����Խ�������ʱ���Զ���������Ľ�����
        ValueMetaData valueMETADATA = (ValueMetaData) this.getRootTypeParser().parser(att, pdefine);
        if (valueMETADATA == null)
            throw new NullPointerException("ͨ��QPP��������ֵԪ��Ϣʧ�ܣ�����ֵΪ�ա�");
        pdefine.setValueMetaData(valueMETADATA);
    }
}