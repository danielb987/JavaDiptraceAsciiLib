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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Analyze the report from CheckStyle.
 */
public class CheckStyleAnalyzer {
    
    /**
     * The file name of the xml file that checkstyle has generated and that
     * this class will analyze.
     */
    private final String fFilename;
    
    /**
     * The file name of the report that this class generates.
     */
    private final String fReportFilename;
    
    
    /**
     * Main method.
     * This method is used to be able to run this class from a script.
     * @param args command line parameters
     */
    //CHECKSTYLE.OFF: MagicNumber - No reason to create a magic number
    public static final void main(final String[] args) {
        if ((args.length == 3)
            && (args[0].toLowerCase().equals("checkstyle"))) {
            new CheckStyleAnalyzer(args[1], args[2]).analyze();
        } else {
            System.err.println("Invalid parameters");
        }
    }
    //CHECKSTYLE.ON: MagicNumber - No reason to create a magic number
    
    
    /**
     * Create an instance.
     * @param aFilename the file name of the file to be analyzed. This file is
     * an xml file that is generated by checkstyle.
     * @param aReportFilename The file name of the report that this class
     * generates. The result file is a html file.
     */
    public CheckStyleAnalyzer(
        final String aFilename,
        final String aReportFilename) {
        
        this.fFilename = aFilename;
        this.fReportFilename = aReportFilename;
    }
    
    
    /**
     * Do the analyze of the file.
     */
    public void analyze() {
        
        try (PrintWriter writer =
            new PrintWriter(
                new OutputStreamWriter(
                    new FileOutputStream(fReportFilename),
                        StandardCharsets.UTF_8))) {
            
            File inputFile = new File(fFilename);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler(writer);
            saxParser.parse(inputFile, userhandler);
            userhandler.createReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Private helper class that receives the events from the SAX parser that
     * parses the xml file.
     */
    private static class UserHandler extends DefaultHandler {
        
        /**
         * The writer that the report is written to.
         */
        private final PrintWriter fWriter;
        
        /**
         * The file name of the file that is currently analyzed in the
         * checkstyle report.
         */
        private String fCurrentFilename = "";
        
        /**
         * The number of errors in the current file in the checkstyle report.
         */
        private AtomicInteger fCurrentFileTotalErrorCount;
        
        /**
         * The total number of files in the checkstyle report.
         */
        private int fNumFiles = 0;
        
        /**
         * The number of files in the checkstyle report that has errors.
         */
        private int fNumFilesWithError = 0;
        
        /**
         * The total number of errors in the checkstyle report.
         */
        private int fNumErrors = 0;
        
        /**
         * true if the current file in the checkstyle report has any errors.
         */
        private boolean fCurrentFileHasErrors = false;
        
        /**
         * A map of the error messages for each error in the checkstyle report.
         */
        private final Map<String, String> fErrorMessages = new HashMap<>();
        
        /**
         * A map of the total number of errors for each error in the checkstyle
         * report.
         */
        private final Map<String, AtomicInteger> fTotalErrorCount =
            new HashMap<>();
        
        /**
         * A map of the total number of errors for each file name in the
         * checkstyle report.
         */
        private final Map<String, AtomicInteger> fFileTotalErrorCount =
            new HashMap<>();
        
        /**
         * A map of a map of the total number of errors for each error for each
         * file name in the
         * checkstyle report.
         */
        private final Map<String, Map<String, AtomicInteger>> fFileErrorCount =
            new HashMap<>();
        
        /**
         * A map of the total number of errors for each error in the the current
         * file in the checkstyle report.
         */
        private Map<String, AtomicInteger> fCurrentFileErrorCount =
            new HashMap<>();
        
        
        /**
         * Create an instance.
         * @param aWriter a writer there the report is written to
         */
        UserHandler(final PrintWriter aWriter) {
            this.fWriter = aWriter;
        }
        
        
        /**
         * Create the report.
         */
        public void createReport() {
            
            Comparator<Map.Entry<String, AtomicInteger>> comparator =
                (   final Map.Entry<String, AtomicInteger> o1,
                    final Map.Entry<String, AtomicInteger> o2) -> {
                    
                    int a = o1.getValue().get();
                    int b = o2.getValue().get();
                    
                    // Sort reverse order
                    if (a > b) {
                        return -1;
                    } else if (a < b) {
                        return 1;
                    } else {
                        return 0;
                    }
            };

            List<Map.Entry<String, AtomicInteger>> sortedTotalErrorCount =
                    new LinkedList<>(fTotalErrorCount.entrySet());
            
            Collections.sort(sortedTotalErrorCount, comparator);
            
            
            List<Map.Entry<String, AtomicInteger>> sortedFileTotalErrorCount =
                    new LinkedList<>(fFileTotalErrorCount.entrySet());
            
            Collections.sort(sortedFileTotalErrorCount, comparator);
            
            fWriter.println("<!DOCTYPE html>");
            fWriter.println("<html dir=\"ltr\" lang=\"en\">");
            fWriter.println("<head>");
            fWriter.println("<meta charset=\"utf-8\" />");
            fWriter.println("<title>Checkstyle report</title>");
            fWriter.println("</head>");
            fWriter.println("<body>");
            
            
            fWriter.format("<p>%d errors in %d files. %d files in total.</p>%n",
                          fNumErrors, fNumFilesWithError, fNumFiles);
            
            System.out.format("%d errors in %d files%n", fNumErrors, fNumFiles);
            
            
            fWriter.println("<table>");
//            writer.format(
//                    "<tr style=\"background-color: coral;\">" +
//                    "<td>%</td><td>%s</td></tr>%n",
//                    item.getValue().get(),
//                    item.getKey());

//            System.out.format("Filename: %s%n", item.getKey());
            
            for (Map.Entry<String, AtomicInteger> subEntry :
                sortedTotalErrorCount) {
                
                fWriter.format("<tr><td>%d</td><td>%s</td></tr>%n",
                              subEntry.getValue().get(),
                              subEntry.getKey());
                System.out.format("Error: %s, count: %d%n",
                                  subEntry.getKey(),
                                  subEntry.getValue().get());
            }
            fWriter.println("</table>");
            
            
            for (Map.Entry<String, AtomicInteger> item :
                sortedFileTotalErrorCount) {
                
                Map<String, AtomicInteger> entry =
                    fFileErrorCount.get(item.getKey());
                fWriter.println("<table>");
                fWriter.format(
                        "<tr style=\"background-color: coral;\">" +
                            "<td>%d</td><td>%s</td></tr>%n",
                        item.getValue().get(),
                        item.getKey());
                
                System.out.format("Filename: %s%n", item.getKey());
                
                for (Map.Entry<String, AtomicInteger> subEntry :
                    entry.entrySet()) {
                    
                    fWriter.format("<tr><td>%d</td><td>%s</td></tr>%n",
                                  subEntry.getValue().get(),
                                  subEntry.getKey());
                    System.out.format("Error: %s, count: %d%n",
                                      subEntry.getKey(),
                                      subEntry.getValue().get());
                }
                fWriter.println("</table>");
            }
            
            fWriter.println("</body>");
            fWriter.println("</html>");
        }
        
        /**
         * Receive notification of the start of an element.
         *
         * @param uri The Namespace URI, or the empty string if the element has
         * no Namespace URI or if Namespace processing is not being performed.
         * @param localName The local name (without prefix), or the empty string
         * if Namespace processing is not being performed.
         * @param qName The qualified name (with prefix), or the empty string if
         * qualified names are not available.
         * @param attributes The attributes attached to the element. If there
         * are no attributes, it shall be an empty Attributes object.
         * @throws SAXException Any SAX exception, possibly wrapping another
         * exception.
         */
        @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "DM_EXIT",
            justification = "We need to be able to abort")
        @Override
        public void startElement(final String uri,
                                 final String localName,
                                 final String qName,
                                 final Attributes attributes)
                throws SAXException {
            
//            System.out.println();
            
//            for (int i=0;  i < attributes.getLength(); i++) {
//                System.out.format("%s: %s: %s%n", qName,
//                        attributes.getQName(i),
//                        attributes.getValue(i));
//            }
            
//            if ("checkstyle".equalsIgnoreCase(qName)) {
//                System.out.format("checkstyle tag%n");
//            }
            if ("file".equalsIgnoreCase(qName)) {
//                System.out.format("file tag%n");
                fNumFiles++;
                fCurrentFileHasErrors = false;
                fCurrentFilename = attributes.getValue("name");
                fCurrentFileErrorCount = new HashMap<>();
                fFileErrorCount.put(fCurrentFilename, fCurrentFileErrorCount);
                fCurrentFileTotalErrorCount = new AtomicInteger(0);
                fFileTotalErrorCount.put(
                    fCurrentFilename,
                    fCurrentFileTotalErrorCount);
            }
            if ("error".equalsIgnoreCase(qName)) {
                fNumErrors++;
                fCurrentFileHasErrors = true;
                String error = attributes.getValue("source");
                String errorMessage = attributes.getValue("message");
                if (error == null) {
                    System.out.println("Error is null");
                    for (int i = 0;  i < attributes.getLength(); i++) {
                        System.out.format("-- %s: %s%n",
                            attributes.getQName(i),
                            attributes.getValue(i));
                    }
                    System.exit(1);
                }
                
                fCurrentFileTotalErrorCount.addAndGet(1);
                
                fErrorMessages.putIfAbsent(error, errorMessage);
                AtomicInteger value =
                    fTotalErrorCount.putIfAbsent(error, new AtomicInteger(1));
                if (value != null) {
                    value.addAndGet(1);
                }
                
//                currentFileErrorCount = new HashMap<>();
                value =
                    fCurrentFileErrorCount.putIfAbsent(
                        error,
                        new AtomicInteger(1));
                
                if (value != null) {
                    value.addAndGet(1);
                }
            }
        }
        
        
        /**
         * Receive notification of the end of an element.
         *
         * @param uri The Namespace URI, or the empty string if the element has
         * no Namespace URI or if Namespace processing is not being performed.
         * @param localName The local name (without prefix), or the empty string
         * if Namespace processing is not being performed.
         * @param qName The qualified name (with prefix), or the empty string if
         * qualified names are not available.
         */
        @Override
        public void endElement(
            final String uri,
            final String localName,
            final String qName)
                throws SAXException {
            
            if ("file".equalsIgnoreCase(qName)) {
                if (fCurrentFileHasErrors) {
                    fNumFilesWithError++;
                }
            }
        }
        
        
        /**
         * Receive notification of character data inside an element.
         * @param ch The characters
         * @param start The start position in the character array
         * @param length The number of characters to use from the character
         * array
         * @throws SAXException Any SAX exception, possibly wrapping another
         * exception
         */
        @Override
        public void characters(
            final char[] ch,
            final int start,
            final int length)
                throws SAXException {
        }
        
    }
    
}
