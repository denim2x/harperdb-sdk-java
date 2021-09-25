package io.harperdb.operations;

import io.harperdb.base.Payload;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class RecordsOp<E> extends TableRef implements Payload {
    public final String operation;
    public final List<E> records;

    RecordsOp(String operation, TableRef table) {
        super(table);
        this.operation = operation;
        this.records = new ArrayList<>();
    }
}
