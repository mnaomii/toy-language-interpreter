package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GarbageCollectorTest {
    // the state of Ex9 after new(v, 30): v -> (3, int), a -> (2, Ref(int))
    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()), new RefValue(2, new RefType(new IntType())));
    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20), 2, new RefValue(1, new IntType()),
            3, new IntValue(30), 4, new IntValue(7));

    // only the references count, the other values are skipped
    @Test
    void symTableAddressesComeOnlyFromReferences() {
        var values = List.<IValue>of(new IntValue(5), new RefValue(3, new IntType()));
        assertEquals(Set.of(3), GarbageCollector.getAddrFromSymTable(values));
    }

    // unsafe keeps only what the SymTable refers to: 4 is garbage, but 1 (reachable through 2) is lost too
    @Test
    void unsafeKeepsOnlyTheSymTableAddresses() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.unsafeGarbageCollector(roots, heap);
        assertEquals(Set.of(2, 3), kept.keySet());
    }

    // safe also keeps 1, because 2 refers to it; 4 is still garbage
    @Test
    void safeKeepsTheLocationsReachableThroughTheHeap() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);
        assertEquals(Set.of(1, 2, 3), kept.keySet());
    }

    // a -> (3, Ref(int)), c -> (0, int); 5 -> 4 -> 1 is a chain, but no variable reaches it
    @Test
    void aChainNoVariableReachesIsGarbage() {
        var values = List.<IValue>of(new RefValue(3, new RefType(new IntType())), new RefValue(0, new IntType()));
        Map<Integer, IValue> locations = Map.of(
                1, new IntValue(7), 2, new IntValue(8), 3, new RefValue(2, new IntType()),
                4, new RefValue(1, new IntType()), 5, new RefValue(4, new RefType(new IntType())));
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(values);

        assertEquals(Set.of(2, 3), GarbageCollector.reachableAddresses(roots, locations));
        assertEquals(Set.of(2, 3), GarbageCollector.safeGarbageCollector(roots, locations).keySet());
        assertEquals(Set.of(3), GarbageCollector.unsafeGarbageCollector(roots, locations).keySet());
    }
}
