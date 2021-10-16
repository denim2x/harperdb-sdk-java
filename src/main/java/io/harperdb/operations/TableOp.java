package io.harperdb.operations;

import java.util.ArrayList;
import java.util.List;
import io.harperdb.base.Payload;

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

    public static  RecordsOp insert(TableRef table) {
        return new RecordsOp("insert", table);
    }

    public static  RecordsOp update(TableRef table) {
        return new RecordsOp("update", table);
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

    public static SearchOp<List<String>> search(SearchPattern pattern) {
        return new SearchOp<>("search_by_value", pattern, new ArrayList<>());
    }

    public static SearchOp<String> search(SearchPattern pattern, String attributes) {
        return new SearchOp<>("search_by_value", pattern, attributes);
    }

}
