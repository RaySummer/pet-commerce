package com.pet.commerce.core.constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Molly
 * @since 2021-09-18 18:11
 */
public class JsonbType implements UserType, ParameterizedType {

    private static final ObjectMapper mapper = new ObjectMapper();
    public static final String PARAMETER_KEY = "type";
    private Class<?> valueType;

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue != null) {
            try {
                return mapper.readValue(mapper.writeValueAsString(originalValue), returnedClass());
            } catch (IOException e) {
                throw new HibernateException("Failed to deep copy object", e);
            }
        }
        return null;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);

        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }

        throw new SerializationException(String.format("Cannot serialize '%s', %s is not Serializable.", value,
                value.getClass()), null);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null) {
            return 0;
        }

        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings,
                              SharedSessionContractImplementor sharedSessionContractImplementor, Object object)
            throws HibernateException, SQLException {
        Object resultObject = resultSet.getObject(strings[0]);
        PGobject o = (PGobject) resultObject;
        if (o == null || StringUtils.isBlank(o.getValue())) {
            return new HashMap<String, Object>();
        }
        try {
            String value = o.getValue();
            if (value.startsWith("[") && value.endsWith("]")) {
                return mapper.readValue(value, List.class);
            }
            if (valueType != null) {
                return mapper.readValue(value, valueType);
            }
            return mapper.readValue(value, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<String, Object>();
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i,
                            SharedSessionContractImplementor sharedSessionContractImplementor)
            throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            try {
                preparedStatement.setObject(i, mapper.writeValueAsString(o), Types.OTHER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }


    @Override
    public void setParameterValues(Properties parameters) {
        if (parameters.get(PARAMETER_KEY) == null) {
            return;
        }
        String clazz = (String) parameters.get(PARAMETER_KEY);
        try {
            valueType = Class.forName(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
