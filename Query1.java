package sales;
import java.sql.*;
/*
Data structure to store the data 
*/
class data {
	String customer, max_prod, max_state,  
					min_prod, min_state;
	int max, min,j,max_day, max_month, max_year,min_day, min_month, min_year;
	float avg,cnt;
}
/*
Author - Moulya
CWID - 10415052
*/

public class Query1 {

	public static void main(String[] args) {
		/*Replace with appropriate username and password*/
		String usr = "postgres";
		String pwd = "hanumanp";
		String url = "jdbc:postgresql://localhost:5432/postgres";

		/*Initializing the data structure*/ 
		data[] r = new data[500];

		/*Connection to the database*/
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
		}

		catch (Exception e) {
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}

		try {
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");

			rs.next();
			
			/*Initializing the data structure with the first tuple*/
			r[0] = new data();
			r[0].customer = rs.getString("cust");

			r[0].max = Integer.parseInt(rs.getString("quant"));
			r[0].max_prod = rs.getString("prod");
			r[0].max_day = Integer.parseInt(rs.getString("day"));
			r[0].max_month = Integer.parseInt(rs.getString("month"));
			r[0].max_year = Integer.parseInt(rs.getString("year"));
			r[0].max_state = rs.getString("state");

			r[0].min = Integer.parseInt(rs.getString("quant"));
			r[0].min_prod = rs.getString("prod");
			r[0].min_day = Integer.parseInt(rs.getString("day"));
			r[0].min_month = Integer.parseInt(rs.getString("month"));
			r[0].min_year = Integer.parseInt(rs.getString("year"));
			r[0].min_state = rs.getString("state");
			
			r[0].avg = Float.parseFloat(rs.getString("quant"));
			r[0].cnt=1;

			int c = 1; 
			/*Fetching the next tuple while it exists*/
			while (rs.next()) {
				int flag = 0;
				for (int i = 1; i <= c; i++) {
					/*Searching in the data structure if the product exists*/
					if (r[i - 1].customer.equals(rs.getString("cust"))) {
						if (r[i - 1].max < Integer.parseInt(rs.getString("quant"))) {
							r[i - 1].max = Integer.parseInt(rs.getString("quant"));
							r[i - 1].max_prod = rs.getString("prod");
							r[i - 1].max_day = Integer.parseInt(rs.getString("day"));
							r[i - 1].max_month = Integer.parseInt(rs.getString("month"));
							r[i - 1].max_year = Integer.parseInt(rs.getString("year"));
							r[i - 1].max_state = rs.getString("state");
						}
						if (r[i - 1].min > Integer.parseInt(rs.getString("quant"))) {
							r[i - 1].min = Integer.parseInt(rs.getString("quant"));
							r[i - 1].min_prod = rs.getString("prod");
							r[i - 1].min_day = Integer.parseInt(rs.getString("day"));
							r[i - 1].min_month = Integer.parseInt(rs.getString("month"));
							r[i - 1].min_year = Integer.parseInt(rs.getString("year"));
							r[i - 1].min_state = rs.getString("state");
						}
						r[i - 1].avg += Float.parseFloat(rs.getString("quant"));
						r[i - 1].cnt += 1;
						flag = 1;
					}
				}
				if (flag == 0) {
					/*Creating a new entry in the data structure if the product doesn't exist*/
					r[c] = new data();

					r[c].customer = rs.getString("cust");
					r[c].max = Integer.parseInt(rs.getString("quant"));
					r[c].max_prod = rs.getString("prod");
					r[c].max_day = Integer.parseInt(rs.getString("day"));
					r[c].max_month = Integer.parseInt(rs.getString("month"));
					r[c].max_year = Integer.parseInt(rs.getString("year"));
					r[c].max_state = rs.getString("state");

					r[c].min = Integer.parseInt(rs.getString("quant"));
					r[c].min_prod = rs.getString("prod");
					r[c].min_day = Integer.parseInt(rs.getString("day"));
					r[c].min_month = Integer.parseInt(rs.getString("month"));
					r[c].min_year = Integer.parseInt(rs.getString("year"));
					r[c].min_state = rs.getString("state");
					
					r[c].avg += Float.parseFloat(rs.getString("quant"));
					r[c].cnt += 1;
					c++;
				}
			}
			
			System.out.println("CUSTOMER        " + "MIN_Q	" + "MIN_PRODUCT	" + "MIN_DATE" +     "        ST " + "    MAX_Q	"
					+ "MAX_PRODUCT" + "      MAX_DATE" + "      ST" + "        AVG_Q");
			System.out.println("=======        " + "=====	" + "========	" + "==========	" + "== " + "    =====	"
					+ "========	" + "==========	" + "==	" + "========");
			for (int i = 0; i < 10; i++) {
				System.out.format("%s		%4s	%s		%02d/%02d/%02d	%s	%4s	%s		%02d/%02d/%02d	%s	%4.3f", r[i].customer,r[i].min,
						r[i].min_prod, r[i].min_month, r[i].min_day, r[i].min_year, r[i].min_state, r[i].max,
						r[i].max_prod, r[i].max_month, r[i].max_day, r[i].max_year, r[i].max_state,
						r[i].avg/r[i].cnt);
				System.out.println();
			}

		}

		catch (SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}
