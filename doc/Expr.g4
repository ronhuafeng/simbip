/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar Expr;

CT_INT        :       'int';
CT_CHAR       :       'char';
CT_FLOAT:     'float';
CT_DOUBLE
      :       'double';
TRUE : 'true';
FALSE : 'false';
EXTERN  : 'extern';
EXPORT        :       'export';
DEFINE        :       'define';
DATA  :       'data';
PACKAGE :     'package';
END   :       'end';
DOT   :       '.';
USE   :       'use';
AS    :       'as';
SEMICOL       :       ';';
ATOM  :       'atom';
COMPOUND:     'compound';
COMPONENT
      :       'component';
ON    :       'on';
INTERNAL      :                     'internal';
DO    :       'do';
PROVIDED:     'provided';
INITIAL       :       'initial';
PLACE :       'place';
FROM  :       'from';
TO    :       'to';
PRIORITY:     'priority';
CONNECTOR
      :       'connector';
UP_ACTION     :       'up';
DOWN_ACTION   :       'down';
PORT  :       'port';
TYPE  :       'type';
LPAREN        :       '(';
RPAREN        :       ')';
COMMA :       ',';
QUOTE :       '\'';

IF : 'if';
THEN : 'then';
ELSE : 'else';
FI : 'fi';


ID  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT : '0'..'9'+
    ;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

COMMENT
    :  (  '/*' .*? '*/'
    |   '//' ~[\r\n]* ) -> channel(HIDDEN)
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) -> skip
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;


LT_OP         :       '<';
GT_OP   :     '>';
LE_OP :       '<=';
GE_OP :       '>=';
EQ_OP :       '==';
NE_OP :       '!=';
AND_OP        :       '&&';
OR_OP :       '||';


// Rules for action language

do_action
    : (statement)*
    ;

primary_expression
    returns [String tag, Object value]
      : ID  {$tag="Identifier"; $value = $ID.text;}
      | INT {$tag="Integer"; $value=Integer.parseInt($INT.text);}
//      | FLOAT
//      | STRING
      | TRUE {$tag="Boolean"; $value=true;}
      | FALSE {$tag="Boolean"; $value=false;}
      | '(' logical_or_expression ')'
      ;

statement
    returns [String tag, Object value]
      : assignment_expression SEMICOL
      | logical_or_expression SEMICOL
      | if_then_else_expression
      | SEMICOL
      ;

if_then_else_expression
    returns [String tag, Object value]
    : IF '(' logical_or_expression ')' THEN (then_stmts+=statement)+
      (ELSE (else_stmts+=statement )+)?
      FI
    ;

assignment_expression
    returns [String tag, Object value]
      : postfix_expression '=' logical_or_expression
      ;

logical_or_expression
    returns [String tag, Object value]
      : logical_and_expression
        (OR_OP logical_and_expression )*
                      ;

logical_and_expression
    returns [String tag, Object value]
      : inclusive_or_expression
        (AND_OP inclusive_or_expression)*
      ;

inclusive_or_expression
    returns [String tag, Object value]
      :  exclusive_or_expression
         ('|' eox+=exclusive_or_expression)*
      ;

exclusive_or_expression
    returns [String tag, Object value]
      : and_expression
        ('^' and_expression)*
      ;


and_expression
    returns [String tag, Object value]
      : equality_expression
        ('&' equality_expression)*
      ;

equality_expression
    returns [String tag, Object value]
      : relational_expression ((operators+=EQ_OP|operators+=NE_OP) relational_expression)?
      ;

relational_expression
    returns [String tag, Object value]
      : additive_expression 
        ((operators+=LT_OP|operators+=GT_OP|operators+=LE_OP|operators+=GE_OP) additive_expression)?
      ;

additive_expression
    returns [String tag, Object value]
      : subtractive_expression
        ('+'subtractive_expression)*
      ;

subtractive_expression
    returns [String tag, Object value]
  : multiplicative_expression
    ('-'  multiplicative_expression)*
  ;

multiplicative_expression
    returns [String tag, Object value]
      :  unary_expression ((operators+='/'|operators+='%'|operators+='*') unary_expression)*
      ;

unary_expression
    returns [String tag, Object value]
      : op='-' postfix_expression
       | (op='~'| op='!')? postfix_expression
      ;

postfix_expression
    returns [String tag, Object value]
      : primary_expression (
             // '[' logical_or_expression ']'
              //|  '(' ')'
              //|  '(' argument_expression_list ')'
        //      |  
        '.' primary_expression
              )*
      ;

//argument_expression_list
 //     : logical_or_expression (',' logical_or_expression)*
 //     ;