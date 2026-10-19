map_get(Map, Key, Value) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key = NodeKey, Value = NodeValue.

map_get(Map, Key, Value) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key < NodeKey, map_get(Left, Key, Value).

map_get(Map, Key, Value) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key > NodeKey,map_get(Right, Key, Value).

map_build([], []) :-!.

map_build(ListMap, TreeMap) :- length(ListMap, ListLength), map_build_impl(ListLength, ListMap, TreeMap, []).

map_build_impl(0, List, [], List) :-!.

map_build_impl(ListLength, ListMap, Map, Rest) :-  Map = tree_node(NodeKey, NodeValue, Left, Right), ListLength > 0,
    LeftLength is ListLength // 2,
    RightLength is ListLength - LeftLength -1,
    H = (NodeKey, NodeValue), 
    map_build_impl(LeftLength, ListMap, Left, [H | Tail]),
    map_build_impl(RightLength, Tail, Right, Rest).


choose_min(Map, MinKey, MinValue) :- Map = tree_node(NodeKey, NodeValue, [], Right), MinKey = NodeKey, MinValue = NodeValue.

choose_min(Map, MinKey, MinValue) :- Map = tree_node(NodeKey, NodeValue, Left, Right), choose_min(Left, MinKey, MinValue).

map_remove([], Key, []) :-!.

map_remove(Map, Key, Result) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key < NodeKey, !, map_remove(Left, Key, Left_),
    Result = tree_node(NodeKey, NodeValue, Left_, Right).

map_remove(Map, Key, Result) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key > NodeKey, !, map_remove(Right, Key, Right_),
    Result = tree_node(NodeKey, NodeValue, Left, Right_).

map_remove(Map, Key, Result) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key = NodeKey, Left = [], !,
    Result = Right.

map_remove(Map, Key, Result) :- Map = tree_node(NodeKey, NodeValue, Left, Right), Key = NodeKey, Left = tree_node(_, _, _, _), Right = [], !,
    Result = Left.

map_remove(Map, Key, Result) :-!,  Map = tree_node(NodeKey, NodeValue, Left, Right), Key = NodeKey, choose_min(Right, MinKey, MinValue),
    Left = tree_node(_, _, _, _), Right = tree_node(_, _, _, _),
    map_remove(Right, MinKey, Right_),
    Result = tree_node(MinKey, MinValue, Left, Right_).

map_lowerKey(Map, Key, LowerKey) :- map_lowerEntry(Map, Key, (LowerKey, _)).

map_lowerValue(Map, Key, LowerValue) :- map_lowerEntry(Map, Key, (_, LowerValue)).

map_lowerEntry(Map, Key, Entry) :- lowerEntry_impl(Map, Key, Entry).

lowerEntry_impl(Map, Key, Entry) :- Map = tree_node(NodeKey, NodeValue, Left, Right), NodeKey < Key, 
    lowerEntry_impl(Right, Key, Entry), !.

lowerEntry_impl(Map, Key, (NodeKey, NodeValue)) :- Map = tree_node(NodeKey, NodeValue, Left, Right), NodeKey < Key, !.

lowerEntry_impl(Map, Key, Entry) :- Map = tree_node(NodeKey, NodeValue, Left, Right), NodeKey >= Key, !, 
    lowerEntry_impl(Left, Key, Entry).
