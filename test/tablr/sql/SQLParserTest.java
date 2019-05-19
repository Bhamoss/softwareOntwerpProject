package tablr.sql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static tablr.sql.SQLParser.parseQuery;

class SQLParserTest {

	@Test
	void test() {
		SQLQuery q1 = parseQuery("SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7");
		SQLQuery q1r = new SQLQuery(
				new Filter(
						new Scan("movies", "movie"),
						new Greater(new CellId("movie","imdb_score"), new IntLiteral(7))
				),
				Collections.singletonList(
						new ColumnSpec(new CellId("movie", "title"), "title")
				)
		);
		assertEquals(q1,q1r);

		SQLQuery q2 = parseQuery("SELECT student.name AS name, student.program AS program" +
				" FROM enrollments AS enrollment INNER JOIN students AS student" +
				" ON enrollment.student_id = student.student_id" +
				" WHERE enrollment.course_id = \"SWOP\"");
		assertEquals(q2, new SQLQuery(
				new Filter(
						new Join(
								new Scan("enrollments", "enrollment"),
								new Scan("students", "student"),
								new CellId("enrollment","student_id"),
								new CellId("student", "student_id")
						),
						new Equals(new CellId("enrollment", "course_id"), new StringLiteral("SWOP"))
				),
				Arrays.asList(
						new ColumnSpec(new CellId("student","name"), "name"),
						new ColumnSpec(new CellId("student", "program"), "program")
				)
		));

		/*
		assertRoundTrip("SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7");
		assertRoundTrip("SELECT student.name AS name, student.program AS program" + 
				" FROM enrollments AS enrollment INNER JOIN students AS student" + 
				" ON enrollment.student_id = student.student_id" + 
				" WHERE enrollment.course_id = \"SWOP\"");
		assertRoundTrip("SELECT parent.name AS parentName, child.name AS childName" + 
				" FROM persons AS parent" + 
				" INNER JOIN is_child_of AS link ON parent.id = link.parent_id" + 
				" INNER JOIN persons AS child ON link.child_id = child.id" +
				" WHERE TRUE");
		System.out.println(parseQuery("SELECT parent.name AS parentName, child.name AS childName" +
				" FROM persons AS parent" +
				" INNER JOIN is_child_of AS link ON parent.id = link.parent_id" +
				" INNER JOIN persons AS child ON link.child_id = child.id" +
				" WHERE TRUE"));
		*/
	}

}
