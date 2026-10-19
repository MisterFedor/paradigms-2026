add(A, B, R) :- R is A + B.

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).
