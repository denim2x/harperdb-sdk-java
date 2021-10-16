package io.harperdb.operations;

import java.util.ArrayList;
import java.util.List;
import io.harperdb.base.Payload;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class RecordsOp extends TableRef implements Payload {
    public final String operation;
    public final List<? extends Record> records;

    RecordsOp(String operation, TableRef table) {
        super(table);
        this.operation = operation;
        this.records = new ArrayList<>();
    }
}
