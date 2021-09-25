/*
 * Copyright (c)
 *
 *
 */
package io.harperdb.operations;

import io.harperdb.base.Payload;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class DatabaseOp implements Payload {
    public final String operation;

    DatabaseOp(String operation) {
        this.operation = operation;
    }

    public static DatabaseOp describeAll() {
        return new DatabaseOp("describe_all");
    }
}
