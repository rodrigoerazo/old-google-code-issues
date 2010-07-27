package org.sample.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author nmaves
 */
public class DatabaseInitializer {

	public static void init() {
		try {
			Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:contacts", "SA", "");

			PreparedStatement s = c.prepareStatement(
					"CREATE TABLE contact ( " +
					"id       	INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
					"firstName	varchar(25) NULL," +
					"lastName	varchar(25) NULL," +
					"phone	varchar(25) NULL," +
					"email	varchar(25) NULL" +
					")"
					);

			s.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
