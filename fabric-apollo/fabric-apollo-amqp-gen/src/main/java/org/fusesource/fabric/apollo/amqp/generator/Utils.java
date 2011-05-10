/**
 * Copyright (C) 2010-2011, FuseSource Corp.  All rights reserved.
 *
 *     http://fusesource.com
 *
 * The software in this package is published under the terms of the
 * CDDL license a copy of which has been included with this distribution
 * in the license.txt file.
 */

package org.fusesource.fabric.apollo.amqp.generator;

import org.apache.maven.plugin.logging.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Utils {

    public static final String JAVA_TAB = "    ";
    public static final String NL = "\n";
    public static Log LOG;

    public static final String option(String maybe, String ifNot) {
        if (maybe != null) {
            return maybe;
        }
        return ifNot;
    }

    public static final String toJavaName(String name) {
        StringTokenizer tok = new StringTokenizer(name.trim(), "- ");
        String javaName = "";
        int i = 0;
        while (tok.hasMoreElements()) {
            String token = tok.nextToken();
            if (i > 0) {
                javaName += token.substring(0, 1).toUpperCase();
                javaName += token.substring(1);
            } else {
                javaName += token;
            }
            i++;
        }
        return javaName;
    }

    public static final String toJavaConstant(String name) {
        StringTokenizer tok = new StringTokenizer(name.trim(), "- ");
        String javaName = "";
        int i = 0;
        while (tok.hasMoreElements()) {
            String token = tok.nextToken().toUpperCase();
            if (i > 0) {
                javaName += "_";
            }
            javaName += token;
            i++;
        }
        return javaName;
    }

    public static final String capFirst(String toCap) {
        String ret = "";
        ret += toCap.substring(0, 1).toUpperCase();
        ret += toCap.substring(1);
        return ret;
    }

    public static final String tab(int num) {
        String ret = "";
        for (int i = 0; i < num; i++) {
            ret += JAVA_TAB;
        }
        return ret;
    }

    public static final String padHex(String hex, int count) {
        while (hex.length() < count) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static void writeAutoGeneratedWarning(BufferedWriter writer, int indent) throws IOException {

        writer.write(tab(indent) + "//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//");
        writer.newLine();
        writer.write(tab(indent) + "//!!!!!!!!THIS CLASS IS AUTOGENERATED DO NOT MODIFY DIRECTLY!!!!!!!!!!!!//");
        writer.newLine();
        writer.write(tab(indent) + "//!!!!!!Instead, modify the generator in fabric-apollo-amqp-gen!!!!!!!!!//");
        writer.newLine();
        writer.write(tab(indent) + "//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//");
        writer.newLine();
    }

    public static void writeJavaCopyWrite(BufferedWriter writer) throws IOException {

        writeJavaComment(writer, 0, "Licensed to the Apache Software Foundation (ASF) under one or more", "contributor license agreements.  See the NOTICE file distributed with",
                "his work for additional information regarding copyright ownership.", "The ASF licenses this file to You under the Apache License, Version 2.0",
                "(the \"License\"); you may not use this file except in compliance with", "the License.  You may obtain a copy of the License at", "",
                "     http://www.apache.org/licenses/LICENSE-2.0", "", "Unless required by applicable law or agreed to in writing, software",
                "distributed under the License is distributed on an \"AS IS\" BASIS,", "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.",
                "See the License for the specific language governing permissions and", "limitations under the License.");
    }

    public static void writeJavaComment(BufferedWriter writer, int indent, String... commentLines) throws IOException {
        if (commentLines == null) {
            return;
        }

        writer.write(tab(indent) + "/**");
        writer.newLine();
        for (String s : commentLines) {
            writer.write(tab(indent) + " * " + s);
            writer.newLine();
        }
        writer.write(tab(indent) + " */");
        writer.newLine();
    }

    public static String[] convertToLines(String s, int charsPerLine) {
        LinkedList<String> rc = new LinkedList<String>();
        String line = "";
        StringTokenizer lines = new StringTokenizer(s, "\r\n");
        while (lines.hasMoreElements()) {
            StringTokenizer tok = new StringTokenizer(lines.nextToken(), " ");
            int chars = 0;

            while (tok.hasMoreTokens()) {
                String word = tok.nextToken();
                chars += word.length();
                if (chars > charsPerLine) {
                    if (line.length() == 0) {
                        rc.add(word);
                    } else {
                        rc.add(line);
                        line = word;
                        chars = word.length();
                        continue;
                    }
                }

                if (line.length() > 0) {
                    line += " ";
                }

                line += word;

            }
            if (line.length() > 0) {
                rc.add(line);
            }
            line = "";
        }

        return rc.toArray(new String[] {});
    }

    public static final String javaPackageOf(String fullName) {
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public static final String getBar(int length) {
        String ret = "";
        for ( int i=0; i < length; i++ ) {
            ret += "-";
        }
        return ret;
    }

    public static final String getTitle(int length, String title) {
        title = " " + title + " ";
        String ret = "";
        int barSize = (length / 2) - (title.length() / 2);

        while ( barSize * 2 + title.length() > length ) {
            barSize--;
        }
        while ( barSize * 2 + title.length() < length ) {
            title += "-";
        }

        return getBar(barSize) + title + getBar(barSize);

    }

    public static final String dumpMap(Map map, String title) {
        String formatString = "|%3s|%30s|%30s";
        String header = String.format(formatString, "#", "Key", "Value");
        String ret = Utils.getTitle(header.length(), title);
        ret += "\n" + header + "\n";
        int entry = 0;
        for ( Object key : map.keySet() ) {
            ret += String.format(formatString + "\n", ++entry, key, map.get(key));
        }
        ret += getBar(header.length());

        return ret;
    }

    public static final String appendIfTrue(String format, Object value, boolean append) {
        if ( append && value != null ) {
            return String.format(format, value);
        }
        return "";
    }

    public static final String appendIfNotNull(String format, Object value) {
        if ( value != null ) {
            return String.format(format, value);
        }
        return "";
    }

    public static final List<File> findFiles(File dir) {
        LinkedList<File> rc = new LinkedList<File>();
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                rc.add(file);
            } else {
                rc.addAll(findFiles(file));
            }
        }

        return rc;
    }

}
