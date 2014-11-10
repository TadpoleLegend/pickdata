package com.meirong.helper.jqgrid.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcOperations;

import com.meirong.helper.jqgrid.JqParameter;
import com.meirong.helper.jqgrid.JqSearch;
import com.meirong.helper.jqgrid.PagedResult;

/**
 * @author jtwite
 * 
 */
public abstract class JqGridDao {

	private static final int ALL_FILTER = -1;
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 
	 * @param <T>
	 * @param selectStatement
	 * @param queryBody
	 *            - include FROM
	 * @param groupBy
	 *            - panel from a JqFilter enum
	 * @param mapper
	 * @param start
	 * @param end
	 * @param sortDir
	 * @param sortCol
	 * @param search
	 * @param sField
	 * @param searchVal
	 * @param filterField
	 * @param filterId
	 * @return
	 */
	protected <T> PagedResult<T> getPagedResults(final SimpleJdbcOperations db, final String selectStatement,
			final String queryBody, final String groupBy, final RowMapper<T> mapper, final Integer start,
			final Integer end, final String sortDir, final String sortCol, final JqSearch search, final String sField,
			final String searchVal, final JqParameter... parameters) {
		return getPagedResults(db, selectStatement, queryBody, groupBy, mapper, start, end, sortDir, sortCol, search,
				sField, searchVal, Arrays.asList(parameters));
	}

	/**
	 * 
	 * @param db
	 * @param selectStatement
	 * @param queryBody
	 * 			- include FROM
	 * @param groupBy
	 * 			- panel from a JqFilter enum
	 * @param mapper
	 * @param start
	 * @param end
	 * @param sortDir
	 * @param sortCol
	 * @param parameters
	 * @return
	 */
	protected <T> PagedResult<T> getPagedResults(final SimpleJdbcOperations db, final String selectStatement,
			final String queryBody, final String groupBy, final RowMapper<T> mapper, final Integer start,
			final Integer end, final String sortDir, final String sortCol, final JqParameter... parameters) {
		return getPagedResults(db, selectStatement, queryBody, groupBy, mapper, start, end, sortDir, sortCol, null,
				null, null, Arrays.asList(parameters));
	}

	/**
	 * 
	 * @param db
	 * @param selectStatement
	 * @param queryBody
	 * @param groupBy
	 * @param mapper
	 * @param start
	 * @param end
	 * @param sortDir
	 * @param sortCol
	 * @param parameters
	 * @return
	 */
	protected <T> PagedResult<T> getPagedResults(final SimpleJdbcOperations db, final String selectStatement,
			final String queryBody, final String groupBy, final RowMapper<T> mapper, final Integer start,
			final Integer end, final String sortDir, final String sortCol, final List<JqParameter> parameters) {
		return getPagedResults(db, selectStatement, queryBody, groupBy, mapper, start, end, sortDir, sortCol, null,
				null, null, parameters);
	}

	/**
	 * 
	 * @param <T>
	 * @param selectStatement
	 * @param queryBody
	 *            - include FROM
	 * @param groupBy
	 *            - panel from a JqFilter enum
	 * @param mapper
	 * @param start
	 * @param end
	 * @param sortDir
	 * @param sortCol
	 * @param search
	 * @param sField
	 * @param searchVal
	 * @param filterField
	 * @param filterId
	 * @return
	 */
	protected <T> PagedResult<T> getPagedResults(final SimpleJdbcOperations db, final String selectStatement,
			final String queryBody, final String groupBy, final RowMapper<T> mapper, final Integer start,
			final Integer end, final String sortDir, final String sortCol, final JqSearch search, final String sField,
			final String searchVal, final List<JqParameter> parameters) {
		List<Object> args = new ArrayList<Object>();
		PagedResult<T> ret = new PagedResult<T>();
		StringBuffer sql = new StringBuffer("SELECT " + selectStatement + " " + queryBody + " ");
		StringBuffer cSql = new StringBuffer("SELECT count(" + (groupBy != null && groupBy.length() > 0 ? "DISTINCT " + groupBy : "*") + ") " + queryBody + " ");

		if (search != null) {
			sql.append("WHERE " + sField + " " + search.getComparator() + " ? ");
			cSql.append("WHERE " + sField + " " + search.getComparator() + " ? ");

			args.add(search.prepareValue(searchVal));
		}

		if (parameters != null) {
			for (JqParameter param : parameters) {
				sql.append((sql.toString().contains("WHERE") ? " AND " : " WHERE ") + param.getSql());
				cSql.append((cSql.toString().contains("WHERE") ? " AND " : " WHERE ") + param.getSql());
				args.add(param.getPreparedValue());
			}
		}

		args.add(start != null ? start : 0);
		args.add(end != null ? end : 100000000);

		if (groupBy != null && !groupBy.equals("")) {
			sql.append(" GROUP BY " + groupBy);
		}
		
		sql.append(" ORDER BY " + sortCol + " " + checkSortBy(sortDir) + " LIMIT ?, ?");

		if (this.log.isInfoEnabled()) {
			this.log.info(sql + "\n" + args);

		}
		ret.setRows(db.query(sql.toString(), mapper, args.toArray()));

		if (args.size() > 2) {
			if (this.log.isInfoEnabled()) {
				this.log.info(cSql + "\n" + args.subList(0, args.size() - 2));
			}
			ret.setTotalRows(db.queryForInt(cSql.toString(), args.subList(0, args.size() - 2).toArray()));
		} else {
			ret.setTotalRows(db.queryForInt(cSql.toString()));
		}

		return ret;
	}

	public Integer getLastId(final JdbcTemplate db) {
		return db.queryForInt("SELECT LAST_INSERT_ID()");
	}

	public int getTotalFound(final JdbcTemplate db) {
		return db.queryForInt("SELECT FOUND_ROWS()");
	}

	/**
	 * Make sure the sort direction is valid
	 * 
	 * @param sort
	 * @return
	 */
	public String checkSortBy(final String sort) {
		String ret = sort;
		if (!ret.equalsIgnoreCase("ASC") && !ret.equalsIgnoreCase("DESC")) {
			ret = "ASC";
		}
		return ret;
	}
	
	public void addJqParam(Integer id, String field, List<JqParameter> params) {
		if(id != null && !id.equals(ALL_FILTER)) {
			params.add(new JqParameter(field, id, JqSearch.eq));
		}
	}
	public void addJqParam(String name, String field, List<JqParameter> params) {
		if(name != null && !name.isEmpty()  && !name.equals(String.valueOf(ALL_FILTER))) {
			params.add(new JqParameter(field, name, JqSearch.cn));
		}
	}
	public void addJqParam(Double number, String field, List<JqParameter> params) {
		if(number != null && !number.equals(Double.valueOf(ALL_FILTER))) {
			params.add(new JqParameter(field, number.toString(), JqSearch.eq));
		}
	}

}