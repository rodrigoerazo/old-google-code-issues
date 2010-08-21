/*
 *    Copyright 2010 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice;

import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 *
 * @version $Id$
 */
@Singleton
@RunWith(GuiceTestRunner.class)
public final class MyBatisModuleTestCase {

    @Inject
    private Contact contact;

    @Inject
    private ContactMapperClient contactMapperClient;

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setContactMapperClient(ContactMapperClient contactMapperClient) {
        this.contactMapperClient = contactMapperClient;
    }

    @Test
    public void verifyNotNullMapper() {
        assertNotNull(this.contactMapperClient);
    }

    @Test
    public void insertContact() throws Exception {
        this.contactMapperClient.insert(this.contact);
    }

    @Test
    public void selectAllContacts() throws Exception {
        List<Contact> contacts = this.contactMapperClient.getAll();
        assert contacts.size() > 0 : "Expected not empty contact table";
    }

    @Test
    public void reSelectAllContacts() throws Exception {
        List<Contact> contacts = this.contactMapperClient.getAll();
        assert contacts.size() > 0 : "Expected not empty contact table";
    }

    @Test
    public void selectContact() throws Exception {
        Contact contact = this.contactMapperClient.selectById(this.contact.getId());
        assert contact != null : "impossible to retrieve Contact with id '"
                                + this.contact.getId()
                                + "'";
        assert this.contact.equals(contact) : "Expected "
                                                + this.contact
                                                + " but found "
                                                + contact;
    }

    @Test(expected = CustomException.class)
    public void catchSQLException() throws Exception {
        this.contact.setFirstName("Supercalifragilistichespiralidoso");
        this.contactMapperClient.update(contact);
    }

}
