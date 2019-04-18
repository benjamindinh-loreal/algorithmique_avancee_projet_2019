package fr.umlv.info2.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.umlv.info2.graphs.*;

public class Parser {
	
	public static long[][] parseCo(File co) throws FileNotFoundException, IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(co))){
			var handler = new Object() {
				long[][] coordinates ;
			} ;
			reader.lines().forEach((line) -> {
				var token = line.split(" ") ;
				if(token[0].equals("p")) {
					handler.coordinates = new long[Integer.parseInt(token[4])][2] ;
				} else if(token[0].equals("v")) {
					handler.coordinates[Integer.parseInt(token[0])][0] = (long) Math.ceil(Long.parseLong(token[1]) * 1.6) ;
					handler.coordinates[Integer.parseInt(token[0])][1] = (long) Math.ceil(Long.parseLong(token[2]) * 1.6) ;
				}
			}) ;
			return handler.coordinates ;		
		}		
	}

	public static ArrayList<Edge> parseGr(File gr) throws IOException, FileNotFoundException {
		ArrayList<Edge> edges = new ArrayList<>() ;
		try(BufferedReader reader = new BufferedReader(new FileReader(gr))){
			reader.lines().forEach((line) -> {
				var token = line.split(" ") ;
				if(token[0].equals("a")) {
					var start = Integer.parseInt(token[1]) ;
					var end = Integer.parseInt(token[2]) ;
					var value = Integer.parseInt(token[3]) ;
					edges.add(new Edge(start,end,value)) ;
				}
			}) ;
		}
		return edges ;
	}
}
