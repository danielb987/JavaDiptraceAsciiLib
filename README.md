# JavaDiptraceAsciiLib
A simple java library to parse and generate simple Diptrace ascii files

DipTrace has a special file format, DipTrace ASCII, that is a text file readable by any text editor.
The purpose of this project is to create a simple Java library to read and write those text files
and edit the contents of the file in a simple way.

A main goal is to keep the library simple and easy to use. If fancy features are wanted, it's recommended to place those features in a "DiptraceAdvanced" class or something similar.

Once a class or method is declared public, changes to the interface of that class or method will break existing code. Therefore everything that doesn't need to be public should be private or package private, or in some rare cases protected.

Contributions are welcome! If you have ideas of new features or extensions of the library, it's recommended to start with adding an Issue, explaining the new feature.
