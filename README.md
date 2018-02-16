# GOTLeastCommonAncestor
Given the Game of Thrones family tree in .dot format and any two characters in the .dot file, this returns the least common ancestor(s) of the characters.

Instructions for compiling from command line: There are 2 ways to do this


(A)Using Maven on Command Line:
1. Make sure maven is installed on your command line using the command : mvn -version 
   or download it from https://maven.apache.org/download.cgi#Installation
2. On the command line, clone the repositor and  navigate to the 'GOTLeastCommonAncestor' project folder
4. Compile the program using the command : **mvn -q compile**
5. Execute the program using the following command:
   **mvn exec:java -Dexec.mainClass="com.gotlca.java.App" -Dexec.args="[filelocation] [Character1] [Character2]"**
   where filelocation,Character1 and Character2 are the required command line parameters
   
   
   
(B)Using the jgrapht .jar files:
1. Find the location where the .jar files associated with the jgrapht library is present. If not present,download from
   https://sourceforge.net/projects/jgrapht/
2. Suppose the location of the .jar files is /Users/abc/jgrapht-1.1.0/lib
3. On the command line, clone the repository and change current directory to 'GOTLeastCommonAncestor' project folder.
4. Again change the current directory to src/main/java
5. Compile the program using the location from step 2 using following command :
   **javac -classpath ".:/Users/abc/jgrapht-1.1.0/lib/*" com/gotlca/java/App.java**
6. Execute the class file using following command:
   **java -classpath ".:/Users/abc/jgrapht-1.1.0/lib/*" com/example/org/App [filelocation] [Character1] [Character2]**
   where filelocation,Character1 and Character2 are the required command line parameters
   
   
   
NOTE: The .dot file required is present in the project folder, so you can use [filelocation] as file.dot
