#!/bin/bash

base="dc=bookshelf,dc=itix,dc=fr"

echo "Extracting level 1..."
egrep '^[0-9]00 ' dewey.txt > dewey1.txt
echo "Extracting level 2..."
egrep '^[0-9][0-9]0 ' dewey.txt > dewey2.txt
echo "Extracting level 3..."
egrep '^[0-9][0-9][1-9] ' dewey.txt > dewey3.txt

n=$(wc -l < dewey.txt)
n1=$(wc -l < dewey1.txt)
n2=$(wc -l < dewey2.txt)
n3=$(wc -l < dewey3.txt)

sum=$(($n1 + $n2 + $n3))

echo "original: $n"
echo "dewey1/2/3: $n1/$n2/$n3"
echo "sum: $sum"

echo "Generating LDAP, level 1..."

sed -r "s/([0-9][0-9][0-9]) (.*)/dn: frItixCategoryId=\\1,$base\\
frItixCategoryId: \\1\\
frItixCategoryName: \\2\\
objectClass: frItixCategory\\
/" dewey1.txt > dewey1.ldif



echo "Generating LDAP, level 2..."

sed -r "s/(([0-9])[0-9][0-9]) (.*)/dn: frItixCategoryId=\\1,frItixCategoryId=\\200,$base\\
frItixCategoryId: \\1\\
frItixCategoryName: \\3\\
objectClass: frItixCategory\\
/" dewey2.txt > dewey2.ldif



echo "Generating LDAP, level 3..."

sed -r "s/((([0-9])[0-9])[0-9]) (.*)/dn: frItixCategoryId=\\1,frItixCategoryId=\\20,frItixCategoryId=\\300,$base\\
frItixCategoryId: \\1\\
frItixCategoryName: \\4\\
objectClass: frItixCategory\\
/" dewey3.txt > dewey3.ldif

