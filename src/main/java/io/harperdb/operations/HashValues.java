package io.harperdb.operations;

import io.harperdb.base.Payload;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class HashValues<A> extends TableRef implements Payload {

    public final String operation;
    public final List<Integer> hash_values;
    public final A get_attributes;

    HashValues(String operation, TableRef table, A get_attributes) {
        super(table);
        this.operation = operation;
        this.hash_values = new ArrayList<>();
        this.get_attributes = get_attributes;
    }

}
