package io.harperdb.operations;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class TableRef extends SchemaRef {

    public final String table;

    public TableRef(String table, String schema) {
        super(schema);
        this.table = table;
    }

    TableRef(TableRef ref) {
        this(ref.table, ref.schema);
    }
}
