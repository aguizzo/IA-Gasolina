export CLASSPATH=".:./lib/AIMA.jar:./lib/Gasolina.jar"
rm -f ./src/Main.class ./IAGas/*.class
javac -d . ./src/IAGas/*.java
javac Main.java