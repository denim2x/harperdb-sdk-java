package io.harperdb.operations;

import io.harperdb.base.Payload;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public final class TableOp extends TableRef implements Payload {
    public final String operation;
    public final String hash_attribute;

    TableOp(String operation, TableRef table, String hash_attribute) {
        super(table);
        this.operation = operation;
        this.hash_attribute = hash_attribute;
    }

    public static TableOp describe(TableRef table) {
        return new TableOp("describe_table", table, null);
    }


    public static TableOp create(TableRef table, String hash_attribute) {
        return new TableOp("create_table", table, hash_attribute);
    }

    public static TableOp drop(TableRef table) {
        return new TableOp("drop_table", table, null);
    }

    public static <E> RecordsOp<E> insert(TableRef table) {
        return new RecordsOp<>("insert", table);
    }

    public static <E> RecordsOp<E> update(TableRef table) {
        return new RecordsOp<>("update", table);
    }

    public static HashValues<String> delete(TableRef table) {
        return new HashValues<>("delete", table, null);
    }

    public static HashValues<List<String>> search(TableRef table) {
        return new HashValues<>("search_by_hash", table, new ArrayList<>());
    }

    public static HashValues<String> search(TableRef table, String attributes) {
        return new HashValues<>("search_by_hash", table, attributes);
    }

    public static SearchOp<List<String>> search(SearchDetails details) {
        return new SearchOp<>("search_by_value", details, new ArrayList<>());
    }

    public static SearchOp<String> search(SearchDetails details, String attributes) {
        return new SearchOp<>("search_by_value", details, attributes);
    }

}
