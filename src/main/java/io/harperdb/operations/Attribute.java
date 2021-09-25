package io.harperdb.operations;

import io.harperdb.base.Payload;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class Attribute implements Payload {
    public final String operation;
    public final String schema;
    public final String table;
    public final String attribute;

    Attribute(String operation, String schema, String table, String attribute) {
        this.operation = operation;
        this.schema = schema;
        this.table = table;
        this.attribute = attribute;
    }

    public static Attribute create(String schema, String table, String attribute) {
        return new Attribute("create_attribute", schema, table, attribute);
    }

    public static Attribute drop(String schema, String table, String attribute) {
        return new Attribute("drop_attribute", schema, table, attribute);
    }
}
