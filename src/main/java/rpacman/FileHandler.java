package rpacman;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

	public ArrayList<String> returnFile(String filename) throws IllegalArgumentException{
		ArrayList<String> rows = new ArrayList<String>();

		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(new File(this.getClass().getResource("/rpacman/levels/" + filename).toURI())));
			String strLine;
			while ((sc.hasNext())) {
				strLine = sc.nextLine();
				rows.add(strLine);
			}

		} catch (FileNotFoundException fnfex) {
			throw new IllegalArgumentException("Nincs meg a fajl");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally{
			sc.close();
		}
		return rows;

	}

}
