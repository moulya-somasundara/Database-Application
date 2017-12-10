package sales;
import java.sql.*;
/*
Data structure to store the data 
*/
class data2 {
	String product, cust;
	int NJ_MAX, NY_MIN, CT_MIN, NJ_day, NJ_month, NJ_year, NY_day, NY_month, NY_year, CT_day, CT_month, CT_year;
};
/*
Author - Moulya
CWID - 10415052
*/

public class Query2 {

	/*Function for setting NJ_MAX, NY_MIN, CT_MIN*/
	public static void min_max(data2[] r, int i, String state, int quant, String day, String month, String year) {
		if (state.equals("NJ")) {
			if (Integer.parseInt(year) < 2009  && r[i].NJ_MAX == -1) {
				r[i].NJ_MAX = quant;
				r[i].NJ_day = Integer.parseInt(day);
				r[i].NJ_month = Integer.parseInt(month);
				r[i].NJ_year = Integer.parseInt(year);
			} else {
				if (Integer.parseInt(year) <  2009  && r[i].NJ_MAX < quant) {
					r[i].NJ_MAX = quant;
					r[i].NJ_day = Integer.parseInt(day);
					r[i].NJ_month = Integer.parseInt(month);
					r[i].NJ_year = Integer.parseInt(year);
				}

			}

		}
		if (state.equals("NY")) {
			if (Integer.parseInt(year) < 2009  && r[i].NY_MIN == -1) {
				r[i].NY_MIN = quant;
				r[i].NY_day = Integer.parseInt(day);
				r[i].NY_month = Integer.parseInt(month);
				r[i].NY_year = Integer.parseInt(year);
			} else {
				if (Integer.parseInt(year) <  2009  && r[i].NY_MIN < quant) {
					r[i].NY_MIN = quant;
					r[i].NY_day = Integer.parseInt(day);
					r[i].NY_month = Integer.parseInt(month);
					r[i].NY_year = Integer.parseInt(year);
				}

			}
		}
		if (state.equals("CT")) {
			if (r[i].CT_MIN == -1) {
				r[i].CT_MIN = quant;
				r[i].CT_day = Integer.parseInt(day);
				r[i].CT_month = Integer.parseInt(month);
				r[i].CT_year = Integer.parseInt(year);
			} else {
				if (r[i].CT_MIN > quant) {
					r[i].CT_MIN = quant;
					r[i].CT_day = Integer.parseInt(day);
					r[i].CT_month = Integer.parseInt(month);
					r[i].CT_year = Integer.parseInt(year);
				}
			}
		}
	}

	/*Function for initializing the data structure with the first tuple*/
	public static data2 init(String state, int quant, String day, String month, String year) {
		data2 r = new data2();
		if (state.equals("NJ")) {
			if (Integer.parseInt(year) < 2009) {
				r.NJ_MAX = quant;
				r.NJ_day = Integer.parseInt(day);
				r.NJ_month = Integer.parseInt(month);
				r.NJ_year = Integer.parseInt(year);
			} else {
				r.NJ_MAX = -1;
				r.NJ_day = 00;
				r.NJ_month = 00;
				r.NJ_year = 0000;
			}
			r.NY_MIN = -1;
			r.CT_MIN = -1;
			r.NY_day = 00;
			r.NY_month = 00;
			r.NY_year = 0000;
			r.CT_day = 00;
			r.CT_month = 00;
			r.CT_year = 0000;
		}
		if (state.equals("NY")) {
			if (Integer.parseInt(year) < 2009) {
			r.NY_MIN = quant;
			r.NY_day = Integer.parseInt(day);
			r.NY_month = Integer.parseInt(month);
			r.NY_year = Integer.parseInt(year);
			}else {
				r.NY_MIN = -1;
				r.NY_day = 00;
				r.NY_month = 00;
				r.NY_year = 0000;
			}
			r.CT_MIN = -1;
			r.NJ_MAX = -1;
			r.CT_day = 00;
			r.CT_month = 00;
			r.CT_year = 00000;
			r.NJ_day = 00;
			r.NJ_month = 00;
			r.NJ_year = 0000;
		}
		if (state.equals("CT")) {
			r.CT_MIN = quant;
			r.CT_day = Integer.parseInt(day);
			r.CT_month = Integer.parseInt(month);
			r.CT_year = Integer.parseInt(year);
			r.NJ_MAX = -1;
			r.NY_MIN = -1;
			r.NJ_day = 00;
			r.NJ_month = 00;
			r.NJ_year = 0000;
			r.NY_day = 00;
			r.NY_month = 00;
			r.NY_year = 0000;
		}
		return r;
	}

	public static void main(String[] args) {
		/*Replace with appropriate username and password*/
		String usr = "postgres";
		String pwd = "hanumanp";
		String url = "jdbc:postgresql://localhost:5432/postgres";

		/*Initializing the data structure*/
		data2[] r = new data2[500];

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
			r[0] = init(rs.getString("state"), Integer.parseInt(rs.getString("quant")), rs.getString("day"),
					rs.getString("month"), rs.getString("year"));
			r[0].product = rs.getString("prod");
			r[0].cust = rs.getString("cust");

			int c = 1;

			while (rs.next()) {
				int flag = 1;
				for (int i = 0; i < c; i++) {
					if (r[i].product.equals(rs.getString("prod")) && r[i].cust.equals(rs.getString("cust"))
							&& !rs.getString("state").equals("PA")) {
						flag = 0;
						min_max(r, i, rs.getString("state"), Integer.parseInt(rs.getString("quant")),
								rs.getString("day"), rs.getString("month"), rs.getString("year"));
					}
				}
				if (flag == 1 && !rs.getString("state").equals("PA")) {
					r[c] = init(rs.getString("state"), Integer.parseInt(rs.getString("quant")), rs.getString("day"),
							rs.getString("month"), rs.getString("year"));
					r[c].product = rs.getString("prod");
					r[c].cust = rs.getString("cust");
					c++;
				}

			}

			System.out.println("CUSTOMER" + "	" + "PRODUCT" + "	      " + "NJ_MAX" + "	" + "DATE" + "	      "
					+ "NY_MIN" + "	" + "DATE" + "	      " + "CT_MIN" + "	" + "DATE");
			System.out.println("========" + "	" + "=======" + "	      " + "======" + "	" + "====" + "	      "
					+ "======" + "	" + "====" + "	      " + "======" + "	" + "====");
			for (int i = 0; i < c; i++) {
				System.out.format("%s		%s		%4s	%02d/%02d/%02d	%4s	%02d/%02d/%02d	%4s	%02d/%02d/%02d", r[i].cust,
						r[i].product, r[i].NJ_MAX, r[i].NJ_month, r[i].NJ_day, r[i].NJ_year, r[i].NY_MIN, r[i].NY_month,
						r[i].NY_day, r[i].NY_year, r[i].CT_MIN, r[i].CT_month, r[i].CT_day, r[i].CT_year);
				System.out.println();
			}

		}

		catch (SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}
