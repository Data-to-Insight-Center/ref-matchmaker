# matchmaker
For SEAD 2.0

Rules invokes the following Java methods. The order of rules will have no impacted to the final result, therefore, ease the burden of rule creation/verification.
~~~
restrict()
notAllowed()
preferred()
setWeight()
addWeight()
reduceWeight()
~~~

## Sample 1:
~~~
Repositories: A, B, C, D, and E.
ROs: TBD
Persons: TBD
~~~
The matchmaker makes the following initial candidate list:
~~~
{"A":{"weight":0,"priority":0},"B":{"weight":0,"priority":0},"C":{"weight":0,"priority":0},"D":{"weight":0,"priority":0},"E":{"weight":0,"priority":0}}
~~~
6 sample rules are applied. The final result will not be effected by the order of rules.
~~~
1)	Rule: Add weight to B by 3
Output: {"A":{"weight":0,"priority":0},"B":{"weight":3,"priority":0},"C":{"weight":0,"priority":0},"D":{"weight":0,"priority":0},"E":{"weight":0,"priority":0}}
2) Rule: Reduce weight of B by 1
Output: {"A":{"weight":0,"priority":0},"B":{"weight":2,"priority":0},"C":{"weight":0,"priority":0},"D":{"weight":0,"priority":0},"E":{"weight":0,"priority":0}}
3) Rule: Set weight of C to 10
Output: {"A":{"weight":0,"priority":0},"B":{"weight":2,"priority":0},"C":{"weight":10,"priority":0},"D":{"weight":0,"priority":0},"E":{"weight":0,"priority":0}}
4) Rule: C not allowed
Output: {"A":{"weight":0,"priority":0},"B":{"weight":2,"priority":0},"D":{"weight":0,"priority":0},"E":{"weight":0,"priority":0}}
5) Rule: Restricted to A,B,D,F 
Output: {"A":{"weight":0,"priority":0},"B":{"weight":2,"priority":0},"D":{"weight":0,"priority":0}}
6) Rule: A, and B are preferred 
Output: {"A":{"weight":0,"priority":1},"B":{"weight":2,"priority":1},"D":{"weight":0,"priority":0}}

~~~