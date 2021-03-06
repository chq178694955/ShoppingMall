package com.king.handler.mybatis;

import com.king.enums.ActionEnum;
import com.king.enums.BaseEnum;
import com.king.enums.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value= {SexEnum.class, ActionEnum.class})
public class GeneralTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {

    private Logger logger = LoggerFactory.getLogger(GeneralTypeHandler.class);

    private Class<E> type;

    private E[] enums;

    public GeneralTypeHandler(Class<E> type){
        if(type == null){
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = this.type.getEnumConstants();
        if(this.enums == null){
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        //BaseTypeHandler 进行非空校验
        logger.debug("index : {}, parameter : {},jdbcType : {} ", i, parameter.getValue(), jdbcType);
        if (jdbcType == null) {
            ps.setObject(i, parameter.getValue());
        } else {
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String colName) throws SQLException {
        Object code = rs.getObject(colName);

        if (rs.wasNull()) {
            return null;
        }

        return getEnmByCode(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object code = rs.getObject(columnIndex);
        if (rs.wasNull()) {
            return null;
        }

        return getEnmByCode(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object code = cs.getObject(columnIndex);
        if (cs.wasNull()) {
            return null;
        }

        return getEnmByCode(code);
    }

    private E getEnmByCode(Object code) {

        if (code == null) {
            throw new NullPointerException("the result code is null " + code);
        }

        if (code instanceof Integer) {
            for (E e : enums) {
                if (e.getValue() == code) {
                    return e;
                }
            }
            throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
        }


        if (code instanceof String) {
            for (E e : enums) {
                if (code.equals(e.getValue())) {
                    return e;
                }
            }
            throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
        }

        throw new IllegalArgumentException("Unknown enumeration type , please check the enumeration code :  " + code);
    }
}
