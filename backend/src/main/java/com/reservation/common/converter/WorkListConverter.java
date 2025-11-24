package com.reservation.common.converter;

import com.reservation.common.model.Reservation.Work;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WorkArrayType implements UserType<List<Work>> {

    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<List<Work>> returnedClass() {
        return (Class<List<Work>>) (Class) List.class;
    }

    @Override
    public boolean equals(List<Work> x, List<Work> y) {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return x.equals(y);
    }

    @Override
    public int hashCode(List<Work> x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public List<Work> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Array array = rs.getArray(position);
        if (array == null) {
            return new ArrayList<>();
        }
        String[] values = (String[]) array.getArray();
        return Arrays.stream(values)
                .map(Work::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public void nullSafeSet(PreparedStatement st, List<Work> value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null || value.isEmpty()) {
            st.setNull(index, Types.ARRAY);
        } else {
            String[] array = value.stream()
                    .map(Enum::name)
                    .toArray(String[]::new);

            Connection connection = session.getJdbcConnectionAccess().obtainConnection();
            Array sqlArray = connection.createArrayOf("work", array);
            st.setArray(index, sqlArray);
        }
    }

    @Override
    public List<Work> deepCopy(List<Work> value) {
        return value == null ? null : new ArrayList<>(value);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(List<Work> value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public List<Work> assemble(Serializable cached, Object owner) {
        return deepCopy((List<Work>) cached);
    }

    @Override
    public List<Work> replace(List<Work> detached, List<Work> managed, Object owner) {
        return deepCopy(detached);
    }
}