package io.harperdb.base;

import static java.util.Objects.nonNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author denim2x <denim2x@cyberdude.com>
 */
public class Pending<S, R> implements Promise<R> {

    protected final CompletableFuture<S> future;
    protected final Function<S, R> fw;

    protected Pending(CompletableFuture<S> future, Function<S, R> fw) {
        this.future = future;
        this.fw = fw;
    }

    public static <S, R> Promise<R> of(CompletableFuture<S> future, Function<S, R> fw) {
        if (nonNull(future) && nonNull(fw)) {
            return new Pending<>(future, fw);
        }

        return null;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public R get()
            throws InterruptedException, ExecutionException {
        return fw.apply(future.get());
    }

    @Override
    public R get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return fw.apply(future.get(timeout, unit));

    }

    @Override
    public <U> CompletionStage<U> thenApply(Function<? super R, ? extends U> fn) {
        return future.thenApply(fn.compose(fw));
    }

    @Override
    public <U> CompletionStage<U> thenApplyAsync(Function<? super R, ? extends U> fn) {
        return future.thenApplyAsync(fn.compose(fw));
    }

    @Override
    public <U> CompletionStage<U> thenApplyAsync(Function<? super R, ? extends U> fn, Executor executor) {
        return future.thenApplyAsync(fn.compose(fw), executor);
    }

    @Override
    public CompletionStage<Void> thenAccept(Consumer<? super R> consumer) {
        return future.thenAccept((src) -> consumer.accept(fw.apply(src)));
    }

    @Override
    public CompletionStage<Void> thenAcceptAsync(Consumer<? super R> consumer) {
        return future.thenAcceptAsync((src) -> consumer.accept(fw.apply(src)));
    }

    @Override
    public CompletionStage<Void> thenAcceptAsync(Consumer<? super R> consumer, Executor executor) {
        return future.thenAcceptAsync((src) -> consumer.accept(fw.apply(src)), executor);
    }

    @Override
    public CompletionStage<Void> thenRun(Runnable action) {
        return future.thenRun(action);
    }

    @Override
    public CompletionStage<Void> thenRunAsync(Runnable action) {
        return future.thenRunAsync(action);
    }

    @Override
    public CompletionStage<Void> thenRunAsync(Runnable action, Executor executor) {
        return future.thenRunAsync(action, executor);
    }

    @Override
    public <U, V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super R, ? super U, ? extends V> fn) {
        return future.thenCombine(other, (s, u) -> fn.apply(fw.apply(s), u));
    }

    @Override
    public <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super R, ? super U, ? extends V> fn) {
        return future.thenCombineAsync(other, (s, u) -> fn.apply(fw.apply(s), u));
    }

    @Override
    public <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super R, ? super U, ? extends V> fn, Executor executor) {
        return future.thenCombineAsync(other, (s, u) -> fn.apply(fw.apply(s), u), executor);
    }

    @Override
    public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super R, ? super U> action) {
        return future.thenAcceptBoth(other, (s, u) -> action.accept(fw.apply(s), u));
    }

    @Override
    public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super R, ? super U> action) {
        return future.thenAcceptBothAsync(other, (s, u) -> action.accept(fw.apply(s), u));
    }

    @Override
    public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super R, ? super U> action, Executor executor) {
        return future.thenAcceptBothAsync(other, (s, u) -> action.accept(fw.apply(s), u), executor);
    }

    @Override
    public CompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return future.runAfterBoth(other, action);
    }

    @Override
    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return future.runAfterBothAsync(other, action);
    }

    @Override
    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return future.runAfterBothAsync(other, action, executor);
    }

    @Override
    public <U> CompletionStage<U> applyToEither(CompletionStage<? extends R> other, Function<? super R, U> fn) {
        return future.thenApply(fw).applyToEither(other, fn);
    }

    @Override
    public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends R> other, Function<? super R, U> fn) {
        return future.thenApplyAsync(fw).applyToEitherAsync(other, fn);
    }

    @Override
    public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends R> other, Function<? super R, U> fn, Executor executor) {
        return future.thenApplyAsync(fw, executor).applyToEitherAsync(other, fn, executor);
    }

    @Override
    public CompletionStage<Void> acceptEither(CompletionStage<? extends R> other, Consumer<? super R> action) {
        return future.thenApply(fw).acceptEither(other, action);
    }

    @Override
    public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends R> other, Consumer<? super R> action) {
        return future.thenApplyAsync(fw).acceptEitherAsync(other, action);
    }

    @Override
    public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends R> other, Consumer<? super R> action, Executor executor) {
        return future.thenApplyAsync(fw, executor).acceptEitherAsync(other, action, executor);
    }

    @Override
    public CompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return future.runAfterEither(other, action);
    }

    @Override
    public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return future.runAfterEitherAsync(other, action);
    }

    @Override
    public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return future.runAfterEitherAsync(other, action, executor);
    }

    @Override
    public <U> CompletionStage<U> thenCompose(Function<? super R, ? extends CompletionStage<U>> fn) {
        return future.thenCompose(fn.compose(fw));
    }

    @Override
    public <U> CompletionStage<U> thenComposeAsync(Function<? super R, ? extends CompletionStage<U>> fn) {
        return future.thenComposeAsync(fn.compose(fw));
    }

    @Override
    public <U> CompletionStage<U> thenComposeAsync(Function<? super R, ? extends CompletionStage<U>> fn, Executor executor) {
        return future.thenComposeAsync(fn.compose(fw), executor);
    }

    @Override
    public <U> CompletionStage<U> handle(BiFunction<? super R, Throwable, ? extends U> fn) {
        return future.handle((s, t) -> fn.apply(fw.apply(s), t));
    }

    @Override
    public <U> CompletionStage<U> handleAsync(BiFunction<? super R, Throwable, ? extends U> fn) {
        return future.handleAsync((s, t) -> fn.apply(fw.apply(s), t));
    }

    @Override
    public <U> CompletionStage<U> handleAsync(BiFunction<? super R, Throwable, ? extends U> fn, Executor executor) {
        return future.handleAsync((s, t) -> fn.apply(fw.apply(s), t), executor);
    }

    @Override
    public CompletionStage<R> whenComplete(BiConsumer<? super R, ? super Throwable> action) {
        return future.handle((s, t) -> apply(s, t, action));
    }

    @Override
    public CompletionStage<R> whenCompleteAsync(BiConsumer<? super R, ? super Throwable> action) {
        return future.handleAsync((s, t) -> apply(s, t, action));
    }

    @Override
    public CompletionStage<R> whenCompleteAsync(BiConsumer<? super R, ? super Throwable> action, Executor executor) {
        return future.handleAsync((s, t) -> apply(s, t, action), executor);
    }

    @Override
    public CompletionStage<R> exceptionally(Function<Throwable, ? extends R> fn) {
        return future.handle((s, t) -> exceptionally(s, t, fn));
    }

    @Override
    public CompletableFuture<R> toCompletableFuture() {
        return future.thenApply(fw);
    }

    protected R apply(S s, Throwable t, BiConsumer<? super R, ? super Throwable> action) {
        var res = fw.apply(s);
        action.accept(res, t);
        return res;
    }

    protected R exceptionally(S s, Throwable t, Function<Throwable, ? extends R> fn) {
        if (nonNull(t)) {
            return fn.apply(t);
        }

        return null;
    }

}
