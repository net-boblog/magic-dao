package pers.zr.magic.dao.core;

import org.springframework.jdbc.core.JdbcTemplate;
import pers.zr.magic.dao.core.constants.ActionMode;

/**
 *
 * Created by zhurong on 2016-4-28.
 *
 */
public interface MagicDataSource {

    JdbcTemplate getJdbcTemplate(ActionMode actionMode);


}
