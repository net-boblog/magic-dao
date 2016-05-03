package pers.zr.magic.dao.core.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhurong on 2016-4-28.
 */
public class ActionTable {

    /** 表名*/
    private String tableName;

    /** 主键*/
    private String[] keys;

    /** 列*/
    private String[] columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ActionTable that = (ActionTable) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(tableName, that.tableName)
                .append(keys, that.keys)
                .append(columns, that.columns)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(tableName)
                .append(keys)
                .append(columns)
                .toHashCode();
    }
}
