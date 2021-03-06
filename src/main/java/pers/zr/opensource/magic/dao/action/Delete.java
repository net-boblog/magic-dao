package pers.zr.opensource.magic.dao.action;

import pers.zr.opensource.magic.dao.constants.ActionMode;

/**
 * Created by zhurong on 2016-4-28.
 */
public class Delete extends ConditionAction {

    @Override
    public String getSql() {
        if(null == this.sql) {

            String actualTableName;
            if(null != table.getTableShardStrategy()) {
                actualTableName = getShardTableName();
            }else {
                actualTableName = this.table.getTableName();
            }

            this.sql = (new StringBuilder()).append("DELETE FROM ").append(actualTableName)
                    .append(" ").append(getConSql()).toString();

        }
        if (log.isDebugEnabled()) {
            log.debug("### [ " + sql + "] ###");
        }
        return this.sql;
    }

    @Override
    public Object[] getParams() {
        this.params = getConParams().toArray();
        return this.params;

    }

    @Override
    public ActionMode getActionMode() {
        return ActionMode.DELETE;
    }


}
