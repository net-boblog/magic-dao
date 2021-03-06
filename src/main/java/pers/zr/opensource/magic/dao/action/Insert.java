package pers.zr.opensource.magic.dao.action;

import org.springframework.util.CollectionUtils;
import pers.zr.opensource.magic.dao.constants.ActionMode;
import pers.zr.opensource.magic.dao.shard.TableShardHandler;
import pers.zr.opensource.magic.dao.shard.TableShardStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhurong on 2016-4-28.
 */
public class Insert extends Action {

    private Map<String, Object> insertFields;

    public void setInsertFields(Map<String, Object> insertFields) {
        this.insertFields = insertFields;
    }

    private String shardTableName = null;

    @Override
    public String getSql() {

        if(null == this.sql) {
            parse();
        }
        if (log.isDebugEnabled()) {
            log.debug("### [ " + sql + "] ###");
        }
        return this.sql;
    }

    @Override
    public Object[] getParams() {

        if(null == this.params) {
            parse();
        }
        return this.params;
    }

    @Override
    public ActionMode getActionMode() {
        return ActionMode.INSERT;
    }


    private void parse() {

        if(CollectionUtils.isEmpty(insertFields)) {
            throw new RuntimeException("insert fields can not be empty!");
        }

        List<Object> paramsList = new ArrayList<Object>(insertFields.size());
        StringBuilder sqlBuilder = new StringBuilder(" (");

        TableShardStrategy tableShardStrategy = table.getTableShardStrategy();
        TableShardHandler tableShardHandler = table.getTableShardHandler();

        for(String column : insertFields.keySet()) {
            sqlBuilder.append(column).append(",");
            Object value = insertFields.get(column);
            paramsList.add(value);

            if(null == shardTableName) {
                if(null != tableShardHandler && null != tableShardStrategy) {
                    String shardColumn = tableShardStrategy.getShardColumn();
                    if(column.equalsIgnoreCase(shardColumn)) {
                        shardTableName = tableShardHandler.getShardTableName(
                                table.getTableName(),
                                tableShardStrategy.getShardCount(),
                                tableShardStrategy.getSeparator(),
                                value);
                    }
                }
            }

        }

        if(tableShardStrategy != null && this.shardTableName == null) {
            throw new RuntimeException("Failed to get actual name of shard table!");
        }

        sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") VALUES (");
        for(int i=0; i<insertFields.size(); i++) {
            sqlBuilder.append("?,");
        }
        sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");

        String actualTableName;
        if(null != table.getTableShardStrategy()) {
            actualTableName = this.shardTableName;
        }else {
            actualTableName = this.table.getTableName();
        }


        this.sql = "INSERT INTO " + actualTableName + sqlBuilder.toString();
        this.params = paramsList.toArray();


    }

}
