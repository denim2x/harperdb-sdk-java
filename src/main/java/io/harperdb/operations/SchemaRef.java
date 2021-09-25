package io.harperdb.operations;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public abstract class SchemaRef {

    public final String schema;

    protected SchemaRef(String schema) {
        this.schema = schema;
    }
}
