package Core

import groovy.sql.Sql

class Query {
    /**
     *
     */
    private result = [];

    /**
     *
     */
    private select = ['*'];

    /**
     *
     */
    private where = 1;

    /**
     *
     */
    private limit;

    /**
     *
     */
    private offset;

    /**
     *
     */
    private table;

    /**
     *
     */
    private sql = null;

    /**
     *
     * @param connectionString
     * @param username
     * @param password
     * @param driver
     * @return
     */
    def Query(connectionString, username, password, driver = null) {
        this.sql = Sql.newInstance(connectionString, username, password, driver = 'oracle.jdbc.driver.OracleDriver');

    }

    /**
     *
     * @param column
     * @return
     */
    def select(column = ['*']) {
        this.select = column;

        this;
    }

    /**
     * binding to where statement
     *
     * @param column
     * @param value
     * @param operator
     * @return
     */
    def where(column, value, operator = '=') {
        this.__where(column, value, operator);
        this.where;
    }

    /**
     * Handle and binding to where statement
     *
     * @param column
     * @param value
     * @return
     */
    def whereIn(column, value) {
        this.__where(column, "('${value.join('\',\'')}')", 'IN', 'AND', '')

        this;
    }

    /**
     * Handle and binding to where statement
     *
     * @param column
     * @param value
     * @return
     */
    def whereNotIn(column, value) {
        this.__where(column, "('${value.join('\',\'')}')", 'NOT', 'AND', '')

        this;
    }

    /**
     * Set table from stament
     *
     * @param table
     * @return
     */
    def from(table) {
        this.table = table;

        this;
    }

    /**
     * Run query
     *
     * @param offset
     * @param limit
     * @return
     */
    def run(int offset = 0, int limit = 0) throws Exception {
        if (limit == 0) {
            this.sql.eachRow(this.__buildQuery()) {
                row -> this.result << row.toRowResult()
            }
        } else {
            this.sql.eachRow(this.__buildQuery(), offset, limit) {
                row -> this.result << row.toRowResult()
            }
        }

        this.result;
    }

    /**
     * Run query and get first results
     *
     * @return
     */
    def first() throws Exception {
        this.sql.firstRow(this.__buildQuery())
    }

    /**
     * Handle where query
     *
     * @param valueCol
     * @param valueVal
     * @param valueOp
     * @param join
     * @param string
     * @return
     */
    private __where(valueCol, valueVal, valueOp = '=', join = 'AND', string = '\'') {
        if (this.where == 1) {
            this.where = "${valueCol} ${valueOp} ${string}${valueVal}${string}";
        } else {
            this.where += " ${join} ${valueCol} ${valueOp} ${string}${valueVal}${string}";
        }
    }

    /**
     * Build query
     *
     * @return
     */
    private def __buildQuery() {
        def tmp = "SELECT " + this.select.join(',') + " FROM " + this.table;

        if (this.where != 1) {
            tmp += " WHERE " + this.where;
        }

        tmp;
    }
}
