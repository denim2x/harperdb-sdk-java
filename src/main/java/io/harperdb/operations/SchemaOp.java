package io.harperdb.operations;

import io.harperdb.base.Payload;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class SchemaOp implements Payload {
    public final String operation;
    public final String schema;

    SchemaOp(String operation, String schema) {
        this.operation = operation;
        this.schema = schema;
    }

    public static SchemaOp describe(String schema) {
        return new SchemaOp("describe_schema", schema);
    }

    public static SchemaOp create(String schema) {
        return new SchemaOp("create_schema", schema);
    }

    public static SchemaOp drop(String schema) {
        return new SchemaOp("drop_schema", schema);
    }
}
