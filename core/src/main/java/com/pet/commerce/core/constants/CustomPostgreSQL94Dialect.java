package com.pet.commerce.core.constants;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

/**
 * 自定义 PostgreSQL94Dialect
 *
 * @author Molly
 * @since 2021-09-18 18:11
 */
public class CustomPostgreSQL94Dialect extends PostgreSQL94Dialect {
    public CustomPostgreSQL94Dialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
        this.registerHibernateType(Types.OTHER, JsonbType.class.getName());
    }
}
