#bash script to calculate kloc
a=0
for i in `find . -name '*.java' -exec wc -l {}  \; | awk '{ print $1 }'`; do a=$((a + i)); done
echo $a
