# COMP250_Winter2016

## 1. Arbitrary Base Converter

You are given a base B, and the description of a number m
represented in that base as "m=(X.Y)B" where X is the integer part
of m and Y is the fractional part of m. Both parts are provided as
arrays of numbers base B, so X[] is an array of size X.length and
Y[] an array of size Y.length. Each X[i],Y[i] is a number among
{0,1,…,B-1}.
The input is (B,X[],Y[]),R where R is the base in which number
m=(X.Y)B is to be represented. In base R, m will be represented in
the format m=(U.VW)R where U is the integer part of m and VW is
the fractional part of m. The W part is used to represent the
fractional part as an infinitely repeating pattern following a fixed
non-repeating pattern V. We restrict the bases B and R to be 2 ≤
B,R ≤ 60. Note that the length of W is at most Blength(Y).

The output is going to be of the format U[],V[],W[]. Any valid
representation of the input number will be accepted. To simplify
the input/output I have defined Java objects Number that contains a
Base and three arrays U[],V[],W[].

For example, in bases B=10 and R=2, the number 5/2 = (2.5)10
would be represented by (10.1)2 because (101/10)2 yields the
pattern "10.1" when literally dividing (101)2 by (10)2. In the
proposed format we obtain (10.1)2=(U.VW)2 where U=(10)2
V=(1)2 W=(0)2 since indeed (10.1)2 is the same as (10.10)2. The
number 1/5 = (0.2)10 would be represented by (0.0011)2 because
(1/101)2 yields the infinite pattern 0.0011001100110011… In the
proposed format we obtain (0.0011)2=(U.VW)2 where U=(0)2
V=()2 W=(0011)2.

More Details here:
http://crypto.cs.mcgill.ca/~crepeau/COMP250/COMP250-HW1.pdf

## 2. HiRiQ Solver

Description here: http://crypto.cs.mcgill.ca/~crepeau/COMP250/HW4.pdf
