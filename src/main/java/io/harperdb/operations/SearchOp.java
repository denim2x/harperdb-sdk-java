package io.harperdb.operations;

import io.harperdb.base.Payload;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class SearchOp<A> extends SearchDetails implements Payload {

    public final String operation;
    public final A get_attributes;

    SearchOp(String operation, SearchDetails details, A get_attributes) {
        super(details);
        this.operation = operation;
        this.get_attributes = get_attributes;
    }

}
