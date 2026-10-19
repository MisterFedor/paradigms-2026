divides(D, B) :- R is B mod D, R = 0.

prime(N) :- check_divisors(N).

check_divisors(N) :- SD is 2, check_divisors(N, SD).

check_divisors(N, D) :- D * D > N ; D * D =< N, \+ divides(D, N), DX is D + 1, check_divisors(N, DX).

composite(N) :- SD is 2, composite(N, SD).

composite(N, D) :- N > 1, D * D =< N, divides(D, N); N > 1, D * D =< N, \+ divides(D, N), D1 is D + 1, composite(N, D1).

prime_divisors(N, Divisors) :- N > 0, SD is 2, prime_divisors(N, SD, Divisors).

prime_divisors(1, _, []).

prime_divisors(N, D, [N]) :- N > 1, D * D > N.

prime_divisors(N, D, Divisors) :- D * D =< N, divides(D, N), NX is N // D, Divisors = [D | Rest], prime_divisors(NX, D, Rest);
    D * D =< N,
    \+ divides(D, N),
    DX is D + 1,
    prime_divisors(N, DX, Divisors).

square_divisors(N, Divisors) :- prime_divisors(N, PD), square(PD, Divisors).

square([], []).

square([D | Rest], [D, D | SquareRest]) :- square(Rest, SquareRest).
