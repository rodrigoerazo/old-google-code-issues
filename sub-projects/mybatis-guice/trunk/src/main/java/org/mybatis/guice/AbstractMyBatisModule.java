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

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.TransactionalMethodInterceptor;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;

/**
 *
 * @version $Id$
 */
abstract class AbstractMyBatisModule implements Module {

    private Binder binder;

    /**
     * {@inheritDoc}
     */
    public void configure(Binder binder) {
        if (this.binder != null) {
            throw new IllegalArgumentException("Re-entry is not allowed");
        }

        if (binder == null) {
            throw new IllegalArgumentException("Parameter 'binder' must be not null");
        }

        this.binder = binder;

        // sql session manager
        binder.bind(SqlSessionManager.class).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
        binder.bind(SqlSession.class).to(SqlSessionManager.class).in(Scopes.SINGLETON);

        // transactional interceptor
        TransactionalMethodInterceptor interceptor = new TransactionalMethodInterceptor();
        binder.requestInjection(interceptor);
        binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transactional.class), interceptor);

        try {
            this.internalConfigure();
        } finally {
            this.binder = null;
        }
    }

    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    protected abstract void internalConfigure();

    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    protected abstract void configure();

    /**
     * Gets direct access to the underlying {@code Binder}.
     */
    protected final Binder binder() {
        return this.binder;
    }

}
