/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.ibatis.ibator.generator.ibatis3.javamapper.elements;

import org.apache.ibatis.ibator.api.dom.java.Interface;
import org.apache.ibatis.ibator.generator.AbstractGenerator;

/**
 * 
 * @author Jeff Butler
 */
public abstract class AbstractJavaMapperMethodGenerator extends AbstractGenerator {
    public abstract void addInterfaceElements(Interface interfaze);

    public AbstractJavaMapperMethodGenerator() {
        super();
    }
}
