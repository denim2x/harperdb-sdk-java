package io.harperdb.base;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public interface Promise<R> extends Future<R>, CompletionStage<R> {

}
