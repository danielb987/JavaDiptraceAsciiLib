/*
 * The MIT License
 *
 * Copyright 2017 Daniel Bergqvist.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Find text files that has a ascii 0 character.
 * 
 * @author Daniel Bergqvist
 */
public class FindFilesWithNullChar {
    
    
    /**
     * Find text files with ascii 0 character in the current directory and sub directories
     * 
     * @return true if any text file with ascii 0 character is found
     */
    public static boolean find() {
        return find(".");
    }
    
    
    public static boolean find(final String folder) {
        
        final Set<String> fileExtensionsToIgnore = new HashSet<>();
        final Set<String> foldersToIgnore = new HashSet<>();
        
        fileExtensionsToIgnore.add("emojic");
        fileExtensionsToIgnore.add("emojib");
        fileExtensionsToIgnore.add("class");
        fileExtensionsToIgnore.add("jar");
        fileExtensionsToIgnore.add("zip");
        fileExtensionsToIgnore.add("png");
        fileExtensionsToIgnore.add("jpg");
        fileExtensionsToIgnore.add("jpeg");
        fileExtensionsToIgnore.add("gif");
        
        foldersToIgnore.add("./findbugs");
        
        final Flag foundError = new Flag();
        
        File f = new File(folder);
        File[] matchingFiles = f.listFiles((File dir, String name) -> {
//            System.out.format("File: %s, %s%n", name, dir.getAbsolutePath());
            if (name.equals(".") || name.equals("..") || name.equals(".git")) {
                return false;
            }
            
            File file = new File(dir+"/"+name);
            if (file.isDirectory()) {
//                System.out.println("AAAAA: Folder: "+dir+"/"+name);
                
                if (!foldersToIgnore.contains(dir+"/"+name)) {
                    foundError.flag |= find(dir+"/"+name);
                }
                return false;
            }
            
            return !fileExtensionsToIgnore.contains(name.substring(name.lastIndexOf('.')+1));
//            if (filesToIgnore.contains(name))
//                return false;
//            return name.endsWith(".emojic");
//                return name.startsWith("temp") && name.endsWith("txt");
        });
        
        if (matchingFiles == null) {
            return false;
        }
        
        for (File file : matchingFiles) {
            foundError.flag |= scanFile(file.getAbsolutePath());
//            testFile(file.getAbsolutePath());
//            testFile_other(file.getAbsolutePath());
        }
        
        return foundError.flag;
    }
    
    
    private static boolean scanFile(final String filename) {
        
//        System.out.format("File: %s%n", filename);
        boolean foundError = false;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            
            String line;
            int lineNo = 1;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(0) >= 0) {
                    if (! foundError) {
                        System.out.format("File: %s%n", filename);
                    }
//                    String line2 = line.replaceAll("/\\000/", "###");
                    String line2 = line.replaceAll("\\000", "§§§§§");
                    System.out.format("%d: %s%n", lineNo, line2);
                    foundError = true;
                }
                lineNo++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FindFilesWithNullChar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FindFilesWithNullChar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return foundError;
    }
    

    private FindFilesWithNullChar() {
    }
    
    
    
    private static class Flag {
        boolean flag = false;
    }
    
}
