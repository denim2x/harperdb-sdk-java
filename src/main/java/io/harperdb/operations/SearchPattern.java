package io.harperdb.operations;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class SearchPattern extends TableRef {

    public final String search_attribute;
    public final String search_value;

    public SearchPattern(TableRef table, String attribute, String value) {
        this(table.schema, table.table, attribute, value);
    }

    SearchPattern(String schema, String table, String attribute, String value) {
        super(table, schema);
        this.search_attribute = attribute;
        this.search_value = value;
    }

    SearchPattern(SearchPattern params) {
        this(params.schema, params.table, params.search_attribute, params.search_value);
    }

}
